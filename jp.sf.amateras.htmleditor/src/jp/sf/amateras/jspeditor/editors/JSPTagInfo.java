package jp.sf.amateras.jspeditor.editors;

import jp.sf.amateras.htmleditor.assist.TagInfo;

/**
 * 
 * @author Naoki Takezoe
 * @since 2.0.6
 */
public class JSPTagInfo extends TagInfo {
    
    private boolean dynamicAttributes;
    
    public JSPTagInfo(String tagName, boolean hasBody) {
        super(tagName, hasBody);
    }

    public JSPTagInfo(String tagName, boolean hasBody, boolean emptyTag) {
        super(tagName, hasBody, emptyTag);
    }

    public boolean isDynamicAttributes() {
        return dynamicAttributes;
    }

    public void setDynamicAttributes(boolean dynamicAttributes) {
        this.dynamicAttributes = dynamicAttributes;
    }

    @Override public String getDisplayString() {
        if(dynamicAttributes){
            return super.getDisplayString() + " (dynamic-attributes)";
        }
        return super.getDisplayString();
    }

}
