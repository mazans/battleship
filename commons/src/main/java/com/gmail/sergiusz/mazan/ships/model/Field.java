package com.gmail.sergiusz.mazan.ships.model;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/*
 * This class represents game field on which player can put its ships
 * During game ships on this field are shot by opponent
 */
public class Field {
	
	private int width, height;
	private Set<Ship> ships = new HashSet<Ship>();
	private int partsOfShips = 0;
	
	public Field(int width, int height) {
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
		
		//verifying if Ship fits in Field
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Collection<Ship> getShips() {
		Collection<Ship> result = new HashSet<Ship>();
		for(Ship ship : ships)
			result.add((Ship) ship.clone());
		return result;
	}
}
	
