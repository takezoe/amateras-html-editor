package jp.sf.amateras.jspeditor.editors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import jp.sf.amateras.htmleditor.HTMLPlugin;
import jp.sf.amateras.htmleditor.editors.HTMLConfiguration;
import jp.sf.amateras.htmleditor.editors.HTMLEditor;
import jp.sf.amateras.htmleditor.editors.HTMLEditorPart;
import jp.sf.amateras.htmleditor.editors.HTMLSourceEditor;

import org.eclipse.ui.IFileEditorInput;

/**
 * JSP editor.
 */
public class JSPEditor extends HTMLEditor {
	
	private JSPConfiguration configuration;
	
	public JSPEditor() {
		super();
	}
	
	@Override protected HTMLSourceEditor createHTMLSourceEditor(HTMLConfiguration config) {
		return new JSPSourceEditor(config);
	}
	
	@Override protected HTMLConfiguration getSourceViewerConfiguration() {
		if(configuration==null){
			configuration = new JSPConfiguration(HTMLPlugin.getDefault().getColorProvider());
		}
		return configuration;
	}
	
	/**
	 * Update preview.
	 */
	@Override public void updatePreview(){
		if(!(editor instanceof HTMLEditorPart)){
			return;
		}
		try {
			if(!((HTMLEditorPart)editor).isFileEditorInput()){
				return;
			}
			// write to temporary file
			HTMLEditorPart editor = (HTMLEditorPart)this.editor;
			IFileEditorInput input = (IFileEditorInput)this.editor.getEditorInput();
			String charset = input.getFile().getCharset();
			String html    = editor.getSourceEditor().getDocumentProvider().getDocument(input).get();
			// replace JSP parts
			html = JSPPreviewConverter.convertJSP((IFileEditorInput)getEditorInput(),html);
			
			File tmpFile = editor.getSourceEditor().getTempFile();
			FileOutputStream out = new FileOutputStream(tmpFile);
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, charset), true); 
			pw.write(html);
			pw.close();
			
			if(prevTempFile!=null && prevTempFile.equals(tmpFile)){
				editor.getBrowser().refresh();
			} else {
				if(prevTempFile!=null){
					prevTempFile.delete();
				}
				prevTempFile = tmpFile;
				editor.getBrowser().setUrl("file://" + tmpFile.getAbsolutePath()); //$NON-NLS-1$
			}
		} catch(Exception ex){
			HTMLPlugin.logException(ex);
			//ex.printStackTrace();
		}
	}
}
