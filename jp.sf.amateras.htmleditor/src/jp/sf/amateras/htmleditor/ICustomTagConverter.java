package jp.sf.amateras.htmleditor;

import java.util.Map;

import jp.aonir.fuzzyxml.FuzzyXMLNode;
import jp.sf.amateras.jspeditor.editors.JSPInfo;

/**
 * An interface to convert taglibs for HTML preview.
 * 
 * @author Naoki Takezoe
 */
public interface ICustomTagConverter {
	
	public String process(Map<String, String> attrs,FuzzyXMLNode[] children,
			JSPInfo info, boolean fixPath);
	
}
