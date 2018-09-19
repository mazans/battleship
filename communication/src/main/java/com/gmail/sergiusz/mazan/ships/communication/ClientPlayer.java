package com.gmail.sergiusz.mazan.ships.communication;

public abstract class ClientPlayer {
	
	private CheckField checkField;
	private Field field;
	private GameState gameState;
	
	public ClientPlayer(Field field, CheckField checkField) {
		this.field = field;
		this.checkField = checkField;
		this.gameState = GameState.NEW;
	}
	
	public CheckField getCheckField() {
		return checkField;
	}
	
	public Field getField() {
		return field;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	public abstract void sendClientCommand(ClientCommand commmand);
}
