package my.parser.task;

import static org.junit.Assert.*;

import java.util.ArrayList;

import my.parser.task.measurement.Measurement;
import my.parser.task.util.ValueEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:Beans.xml" })
public class AppTest {
	private static final String TEST_XML_PATH = "../xml-files/m_MeterBackup1050711360.xml";
	private static final int MAX_ENTRIES = 12;

	private AnnotationConfigApplicationContext context = null;
	private Settings settings = null;
	private ArrayList<ValueEntry> shouldContainList = null; 
	
	@Before
	public void setUp() {
		context = new AnnotationConfigApplicationContext("my.parser.task");
		settings = (Settings)context.getBean("settings");
		
		shouldContainList = new ArrayList<ValueEntry>(); 
		shouldContainList.ensureCapacity(MAX_ENTRIES);
		
		shouldContainList.add(new ValueEntry("AC1KWH", 289573.65625));
		shouldContainList.add(new ValueEntry("AC3KW", 10.734375));
		shouldContainList.add(new ValueEntry("AC2KW", 8.828125));
		shouldContainList.add(new ValueEntry("HCKWH", 202993.6875));
		shouldContainList.add(new ValueEntry("AC3KWH", 308527.0625));
		shouldContainList.add(new ValueEntry("AC2KWH", 163895.1875));
		shouldContainList.add(new ValueEntry("MainKW", 127.2));
		shouldContainList.add(new ValueEntry("AC4KW", 15.140625));
		shouldContainList.add(new ValueEntry("AC4KWH", 205004.21875));
		shouldContainList.add(new ValueEntry("AC1KW", 10.65625));
		shouldContainList.add(new ValueEntry("MainScaledKWH", 4771077.12));
		shouldContainList.add(new ValueEntry("HCKW", 5.1875));
	}
	
	@After
	public void tearDown() {
		context.close();
	}
	
	@Test
	public void testXmlProcessor() {
		XmlProcessor processor = new XmlProcessor(TEST_XML_PATH, settings);
		try {
			processor.call();
		} catch (Exception e) {
			//assertTrue(m != null);
			e.printStackTrace();
		}
		
		Measurement m = processor.getMeter();
		assertNotNull(m);
		
		assertTrue(m.getTimestamp() != 0);
		
		ValueContainer container = m.getValues();
		assertNotNull(container);
		assertTrue(container.getValues().size() == MAX_ENTRIES);
		assertTrue(container.containsAll(shouldContainList));
	}
}
