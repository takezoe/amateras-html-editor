package jp.sf.amateras.jspeditor.converters;

import java.util.Map;

import jp.aonir.fuzzyxml.FuzzyXMLNode;
import jp.sf.amateras.jspeditor.editors.JSPInfo;

public class NullConverter extends AbstractCustomTagConverter {
	
	public String process(Map<String, String> attrs, 
			FuzzyXMLNode[] children, JSPInfo info, boolean fixPath) {
		
		return evalBody(children, info, fixPath);
	}
	
}
