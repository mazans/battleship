package com.gmail.sergiusz.mazan.ships.model;

import java.io.Serializable;

public enum ShipSize implements Serializable {
	TWO(2), 
	THREE(3), 
	FOUR(4), 
	SIX(6);
	
	int size;
	
	ShipSize(int size) {
		this.size =size;
	}
	
	public int toInt() {
		return size;
	}
	
	public static ShipSize getShipSizeEnum(int size) {
		if(size == 2)
			return TWO;
		else if(size == 3)
			return THREE;
		else if(size == 4)
			return FOUR;
		else if(size == 6)
			return SIX;
		else
			return null;
	}
}