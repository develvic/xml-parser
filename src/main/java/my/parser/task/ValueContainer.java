/**
 * 
 */
package my.parser.task;

import java.util.ArrayList;
import java.util.Map.Entry;

/**
 * @author victor
 *
 */
public class ValueContainer {
	private ArrayList<Entry<String, Double>> values = null;
	
	public ValueContainer() {
		values = new ArrayList<Entry<String, Double>>();
	}

	public ArrayList<Entry<String, Double>> getValues() {
		return values;
	}

	public void setValues(ArrayList<Entry<String, Double>> values) {
		this.values = values;
	}
}
