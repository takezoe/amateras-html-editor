package jp.sf.amateras.htmleditor.editors;

public class HTMLHyperlinkInfo {
	
	private Object obj;
	private int offset;
	private int length;
	private int targetOffset = -1;
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	public Object getObject() {
		return obj;
	}

	public void setObject(Object obj) {
		this.obj = obj;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getTargetOffset() {
		return targetOffset;
	}
	
	public void setTargetOffset(int targetOffset) {
		this.targetOffset = targetOffset;
	}
}
