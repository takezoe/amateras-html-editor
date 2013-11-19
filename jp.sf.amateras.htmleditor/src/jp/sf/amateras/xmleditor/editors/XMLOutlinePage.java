package jp.sf.amateras.xmleditor.editors;

import jp.aonir.fuzzyxml.FuzzyXMLElement;
import jp.aonir.fuzzyxml.FuzzyXMLNode;
import jp.sf.amateras.htmleditor.HTMLPlugin;
import jp.sf.amateras.htmleditor.editors.HTMLOutlinePage;

import org.eclipse.swt.graphics.Image;

/**
 * The content outline page implementation for the <code>XMLEditor</code>.
 * 
 * @author Naoki Takezoe
 */
public class XMLOutlinePage extends HTMLOutlinePage {
	
	public XMLOutlinePage(XMLEditor editor) {
		super(editor);
	}
	
	@Override protected Image getNodeImage(FuzzyXMLNode element){
		if(element instanceof FuzzyXMLElement){
			return HTMLPlugin.getDefault().getImageRegistry().get(HTMLPlugin.ICON_TAG);
		}
		return super.getNodeImage(element);
	}
	
	@Override protected boolean isHTML(){
		return false;
	}
}
