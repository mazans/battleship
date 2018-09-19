package com.gmail.sergiusz.mazan.ships.server;

import java.util.ArrayList;
import java.util.List;

import com.gmail.sergiusz.mazan.ships.communication.ShipSize;
import com.gmail.sergiusz.mazan.ships.communication.ShipsCheckList;

public class ShipsCheckListImpl implements ShipsCheckList{
	
	private List<ShipSize> listOfShips = new ArrayList<ShipSize>();
	
	public ShipsCheckListImpl(List<ShipSize> listOfShips) {
		for(ShipSize size : listOfShips)
			this.listOfShips.add(size);
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
}
