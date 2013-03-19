/**
 * 
 */
package my.parser.task.measurement.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import my.parser.task.measurement.Measurement;
import my.parser.task.measurement.dao.MeasurementDAO;

/**
 * @author victor
 *
 */
public class JdbcMeasurementDAO implements MeasurementDAO {
	private DataSource dataSource;
	/**
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/* (non-Javadoc)
	 * @see my.parser.task.measurement.dao.MeasurementDAO#insert(my.parser.task.measurement.Measurement)
	 */
	@Override
	public void insert(Measurement measurement) {
		String sql = "INSERT INTO DATE " +
				//"(DATE, INFO, ID) VALUES (?, ?, ?)";
				"(DATE, INFO) VALUES (?, ?)";
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, new Date(measurement.getTimestamp()));
			ps.setString(2, measurement.getInfo());
			//ps.setInt(3, measurement.getId());
			ps.executeUpdate();
			ps.close();
 
		} catch (SQLException e) {
			throw new RuntimeException(e);
 		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	/* (non-Javadoc)
	 * @see my.parser.task.measurement.dao.MeasurementDAO#findByCustomerId(int)
	 */
	@Override
	public Measurement findByCustomerId(int measurementId) {
		String sql = "SELECT * FROM CUSTOMER WHERE CUST_ID = ?";
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(3, measurementId);
			Measurement measurement = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				/*
				measurement = new Measurement(
					rs.getDate(1),
					rs.getString("INFO"), 
					rs.getInt("ID")
				);
				*/
			}
			rs.close();
			ps.close();
			return measurement;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}

}
