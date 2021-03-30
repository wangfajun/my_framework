package com.wangfajun.framework.config.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 自定义环境加载策略 需要在classpath下application.yml或application.properties中指定文件位置
 * 配置key:config.file.absolute.path
 * </p>
 *
 */
public class ZdEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

	/**
	 * 指定加载外部文件类型: [.properties], [.yml], [.yaml]
	 */
	private static final String SUFFIX_TYPE_YML = ".yml";
	private static final String SUFFIX_TYPE_YAML = ".yaml";
	private static final String SUFFIX_TYPE_PROPERTIES = ".properties";

	/**
	 * 指定外部配置文件路径的KEY
	 */
	private static final String CONFIG_FILE_ABSOLUTE_PATH = "config.file.absolute.path";

	private static final int DEFAULT_ORDER = ConfigFileApplicationListener.DEFAULT_ORDER + 1;

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

		try {
			// Get file absolute path
			String path = environment.getProperty(CONFIG_FILE_ABSOLUTE_PATH);
			if (StringUtils.isEmpty(path)) {
				return;
			}

			// Loading external file
			Resource resource = new PathResource(path);

			PropertySourceLoader loader;

			if (resource.exists() && resource.isFile()) {
				String filename = resource.getFilename();
				String fileSuffix = filename.substring(filename.indexOf("."));

				if (SUFFIX_TYPE_PROPERTIES.equalsIgnoreCase(fileSuffix)) {
					loader = new PropertiesPropertySourceLoader();

				} else if (SUFFIX_TYPE_YML.equalsIgnoreCase(fileSuffix)
						|| SUFFIX_TYPE_YAML.equalsIgnoreCase(fileSuffix)) {
					loader = new YamlPropertySourceLoader();

				} else {
					throw new RuntimeException("Unsupported file types: " + fileSuffix);
				}
			} else {
				throw new FileNotFoundException("Cannot find the file : \"" + path + "\"");
			}

			List<PropertySource<?>> sources = loader.load("externalFiles", resource);
			for (PropertySource<?> source : sources) {
				environment.getPropertySources().addLast(source);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int getOrder() {
		return DEFAULT_ORDER;
	}
}
