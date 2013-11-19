package jp.sf.amateras.htmleditor;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * An data object which has server configurations.
 * 
 * @author Naoki Takezoe
 * @since 2.0.6
 */
public class ServerConfig {
	
	private static final String PREF_SERVER = "faceside.pref.server";
	private static final String PREF_CONTEXT = "faceside.pref.context";
	
	private IPreferenceStore store;
	private IProject project;
	
	public ServerConfig(IProject project, String pluginId){
		this.store = new ScopedPreferenceStore(
				new ProjectScope(project), pluginId);
		this.project = project;
	}
	
	public String getServer(){
		String server = store.getString(PREF_SERVER);
		if(server==null || server.length()==0){
			server = getDefaultServer();
		}
		return server;
	}
	
	public void setServer(String server){
		store.setValue(PREF_SERVER, server);
	}

	public String getDefaultServer(){
		return "http://localhost:8080";
	}
	
	public String getContext(){
		String context = store.getString(PREF_CONTEXT);
		if(context==null || context.length()==0){
			context = getDefaultContext();
		}
		return context;
	}
	
	public void setContext(String context){
		store.setValue(PREF_CONTEXT, context);
	}
	
	public String getDefaultContext(){
		return "/" + project.getName();
	}
	
}
