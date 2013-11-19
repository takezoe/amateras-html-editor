package jp.sf.amateras.htmleditor.gefutils;

import jp.sf.amateras.htmleditor.HTMLUtil;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

/**
 * This is PropertyDescriptor which can input a class name directry
 * and select from JDT class selection dialog.
 * 
 * @author takezoe
 */
public class ClassSelectPropertyDescriptor extends AbstractDialogPropertyDescriptor {

	/**
	 * @param id
	 * @param displayName
	 */
	public ClassSelectPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
	}
	
	protected Object openDialogBox(Object obj, Control cellEditorWindow) {
		IEditorPart editorPart = HTMLUtil.getActiveEditor();
		
		IFileEditorInput input = (IFileEditorInput)editorPart.getEditorInput();
		IJavaProject project = JavaCore.create(input.getFile().getProject());
		
		return HTMLUtil.openTypeSelectDialog(project, cellEditorWindow);
	}
}
