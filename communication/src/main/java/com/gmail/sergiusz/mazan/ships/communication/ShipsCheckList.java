package com.gmail.sergiusz.mazan.ships.communication;

public interface ShipsCheckList {
	
	public boolean containsShipSize(ShipSize size);
	public void check(ShipSize size);
	public boolean isAllChecked();
}
