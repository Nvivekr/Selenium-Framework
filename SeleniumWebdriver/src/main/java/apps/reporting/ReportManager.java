package apps.reporting;

import apps.reporting.Reporter;

public class ReportManager {

	private Reporter reporter;
	
	public ReportManager(Reporter reporter){
		this.reporter = reporter;
	}
	
	public Reporter getReporter() {
		return (reporter == null) ? reporter = new Reporter() : reporter;
	}	
	
}
