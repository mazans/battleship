package com.gmail.sergiusz.mazan.ships.model;

import java.util.ArrayList;
import java.util.List;

/*
 * This class represents CheckList for Ships placed by player
 * When server receives AddShipCommand and ship can actually be placed on Field
 * It removes ship from the list
 */
public class ShipsCheckList {
	
	private List<ShipSize> listOfShips;
	
	public ShipsCheckList(List<ShipSize> listOfShips) {
		this.listOfShips = new ArrayList<ShipSize>(listOfShips);
	}
	
	public boolean containsShipSize(ShipSize size) {
		return listOfShips.contains(size);
	}
	
	public void check(ShipSize size) {
		listOfShips.remove(size);
	}
	
	public boolean isAllChecked() {
		return listOfShips.isEmpty();
	}
	
	public List<ShipSize> getListOfShips() {
		List<ShipSize> result = new ArrayList<ShipSize>();
		for(ShipSize size : listOfShips) {
			result.add(size);
		}
		return result;
	}
}
