package jp.sf.amateras.jseditor.editors;

import jp.sf.amateras.htmleditor.ColorProvider;
import jp.sf.amateras.htmleditor.HTMLPlugin;

import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

/**
 * SourceViewerConfiguration implementation for JavaScriptEditor.
 *
 * @see jp.sf.amateras.jseditor.editors.JavaScriptEditor
 * @author Naoki Takezoe
 */
public class JavaScriptConfiguration extends TextSourceViewerConfiguration {

	private ColorProvider colorProvider;
	private RuleBasedScanner commentScanner;
	private RuleBasedScanner jsdocScanner;
	private RuleBasedScanner defaultScanner;
	private JavaScriptAssistProcessor assistProcessor;
	private JavaScriptJsDocAssistProcessor jsDocAssistProcessor;
	private JavaScriptAutoEditStrategy autoEditStrategy;
	private JavaScriptDoubleClickStrategy doubleClickStrategy;

	public JavaScriptConfiguration(ColorProvider colorProvider){
		this.colorProvider = colorProvider;
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(
			ISourceViewer sourceViewer, String contentType) {
		if(doubleClickStrategy == null){
			doubleClickStrategy = new JavaScriptDoubleClickStrategy();
		}
		return doubleClickStrategy;
	}

	private RuleBasedScanner getCommentScanner(){
		if (commentScanner == null) {
			commentScanner = new RuleBasedScanner();
			commentScanner.setDefaultReturnToken(
					colorProvider.getToken(HTMLPlugin.PREF_COLOR_JSCOMMENT));
		}
		return commentScanner;
	}

	private RuleBasedScanner getJsdocScanner(){
		if (jsdocScanner == null) {
			jsdocScanner = new RuleBasedScanner();
			jsdocScanner.setDefaultReturnToken(
					colorProvider.getToken(HTMLPlugin.PREF_COLOR_JSDOC));
		}
		return jsdocScanner;
	}

	private RuleBasedScanner getDefaultScanner(){
		if (defaultScanner == null) {
			defaultScanner = new JavaScriptScanner(colorProvider);
			defaultScanner.setDefaultReturnToken(
					colorProvider.getToken(HTMLPlugin.PREF_COLOR_FG));
		}
		return defaultScanner;
	}

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			JavaScriptPartitionScanner.JS_COMMENT,
			JavaScriptPartitionScanner.JS_JSDOC};
	}

	public JavaScriptAssistProcessor getAssistProcessor(){
		if(assistProcessor==null){
			assistProcessor = new JavaScriptAssistProcessor();
		}
		return assistProcessor;
	}
	
	public JavaScriptJsDocAssistProcessor getJsDocAssistProcessor(){
		if(jsDocAssistProcessor==null){
			jsDocAssistProcessor = new JavaScriptJsDocAssistProcessor();
		}
		return jsDocAssistProcessor;
	}
	
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant = new ContentAssistant();
		assistant.enableAutoInsert(true);
		assistant.setContentAssistProcessor(getAssistProcessor(),IDocument.DEFAULT_CONTENT_TYPE);
		assistant.setContentAssistProcessor(getJsDocAssistProcessor(),JavaScriptPartitionScanner.JS_JSDOC);
	    assistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));
		assistant.install(sourceViewer);

		return assistant;
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr = null;

		dr = new DefaultDamagerRepairer(getDefaultScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		dr = new DefaultDamagerRepairer(getCommentScanner());
		reconciler.setDamager(dr, JavaScriptPartitionScanner.JS_COMMENT);
		reconciler.setRepairer(dr, JavaScriptPartitionScanner.JS_COMMENT);

		dr = new DefaultDamagerRepairer(getJsdocScanner());
		reconciler.setDamager(dr, JavaScriptPartitionScanner.JS_JSDOC);
		reconciler.setRepairer(dr, JavaScriptPartitionScanner.JS_JSDOC);

		return reconciler;
	}

	/**
	 * @since 2.0.5
	 */
	public JavaScriptAutoEditStrategy getAutoEditStrategy(){
		if(autoEditStrategy==null){
			autoEditStrategy = new JavaScriptAutoEditStrategy();
		}
		return autoEditStrategy;
	}

	/**
	 * @since 2.0.3
	 */
	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer, String contentType) {
		return new IAutoEditStrategy[]{ getAutoEditStrategy() };
	}

}
