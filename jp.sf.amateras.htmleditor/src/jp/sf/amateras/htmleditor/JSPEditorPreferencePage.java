package jp.sf.amateras.htmleditor;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @author Naoki Takezoe
 */
public class JSPEditorPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	public JSPEditorPreferencePage() {
		super(GRID); //$NON-NLS-1$
		setPreferenceStore(HTMLPlugin.getDefault().getPreferenceStore());
	}

	public void init(IWorkbench workbench) {
	}
	
	protected void createFieldEditors() {
		setTitle(HTMLPlugin.getResourceString("HTMLEditorPreferencePage.JSP"));
		
		Composite parent = getFieldEditorParent();
		
		addField(new ColorFieldEditor(HTMLPlugin.PREF_JSP_COMMENT,
				HTMLPlugin.getResourceString("HTMLEditorPreferencePage.JSPCommentColor"),
				parent));
		
		addField(new ColorFieldEditor(HTMLPlugin.PREF_JSP_STRING,
				HTMLPlugin.getResourceString("HTMLEditorPreferencePage.JSPStringColor"),
				parent));
		
		addField(new ColorFieldEditor(HTMLPlugin.PREF_JSP_KEYWORD,
				HTMLPlugin.getResourceString("HTMLEditorPreferencePage.JSPKeywordColor"),
				parent));
		
        addField(new ColorFieldEditor(HTMLPlugin.PREF_COLOR_TAGLIB,
                HTMLPlugin.getResourceString("HTMLEditorPreferencePage.JSPTaglibColor"),
                parent));
        
        addField(new ColorFieldEditor(HTMLPlugin.PREF_COLOR_TAGLIB_ATTR,
                HTMLPlugin.getResourceString("HTMLEditorPreferencePage.JSPTaglibAttrColor"),
                parent));
        
		addField(new BooleanFieldEditor(HTMLPlugin.PREF_JSP_FIX_PATH,
				HTMLPlugin.getResourceString("HTMLEditorPreferencePage.JSPFixPath"),
				parent));
	}

}
