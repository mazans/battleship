package com.gmail.sergiusz.mazan.ships.server;

import java.util.ArrayList;
import java.util.List;

import com.gmail.sergiusz.mazan.ships.communication.ClientCommand;
import com.gmail.sergiusz.mazan.ships.communication.PlayerAndResponse;
import com.gmail.sergiusz.mazan.ships.communication.ServerResponse;
import com.gmail.sergiusz.mazan.ships.model.GameState;
import com.gmail.sergiusz.mazan.ships.model.ServerPlayer;

public class QuitCommand extends ClientCommand {

	private static final long serialVersionUID = 1L;

	@Override
	public List<PlayerAndResponse> execute() {
		List<PlayerAndResponse> result = new ArrayList<PlayerAndResponse>();
		ServerPlayer player = getPlayer();
		if(player.getGameState() != GameState.WON && player.getGameState() != GameState.LOST
				&& player.getOpponent() != null) {
			player.getOpponent().setGameState(GameState.WON);
			result.add(new PlayerAndResponse(player.getOpponent(), new ServerResponse(GameState.WON)));
		}
		player.setGameState(GameState.ENDED);
		return result;
	}
}
