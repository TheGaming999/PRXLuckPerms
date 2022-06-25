package me.prxluckperms.interfaces;

import java.util.UUID;

import me.prxluckperms.EZLuckPerms;
import me.prxluckperms.PRXLuckPerms;

public class GroupUpdateTrackless implements IGroupUpdate {

	private PRXLuckPerms plugin;
	private String groupName;
	private String serverName;

	public GroupUpdateTrackless(PRXLuckPerms plugin) {
		this.plugin = plugin;
		this.groupName = this.plugin.getResetPlayerGroupName();
		this.serverName = this.plugin.getServerName();
	}

	@Override
	public void set(UUID uuid) {
		if (serverName != null)
			EZLuckPerms.setPlayerServerGroup(uuid, EZLuckPerms.getGroup(groupName), serverName);
		else
			EZLuckPerms.setPlayerGroup(uuid, EZLuckPerms.getGroup(groupName));
	}

}
