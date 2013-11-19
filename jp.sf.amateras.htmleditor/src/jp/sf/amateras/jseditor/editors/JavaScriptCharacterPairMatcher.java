package jp.sf.amateras.jseditor.editors;

import jp.sf.amateras.htmleditor.editors.AbstractCharacterPairMatcher;

import org.eclipse.jface.text.IDocument;

public class JavaScriptCharacterPairMatcher extends AbstractCharacterPairMatcher { //implements ICharacterPairMatcher {

	public JavaScriptCharacterPairMatcher() {
		addQuoteCharacter('\'');
		addQuoteCharacter('"');
		addBlockCharacter('{', '}');
		addBlockCharacter('(', ')');
		addBlockCharacter('[', ']');
	}

	protected String getSource(IDocument doc){
		return JavaScriptUtil.removeComments(doc.get());
	}

}
