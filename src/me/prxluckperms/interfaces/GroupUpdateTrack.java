package me.prxluckperms.interfaces;

import java.util.UUID;

import me.prxluckperms.EZLuckPerms;
import me.prxluckperms.PRXLuckPerms;

public class GroupUpdateTrack implements IGroupUpdate {

	private PRXLuckPerms plugin;
	private String trackName;
	private String groupName;
	private String serverName;

	public GroupUpdateTrack(PRXLuckPerms plugin) {
		this.plugin = plugin;
		this.trackName = this.plugin.getTrackName();
		this.groupName = this.plugin.getResetPlayerGroupName();
		this.serverName = this.plugin.getServerName();
	}

	@Override
	public void set(UUID uuid) {
		if (serverName != null)
			EZLuckPerms.setPlayerServerGroup(uuid, EZLuckPerms.getGroup(groupName), trackName, serverName);
		else
			EZLuckPerms.setPlayerGroup(uuid, EZLuckPerms.getGroup(groupName), trackName);
	}

}
