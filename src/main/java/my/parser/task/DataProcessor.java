/**
 * 
 */
package my.parser.task;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author victor
 *
 */
public class DataProcessor {
	private final static Logger LOGGER = Logger.getLogger(DataProcessor.class);
	
	private ResultContainer rc = null;
	private ArrayList<Meter> results = null;
	private Collection<Future<?>> futures = null;
	
	@Autowired
	public DataProcessor(ResultContainer rc) {
		this.rc = rc;
		results = this.rc.getResults();
	}
	
	public void init() {
		futures = new ArrayList<Future<?>>();
	}
	
	public void destroy() {
		futures.clear();
	}
	
	private void prepareResults() {
		if (results == null)
			results = new ArrayList<Meter>(futures.size());
		else
			results.ensureCapacity(futures.size());
		
		for (Future<?> future : futures) {
		    try {
				results.add((Meter) future.get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void process(final String path) {
		results.clear();
		final Settings settings = App.context.getBean(Settings.class);
		
		File file = new File(path);
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.matches(settings.getMask());
			}
		};
		String[] xmlFiles = file.list(filter);
		
		if (xmlFiles.length == 0) {
			LOGGER.error("No appropriate XML files in '" + path + "'");
		} else {
			LOGGER.info(xmlFiles.length + " file(s) found in '" + path + "'");
		}
		
		// start timer
		long start = System.currentTimeMillis();
		
		ExecutorService executor = Executors.newCachedThreadPool();
		for (String xmlFile : xmlFiles) {
			StringBuilder sb = new StringBuilder(path);
			if (path.lastIndexOf(File.separatorChar) != path.length() - 1) {
				sb.append(File.separatorChar);
			}
			final String filePath = sb + xmlFile;
			
			futures.add(executor.submit(new XmlProcessor(filePath, settings)));
		}
		executor.shutdown();
		
		// stop timer & calc elapsed time
		long elapsedTime = (System.currentTimeMillis() - start);
		
		LOGGER.info("All tasks executed time: " + elapsedTime + " ms.\n");
		
		prepareResults();
	}

	public void print() {
		rc.printResults();
		
		Profiling.printTotal();
		Profiling.printAverage();
	}
}
