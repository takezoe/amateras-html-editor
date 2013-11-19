package jp.sf.amateras.jseditor.editors;

import jp.sf.amateras.htmleditor.editors.HTMLSourceEditorContributer;

public class JavaScriptEditorContributor extends HTMLSourceEditorContributer {

	public JavaScriptEditorContributor(){
		addActionId(JavaScriptEditor.ACTION_COMMENT);
		addActionId(JavaScriptEditor.ACTION_OUTLINE);
	}

}
