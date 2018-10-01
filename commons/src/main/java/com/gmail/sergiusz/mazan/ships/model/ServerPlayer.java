package com.gmail.sergiusz.mazan.ships.model;

import com.gmail.sergiusz.mazan.ships.model.Field;
import com.gmail.sergiusz.mazan.ships.model.GameState;
import com.gmail.sergiusz.mazan.ships.model.ShipsCheckList;

/*
 * This class represents model of player on server side
 */
public class ServerPlayer {
	
	private Field field;
	private ServerPlayer opponent;
	private ShipsCheckList checkList;
	private GameState gameState;
	
	public ServerPlayer(Field field, ShipsCheckList checkList) {
		this.field = field;
		this.checkList = checkList;
		this.gameState = GameState.NEW;
	}
	
	public void setOpponent(ServerPlayer opponent) {
		this.opponent = opponent;
	}

	public ServerPlayer getOpponent() {
		return opponent;
	}

	public ShipsCheckList getCheckList() {
		return checkList;
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
}