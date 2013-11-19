package jp.sf.amateras.htmleditor.editors;

import org.eclipse.swt.graphics.RGB;

/** Defines initial colors used in editors. */
public interface IHTMLColorConstants {
	RGB HTML_COMMENT = new RGB(128,   0,   0);
	RGB PROC_INSTR   = new RGB(128, 128, 128);
	RGB STRING       = new RGB(  0, 128,   0);
	RGB DEFAULT      = new RGB(  0,   0,   0);
	RGB TAG          = new RGB(  0,   0, 128);
	RGB SCRIPT       = new RGB(255,   0,   0);
	RGB CSS_PROP     = new RGB(  0,   0, 255);
	RGB CSS_COMMENT  = new RGB(128,   0,   0);
	RGB CSS_VALUE    = new RGB(  0, 128,   0);
	RGB FOREGROUND   = new RGB(  0,   0,   0);
	RGB BACKGROUND   = new RGB(255, 255, 255);
	RGB JAVA_COMMENT = new RGB(  0, 128,   0);
	RGB JAVA_STRING  = new RGB(  0,   0, 255);
	RGB JAVA_KEYWORD = new RGB(128,   0, 128);
    RGB TAGLIB       = new RGB(255,   0,   0);
    RGB TAGLIB_ATTR  = new RGB(128, 128,   0);
	RGB JSDOC        = new RGB(128, 128, 255);
}
