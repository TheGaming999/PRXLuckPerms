package me.prxluckperms.interfaces;

import java.util.UUID;

import me.prxluckperms.EasyLuckPerms;
import me.prxluckperms.PRXLuckPerms;

public class GroupUpdateTrackAndReset implements IGroupUpdate {

	private PRXLuckPerms plugin;
	private EasyLuckPerms ezLuckPerms;
	private String trackName;
	private String resetPlayerGroupName;
	
	public GroupUpdateTrackAndReset(PRXLuckPerms plugin) {
		this.plugin = plugin;
		this.ezLuckPerms = this.plugin.getEzLuckPerms();
		this.trackName = this.plugin.getTrackName();
		this.resetPlayerGroupName = this.plugin.getResetPlayerGroupName();
	}
	
	@Override
	public void set(UUID uuid) {
		ezLuckPerms.setPlayerGroup(uuid, ezLuckPerms.getGroup(resetPlayerGroupName), trackName);
	}

}
