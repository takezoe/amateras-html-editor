Eclipse HTML Editor Plugin
========

Eclipse HTML Editor is an Eclipse plugin for HTML/JSP/XML Editing. It requires JDT and GEF. It has following features.

- HTML/JSP/XML/CSS/DTD/JavaScript Hilighting
- HTML/JSP Preview
- JSP/XML Validation
- Contents Assist (HTML Tags/Attributes, XML based on DTD and JSP taglib and more)
- Wizards for creating HTML/JSP/XML files
- Outline View
- Editor Preferences
- Editor Folding
- Web Browser (It works as an Eclipse's editor)
- Image Viewer
- Tag Palette
- CSS code completion and outline
- DTD code completion, outline and validation
- JavaScript code completion, outline and validation

Installation
--------
Install from the update site: http://takezoe.github.io/amateras-update-site/

Note: SWT Browser widget (HTMLEditor uses it for preview HTML and JSP) requires Mozilla in the Linux. See details at [The SWT FAQ](http://www.eclipse.org/swt/faq.php#browserlinux). Also you can disable preview in the preference dialog. Choose [Window]->[Preferences]->[Amateras] and check [Disable Preview]. Then you would be able to use HTMLEditor without Mozilla.

License
--------
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

Some icons are based on Eclipse distribution's. Eclipse Public License is applied to these icons. And included libraries are distributed by their original license.

History
--------
### Version 2.2.0 (2012/12/28)

- Eclipse Juno Support
- HTML validation improvoment (XHTML validation problem was fixed)

### Version 2.1.0 (2011/02/20)

- Many part of JavaScript Editor are re-written and many features were improved.
- JavaScript Formatter Improvement (based on JS Beautifier)
- JavaScript Validator Improvement (based on Google Closure Compiler)
- JavaScript Code Completion Improvement
- CommonJS Modules 1.0 Support

See details [here](http://sourceforge.jp/projects/amateras/wiki/AmaterasIDE_2_1_0) about new features.

### Version 2.0.7 (2010/07/05)

- JavaScript editor improvenemnt
- Code completion was greatly improved
- jQuery code completion
- Quick Outline (CTRL + O)
- JsDoc support
- Selected word highlighting
- XPath search dialog is available in the HTML editor
- Configurable HTML/JSP formatter
- Synchronization of the caret offset and outline view selection in HTML, XML, JSP and JavaScript editor

See details [here](http://sourceforge.jp/projects/amateras/wiki/AmaterasIDE_2_0_7) about new features.

### Version 2.0.6 (2008/09/06)

- JavaScript code completion improved
- JavaSCript code formatter
- Add JavaScript Library wizard
- Taglib Highlighting
- JSP code completion improved (JSTL, EL and others...)
- New extension points are available

See details [here](http://sourceforge.jp/projects/amateras/wiki/AmaterasIDE_2_0_6) about new features.

### Version 2.0.5.1 (2008/02/21)

- Bug fixed: Rhino debugger dies with Eclipse.

### Version 2.0.5 (2008/02/16)

- Eclipse 3.3 support (This version can not work with Eclipse 3.2)
- Rhino JavaScript Debugger
- CSS Preview view
- Code completion and Hyperlink improved
- XPath serach in the XML editor
- Enable / disable auto editing
- New extension point (tk.eclipse.plugin.htmleditor.pagefilter)

See details [here](http://sourceforge.jp/projects/amateras/wiki/AmaterasIDE_2_0_5) about new features.

### Version 2.0.4 (2007/02/25)

- GEF dependency became an optional. If you don't have GEF, Palette view will be unavailable.
- Fixed JSP editor bugs.

See details [here](https://amateraside.dev.java.net/releases/AmaterasIDE/2.0.4/) about new features.

### Version 2.0.3 (2006/12/30)

- This version works with only Eclipse 3.2
- Inner JavaScript/CSS Editing in the HTML editor
- Java code completion in the JSP editor
- Classpath variable that provides Servlet/JSP API
- CDATA section and XML declaration highlighting in the XML editor
- IDREF/IDREFS attribute value completion in the XML editor
- Java Classname hyperlink/completion in the XML editor
- Auto editing
- Code completion in the XML file which doesn't use DTD/XSD
- New extention-points

See details [here](https://amateraside.dev.java.net/releases/AmaterasIDE/2.0.3/) about new features.

### Version 2.0.2 (2006/08/20)

- Displays taglib description in the JSP completion.
- JSP debugging with WTP.
- JavaScript Launcher.
- JavaScript common libraries for code completion in the JavaScript editor.
- TaskTag in HTML/JSP/XML/JavaScript editor.
- Toggle Comment in the JavaScript editor (CTRL+/)
- Generate XSD action in the DTD editor.
- Supports Eclipse 3.1 & 3.2 (this version can't work with Eclipse 3.0).

See details [here](https://amateraside.dev.java.net/releases/AmaterasIDE/2.0.2/) about new features.

### Version 2.0.1 (2006/06/18)

- Code completion using custom elements / attributes in HTML/JSP editor
- Code completion using Templates in HTML/JSP/XML/JavaScript editor
- Color choose action in the HTML/JSP/CSS editor's context menu

See details [here](https://amateraside.dev.java.net/releases/AmaterasIDE/2.0.1/) about new features.

### Version 2.0.0 (2006/03/06)

- DTD Editor that provides syntax highlighting, code completion and valdiation
- JavaScript Editor that provides syntax highlighting, code completion and valdiation
- Toggle comment
- Added keybord shortcut to some editor actions

### Version 1.6.9 (2005/12/25)

- Improvemrnt for the css editor highlighting
- Improvement for the outline view
- Highlighting for paired characters
- Fixed a problem about recursive xml schema

### Version 1.6.8 (2005/11/26)

- Fixed some problems about charset in the XML editor.
- Added an option to disable the preview pane.
- Code completion for directives (<%@ ... %>) in the JSP editor.
- Syntax hilighting for java code in the JSP editor.
- Improved attribute value processing.

### Version 1.6.7 (2005/09/17)

- Improvement about XML schema support
- JSP editor supports jsp:attribute, jsp:body and more
- JSP editor supports tagdir
- HTML/JSP editor supports onXXXX attributes
- Added toolbar to the web browser (it has some buttons such as "Back" and "Next")
- HTML validation using JTidy (it can be configured in the project preference)
- The outline view displays attributes
- Multi-page editors supports ruler context menu actions
- Editors support external files (CVS repositry and out of the workspace)

### Version 1.6.6 (2005/07/24)

- Generate DTD and XSD in the XML editor
- Some bugs are fiexed

### Version 1.6.5 (2005/07/09)

- Eclipse 3.1 support
- Customization of Palette
- Improvement about DTD preference page
- Soft tab (insert spaces instead of tabs)
- Customization of background and foreground color
- XML Schema based validation and completion in the XML editor
- Mapping taglib-uri to local files (It's same as DTD)
- "Open Palette" action in HTML/JSP/XML editor context menu
- "Comment(JSP)" action in the JSP editor context menu

### Version 1.6.4 (2005/04/08)

- CSS Editor
- Some bugs are fixed

### Version 1.6.3 (2005/03/19)

- JDK 1.5 support
- Getting taglib from xmlns
- Hyperlink support in "href" and others (CTRL+Click)
- Fixed some problems

### Version 1.6.2 (2005/03/01)

- Fixed problem about preview in system undefault charset
- Fixed problem about preview then JSP contains non-close-tag
- Fixed problem that <jsp:...> is reported as error
- Fixed problem that attribute value completion is disabled when no value characters are input
- Supports default text editor's action such as "Markset","Incremental search" and "Print"
- Added option that disable DTD in XML editor

### Version 1.6.1 (2005/02/19)

- Loading TLD from WEB-INF/web.xml and WEB-INF/lib/*.jar.
- Validation taglibs (it examines whether tag and required attrs exist) in the JSP Editor.
- Focusing to editor, after insert tags from tag palette.
- A tag palette insert <%@ taglib .. %> automatically when it is not exist.
- CSS completion supports multi classes like class="aa bb".

### Version 1.6.0 (2005/02/13)

- This version requires JDT and GEF.

### Version 1.5.3 (2005/01/29)
### Version 1.5.2 (2005/01/22)
### Version 1.5.1 (2005/01/16)
### Version 1.5.0 (2004/12/25)
### Version 1.4.1 (2004/12/10)
### Version 1.4.0 (2004/11/30)
### Version 1.3.0 (2004/11/23)
### Version 1.2.0 (2004/09/15)
### Version 1.1.0 (2004/09/13)
### Version 1.0.0 (2004/09/10)
