package jp.sf.amateras.htmleditor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.sf.amateras.jseditor.editors.additional.AdditionalJavaScriptCompleterManager;
import jp.sf.amateras.jseditor.launch.JavaScriptLibPathTable;
import jp.sf.amateras.jseditor.launch.JavaScriptLibraryTable;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.dialogs.PropertyPage;

/**
 * The property page for the JavaScript editor.
 * 
 * @author Naoki Takezoe
 * @author shinsuke
 */
public class JavaScriptPropertyPage extends PropertyPage {

	private HTMLProjectParams params;
	private TabFolder tabFolder;
	private JavaScriptLibraryTable libTableViewer;
	private JavaScriptLibPathTable libPathTableViewer;
	private Table additionalCompleters;
	private Spinner indentSizeText;
	private Combo indentChar;
	private Button preserveNewlines;
	private Spinner initIndentLevelText;
	private Button spaceAfterAnonFunc;
	private Button bracesOnOwnLine;
	private Combo accessControls;
	private Combo checkRegExp;
	private Combo checkTypes;
	private Combo checkVars;
	private Combo deprecated;
	private Combo fileoverviewTags;
	private Combo invalidCasts;
	private Combo missingProperties;
	private Combo nonStandardJsDocs;
	private Combo strictModuleDepCheck;
	private Combo undefinedVars;
	private Combo unknownDefines;
	private Combo visibility;
	private Combo useUndefined;

	public JavaScriptPropertyPage() {
		super();
		setDescription(HTMLPlugin
				.getResourceString("JavaScriptPropertyPage.Description"));
	}

	protected Control createContents(Composite parent) {
		tabFolder = new TabFolder(parent, SWT.NULL);

		createFormatterOptionsTab();
		createValidatorOptionsTab();
		createCompletersTab();
		createLibrariesTab();
		createLibPathsTab();

		try {
			params = new HTMLProjectParams(getProject());
		} catch (Exception ex) {
			HTMLPlugin.logException(ex);
		}
		fillControls();

		return tabFolder;
	}

	private void createFormatterOptionsTab() {
		TabItem tab = new TabItem(tabFolder, SWT.NULL);
		tab.setText(HTMLPlugin
				.getResourceString("JavaScriptPropertyPage.Tab.Formatter"));
		SashForm form = new SashForm(tabFolder, SWT.VERTICAL);
		Composite c = new Composite(form, SWT.NONE);
		c.setLayout(new FormLayout());
		// indent_size (default 4)
		Label label = new Label(c, SWT.NULL);
		label.setText(HTMLPlugin
				.getResourceString("JavaScriptPropertyPage.Label.IndentSize"));
		FormData data = new FormData();
		data.top = new FormAttachment(0, 10);
		data.left = new FormAttachment(0, 5);
		label.setLayoutData(data);
		indentSizeText = new Spinner(c, SWT.BORDER);
		indentSizeText.setMinimum(0);
		indentSizeText.setMaximum(10);
		data = new FormData();
		data.top = new FormAttachment(0, 5);
		data.left = new FormAttachment(label, 5);
		indentSizeText.setLayoutData(data);
		// indent_char (default space)
		label = new Label(c, SWT.NULL);
		label.setText(HTMLPlugin
				.getResourceString("JavaScriptPropertyPage.Label.IndentChar"));
		data = new FormData();
		data.top = new FormAttachment(indentSizeText, 10);
		data.left = new FormAttachment(0, 5);
		label.setLayoutData(data);
		indentChar = new Combo(c, SWT.READ_ONLY);
		indentChar.add(HTMLPlugin
				.getResourceString("JavaScriptPropertyPage.Label.Space"));
		indentChar.add(HTMLPlugin
				.getResourceString("JavaScriptPropertyPage.Label.Tab"));
		data = new FormData();
		data.top = new FormAttachment(indentSizeText, 5);
		data.left = new FormAttachment(label, 5);
		indentChar.setLayoutData(data);
		// preserve_newlines (default true)
		preserveNewlines = new Button(c, SWT.CHECK);
		preserveNewlines
				.setText(HTMLPlugin
						.getResourceString("JavaScriptPropertyPage.Label.PreserveNewLines"));
		data = new FormData();
		data.top = new FormAttachment(indentChar, 5);
		data.left = new FormAttachment(0, 5);
		preserveNewlines.setLayoutData(data);
		// TODO preserve_max_newlines (default unlimited)
		// indent_level (default 0)
		label = new Label(c, SWT.NULL);
		label.setText(HTMLPlugin
				.getResourceString("JavaScriptPropertyPage.Label.InitialIndentLevel"));
		data = new FormData();
		data.top = new FormAttachment(preserveNewlines, 10);
		data.left = new FormAttachment(0, 5);
		label.setLayoutData(data);
		initIndentLevelText = new Spinner(c, SWT.BORDER);
		initIndentLevelText.setMinimum(0);
		initIndentLevelText.setMaximum(10);
		data = new FormData();
		data.top = new FormAttachment(preserveNewlines, 5);
		data.left = new FormAttachment(label, 5);
		initIndentLevelText.setLayoutData(data);
		// space_after_anon_function (default false)
		spaceAfterAnonFunc = new Button(c, SWT.CHECK);
		spaceAfterAnonFunc
				.setText(HTMLPlugin
						.getResourceString("JavaScriptPropertyPage.Label.SpaceAfterAnonFunc"));
		data = new FormData();
		data.top = new FormAttachment(initIndentLevelText, 5);
		data.left = new FormAttachment(0, 5);
		spaceAfterAnonFunc.setLayoutData(data);
		// braces_on_own_line (default false) - ANSI / Allman brace style, each
		// opening/closing brace gets its own line.
		bracesOnOwnLine = new Button(c, SWT.CHECK);
		bracesOnOwnLine
				.setText(HTMLPlugin
						.getResourceString("JavaScriptPropertyPage.Label.BracesOnOwnLine"));
		data = new FormData();
		data.top = new FormAttachment(spaceAfterAnonFunc, 5);
		data.left = new FormAttachment(0, 5);
		bracesOnOwnLine.setLayoutData(data);

		tab.setControl(form);

	}

	private void createValidatorOptionsTab() {
		TabItem tab = new TabItem(tabFolder, SWT.NULL);
		tab.setText(HTMLPlugin
				.getResourceString("JavaScriptPropertyPage.Tab.Validator"));
		SashForm form = new SashForm(tabFolder, SWT.VERTICAL);
		Composite c = new Composite(form, SWT.NONE);
		c.setLayout(new FormLayout());

		// accessControls
		accessControls = createValidatorOptionCombo(c,
				"JavaScriptPropertyPage.Label.AccessControls", null);
		// checkRegExp
		checkRegExp = createValidatorOptionCombo(c,
				"JavaScriptPropertyPage.Label.CheckRegExp", accessControls);
		// checkTypes
		checkTypes = createValidatorOptionCombo(c,
				"JavaScriptPropertyPage.Label.CheckTypes", checkRegExp);
		// checkVars
		checkVars = createValidatorOptionCombo(c,
				"JavaScriptPropertyPage.Label.CheckVars", checkTypes);
		// deprecated
		deprecated = createValidatorOptionCombo(c,
				"JavaScriptPropertyPage.Label.Deprecated", checkVars);
		// fileoverviewTags
		fileoverviewTags = createValidatorOptionCombo(c,
				"JavaScriptPropertyPage.Label.FileoverviewTags", deprecated);
		// invalidCasts
		invalidCasts = createValidatorOptionCombo(c,
				"JavaScriptPropertyPage.Label.InvalidCasts", fileoverviewTags);
		// missingProperties
		missingProperties = createValidatorOptionCombo(c,
				"JavaScriptPropertyPage.Label.MissingProperties", invalidCasts);
		// nonStandardJsDocs
		nonStandardJsDocs = createValidatorOptionCombo(c,
				"JavaScriptPropertyPage.Label.NonStandardJsDocs",
				missingProperties);
		// strictModuleDepCheck
		strictModuleDepCheck = createValidatorOptionCombo(c,
				"JavaScriptPropertyPage.Label.StrictModuleDepCheck",
				nonStandardJsDocs);
		// undefinedVars
		undefinedVars = createValidatorOptionCombo(c,
				"JavaScriptPropertyPage.Label.UndefinedVars",
				strictModuleDepCheck);
		// unknownDefines
		unknownDefines = createValidatorOptionCombo(c,
				"JavaScriptPropertyPage.Label.UnknownDefines", undefinedVars);
		// visibility
		visibility = createValidatorOptionCombo(c,
				"JavaScriptPropertyPage.Label.Visibility", unknownDefines);
		// useUndefined
		useUndefined = createValidatorOptionCombo(c,
				"JavaScriptPropertyPage.Label.UseUndefined", visibility);

		tab.setControl(form);
	}

	private Combo createValidatorOptionCombo(Composite c, String message,
			Control above) {
		FormData data;
		Label label;
		Combo combo = new Combo(c, SWT.READ_ONLY);
		combo.add(HTMLPlugin
				.getResourceString("JavaScriptPropertyPage.Label.VOff"));
		combo.add(HTMLPlugin
				.getResourceString("JavaScriptPropertyPage.Label.VWarning"));
		combo.add(HTMLPlugin
				.getResourceString("JavaScriptPropertyPage.Label.VError"));
		data = new FormData();
		data.top = above == null ? new FormAttachment(0, 5)
				: new FormAttachment(above, 5);
		data.left = new FormAttachment(0, 5);
		combo.setLayoutData(data);
		label = new Label(c, SWT.NULL);
		label.setText(HTMLPlugin.getResourceString(message));
		data = new FormData();
		data.top = above == null ? new FormAttachment(0, 10)
				: new FormAttachment(above, 10);
		data.left = new FormAttachment(combo, 5);
		label.setLayoutData(data);
		return combo;
	}

	private void createLibPathsTab() {
		libPathTableViewer = new JavaScriptLibPathTable(tabFolder);
		TabItem tab = new TabItem(tabFolder, SWT.NULL);
		tab.setText(HTMLPlugin
				.getResourceString("JavaScriptPropertyPage.Tab.LibrariesPaths"));
		tab.setControl(libPathTableViewer.getControl());
	}

	private void createCompletersTab() {
		TabItem tab = new TabItem(tabFolder, SWT.NULL);
		tab.setText(HTMLPlugin
				.getResourceString("JavaScriptPropertyPage.Tab.CodeCompletion"));
		additionalCompleters = new Table(tabFolder, SWT.CHECK | SWT.BORDER);
		additionalCompleters.setLayoutData(new GridData(GridData.FILL_BOTH));
		String[] names = AdditionalJavaScriptCompleterManager
				.getAdditionalJavaScriptCompleterNames();
		for (int i = 0; i < names.length; i++) {
			new TableItem(additionalCompleters, SWT.NULL).setText(names[i]);
		}
		tab.setControl(additionalCompleters);
	}

	private void createLibrariesTab() {
		libTableViewer = new JavaScriptLibraryTable(tabFolder);
		TabItem tab = new TabItem(tabFolder, SWT.NULL);
		tab.setText(HTMLPlugin
				.getResourceString("JavaScriptPropertyPage.Tab.Libraries"));
		tab.setControl(libTableViewer.getControl());
	}

	private void fillControls() {
		List<Object> libTableModel = libTableViewer.getModel();
		libTableModel.clear();
		String[] javaScripts = params.getJavaScripts();
		IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();

		for (int i = 0; i < javaScripts.length; i++) {
			if (javaScripts[i].startsWith(JavaScriptLibraryTable.PREFIX)) {
				IResource resource = wsroot.findMember(javaScripts[i]
						.substring(JavaScriptLibraryTable.PREFIX.length()));
				if (resource != null && resource instanceof IFile
						&& resource.exists()) {
					libTableModel.add((IFile) resource);
				}
			} else {
				libTableModel.add(new File(javaScripts[i]));
			}
		}
		libTableViewer.refresh();

		String[] javaScriptCompleters = params.getJavaScriptCompleters();
		for (int i = 0; i < javaScriptCompleters.length; i++) {
			TableItem[] items = additionalCompleters.getItems();
			for (int j = 0; j < items.length; j++) {
				if (items[j].getText().equals(javaScriptCompleters[i])) {
					items[j].setChecked(true);
				}
			}
		}

		List<Object> libPathTableModel = libPathTableViewer.getModel();
		libPathTableModel.clear();
		String[] javaScriptLibPaths = params.getJavaScriptLibPaths();
		for (int i = 0; i < javaScriptLibPaths.length; i++) {
			if (javaScriptLibPaths[i].startsWith(JavaScriptLibPathTable.PREFIX)) {
				IResource resource = wsroot.findMember(javaScriptLibPaths[i]
						.substring(JavaScriptLibPathTable.PREFIX.length()));
				if (resource != null && resource instanceof IFolder
						&& resource.exists()) {
					libPathTableModel.add((IFolder) resource);
				}
			} else {
				libPathTableModel.add(new File(javaScriptLibPaths[i]));
			}
		}
		libPathTableViewer.refresh();

		// validator
		accessControls.select(convertOptionToIndex(params
				.getJavaScriptAccessControls()));
		checkRegExp.select(convertOptionToIndex(params
				.getJavaScriptCheckRegExp()));
		checkTypes
				.select(convertOptionToIndex(params.getJavaScriptCheckTypes()));
		checkVars.select(convertOptionToIndex(params.getJavaScriptCheckVars()));
		deprecated
				.select(convertOptionToIndex(params.getJavaScriptDeprecated()));
		fileoverviewTags.select(convertOptionToIndex(params
				.getJavaScriptFileoverviewTags()));
		invalidCasts.select(convertOptionToIndex(params
				.getJavaScriptInvalidCasts()));
		missingProperties.select(convertOptionToIndex(params
				.getJavaScriptMissingProperties()));
		nonStandardJsDocs.select(convertOptionToIndex(params
				.getJavaScriptNonStandardJsDocs()));
		strictModuleDepCheck.select(convertOptionToIndex(params
				.getJavaScriptStrictModuleDepCheck()));
		undefinedVars.select(convertOptionToIndex(params
				.getJavaScriptUndefinedVars()));
		unknownDefines.select(convertOptionToIndex(params
				.getJavaScriptUnknownDefines()));
		visibility
				.select(convertOptionToIndex(params.getJavaScriptVisibility()));
		useUndefined.select(convertOptionToIndex(params
				.getJavaScriptUseUndefined()));

		// formatter
		indentSizeText.setSelection(params.getJavaScriptIndentSize());
		char indentChar2 = params.getJavaScriptIndentChar();
		switch (indentChar2) {
		case '\t':
			indentChar.select(1);
			break;
		case ' ':
		default:
			indentChar.select(0);
			break;
		}
		preserveNewlines.setSelection(params.isJavaScriptPreserveNewlines());
		initIndentLevelText.setSelection(params.getJavaScriptInitIndentLevel());
		spaceAfterAnonFunc
				.setSelection(params.isJavaScriptSpaceAfterAnonFunc());
		bracesOnOwnLine.setSelection(params.isJavaScriptBracesOnOwnLine());
	}

	private int convertOptionToIndex(String value) {
		if ("Error".equals(value)) {
			return 2;
		} else if ("Warning".equals(value)) {
			return 1;
		}
		return 0;
	}

	protected void performDefaults() {
		params = new HTMLProjectParams();
		fillControls();
	}

	public boolean performOk() {
		// save configuration
		try {
			params = new HTMLProjectParams(getProject());
			List<Object> libTableModel = libTableViewer.getModel();

			String[] javaScripts = new String[libTableModel.size()];
			for (int i = 0; i < libTableModel.size(); i++) {
				Object obj = libTableModel.get(i);
				if (obj instanceof File) {
					javaScripts[i] = ((File) obj).getAbsolutePath();
				} else if (obj instanceof IFile) {
					javaScripts[i] = JavaScriptLibraryTable.PREFIX
							+ ((IFile) obj).getFullPath().toString();
				}
			}
			params.setJavaScripts(javaScripts);

			TableItem[] items = additionalCompleters.getItems();
			List<String> javaScriptCompleters = new ArrayList<String>();
			for (int i = 0; i < items.length; i++) {
				if (items[i].getChecked()) {
					javaScriptCompleters.add(items[i].getText());
				}
			}
			params.setJavaScriptCompleters(javaScriptCompleters
					.toArray(new String[javaScriptCompleters.size()]));

			List<Object> libPathTableModel = libPathTableViewer.getModel();
			String[] javaScriptLibPaths = new String[libPathTableModel.size()];
			for (int i = 0; i < libPathTableModel.size(); i++) {
				Object obj = libPathTableModel.get(i);
				if (obj instanceof File) {
					javaScriptLibPaths[i] = ((File) obj).getAbsolutePath();
				} else if (obj instanceof IFolder) {
					javaScriptLibPaths[i] = JavaScriptLibPathTable.PREFIX
							+ ((IFolder) obj).getFullPath().toString();
				}
			}
			params.setJavaScriptLibPaths(javaScriptLibPaths);

			// validator
			params.setJavaScriptAccessControls(convertIndexToOption(accessControls
					.getSelectionIndex()));
			params.setJavaScriptCheckRegExp(convertIndexToOption(checkRegExp
					.getSelectionIndex()));
			params.setJavaScriptCheckTypes(convertIndexToOption(checkTypes
					.getSelectionIndex()));
			params.setJavaScriptCheckVars(convertIndexToOption(checkVars
					.getSelectionIndex()));
			params.setJavaScriptDeprecated(convertIndexToOption(deprecated
					.getSelectionIndex()));
			params.setJavaScriptFileoverviewTags(convertIndexToOption(fileoverviewTags
					.getSelectionIndex()));
			params.setJavaScriptInvalidCasts(convertIndexToOption(invalidCasts
					.getSelectionIndex()));
			params.setJavaScriptMissingProperties(convertIndexToOption(missingProperties
					.getSelectionIndex()));
			params.setJavaScriptNonStandardJsDocs(convertIndexToOption(nonStandardJsDocs
					.getSelectionIndex()));
			params.setJavaScriptStrictModuleDepCheck(convertIndexToOption(strictModuleDepCheck
					.getSelectionIndex()));
			params.setJavaScriptUndefinedVars(convertIndexToOption(undefinedVars
					.getSelectionIndex()));
			params.setJavaScriptUnknownDefines(convertIndexToOption(unknownDefines
					.getSelectionIndex()));
			params.setJavaScriptVisibility(convertIndexToOption(visibility
					.getSelectionIndex()));
			params.setJavaScriptUseUndefined(convertIndexToOption(useUndefined
					.getSelectionIndex()));

			// formatter
			params.setJavaScriptIndentSize(indentSizeText.getSelection());
			int index = indentChar.getSelectionIndex();
			if (index == 1) {
				params.setJavaScriptIndentChar('\t');
			} else {
				params.setJavaScriptIndentChar(' ');
			}
			params.setJavaScriptPreserveNewlines(preserveNewlines
					.getSelection());
			params.setJavaScriptInitIndentLevel(initIndentLevelText
					.getSelection());
			params.setJavaScriptSpaceAfterAnonFunc(spaceAfterAnonFunc
					.getSelection());
			params.setJavaScriptBracesOnOwnLine(bracesOnOwnLine.getSelection());

			params.save(getProject());

		} catch (Exception ex) {
			HTMLPlugin.logException(ex);
			return false;
		}
		return true;
	}

	private String convertIndexToOption(int value) {
		if (value == 1) {
			return "Warning";
		} else if (value == 2) {
			return "Error";
		}
		return "Off";
	}

	private IProject getProject() {
		IAdaptable adaptable = getElement();
		return (IProject) adaptable.getAdapter(IProject.class);
	}
}
