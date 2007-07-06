package com.bretth.osmosis.mysql;

import com.bretth.osmosis.mysql.impl.DatabaseContext;
import com.bretth.osmosis.task.RunnableTask;


/**
 * A standalone OSM task with no inputs or outputs that truncates tables in a
 * mysql database. This is used for removing all existing data from tables.
 * 
 * @author Brett Henderson
 */
public class MysqlTruncator implements RunnableTask {
	
	// These SQL statements will be invoked to truncate each table.
	private static final String INVOKE_TRUNCATE_NODE = "TRUNCATE nodes";
	private static final String INVOKE_TRUNCATE_SEGMENT = "TRUNCATE segments";
	private static final String INVOKE_TRUNCATE_WAY = "TRUNCATE ways";
	private static final String INVOKE_TRUNCATE_WAY_TAG = "TRUNCATE way_tags";
	private static final String INVOKE_TRUNCATE_WAY_SEGMENT = "TRUNCATE way_segments";
	
	
	private DatabaseContext dbCtx;
	
	
	/**
	 * Creates a new instance.
	 * 
	 * @param host
	 *            The server hosting the database.
	 * @param database
	 *            The database instance.
	 * @param user
	 *            The user name for authentication.
	 * @param password
	 *            The password for authentication.
	 */
	public MysqlTruncator(String host, String database, String user, String password) {
		dbCtx = new DatabaseContext(host, database, user, password);
	}
	
	
	/**
	 * Truncates all data from the database.
	 */
	public void run() {
		try {
			dbCtx.executeStatement(INVOKE_TRUNCATE_WAY_TAG);
			dbCtx.executeStatement(INVOKE_TRUNCATE_WAY_SEGMENT);
			dbCtx.executeStatement(INVOKE_TRUNCATE_WAY);
			dbCtx.executeStatement(INVOKE_TRUNCATE_SEGMENT);
			dbCtx.executeStatement(INVOKE_TRUNCATE_NODE);
			
		} finally {
			dbCtx.release();
		}
	}
}