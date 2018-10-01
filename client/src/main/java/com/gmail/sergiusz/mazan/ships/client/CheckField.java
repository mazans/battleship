package com.gmail.sergiusz.mazan.ships.client;

import java.util.Arrays;

import com.gmail.sergiusz.mazan.ships.model.Point;
import com.gmail.sergiusz.mazan.ships.model.ShotResult;

/*
 * This class is model for field on which Battleship player marks position which has been shot
 */
public class CheckField {

	int width, height;
	ShotResult[][] position;
	
	public CheckField(int width, int height) {
		this.width = width;
		this.height = height;
		position = new ShotResult[height][width];
		for(int i = 0; i < height; i++)
			Arrays.fill(position[i], ShotResult.NONE);
	}
	
	public void setShotResult(Point pos, ShotResult result) {
		position[pos.getY()][pos.getX()] = result;
	}
	
	public ShotResult getShotResult(Point pos) {
		return position[pos.getY()][pos.getX()];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}