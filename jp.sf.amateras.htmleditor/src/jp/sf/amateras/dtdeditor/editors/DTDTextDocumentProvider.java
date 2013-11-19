package jp.sf.amateras.dtdeditor.editors;

import jp.sf.amateras.htmleditor.editors.HTMLPartitionScanner;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;

/**
 * 
 * @author Naoki Takezoe
 */
public class DTDTextDocumentProvider extends TextFileDocumentProvider {
	
	@Override protected FileInfo createFileInfo(Object element) throws CoreException {
		FileInfo info = super.createFileInfo(element);
		if(info==null){
			info = createEmptyFileInfo();
		}
		IDocument document = info.fTextFileBuffer.getDocument();
		if (document != null) {
			IDocumentPartitioner partitioner =
				new FastPartitioner(
					new DTDPartitionScanner(),
					new String[] {
						HTMLPartitionScanner.HTML_TAG,
						HTMLPartitionScanner.HTML_COMMENT});
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return info;
	}
	
}
