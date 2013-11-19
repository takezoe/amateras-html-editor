package jp.sf.amateras.htmleditor.gefutils;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 
 * 
 * @since 2.0.5
 * @author Naoki Takezoe
 * @see JarAcceptor
 */
public interface IJarVisitor {
	
	/**
	 * Visit the entry of JAR files.
	 * 
	 * @param file the jar file
	 * @param entry the entry
	 * @return
	 * <ul>
	 *   <li><code>null</code> - continue visiting</li>
	 *   <li>not <code>null</code> - stop visiting and 
	 *   <code>JarAcceptor</code> returns this value</li>
	 * </ul>
	 * @throws Exception Any error occured
	 */
	public Object visit(ZipFile file, ZipEntry entry) throws Exception;
	
}
