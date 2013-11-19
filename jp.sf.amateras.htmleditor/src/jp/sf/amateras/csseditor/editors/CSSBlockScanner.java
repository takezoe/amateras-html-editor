package jp.sf.amateras.csseditor.editors;

import java.util.ArrayList;
import java.util.List;

import jp.sf.amateras.htmleditor.ColorProvider;
import jp.sf.amateras.htmleditor.HTMLPlugin;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;

/**
 * @author Naoki Takezoe
 */
public class CSSBlockScanner extends RuleBasedScanner {
	
	public CSSBlockScanner(ColorProvider colorProvider){
		List<IRule> rules = createRules(colorProvider);
		setRules(rules.toArray(new IRule[rules.size()]));
	}
	
	/**
	 * Creates the list of <code>IRule</code>.
	 * If you have to customize rules, override this method.
	 * 
	 * @param colorProvider ColorProvider
	 * @return the list of <code>IRule</code>
	 */
	protected List<IRule> createRules(ColorProvider colorProvider){
		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new CSSRule(
				colorProvider.getToken(HTMLPlugin.PREF_COLOR_CSSPROP), 
				colorProvider.getToken(HTMLPlugin.PREF_COLOR_CSSVALUE)));
		return rules;
	}
	
}
