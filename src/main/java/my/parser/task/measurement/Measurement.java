/**
 * 
 */
package my.parser.task.measurement;

import java.util.Date;
import java.util.Map.Entry;

import my.parser.task.ValueContainer;

import org.springframework.stereotype.Component;

/**
 * @author victor
 *
 */
@Component
public class Measurement {
	private ValueContainer values = null;
	private int id = 0;
	private Long timestamp = null;
	private String info = null;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public ValueContainer getValues() {
		return values;
	}

	public void setValues(ValueContainer values) {
		this.values = values;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if (info != null)
			sb.append("data.info='" + info + "'\n");
		
		if (timestamp != 0)
			sb.append("data.entry.timestamp=" + new Date(timestamp) + "\n");

		if (values != null) {
			for (Entry<String, Double> entry : values.getValues()) {
				sb.append("data.entry.value: " + entry.getKey() +
						"=" + entry.getValue() + "\n");
			}
		}
		
		return sb.toString();
	}
}
