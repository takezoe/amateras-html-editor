package jp.sf.amateras.jseditor.editors.model;

/**
 * The model for the JavaScript comment.
 * 
 * @author Naoki Takezoe
 * @author shinsuke
 */
public class JavaScriptComment {
	private int start;
	private int end;
	private String text;

	public JavaScriptComment(int start, int end, String text) {
		this.start = start;
		this.end = end;
		this.text = text;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public String getText() {
		return text;
	}

	public String toString() {
		return getText();
	}
}
