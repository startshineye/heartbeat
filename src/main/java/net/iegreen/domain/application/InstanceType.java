package net.iegreen.domain.application;
/***
 * 
 * @author pzy
 *
 */
public enum InstanceType {
		URL("URL"),
	    DATABASE("DATABASE"),
		FTP("FTP");
	 	private String label;

	    private InstanceType(String label) {
	        this.label = label;
	    }
	    public String getLabel() {
	        return label;
	    }
	    public String getValue() {
	        return name();
	    }
	    public String getStatus() {
	        return label;
	    }
	   
}
