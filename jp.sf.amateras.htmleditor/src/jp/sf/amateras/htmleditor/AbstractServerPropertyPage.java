package jp.sf.amateras.htmleditor;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

/**
 * A base class for server configuration property page.
 * 
 * @author Naoki Takezoe
 * @since 2.0.6
 */
public abstract class AbstractServerPropertyPage extends PropertyPage {
	
	private Text server;
	private Text context;
	private ServerConfig config;
	
	protected abstract String getPluginId();
	
	protected Control createContents(Composite parent) {
		config = new ServerConfig(getProject(), getPluginId());
		
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));
		
		new Label(composite, SWT.NULL).setText(HTMLPlugin.getResourceString("AbstractServerPropertyPage.Server"));
		server = new Text(composite, SWT.BORDER);
		server.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		server.setText(config.getServer());
		
		new Label(composite, SWT.NULL).setText(HTMLPlugin.getResourceString("AbstractServerPropertyPage.Context"));
		context = new Text(composite, SWT.BORDER);
		context.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		context.setText(config.getContext());
		
		return composite;
	}
	
	protected void performDefaults() {
		server.setText(config.getDefaultServer());
		server.setText(config.getDefaultContext());
	}

	public boolean performOk() {
		config.setServer(server.getText());
		config.setContext(context.getText());
		return true;
	}

	private IProject getProject(){
		IAdaptable adaptable = getElement();
		return (IProject) adaptable.getAdapter(IProject.class);
	}


}
