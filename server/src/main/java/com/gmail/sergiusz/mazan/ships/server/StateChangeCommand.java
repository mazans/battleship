package com.gmail.sergiusz.mazan.ships.server;

import java.util.ArrayList;
import java.util.List;

import com.gmail.sergiusz.mazan.ships.communication.ClientCommand;
import com.gmail.sergiusz.mazan.ships.communication.PlayerAndResponse;
import com.gmail.sergiusz.mazan.ships.communication.ServerResponse;
import com.gmail.sergiusz.mazan.ships.model.GameState;
import com.gmail.sergiusz.mazan.ships.model.ServerPlayer;

public class StateChangeCommand extends ClientCommand {

	private static final long serialVersionUID = 1L;
	private GameState changedState;
	
	public StateChangeCommand(GameState changedState) {
		this.changedState = changedState;
	}
	
	@Override
	public List<PlayerAndResponse> execute() {
		List<PlayerAndResponse> result = new ArrayList<PlayerAndResponse>();
		ServerPlayer player = getPlayer();
		player.setGameState(changedState);
		result.add(new PlayerAndResponse(player, new ServerResponse(changedState)));
		return result;
	} 
}
