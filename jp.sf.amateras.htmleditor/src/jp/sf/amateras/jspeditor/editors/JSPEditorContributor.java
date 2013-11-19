package jp.sf.amateras.jspeditor.editors;

import jp.sf.amateras.htmleditor.editors.HTMLEditorContributor;

public class JSPEditorContributor extends HTMLEditorContributor {
	
	@Override protected void init(){
		super.init();
		contributer.addActionId(JSPSourceEditor.ACTION_JSP_COMMENT);
	}
	
}
