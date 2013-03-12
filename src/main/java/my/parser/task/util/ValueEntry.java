/**
 * 
 */
package my.parser.task.util;

import java.util.Map.Entry;

/**
 * @author victor
 *
 */
public class ValueEntry implements Entry<String, Double> {
	private String key = null;
	private Double value = null;
	
	public ValueEntry(String key, Double value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public Double setValue(Double value) {
		this.value = value;
		return this.value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		ValueEntry ve = (ValueEntry)obj;
		if (this.key.equals(ve.key) && this.value.equals(ve.value))
			return true;
		
		return false;
	}
}
