package com.gmail.sergiusz.mazan.ships.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gmail.sergiusz.mazan.ships.model.Direction;
import com.gmail.sergiusz.mazan.ships.model.GameState;
import com.gmail.sergiusz.mazan.ships.model.Point;
import com.gmail.sergiusz.mazan.ships.model.ShipSize;

public class Main {
	
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
	private static final int PIXEL_SIZE = 25;
	private static final Point DEFAULT_POSITION = new Point(-1, -1);
	private static final Direction DEFAULT_DIRECTION = Direction.HORIZONTAL;
	
	public static void main(String[] args) {
		Map<GameState, String> informationMap = new HashMap<GameState, String>();
		informationMap.put(GameState.NEW, "Waiting for start");
		informationMap.put(GameState.WAITING, "Waiting for another player");
		informationMap.put(GameState.INITIALIZATING, "Place your ships");
		informationMap.put(GameState.ATTACKING, "Choose position to shoot");
		informationMap.put(GameState.DEFEND, "Waiting for opponent's move");
		informationMap.put(GameState.WON, "Congratultions, you have won!");
		informationMap.put(GameState.LOST, "You lost!");
		informationMap.put(GameState.ENDED, "Game has ended");
		
		List<ShipSize> listOfShips = new ArrayList<ShipSize>();
		listOfShips.add(ShipSize.SIX);
		listOfShips.add(ShipSize.FOUR);
		listOfShips.add(ShipSize.FOUR);
		listOfShips.add(ShipSize.THREE);
		listOfShips.add(ShipSize.THREE);
		listOfShips.add(ShipSize.TWO);
		
		GameBuilder gameBuilder = new GameBuilder()
								.setWidth(WIDTH)
								.setHeight(HEIGHT)
								.setPixelSize(PIXEL_SIZE)
								.setDefaultShipPosition(DEFAULT_POSITION)
								.setDefaultShipDirection(DEFAULT_DIRECTION)
								.setListOfShips(listOfShips)
								.setInformationMap(informationMap);
		
		new OpeningWindow(gameBuilder).setVisible(true);
	}
}
