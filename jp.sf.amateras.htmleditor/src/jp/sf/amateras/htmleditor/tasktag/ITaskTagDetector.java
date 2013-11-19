package jp.sf.amateras.htmleditor.tasktag;

import org.eclipse.core.resources.IFile;

/**
 * The interface for TaskTag detectors.
 * 
 * @author Naoki Takezoe
 * @see jp.sf.amateras.htmleditor.tasktag.TaskTag
 * @see jp.sf.amateras.htmleditor.HTMLProjectBuilder
 */
public interface ITaskTagDetector {
	
	/**
	 * If this detector supports the specified file,
	 * return <code>true</code>. Otherwise return <code>false</code>.
	 * 
	 * @param file the target file
	 * @return <code>true</code> or <code>false</code>
	 */
	public boolean isSupported(IFile file);
	
	/**
	 * Detects TaskTags.
	 * 
	 * @param file the target file
	 */
	public void detect(IFile file, TaskTag[] tags) throws Exception;
	
}
