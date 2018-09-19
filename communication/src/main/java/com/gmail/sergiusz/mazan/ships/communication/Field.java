package com.gmail.sergiusz.mazan.ships.communication;

public interface Field {
	
	public boolean canBePlaced(Ship newShip);
	public boolean addShip(Ship newShip);
	public boolean shootShip(Point point);
	public boolean isFleetSubmerged();
	
}
