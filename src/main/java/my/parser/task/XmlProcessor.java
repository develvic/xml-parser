/**
 * 
 */
package my.parser.task;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import my.parser.task.measurement.Measurement;
import my.parser.task.util.ValueEntry;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author victor
 *
 */
@Component
public class XmlProcessor implements Callable<Measurement> {
	private final static String TAG_ENTRY = "entry";
	private final static String ATTR_INFO = "info";
	private final static String ATTR_TIME = "timestamp";
	private final static String ATTR_NAME = "name";
	private final static String TAG_VALUE = "value";
	
	private String file = null;
	private Document dom = null;
	private Measurement meter = null;
	
	private DateFormat dateFormat = null;
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	public XmlProcessor() {
	}
	
	public XmlProcessor(final String file, final Settings settings) {
		this.file = file;
		dateFormat = new SimpleDateFormat(settings.getDataFormat());
	}
	
	public Measurement getMeter() {
		return meter;
	}
	
	private void parseXmlFile() {
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			//parse using builder to get DOM representation of the XML file
			dom = db.parse(file);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private void parseDocument() {
		// get the root element
		Element docElement = dom.getDocumentElement();
		//get a nodelist of elements
		NodeList nl = docElement.getElementsByTagName(TAG_ENTRY);
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				// get the entry element
				Element element = (Element)nl.item(i);
				// create the meter object
				createMeter(element);
			}
		}
	}
	
	private void createMeter(final Element element) {
		final String info = element.getAttribute(ATTR_INFO);
		ValueContainer values = new ValueContainer();//App.context.getBean(ValueContainer.class);
		String time = element.getAttribute(ATTR_TIME);
		Date date = null;
		
		try {
			date = dateFormat.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long timestamp = date.getTime();
		
		NodeList vnl = element.getChildNodes();
		if (vnl != null && vnl.getLength() > 0) {
			for (int j = 0; j < vnl.getLength(); j++) {
				Node node = vnl.item(j);
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element valueElement = (Element)node;
					
					String valueName = valueElement.getAttribute(ATTR_NAME);
					Double valueNumber = getDoubleValue(valueElement, TAG_VALUE);
					
					values.getValues().add(new ValueEntry(valueName, valueNumber));
				}
			}
		}
		
		//meter = (Meter) App.context.getBean("meter");
		meter = new Measurement();
		
		if (!values.getValues().isEmpty())
			meter.setValues(values);
		
		if (!info.isEmpty())
			meter.setInfo(info);
		
		if (timestamp != 0)
			meter.setTimestamp(timestamp);
	}

	private Double getDoubleValue(Element element, String tagName) {
		Double value = null;
		try {
			value = Double.parseDouble(element.getFirstChild().getNodeValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	@Override
	public Measurement call() throws Exception {
		parseXmlFile();
		parseDocument();
		
		return meter;
	}
}
