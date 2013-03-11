/**
 * 
 */
package my.parser.task;

import java.util.ArrayList;
import my.parser.task.util.ValueEntry;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author victor
 *
 */
public class ValueContainer {
	private ArrayList<ValueEntry> values = null;
	
	public ValueContainer() {
		values = new ArrayList<ValueEntry>();
	}

	@Autowired
	public ArrayList<ValueEntry> getValues() {
		return values;
	}

	@Autowired
	public void setValues(ArrayList<ValueEntry> values) {
		this.values = values;
	}
	
	public boolean containsAll(ArrayList<ValueEntry> extValue) {
		boolean found = true;
		for (ValueEntry extEntry : extValue) {
			if (found) {
				found = false;
				for (ValueEntry entry : values) {
					if (entry.equals(extEntry)) {
						found = true;
						break;
					}
				}
			} else {
				return false;
			}
		}
		
		return found;
	}
}
