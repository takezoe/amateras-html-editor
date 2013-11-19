package jp.sf.amateras.csseditor.editors;

import jp.sf.amateras.htmleditor.HTMLUtil;
import jp.sf.amateras.htmleditor.editors.AbstractCharacterPairMatcher;

import org.eclipse.jface.text.IDocument;

/**
 * @author Naoki Takezoe
 */
public class CSSCharacterPairMatcher extends AbstractCharacterPairMatcher {

	public CSSCharacterPairMatcher() {
		addBlockCharacter('{', '}');
	}
	
	@Override protected String getSource(IDocument document){
		return HTMLUtil.cssComment2space(document.get());
	}

}
