package jp.sf.amateras.jspeditor.editors;

/**
 * 
 * @author Naoki Takezoe
 * @since 2.0.6
 */
public class Function {
	
	private String name;
//	private String signature;
	private String description;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
//	public String getSignature() {
//		return signature;
//	}
//	
//	public void setSignature(String signature) {
//		this.signature = signature;
//	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
