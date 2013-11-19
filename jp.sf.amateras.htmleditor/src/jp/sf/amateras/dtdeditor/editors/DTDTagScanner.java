package jp.sf.amateras.dtdeditor.editors;

import jp.sf.amateras.htmleditor.ColorProvider;
import jp.sf.amateras.htmleditor.HTMLPlugin;
import jp.sf.amateras.htmleditor.editors.HTMLTagScanner;
import jp.sf.amateras.htmleditor.editors.HTMLWhitespaceDetector;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.WhitespaceRule;

/**
 * @author Naoki Takezoe
 */
public class DTDTagScanner extends HTMLTagScanner {

	public DTDTagScanner(ColorProvider colorProvider) {
		super(colorProvider, false);
		
		IToken string = colorProvider.getToken(HTMLPlugin.PREF_COLOR_STRING);
		
		IRule[] rules = new IRule[3];
		rules[0] = new MultiLineRule("\"" , "\"" , string, '\\');
		rules[1] = new MultiLineRule("(" , ")" , string);
		rules[2] = new WhitespaceRule(new HTMLWhitespaceDetector());
		
		setRules(rules);
	}
	
}
