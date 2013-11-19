package jp.sf.amateras.htmleditor.editors;

import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public interface IHTMLOutlinePage extends IContentOutlinePage {

	public void update();

	public void setSelection(int offset);

}
