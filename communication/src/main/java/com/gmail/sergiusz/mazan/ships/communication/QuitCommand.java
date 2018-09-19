package com.gmail.sergiusz.mazan.ships.communication;

public class QuitCommand implements ClientCommand {

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(ServerPlayer player) {
		player.setGameState(GameState.ENDED);
	}
}
