package jp.sf.amateras.csseditor.editors;


/**
 * @author Naoki Takezoe
 */
public class CSSDefinition {
	
	public static final CSSInfo[] CSS_KEYWORDS = {
			new CSSInfo("text-indent"),
			new CSSInfo("text-align"),
			new CSSInfo("text-decoration"),
			new CSSInfo("text-shadow"),
			new CSSInfo("letter-spacing"),
			new CSSInfo("word-spacing"),
			new CSSInfo("text-transform"),
			new CSSInfo("white-space"),
			new CSSInfo("color"),
			new CSSInfo("background-color"),
			new CSSInfo("background-image"),
			new CSSInfo("background-repeat"),
			new CSSInfo("background-attachment"),
			new CSSInfo("background-position"),
			new CSSInfo("background"),
			new CSSInfo("padding-left"),
			new CSSInfo("padding-right"),
			new CSSInfo("padding-top"),
			new CSSInfo("padding-bottom"),
			new CSSInfo("padding"),
			new CSSInfo("border-left"),
			new CSSInfo("border-right"),
			new CSSInfo("border-top"),
			new CSSInfo("border-bottom"),
			new CSSInfo("border"),
			new CSSInfo("margin-left"),
			new CSSInfo("margin-right"),
			new CSSInfo("margin-top"),
			new CSSInfo("margin-bottom"),
			new CSSInfo("margin"),
			new CSSInfo("font-style"),
			new CSSInfo("font-weight"),
			new CSSInfo("font-variant"),
			new CSSInfo("font-stretch"),
			new CSSInfo("font-size-adjust"),
			new CSSInfo("font-size"),
			new CSSInfo("font"),
			new CSSInfo("border-left-width"),
			new CSSInfo("border-right-width"),
			new CSSInfo("border-top-width"),
			new CSSInfo("border-bottom-width"),
			new CSSInfo("border-left-color"),
			new CSSInfo("border-right-color"),
			new CSSInfo("border-top-color"),
			new CSSInfo("border-bottom-color"),
			new CSSInfo("border-left-style"),
			new CSSInfo("border-right-style"),
			new CSSInfo("border-top-style"),
			new CSSInfo("border-bottom-style"),
			new CSSInfo("display"),
			new CSSInfo("position"),
			new CSSInfo("top"),
			new CSSInfo("bottom"),
			new CSSInfo("left"),
			new CSSInfo("right"),
			new CSSInfo("float"),
			new CSSInfo("clear"),
			new CSSInfo("z-index"),
			new CSSInfo("direction"),
			new CSSInfo("unicode-bidi"),
			new CSSInfo("width"),
			new CSSInfo("min-width"),
			new CSSInfo("max-width"),
			new CSSInfo("height"),
			new CSSInfo("min-height"),
			new CSSInfo("max-height"),
			new CSSInfo("line-height"),
			new CSSInfo("vertical-align"),
			new CSSInfo("overflow"),
			new CSSInfo("clip"),
			new CSSInfo("visibility"),
			new CSSInfo("caption-side"),
			new CSSInfo("table-layout"),
			new CSSInfo("border-collapse"),
			new CSSInfo("border-spacing"),
			new CSSInfo("empty-cells"),
			new CSSInfo("content"),
			new CSSInfo("quotes"),
			new CSSInfo("list-style-type"),
			new CSSInfo("list-style-image"),
			new CSSInfo("list-style-position"),
			new CSSInfo("list-style"),
			new CSSInfo("marker-offset"),
			new CSSInfo("cursor"),
			new CSSInfo("outline-width"),
			new CSSInfo("outline-color"),
			new CSSInfo("outline-style"),
			new CSSInfo("outline")
	};
	
//	static {
//		// sort by length
//		Arrays.sort(CSS_KEYWORDS,new Comparator(){
//			public int compare(Object o1, Object o2){
//				CSSInfo info1 = (CSSInfo)o1;
//				CSSInfo info2 = (CSSInfo)o2;
//				if(info1.getReplaceString().length() > info2.getReplaceString().length()){
//					return -1;
//				}
//				if(info1.getReplaceString().length() < info2.getReplaceString().length()){
//					return 1;
//				}
//				return 0;
//			}
//		});
//	}
}
