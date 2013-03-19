/**
 * 
 */
package my.parser.task.measurement.dao;

import my.parser.task.measurement.Measurement;

/**
 * @author victor
 *
 */
public interface MeasurementDAO {
	public void insert(Measurement measurement);
	public Measurement findByCustomerId(int measurementId);
}
