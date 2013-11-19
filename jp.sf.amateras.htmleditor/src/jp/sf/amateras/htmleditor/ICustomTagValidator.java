package jp.sf.amateras.htmleditor;

import java.util.Map;

import jp.aonir.fuzzyxml.FuzzyXMLElement;
import jp.sf.amateras.jspeditor.editors.IJSPValidationMarkerCreator;
import jp.sf.amateras.jspeditor.editors.JSPInfo;

/**
 * An interface to convert taglibs for HTML preview.
 * 
 * @author Naoki Takezoe
 */
public interface ICustomTagValidator {
	
	public void validate(IJSPValidationMarkerCreator creator, 
			Map<String, String> attrs,FuzzyXMLElement element,JSPInfo info);
	
}