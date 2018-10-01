package com.gmail.sergiusz.mazan.ships.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gmail.sergiusz.mazan.ships.model.Direction;
import com.gmail.sergiusz.mazan.ships.model.Point;
import com.gmail.sergiusz.mazan.ships.model.Ship;
import com.gmail.sergiusz.mazan.ships.model.ShipSize;

/*
 * Generates ships which should be put on Field
 * FleetGenerator must be initialized the same List of ShipSize as server's ShipsCheckList
 */
public class FleetGenerator {
	
	private List<ShipSize> sizes;
	private Point defaultPosition;
	private Direction defaultDirection;
	
	private Iterator<ShipSize> iterator;
	
	public FleetGenerator(List<ShipSize> sizes, Direction defaultDirection, Point defaultPosition) {
		this.sizes = new ArrayList<ShipSize>(sizes);
		this.defaultPosition = (Point) defaultPosition.clone();
		this.defaultDirection = defaultDirection;
		
		iterator = this.sizes.iterator();
	}
	
	public boolean isNextShip() {
		return iterator.hasNext();
	}
	
	public Ship getNextShip() {
		return new Ship(iterator.next(), defaultDirection, defaultPosition);
	}
	
	public void reset() {
		iterator = sizes.iterator();
	}
}