/**
 * 
 */
package my.parser.task;

import java.util.ArrayList;

import my.parser.task.measurement.Measurement;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author victor
 *
 */
class ResultContainer implements InitializingBean, DisposableBean {
	private static final Logger LOGGER = Logger.getLogger(ResultContainer.class);

	private ArrayList<Measurement> results = null;
	
	public ArrayList<Measurement> getResults() {
		return results;
	}

	public void setResults(ArrayList<Measurement> results) {
		this.results = results;
	}

	public void printResults() {
		for (Measurement meter : results) {
			LOGGER.info(meter.toString());
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		results = new ArrayList<Measurement>();
	}

	@Override
	public void destroy() throws Exception {
		if (results != null)
			results.clear();
	}
}