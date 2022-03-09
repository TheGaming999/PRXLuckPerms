package me.prxluckperms.interfaces;

import java.util.UUID;

import me.prxluckperms.EasyLuckPerms;
import me.prxluckperms.PRXLuckPerms;

public class GroupUpdateTracklessAndReset implements IGroupUpdate {

	private PRXLuckPerms plugin;
	private EasyLuckPerms ezLuckPerms;
	private String resetPlayerGroupName;
	
	public GroupUpdateTracklessAndReset(PRXLuckPerms plugin) {
		this.plugin = plugin;
		this.ezLuckPerms = this.plugin.getEzLuckPerms();
		this.resetPlayerGroupName = this.plugin.getResetPlayerGroupName();
	}
	
	@Override
	public void set(UUID uuid) {
		ezLuckPerms.setPlayerGroup(uuid, ezLuckPerms.getGroup(resetPlayerGroupName));
	}

}
