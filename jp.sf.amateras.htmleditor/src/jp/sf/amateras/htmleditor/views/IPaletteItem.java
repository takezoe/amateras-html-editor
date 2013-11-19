package jp.sf.amateras.htmleditor.views;

import jp.sf.amateras.htmleditor.editors.HTMLSourceEditor;

import org.eclipse.jface.resource.ImageDescriptor;

public interface IPaletteItem {
	
	public String getLabel();
	
	public ImageDescriptor getImageDescriptor();
	
	public void execute(HTMLSourceEditor editor);
	
}
