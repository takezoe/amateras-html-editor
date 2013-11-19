package jp.sf.amateras.jseditor.editors.additional;

import java.util.ArrayList;
import java.util.List;

import jp.sf.amateras.htmleditor.HTMLPlugin;
import jp.sf.amateras.jseditor.editors.model.JavaScriptModel;

/**
 * <code>IAdditionalJavaScriptCompleter</code> implementation for
 * <strong>jQuery</strong>.
 * 
 * @author Naoki Takezoe
 * @author shinsuke
 */
public class JQueryCompleter extends AbstractCompleter {

	public List<JavaScriptModel> loadModel(List<JavaScriptModel> libModelList) {
		try {
			JsFileCache jsFileCache = getJsFileCache("js/jquery/1.7.1/jquery-1.7.1.js");
			String source = new String(jsFileCache.getBytes(), "UTF-8");
			JavaScriptModel model = new JavaScriptModel(jsFileCache.getFile(),
					source, libModelList);
			if (model != null) {
				libModelList.add(model);
				List<JavaScriptModel> list = new ArrayList<JavaScriptModel>();
				list.add(model);
				return list;
			}
		} catch (Exception e) {
			HTMLPlugin.logException(e);
		}
		return null;
	}
}
