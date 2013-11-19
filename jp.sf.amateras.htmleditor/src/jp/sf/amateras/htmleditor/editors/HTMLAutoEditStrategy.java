package jp.sf.amateras.htmleditor.editors;

import jp.sf.amateras.htmleditor.HTMLPlugin;
import jp.sf.amateras.jseditor.editors.JavaScriptAutoEditStrategy;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;

/**
 * Provdides auto inserting for HTML.
 *
 * @author Naoki Takezoe
 * @since 2.0.3
 */
public class HTMLAutoEditStrategy extends DefaultIndentLineAutoEditStrategy {

	private JavaScriptAutoEditStrategy jsAutoEditStrategy = new JavaScriptAutoEditStrategy();
	private String charset = System.getProperty("file.encoding");
	protected boolean enable;

	public HTMLAutoEditStrategy(){
		IPreferenceStore store = HTMLPlugin.getDefault().getPreferenceStore();
		this.enable = store.getBoolean(HTMLPlugin.PREF_AUTO_EDIT);
	}

	/**
	 * @since 2.0.5
	 */
	public void setEnabled(boolean enable){
		this.enable = enable;
	}

	public void setFile(IFile file){
		try {
			this.charset = file.getCharset();
		} catch(CoreException e){
			HTMLPlugin.logException(e);
		}
	}

	public void customizeDocumentCommand(IDocument d, DocumentCommand c) {

		try {
			if(d.getContentType(c.offset).equals(HTMLPartitionScanner.JAVASCRIPT)){
				jsAutoEditStrategy.customizeDocumentCommand(d, c);
				return;
			}
		} catch(Exception ex){
		}

		if(enable){
			try {
				if("-".equals(c.text) && c.offset >= 3 && d.get(c.offset - 3, 3).equals("<!-")){
					c.text = "-  -->";
					c.shiftsCaret = false;
					c.caretOffset = c.offset + 2;
					c.doit = false;
					return;
				}
				if("[".equals(c.text) && c.offset >= 2 && d.get(c.offset - 2, 2).equals("<!")){
					c.text = "[CDATA[]]>";
					c.shiftsCaret = false;
					c.caretOffset = c.offset + 7;
					c.doit = false;
					return;
				}
				if("l".equals(c.text) && c.offset >= 4 && d.get(c.offset - 4, 4).equals("<?xm")){
					c.text = "l version=\"1.0\" encoding=\"" + charset + "\"?>";
					return;
				}
			} catch (BadLocationException e) {
				HTMLPlugin.logException(e);
			}
		}
		super.customizeDocumentCommand(d, c);
	}

}
