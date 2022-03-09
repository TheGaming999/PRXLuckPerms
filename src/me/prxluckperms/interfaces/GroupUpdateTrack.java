package me.prxluckperms.interfaces;

import java.util.UUID;

import me.prxluckperms.EasyLuckPerms;
import me.prxluckperms.PRXLuckPerms;

public class GroupUpdateTrack implements IGroupUpdate {

	private PRXLuckPerms plugin;
	private EasyLuckPerms ezLuckPerms;
	private String trackName;
	private String defaultRank;
	
	public GroupUpdateTrack(PRXLuckPerms plugin) {
		this.plugin = plugin;
		this.ezLuckPerms = this.plugin.getEzLuckPerms();
		this.trackName = this.plugin.getTrackName();
		this.defaultRank = this.plugin.getAPI().getDefaultRank();
	}
	
	@Override
	public void set(UUID uuid) {
		ezLuckPerms.setPlayerGroup(uuid, ezLuckPerms.getGroup(defaultRank), trackName);
	}

}
