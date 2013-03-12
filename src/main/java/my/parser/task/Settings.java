/**
 * 
 */
package my.parser.task;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import my.parser.task.util.PathValidator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author victor
 *
 */
public class Settings {
	private static final Logger LOGGER = Logger.getLogger(Settings.class);
	private static final String DATA_PATH = "../xml-files/";
	private static final String PROP_FILE = "xml-parser.properties";
	
	private static final String PATH_KEY = "path";
	private static final String PATH_DEF = DATA_PATH;
	
	private static final String MASK_KEY = "mask";
	private static final String MASK_DEF = "m_MeterBackup#?[0-9]{10}\\.xml";
	
	private static final String DATA_FMT_KEY = "data_format";
	private static final String DATA_FMT_DEF = "yyyy-MM-dd'T'hh:mm";
	
	private Properties props = new Properties();
	
	public Settings() {
		defaults();
	}
	
	@Autowired(required=true)
	public Settings(@Value("#{args}") String[] args) {
		this();
		load();
		
		if (args.length != 0)
			processArgs(args);
		
		save();
	}
	
	private void defaults() {
		props.setProperty(PATH_KEY, PATH_DEF);
		props.setProperty(MASK_KEY, MASK_DEF);
		props.setProperty(DATA_FMT_KEY, DATA_FMT_DEF);
	}
	
	private void load() {
		try {
			FileInputStream fis = new FileInputStream(PROP_FILE);
			props.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			LOGGER.warn("File '" + PROP_FILE + " not found."
					+ " Default settings will be used.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void save() {
		try {
			FileOutputStream fos = new FileOutputStream(PROP_FILE);
			props.store(fos, "xml-parser settings file");
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getPath() {
		return props.getProperty(PATH_KEY, PATH_DEF);
	}

	public void setPath(String path) {
		props.setProperty(PATH_KEY, path);
	}
	
	public String getMask() {
		return props.getProperty(MASK_KEY, MASK_DEF);
	}

	public void setMask(String mask) {
		props.setProperty(MASK_KEY, mask);
	}
	
	public String getDataFormat() {
		return props.getProperty(DATA_FMT_KEY, DATA_FMT_DEF);
	}

	public void setDataFormat(String format) {
		props.setProperty(DATA_FMT_KEY, format);
	}

	public void processArgs(final String[] args) {
		String path = (args.length != 0 && !args[0].isEmpty()) ? args[0] : DATA_PATH;
		
		if (!(PathValidator.isValid(path))) {
			LOGGER.error("Path to XML data files invalid");
		} else {
			props.setProperty(PATH_KEY, path);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Settings.path='" + props.getProperty(PATH_KEY, PATH_DEF) + "'");
		
		return sb.toString();
	}
}
