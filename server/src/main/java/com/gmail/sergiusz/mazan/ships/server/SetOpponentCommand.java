package com.gmail.sergiusz.mazan.ships.server;

import java.util.List;

import com.gmail.sergiusz.mazan.ships.communication.ClientCommand;
import com.gmail.sergiusz.mazan.ships.communication.PlayerAndResponse;
import com.gmail.sergiusz.mazan.ships.model.ServerPlayer;

public class SetOpponentCommand extends ClientCommand {
	
	private static final long serialVersionUID = 1L;
	private ServerPlayer opponent;

	public SetOpponentCommand(ServerPlayer player, ServerPlayer opponent) {
		setPlayer(player);
		this.opponent = opponent;
	}

	@Override
	public List<PlayerAndResponse> execute() {
		getPlayer().setOpponent(opponent);
		return null;
	}
	
}
