package com.gmail.sergiusz.mazan.ships.communication;

import java.io.Serializable;
import java.util.List;

import com.gmail.sergiusz.mazan.ships.model.ServerPlayer;

/*
 * Abstract class which represents command sent by client
 * When server receives subclass of ClientCommand, it sets ServerPlayer bind with client and then executes command
 */
public abstract class ClientCommand implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ServerPlayer player;
	
	public void setPlayer(ServerPlayer player) {
		this.player = player;
	}
	
	protected ServerPlayer getPlayer() {
		return player;
	}
	
	public abstract List<PlayerAndResponse> execute();
	
}
