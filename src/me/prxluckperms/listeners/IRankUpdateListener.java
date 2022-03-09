package me.prxluckperms.listeners;

import org.bukkit.event.Listener;

import me.prisonranksx.events.AsyncAutoRankupEvent;
import me.prisonranksx.events.AsyncRankupMaxEvent;
import me.prisonranksx.events.RankUpdateEvent;

public interface IRankUpdateListener extends Listener {

	void onRankup(RankUpdateEvent e);
	void onAutoRankup(AsyncAutoRankupEvent e);
	void onRankupMax(AsyncRankupMaxEvent e);
	
}
