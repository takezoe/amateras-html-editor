package jp.sf.amateras.jspeditor.editors;

public interface IJSPValidationMarkerCreator {

	public void addMarker( int severity, int offset, int length, String message);
}
