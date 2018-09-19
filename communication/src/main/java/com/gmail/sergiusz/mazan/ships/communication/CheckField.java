package com.gmail.sergiusz.mazan.ships.communication;

public interface CheckField {
	
	public void setShotResult(Point pos, ShotResult result);
	public ShotResult getShotResult(Point point);
	
}