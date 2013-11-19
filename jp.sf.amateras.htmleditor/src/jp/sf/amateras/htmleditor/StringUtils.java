package jp.sf.amateras.htmleditor;

/**
 * Provides utility methods for string operation.
 * 
 * @author Naoki Takezoe
 */
public class StringUtils {
	
	public static boolean isEmpty(String value){
		return value == null || value.length() == 0;
	}
	
	public static boolean isNotEmpty(String value){
		return !isEmpty(value);
	}
}
