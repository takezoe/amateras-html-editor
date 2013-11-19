package jp.sf.amateras.htmleditor;

import jp.sf.amateras.htmleditor.assist.AssistInfo;

import org.eclipse.core.resources.IFile;

public interface IFileAssistProcessor {
	
	public void reload(IFile file);
	
	public AssistInfo[] getAssistInfo(String value);
	
}
