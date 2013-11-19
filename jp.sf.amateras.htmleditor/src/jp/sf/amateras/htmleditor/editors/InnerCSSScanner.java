package jp.sf.amateras.htmleditor.editors;

import java.util.ArrayList;
import java.util.List;

import jp.sf.amateras.csseditor.editors.CSSBlockScanner;
import jp.sf.amateras.htmleditor.ColorProvider;
import jp.sf.amateras.htmleditor.HTMLPlugin;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.SingleLineRule;

/**
 * <code>RuleBasedScanner</code> for the inner CSS in the HTML.
 * 
 * @author Naoki Takezoe
 * @see 2.0.3
 */
public class InnerCSSScanner extends CSSBlockScanner {

	public InnerCSSScanner(ColorProvider colorProvider) {
		super(colorProvider);
	}
	
	@Override protected List<IRule> createRules(ColorProvider colorProvider) {
		IToken tag = colorProvider.getToken(HTMLPlugin.PREF_COLOR_TAG);
		IToken comment = colorProvider.getToken(HTMLPlugin.PREF_COLOR_CSSCOMMENT);
		
		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new SingleLineRule("<style", ">", tag));
		rules.add(new SingleLineRule("</style", ">", tag));
		rules.add(new MultiLineRule("/*", "*/", comment));
		rules.addAll(super.createRules(colorProvider));
		
		return rules;
	}
}
