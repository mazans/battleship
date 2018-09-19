package com.gmail.sergiusz.mazan.ships.communication;
import java.io.Serializable;
import java.util.Arrays;

public class Ship implements Cloneable, Serializable{
	
	private static final long serialVersionUID = -4514922481650256194L;
	
	private int size;
	private Direction direction;
	private Point[] pos;
	private boolean[] isDestroyed;
	
	public Ship(ShipSize shipSize, Direction direction, Point position) {
		
		this.size = shipSize.toInt();
		this.direction = direction;
		
		initializePosition(position);
		
		isDestroyed = new boolean[this.size];
		Arrays.fill(isDestroyed, false);
	}
	
	private void initializePosition(Point point) {
		pos = new Point[size];
		if(direction.equals(Direction.HORIZONTAL)) {
			for(int i = 0; i < size; i++) {
				pos[i] = new Point(point.getX()+i, point.getY());
			}
		}
		else if(direction.equals(Direction.VERTICAL)) {
			for(int i = 0; i < size; i++) {
				pos[i] = new Point(point.getX(), point.getY()+i);
			}
		}
	}
	
	public void setPosition(Point point) {
		if(direction == Direction.HORIZONTAL) {
			for(int i = 0; i < size; i++) {
				pos[i].setX(point.getX() + i);
				pos[i].setY(point.getY());
			}
		}
		else if(direction == Direction.VERTICAL) {
			for(int i = 0; i < size; i++) {
				pos[i].setX(point.getX());
				pos[i].setY(point.getY() + i);
			}
		}
	}
	
	public Point getPos(int i) {
		return (Point) pos[i].clone();
	}
	
	public ShipSize getSize() {
		return ShipSize.getShipSizeEnum(size);
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void changeDirection() {
		if(direction == Direction.HORIZONTAL)
			direction = Direction.VERTICAL;
		else if(direction == Direction.VERTICAL)
			direction = Direction.HORIZONTAL;
		setPosition(pos[0]);
	}
	
	public boolean isCollision(Ship otherShip) {
		boolean isCollision = false;
		for(int i = 0; i < size; i++) {
			for(int j =0; j < otherShip.size; j++) {
				if(pos[i].equals(otherShip.pos[j])) {
					isCollision = true;
					break;
				}
			}
		}
		return isCollision;
	}
	
	public boolean shoot(Point point) {
		boolean isShot = false;
		for(int i = 0; i < size; i++) {
			if(point.equals(pos[i]) && !isDestroyed[i]) {
				isShot = true;
				isDestroyed[i] = true;
				break;
			}
		}
		return isShot;
	}
	
	public boolean isPartofShipDestroyed(int i) {
		return isDestroyed[i];
	}
	
	@Override
	public Object clone() {
		Ship result = new Ship(this.getSize(), direction, pos[0]);
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + Arrays.hashCode(isDestroyed);
		result = prime * result + Arrays.hashCode(pos);
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ship other = (Ship) obj;
		if (direction != other.direction)
			return false;
		if (!Arrays.equals(isDestroyed, other.isDestroyed))
			return false;
		if (!Arrays.equals(pos, other.pos))
			return false;
		if (size != other.size)
			return false;
		return true;
	}
}