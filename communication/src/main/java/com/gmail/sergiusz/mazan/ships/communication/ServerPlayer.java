package com.gmail.sergiusz.mazan.ships.communication;

public abstract class ServerPlayer {
	
	private Field field;
	private GameState gameState = GameState.NEW;
	private ServerPlayer opponent;
	private ShipsCheckList checkList;
	
	public ServerPlayer(Field field, ShipsCheckList checkList) {
		this.field = field;
		this.checkList = checkList;
	}
	
	public void setOpponent(ServerPlayer opponent) {
		this.opponent = opponent;
	}

	public ServerPlayer getOpponent() {
		return opponent;
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

	public ShipsCheckList getCheckList() {
		return checkList;
	}
	
	public abstract void sendServerCommand(ServerCommand commmand);
}