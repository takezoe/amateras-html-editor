package jp.sf.amateras.htmleditor.editors;

import jp.sf.amateras.htmleditor.ColorProvider;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.WhitespaceRule;

public class HTMLScanner extends RuleBasedScanner {

	public HTMLScanner(ColorProvider colorProvider) {
//		IToken procInstr = colorProvider.getToken(HTMLPlugin.PREF_COLOR_DOCTYPE);
		
		IRule[] rules = new IRule[1];
		rules[0] = new WhitespaceRule(new HTMLWhitespaceDetector());
		
		setRules(rules);
	}
}
