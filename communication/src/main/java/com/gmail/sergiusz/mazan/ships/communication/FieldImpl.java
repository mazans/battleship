package com.gmail.sergiusz.mazan.ships.communication;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FieldImpl implements Field {
	
	private int width, height;
	private Set<Ship> ships = new HashSet<Ship>();
	private int partsOfShips = 0;
	
	public FieldImpl(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public boolean canBePlaced(Ship newShip) {
		if(newShip == null) {
			return false;
		}
		
		for(Ship ship : ships) {
			if(ship.isCollision(newShip)) {
				return false;
			}
		}
		
		Point firstPart = newShip.getPos(0);
		Point lastPart = newShip.getPos(newShip.getSize().toInt() - 1);
		
		if(firstPart.getX() < 0 || firstPart.getY() < 0 ||
				lastPart.getX() >= width || lastPart.getY() >= height)
			return false;
		else
			return true;
	}
	
	public boolean addShip(Ship newShip) {
		if(newShip == null || !canBePlaced(newShip))
			return false;
		else {
			ships.add((Ship) newShip.clone());
			partsOfShips += newShip.getSize().toInt();
			return true;
		}
	}
	
	public boolean shootShip(Point point) {
		boolean isHit = false;
		for(Ship ship : ships) {
			if(ship.shoot(point)) {
				isHit = true;
				partsOfShips -= 1;
				break;
			}
		}
		return isHit;
	}
	
	public boolean isFleetSubmerged() {
		return partsOfShips == 0;
	}

	protected int getWidth() {
		return width;
	}

	protected int getHeight() {
		return height;
	}
	
	protected Collection<Ship> getShips() {
		Collection<Ship> result = new HashSet<Ship>();
		for(Ship ship : ships)
			result.add((Ship) ship.clone());
		return result;
	}
}
	
