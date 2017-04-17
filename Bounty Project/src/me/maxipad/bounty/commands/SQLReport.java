package me.maxipad.bounty.commands;

public class SQLReport {
	
	private String PlayerName;
	private int CurrentBounty;
	private String PlayerUUID;

	
	public String getPlayerName() {
		return PlayerName;
	}

	public void getPlayerName(String PlayerName) {
		this.PlayerName = PlayerName;
	}
	
	public int getCurrentBounty() {
		return CurrentBounty;
	}

	public void getCurrentBounty(int CurrentBounty) {
		this.CurrentBounty = CurrentBounty;
	}
	
	public String getPlayerUUID() {
		return PlayerUUID;
	}

	public void getPlayerUUID(String PlayerUUID) {
		this.PlayerUUID = PlayerUUID;
	}
	
}
