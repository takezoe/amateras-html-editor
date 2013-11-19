package jp.sf.amateras.xmleditor.editors;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;

import jp.aonir.fuzzyxml.FuzzyXMLDocument;
import jp.aonir.fuzzyxml.FuzzyXMLElement;
import jp.aonir.fuzzyxml.FuzzyXMLNode;
import jp.aonir.fuzzyxml.FuzzyXMLParser;
import jp.sf.amateras.htmleditor.HTMLPlugin;
import jp.sf.amateras.htmleditor.HTMLProjectParams;
import jp.sf.amateras.htmleditor.HTMLUtil;
import jp.sf.amateras.htmleditor.editors.HTMLConfiguration;
import jp.sf.amateras.htmleditor.editors.HTMLSourceEditor;
import jp.sf.amateras.htmleditor.editors.IHTMLOutlinePage;

import org.apache.xerces.dom.CoreDOMImplementationImpl;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.parsers.SAXParser;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

/**
 * The XML editor.
 *
 * @author Naoki Takezoe
 */
public class XMLEditor extends HTMLSourceEditor {

	private List<IDTDResolver> resolvers = new ArrayList<IDTDResolver>();

	public static final String GROUP_XML = "_xml";
	public static final String ACTION_GEN_DTD = "_generate_dtd";
	public static final String ACTION_GEN_XSD = "_generate_xsd";
	public static final String ACTION_ESCAPE_XML = "_escape_xml";
//	public static final String ACTION_XPATH = "_search_xpath";
	public static final String ACTION_FORMAT_XML = "_format_xml";

	private String[] classNameAttributes = null;
	private List<ElementSchemaMapping> schemaMappings = null;

	/**
	 * The constructor.
	 */
	public XMLEditor() {
		this(new XMLConfiguration(HTMLPlugin.getDefault().getColorProvider()));
	}

	/**
	 * The constructor for customize this editor.
	 * <p>
	 * This editor is initialized with the given <code>XMLConfiguration</code>.
	 *
	 * @param config the editor configuration for this editor
	 */
	public XMLEditor(XMLConfiguration config){
		super(config);
		setEditorContextMenuId("#AmaterasXMLEditor");
	}

	@Override protected void createActions() {
	    super.createActions();

		setAction(ACTION_GEN_DTD,new GenerateDTDAction());
		setAction(ACTION_GEN_XSD,new GenerateXSDAction());
		setAction(ACTION_ESCAPE_XML, new EscapeXMLAction());
//		setAction(ACTION_XPATH, new SearchXPathAction());
		setAction(ACTION_FORMAT_XML, new FormatXMLAction());
	}

	/** This method is called when configuration is changed. */
	@Override protected void handlePreferenceStoreChanged(PropertyChangeEvent event){
		super.handlePreferenceStoreChanged(event);
		classNameAttributes = null;
		schemaMappings = null;
	}

	public List<ElementSchemaMapping> getSchemaMappings(){
		if(schemaMappings==null){
			schemaMappings = ElementSchemaMapping.loadFromPreference();
		}
		return schemaMappings;
	}

	public String[] getClassNameAttributes(){
		if(classNameAttributes==null){
			// Load classname attrs from the preference store
			IPreferenceStore store = getPreferenceStore();
			if(store.getBoolean(HTMLPlugin.PREF_ENABLE_CLASSNAME)){
				classNameAttributes = StringConverter.asArray(
						store.getString(HTMLPlugin.PREF_CLASSNAME_ATTRS));
			} else {
				classNameAttributes = new String[0];
			}
		}
		return classNameAttributes;
	}

	/**
	 * Returns the <code>XMLOutlinePage</code>.
	 *
	 * @see XMLOutlinePage
	 */
	@Override protected IHTMLOutlinePage createOutlinePage(){
		return new XMLOutlinePage(this);
	}

	/**
	 * Adds <code>IDTDResolver</code>.
	 *
	 * @param resolver IDTDResolver
	 */
	public void addDTDResolver(IDTDResolver resolver){
		resolvers.add(resolver);
	}

	/**
	 * Returns an array of <code>IDTDResolver</code>
	 * that was added by <code>addEntityResolver()</code>.
	 *
	 * @return an array of <code>IDTDResolver</code>
	 */
	public IDTDResolver[] getDTDResolvers(){
		return (IDTDResolver[])resolvers.toArray(new IDTDResolver[resolvers.size()]);
	}

	/**
	 * Validates the XML document.
	 * <p>
	 * If <code>getValidation()</code> returns <code>false</code>,
	 * this method do nothing.
	 */
	@Override
	protected void doValidate(){
		new Job("XML Validation"){
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					IFileEditorInput input = (IFileEditorInput)getEditorInput();
					String xml = getDocumentProvider().getDocument(input).get();
					IFile resource = input.getFile();
					//String charset = resource.getCharset();
					//charset = "Shift_JIS";
					resource.deleteMarkers(IMarker.PROBLEM,false,0);

					HTMLProjectParams params = new HTMLProjectParams(resource.getProject());
					if(!params.getValidateXML()){
						return Status.OK_STATUS;
					}

					if(params.getUseDTD()==false){
						// remove DOCTYPE decl
						Matcher matcher = patternDoctypePublic.matcher(xml);
						if(matcher.find()){
							xml = removeMatched(xml,matcher.start(),matcher.end());
						}
						matcher = patternDoctypeSystem.matcher(xml);
						if(matcher.find()){
							xml = removeMatched(xml,matcher.start(),matcher.end());
						}
					}

					SAXParser parser = new SAXParser();

					String   dtd = getDTD(xml, false);
					String[] xsd = getXSD(xml, false);

					// Validation configuration
					if((dtd==null && xsd==null) || !params.getUseDTD()){
						parser.setFeature("http://xml.org/sax/features/validation", false);
					} else {
						parser.setFeature("http://xml.org/sax/features/validation", true);
						parser.setFeature("http://apache.org/xml/features/continue-after-fatal-error", true);
					}
					if(xsd!=null && params.getUseDTD()){
//							parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
//							parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", xsd);
						parser.setFeature("http://apache.org/xml/features/validation/schema", true);
						parser.setFeature("http://xml.org/sax/features/namespaces", true);
					}

					parser.setFeature("http://xml.org/sax/features/use-entity-resolver2", true);
					parser.setEntityResolver(new DTDResolver(getDTDResolvers(),
							input.getFile().getLocation().makeAbsolute().toFile().getParentFile()));
					parser.setErrorHandler(new XMLValidationHandler(resource));

					parser.parse(new InputSource(new StringReader(xml))); //new ByteArrayInputStream(xml.getBytes(charset))));

				} catch(Exception ex){
					// ignore
					//HTMLPlugin.logException(ex);
				}

				return Status.OK_STATUS;
			}
		}.schedule();
	}

	/** replace to whitespaces */
	private String removeMatched(String source,int start,int end){
		StringBuffer sb = new StringBuffer();
		sb.append(source.substring(0,start));
		for(int i=start;i<end + 1;i++){
			char c = source.charAt(i);
			if(c=='\r' || c=='\n'){
				sb.append(c);
			} else {
				sb.append(" ");
			}
		}
		sb.append(source.substring(end+1,source.length()));
		return sb.toString();
	}

	/**
	 * Returns URI of DTD (SystemID) which is used in the document.
	 * If any DTD isn't used, this method returns <code>null</code>.
	 *
	 * @param xml XML
	 * @return URL of DTD
	 */
	public String getDTD(String xml, boolean useElementMapping){
		// PUBLIC Identifier
		Matcher matcher = patternDoctypePublic.matcher(xml);
		if(matcher.find()){
			return matcher.group(2);
		}
		// SYSTEM Identifier
		matcher = patternDoctypeSystem.matcher(xml);
		if(matcher.find()){
			return matcher.group(1);
		}

		// Root element mappings
		if(useElementMapping){
			String firstTag = getFirstTag(xml);
			if(firstTag!=null){
				for(ElementSchemaMapping mapping: getSchemaMappings()){
					if(mapping.getRootElement().equals(firstTag) && mapping.getFilePath().endsWith(".dtd")){
						return "file:" + mapping.getFilePath();
					}
				}
			}
		}

		return null;
	}

	/**
	 * Returns URI (schema location) of XML schema which is used in the document.
	 * If any XML schema isn't used, this method returns <code>null</code>.
	 *
	 * @param xml XML
	 * @return URL of XML schema
	 */
	public String[] getXSD(String xml, boolean useElementMapping){
		// PUBLIC Identifier
		Matcher matcher = patternNsXSD.matcher(xml);
		if(matcher.find()){
			String matched = matcher.group(1).trim();
			matched.replaceAll("\r\n","\n");
			matched.replaceAll("\r","\n");
			String[] xsd = matched.split("\n| |\t");
			for(int i=0;i<xsd.length;i++){
				xsd[i] = xsd[i].trim();
			}
			return xsd;
		}
		matcher = patternNoNsXSD.matcher(xml);
		if(matcher.find()){
			return new String[]{matcher.group(1).trim()};
		}

		matcher = patternXmlns.matcher(xml);
		if(matcher.find()){
			return new String[]{matcher.group(1).trim()};
		}

		// Root element mappings
		if(useElementMapping){
			String firstTag = getFirstTag(xml);
			if(firstTag!=null){
				for(ElementSchemaMapping mapping: getSchemaMappings()){
					if(mapping.getRootElement().equals(firstTag) && mapping.getFilePath().endsWith(".xsd")){
						return new String[]{ "file:" + mapping.getFilePath() };
					}
				}
			}
		}

		return null;
	}

	/**
	 * Extracts the first element name in the given xml source.
	 */
	private static String getFirstTag(String xml){
		FuzzyXMLDocument doc = new FuzzyXMLParser().parse(xml);
		FuzzyXMLNode[] nodes = doc.getDocumentElement().getChildren();
		for(int i=0;i<nodes.length;i++){
			if(nodes[i] instanceof FuzzyXMLElement){
				return ((FuzzyXMLElement)nodes[i]).getName();
			}
		}
		return null;
	}

	/** Reular expressions to get DOCTYPE declaration */
	private Pattern patternDoctypePublic
		= Pattern.compile("<!DOCTYPE[\\s\r\n]+?[^<]+?[\\s\r\n]+?PUBLIC[\\s\r\n]*?\"(.+?)\"[\\s\r\n]*?\"(.+?)\".*?>",Pattern.DOTALL);
	private Pattern patternDoctypeSystem
		= Pattern.compile("<!DOCTYPE[\\s\r\n]+?[^<]+?[\\s\r\n]+?SYSTEM[\\s\r\n]*?\"(.+?)\".*?>",Pattern.DOTALL);

	/** Reular expressions to get schema location of XMLschema */
	private Pattern patternNsXSD
		= Pattern.compile("schemaLocation[\\s\r\n]*?=[\\s\r\n]*?\"(.+?)\"",Pattern.DOTALL);
	private Pattern patternNoNsXSD
		= Pattern.compile("noNamespaceSchemaLocation[\\s\r\n]*?=[\\s\r\n]*?\"(.+?)\"",Pattern.DOTALL);
	private Pattern patternXmlns
		= Pattern.compile("xmlns[\\s\r\n]*?=[\\s\r\n]*?\"(.+?)\"",Pattern.DOTALL);

	/**
	 * Update informations about code-completion.
	 */
	@Override
	protected void updateAssist(){
		XMLConfiguration config = (XMLConfiguration)getSourceViewerConfiguration();
		config.getClassNameHyperlinkProvider().setEditor(this);

		if(!isFileEditorInput()){
			return;
		}
		super.updateAssist();

		new Job("Update Content Assist Information"){
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {

					IFileEditorInput input = (IFileEditorInput)getEditorInput();
					HTMLProjectParams params = new HTMLProjectParams(input.getFile().getProject());
					if(params.getUseDTD()==false){
						return Status.OK_STATUS;
					}

					String xml = getDocumentProvider().getDocument(input).get();

					// Update DTD based completion information.
					String dtd = getDTD(xml, true);
					if(dtd!=null){
						DTDResolver resolver = new DTDResolver(getDTDResolvers(),
								input.getFile().getLocation().makeAbsolute().toFile().getParentFile());
						InputStream in = resolver.getInputStream(dtd);
						if(in!=null){
							Reader reader = new InputStreamReader(in);
							// update AssistProcessor
							XMLAssistProcessor assistProcessor =
								(XMLAssistProcessor)((HTMLConfiguration)getSourceViewerConfiguration()).getAssistProcessor();
							assistProcessor.updateDTDInfo(reader);
							reader.close();
						}
					}

					// Update XML Schema based completion information.
					String[] xsd = getXSD(xml, true);
					if(xsd!=null){
						DTDResolver resolver = new DTDResolver(getDTDResolvers(),
								input.getFile().getLocation().makeAbsolute().toFile().getParentFile());
						for(int i=0;i<xsd.length;i++){
							InputStream in = resolver.getInputStream(xsd[i]);
							if(in!=null){
								Reader reader = new InputStreamReader(in);
								// update AssistProcessor
								XMLAssistProcessor assistProcessor =
									(XMLAssistProcessor)((HTMLConfiguration)getSourceViewerConfiguration()).getAssistProcessor();
								assistProcessor.updateXSDInfo(xsd[i],reader);
								reader.close();
							}
						}
					}
				} catch(Exception ex){
					HTMLPlugin.logException(ex);
				}

				return Status.OK_STATUS;
			}
		}.schedule();
	}

	@Override protected void addContextMenuActions(IMenuManager menu){
		menu.add(new Separator(GROUP_HTML));
		addAction(menu,GROUP_HTML,ACTION_SEARCH_XPATH);
		addAction(menu,GROUP_HTML,ACTION_ESCAPE_XML);
		addAction(menu,GROUP_HTML,ACTION_COMMENT);
		addAction(menu,GROUP_HTML,ACTION_FORMAT_XML);

		menu.add(new Separator(GROUP_XML));
		addAction(menu,GROUP_XML,ACTION_GEN_DTD);
		addAction(menu,GROUP_XML,ACTION_GEN_XSD);
//		addAction(menu, GROUP_XML, ACTION_XPATH);
	}

	@Override protected void updateSelectionDependentActions() {
		super.updateSelectionDependentActions();
		ITextSelection sel = (ITextSelection)getSelectionProvider().getSelection();
		if(sel.getText().equals("")){
			getAction(ACTION_ESCAPE_XML).setEnabled(false);
		} else {
			getAction(ACTION_ESCAPE_XML).setEnabled(true);
		}
	}

	////////////////////////////////////////////////////////////////////////////
	// actions
	////////////////////////////////////////////////////////////////////////////
	private class FormatXMLAction extends Action {
		public FormatXMLAction(){
			super(HTMLPlugin.getResourceString("HTMLEditor.Format"));
		}

		@Override public void run(){
			try {
				IDocument doc = getDocumentProvider().getDocument(getEditorInput());
				InputSource source = new InputSource(new StringReader(doc.get()));

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				Document document = factory.newDocumentBuilder().parse(source);

				CoreDocumentImpl coreDocumentImpl = new CoreDocumentImpl();
				DOMImplementation implementation = coreDocumentImpl.getImplementation();
				CoreDOMImplementationImpl impl2 = (CoreDOMImplementationImpl) implementation;
				LSSerializer serializer = impl2.createLSSerializer();

				String charset = System.getProperty("file.encoding");
				IEditorInput input = getEditorInput();
				if(input instanceof IFileEditorInput){
					IFile file = ((IFileEditorInput) input).getFile();
					charset = file.getCharset();
				}

				String xml = serializer.writeToString(document);
				xml = xml.replaceFirst("\"UTF-16\"", "\"" + charset + "\"");

				doc.set(xml);

			} catch(Exception ex){
				HTMLPlugin.openAlertDialog(ex.toString());
			}
		}
	}

//	private class SearchXPathAction extends Action {
//		public SearchXPathAction(){
//			super(HTMLPlugin.getResourceString("XMLEditor.XPathSearch"));
//		}
//
//		@Override public void run(){
//			SearchXPathDialog dialog = new SearchXPathDialog(
//					getEditorSite().getShell(), XMLEditor.this);
//			dialog.open();
//		}
//	}

	/**
	 * The action to escape XML special chars in the selection.
	 */
	private class EscapeXMLAction extends Action {

		public EscapeXMLAction(){
			super(HTMLPlugin.getResourceString("HTMLEditor.EscapeAction"));
			setEnabled(false);
			setAccelerator(SWT.CTRL | '\\');
		}

		@Override public void run() {
			ITextSelection sel = (ITextSelection)getSelectionProvider().getSelection();
			IDocument doc = getDocumentProvider().getDocument(getEditorInput());
			try {
				doc.replace(sel.getOffset(),sel.getLength(),HTMLUtil.escapeXML(sel.getText()));
			} catch (BadLocationException e) {
				HTMLPlugin.logException(e);
			}
		}
	}

	/**
	 * The action to generate DTD from XML.
	 */
	private class GenerateDTDAction extends Action {
		public GenerateDTDAction(){
			super(HTMLPlugin.getResourceString("XMLEditor.GenerateDTD"),
					HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_DTD));
		}
		@Override public void run() {
			FileDialog dialog = new FileDialog(getViewer().getTextWidget().getShell(),SWT.SAVE);
			dialog.setFilterExtensions(new String[]{"*.dtd"});
			String file = dialog.open();
			if(file!=null){
				try {
					SchemaGenerator.generateDTDFromXML(getFile(), new File(file));
				} catch(Exception ex){
					HTMLPlugin.openAlertDialog(ex.toString());
				}
			}
		}
	}

	/**
	 * The action to generate XML schema from XML.
	 */
	private class GenerateXSDAction extends Action {
		public GenerateXSDAction(){
			super(HTMLPlugin.getResourceString("XMLEditor.GenerateXSD"),
					HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_XSD));
		}
		@Override public void run() {
			FileDialog dialog = new FileDialog(getViewer().getTextWidget().getShell(),SWT.SAVE);
			dialog.setFilterExtensions(new String[]{"*.xsd"});
			String file = dialog.open();
			if(file!=null){
				try {
					SchemaGenerator.generateXSDFromXML(getFile(), new File(file));
				} catch(Exception ex){
					HTMLPlugin.openAlertDialog(ex.toString());
				}
			}
		}
	}

}
