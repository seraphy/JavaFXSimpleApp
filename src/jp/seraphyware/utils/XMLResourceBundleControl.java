package jp.seraphyware.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * XMLプロパティ形式のリソースバンドルを読み込み可能にするコントローラ.
 * @author seraphy
 */
public class XMLResourceBundleControl extends ResourceBundle.Control {

	/**
	 * 拡張子
	 */
	private static final String XML = "xml";

	/**
	 * このコントローラが対象する拡張子の一覧を返す.
	 * "xml"だけが有効である.
	 * @param baseName ベース名
	 * @return 対象となる拡張子一覧
	 */
	@Override
	public List<String> getFormats(String baseName) {
		return Arrays.asList(XML);
	}

	/**
	 * 新しいリソースバンドルを生成して返す.
	 * xml形式のプロパティファイルから現在のロケールに従って取得したリソースバンドルを返す.
	 * @return リソースバンドル
	 */
	@Override
	public ResourceBundle newBundle(String baseName, Locale locale,
			String format, ClassLoader loader, boolean reload)
			throws IllegalAccessException, InstantiationException, IOException {
		if (!XML.equals(format)) {
			return null;
		}

		// ロケールと結合したリソース名を求める
		String bundleName = toBundleName(baseName, locale);

		// 対応するフォーマットと結合したリソース名を求める
		String resourceName = toResourceName(bundleName, format);

		URL url = loader.getResource(resourceName);

		// XMLプロパティをロードする.
		if (url != null) {
			Properties props = new Properties();
			props.loadFromXML(url.openStream());

			// XMLプロパティをリソースバンドルに接続する.
			return new ResourceBundle() {
				@Override
				protected Object handleGetObject(String key) {
					return props.getProperty(key);
				}

				@SuppressWarnings("unchecked")
				@Override
				public Enumeration<String> getKeys() {
					return (Enumeration<String>) props.propertyNames();
				}
			};
		}

		// ロードできず.
		return null;
	}
}
