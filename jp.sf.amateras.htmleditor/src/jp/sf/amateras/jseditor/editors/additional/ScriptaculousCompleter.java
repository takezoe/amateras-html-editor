package jp.sf.amateras.jseditor.editors.additional;

import java.util.ArrayList;
import java.util.List;

import jp.sf.amateras.htmleditor.HTMLPlugin;
import jp.sf.amateras.jseditor.editors.model.JavaScriptModel;

public class ScriptaculousCompleter extends AbstractCompleter {
	private static final String[] JS_FILES = new String[] { "scriptaculous.js",
			"builder.js", "effects.js", "dragdrop.js", "controls.js",
			"slider.js", "sound" };
	private static final String BASE_JS_DIR = "js/scriptaculous/1.9.0/";

	public List<JavaScriptModel> loadModel(List<JavaScriptModel> libModelList) {
		try {
			List<JavaScriptModel> list = new ArrayList<JavaScriptModel>();
			for (String jsFile : JS_FILES) {
				JsFileCache jsFileCache = getJsFileCache(BASE_JS_DIR + jsFile);
				String source = new String(jsFileCache.getBytes(), "UTF-8");
				JavaScriptModel model = new JavaScriptModel(
						jsFileCache.getFile(), source, libModelList);
				if (model != null) {
					libModelList.add(model);
					list.add(model);
				}
			}
			return list.isEmpty() ? null : list;
		} catch (Exception e) {
			HTMLPlugin.logException(e);
		}
		return null;
	}
}
