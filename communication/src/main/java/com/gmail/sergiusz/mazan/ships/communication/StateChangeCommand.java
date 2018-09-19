package com.gmail.sergiusz.mazan.ships.communication;

public class StateChangeCommand implements ServerCommand {
	

	private static final long serialVersionUID = 1L;
	private GameState gameState;
	
	public StateChangeCommand(GameState gameState) {
		this.gameState = gameState;
	}



	@Override
	public void execute(ClientPlayer player) {
		player.setGameState(gameState);
	}

}
