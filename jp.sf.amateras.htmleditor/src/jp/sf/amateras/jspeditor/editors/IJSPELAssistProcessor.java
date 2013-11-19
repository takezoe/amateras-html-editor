package jp.sf.amateras.jspeditor.editors;

import jp.aonir.fuzzyxml.FuzzyXMLElement;
import jp.sf.amateras.htmleditor.assist.AssistInfo;

/**
 * 
 * @author Naoki Takezoe
 * @since 2.0.6
 */
public interface IJSPELAssistProcessor {
    
    public AssistInfo[] getCompletionProposals(
            FuzzyXMLElement element, String expression);
    
}
