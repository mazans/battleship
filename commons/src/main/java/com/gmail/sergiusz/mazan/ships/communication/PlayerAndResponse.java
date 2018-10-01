package com.gmail.sergiusz.mazan.ships.communication;

import com.gmail.sergiusz.mazan.ships.model.ServerPlayer;

public class PlayerAndResponse {
	private ServerPlayer player;
	private ServerResponse response;
	
	public PlayerAndResponse(ServerPlayer player, ServerResponse response) {
		this.player = player;
		this.response = response;
	}

	public ServerPlayer getPlayer() {
		return player;
	}

	public ServerResponse getResponse() {
		return response;
	}
}
