package com.gmail.sergiusz.mazan.ships.client;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.gmail.sergiusz.mazan.ships.model.Field;
import com.gmail.sergiusz.mazan.ships.model.GameState;
import com.gmail.sergiusz.mazan.ships.model.Point;
import com.gmail.sergiusz.mazan.ships.model.Ship;
import com.gmail.sergiusz.mazan.ships.model.ShotResult;

public class PlayerModel {
	
	private GameState gameState;
	private Field field;
	private CheckField checkField;
	private Ship shipToBePlaced = null;
	private FleetGenerator generator;
	private PropertyChangeSupport support;
	
	public PlayerModel(Field field, CheckField checkField, FleetGenerator generator) {
		this.field = field;
		this.checkField = checkField;
		this.generator = generator;
		support = new PropertyChangeSupport(this);
	}
	
	public void setPositionOfShip(Point point) {
		if(shipToBePlaced != null) {
			shipToBePlaced.setPosition(point);
			support.firePropertyChange("shipToBePlaced", null, this);
		}
	}
	
	public void changeDirectionOfShip() {
		if(shipToBePlaced != null) {
			shipToBePlaced.changeDirection();
			support.firePropertyChange("shipToBePlaced", null, this);
		}
	}
	
	public boolean canBePlacedShip() {
		return field.canBePlaced(shipToBePlaced);
	}
	
	public void setGameState(GameState gameState) {
		GameState oldState = this.gameState;
		this.gameState = gameState;
		support.firePropertyChange("stateChange", oldState, gameState);
		if(gameState == GameState.INITIALIZATING) {
			shipToBePlaced = generator.getNextShip();
			support.firePropertyChange("shipToBePlaced", null, this);
		}
	}
	
	public boolean placeShip() {
		if(field.addShip(shipToBePlaced)) {
			if(generator.isNextShip())
				shipToBePlaced = generator.getNextShip();
			else
				shipToBePlaced = null;
			support.firePropertyChange("addShip", null, this);
			return true;
		}
		else
			return false;
	}
	
	public void shootShip(Point position) {
		field.shootShip(position);
		support.firePropertyChange("shootShip", null, this);
	}

	public void setShotResult(Point position, ShotResult result) {
		checkField.setShotResult(position, result);
		support.firePropertyChange("shotResult", null, this);
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public Ship getShipToBePlaced() {
		return shipToBePlaced;
	}
	
	public Field getField() {
		return field;
	}
	
	public CheckField getCheckField() {
		return checkField;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener, String propertyName) {
		support.addPropertyChangeListener(propertyName, listener);
	}
}
