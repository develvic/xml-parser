/**
 * 
 */
package my.parser.task;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author victor
 *
 */
class ResultContainer implements InitializingBean, DisposableBean {
	private static final Logger LOGGER = Logger.getLogger(ResultContainer.class);

	private ArrayList<Meter> results = null;
	
	public ArrayList<Meter> getResults() {
		return results;
	}

	public void setResults(ArrayList<Meter> results) {
		this.results = results;
	}

	public void printResults() {
		for (Meter meter : results) {
			LOGGER.info(meter.toString());
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		results = new ArrayList<Meter>();
	}

	@Override
	public void destroy() throws Exception {
		if (results != null)
			results.clear();
	}
}