package net.iegreen.domain.application;

public enum DatabaseType {
		
		MYSQL("MYSQL","com.mysql.jdbc.Driver"),
		
		SQLSERVER("SQLSERVER","com.microsoft.jdbc.sqlserver.SQLServerDriver");
		
	 	private String name,driver;

	    private DatabaseType(String name,String driver) {
	        this.name = name;
	        this.driver = driver;
	    }

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDriver() {
			return driver;
		}

		public void setDriver(String driver) {
			this.driver = driver;
		}
	    
	   
}
