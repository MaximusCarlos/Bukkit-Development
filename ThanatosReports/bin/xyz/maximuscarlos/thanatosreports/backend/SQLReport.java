package xyz.maximuscarlos.thanatosreports.backend;

public class SQLReport {
	
	private String ReportingPlayer;
	private String ReportedPlayer;
	private String Reason;
	private int ReportID;
	
	public String getReportingPlayer() {
		return ReportingPlayer;
	}

	public void getReportingPlayer(String ReportingPlayer) {
		this.ReportingPlayer = ReportingPlayer;
	}
	
	public String getReportedPlayer() {
		return ReportedPlayer;
	}

	public void getReportedPlayer(String ReportedPlayer) {
		this.ReportedPlayer = ReportedPlayer;
	}
	
	public String getReason() {
		return Reason;
	}

	public void getReason(String Reason) {
		this.Reason = Reason;
	}
	
	public int getReportID() {
		return ReportID;
	}

	public void getReportID(int ReportID) {
		this.ReportID = ReportID;
	}

}
