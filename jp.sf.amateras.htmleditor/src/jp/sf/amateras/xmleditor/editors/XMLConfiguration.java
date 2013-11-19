package jp.sf.amateras.xmleditor.editors;

import jp.sf.amateras.htmleditor.ColorProvider;
import jp.sf.amateras.htmleditor.HTMLHyperlinkDetector;
import jp.sf.amateras.htmleditor.assist.HTMLAssistProcessor;
import jp.sf.amateras.htmleditor.editors.HTMLConfiguration;

/**
 * The editor configuration for the <code>XMLEditor</code>.
 * 
 * @author Naoki Takezoe
 * @see jp.sf.amateras.xmleditor.editors.XMLAssistProcessor
 */
public class XMLConfiguration extends HTMLConfiguration {
	
	private ClassNameHyperLinkProvider classNameHyperlinkProvider = null;
	
	public XMLConfiguration(ColorProvider colorProvider) {
		super(colorProvider);
	}
	
	/**
	 * Returns the <code>XMLAssistProcessor</code> as the assist processor.
	 * 
	 * @return the <code>XMLAssistProcessor</code>
	 */
	protected HTMLAssistProcessor createAssistProcessor() {
		return new XMLAssistProcessor();
	}
	
	public ClassNameHyperLinkProvider getClassNameHyperlinkProvider(){
		return this.classNameHyperlinkProvider;
	}
	
	/**
	 * Returns the <code>HTMLHyperlinkDetector</code> which has
	 * <code>ClassNameHyperLinkProvider</code>.
	 * <p>
	 * Provides the classname hyperlink for the following attributes.
	 * <ul>
	 *   <li>type</li>
	 *   <li>class</li>
	 *   <li>classname</li>
	 *   <li>bean</li>
	 *   <li>component</li>
	 * </li>
	 */	
	@Override protected HTMLHyperlinkDetector createHyperlinkDetector() {
		if(this.classNameHyperlinkProvider == null){
			this.classNameHyperlinkProvider = new ClassNameHyperLinkProvider();
		}
		HTMLHyperlinkDetector detector = super.createHyperlinkDetector();
		detector.addHyperlinkProvider(this.classNameHyperlinkProvider);
		return detector;
	}
	
}
