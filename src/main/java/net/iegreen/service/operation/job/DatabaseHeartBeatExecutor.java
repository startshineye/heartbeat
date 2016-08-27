package net.iegreen.service.operation.job;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.log.FrequencyMonitorLog;

/**
 * @author Shengzhao Li
 */
public class DatabaseHeartBeatExecutor extends PerHeartBeatExecutorTemplate {

	public DatabaseHeartBeatExecutor(String instanceGuid) {
		super(instanceGuid);
	}

	@Override
	public FrequencyMonitorLog generateMonitorLog(ApplicationInstance instance) {
		FrequencyMonitorLog monitorLog = new FrequencyMonitorLog();

		final long start = System.currentTimeMillis();
		try {
			Class.forName(instance.databaseType().getDriver());
			Connection con = DriverManager.getConnection(instance.sqlurl(), instance.username(), instance.password());
			Statement stmt = con.createStatement();
			stmt.execute(instance.testsql());
			con.close();
			stmt.close();
			monitorLog.normal(true).costTime(System.currentTimeMillis() - start);
		} catch (Exception e) {
			monitorLog.costTime(System.currentTimeMillis() - start).normal(false)
					.remark(e.getClass().getSimpleName() + ": " + e.getMessage());
		}
		monitorLog.instance(instance);
		return monitorLog;
	}

}