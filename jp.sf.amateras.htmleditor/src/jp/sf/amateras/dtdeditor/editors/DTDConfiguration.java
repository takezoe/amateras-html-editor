package jp.sf.amateras.dtdeditor.editors;

import jp.sf.amateras.htmleditor.ColorProvider;
import jp.sf.amateras.htmleditor.HTMLPlugin;
import jp.sf.amateras.htmleditor.assist.HTMLAssistProcessor;
import jp.sf.amateras.htmleditor.editors.HTMLConfiguration;
import jp.sf.amateras.htmleditor.editors.HTMLTagScanner;

/**
 * @author Naoki Takezoe
 */
public class DTDConfiguration extends HTMLConfiguration {
	
	private HTMLTagScanner tagScanner;
	
	public DTDConfiguration(ColorProvider colorProvider) {
		super(colorProvider);
	}
	
	@Override protected HTMLTagScanner getTagScanner() {
		if (tagScanner == null) {
			tagScanner = new DTDTagScanner(getColorProvider());
			tagScanner.setDefaultReturnToken(
					getColorProvider().getToken(HTMLPlugin.PREF_COLOR_TAG));
		}
		return tagScanner;
	}
	
	@Override protected HTMLAssistProcessor createAssistProcessor() {
		return new DTDAssistProcessor();
	}
}
