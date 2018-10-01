package com.gmail.sergiusz.mazan.ships.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import com.gmail.sergiusz.mazan.ships.model.Direction;
import com.gmail.sergiusz.mazan.ships.model.Field;
import com.gmail.sergiusz.mazan.ships.model.GameState;
import com.gmail.sergiusz.mazan.ships.model.Point;
import com.gmail.sergiusz.mazan.ships.model.ShipSize;

public class GameBuilder {
	
	private String ip = "localhost";
	private int port = 6000;
	
	private int width, height;
	private int pixelSize;
	private Map<GameState, String> informationMap;
	
	private List<ShipSize> listOfShips;
	private Point defaultShipPosition;
	private Direction defaultShipDirection;
	
	private JFrame parent;

	public GameBuilder setInformationMap(Map<GameState, String> informationMap) {
		this.informationMap = new HashMap<GameState, String>(informationMap);
		return this;
	}

	public GameBuilder setParent(JFrame parent) {
		this.parent = parent;
		return this;
	}

	public GameBuilder setIp(String ip) {
		this.ip = ip;
		return this;
	}
	
	public GameBuilder setPort(int port) {
		this.port = port;
		return this;
	}
	
	public GameBuilder setPixelSize(int pixelSize) {
		this.pixelSize = pixelSize;
		return this;
	}
	
	public GameBuilder setWidth(int width) {
		this.width = width;
		return this;
	}
	
	public GameBuilder setHeight(int height) {
		this.height = height;
		return this;
	}
	
	public GameBuilder setListOfShips(List<ShipSize> listOfShips) {
		this.listOfShips = new ArrayList<ShipSize>(listOfShips);
		return this;
	}
	
	public GameBuilder setDefaultShipPosition(Point defaultShipPosition) {
		this.defaultShipPosition = (Point) defaultShipPosition.clone();
		return this;
	}
	
	public GameBuilder setDefaultShipDirection(Direction defaultShipDirection) {
		this.defaultShipDirection = defaultShipDirection;
		return this;
	}
	
	public GameWindow build() {
		FleetGenerator generator = new FleetGenerator(listOfShips, defaultShipDirection, defaultShipPosition);
		CheckField checkField = new CheckField(width, height);
		Field field = new Field(width, height);
		PlayerModel player = new PlayerModel(field, checkField, generator);
		
		ConnectionHandler handler = new ConnectionHandler(player);
		handler.setIp(ip);
		handler.setPort(port);
		
		FieldView fieldView = new FieldView(pixelSize, player);
		CheckFieldView checkFieldView = new CheckFieldView(pixelSize, player);
		GameStateView gameStateView = new GameStateView(informationMap, player);
		
		fieldView.createFieldController(handler);
		checkFieldView.createCheckFieldController(handler);
		
		GameWindow window = new GameWindow(parent, fieldView, checkFieldView, gameStateView);
		window.createWindowListener(handler);
		handler.setWindow(window);
		return window;
	}
}
