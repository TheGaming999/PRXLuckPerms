package me.prxluckperms.interfaces;

import java.util.UUID;

import me.prxluckperms.EasyLuckPerms;
import me.prxluckperms.PRXLuckPerms;

public class GroupUpdateTrackless implements IGroupUpdate {

	private PRXLuckPerms plugin;
	private EasyLuckPerms ezLuckPerms;
	private String defaultRank;
	
	public GroupUpdateTrackless(PRXLuckPerms plugin) {
		this.plugin = plugin;
		this.ezLuckPerms = this.plugin.getEzLuckPerms();
		this.defaultRank = this.plugin.getAPI().getDefaultRank();
	}
	
	@Override
	public void set(UUID uuid) {
		ezLuckPerms.setPlayerGroup(uuid, ezLuckPerms.getGroup(defaultRank));
	}

}
