package com.gmail.sergiusz.mazan.ships.server;

import java.net.Socket;
import java.util.List;

import com.gmail.sergiusz.mazan.ships.model.Field;
import com.gmail.sergiusz.mazan.ships.model.ServerPlayer;
import com.gmail.sergiusz.mazan.ships.model.ShipSize;
import com.gmail.sergiusz.mazan.ships.model.ShipsCheckList;

public class PlayerBuilder {
	
	int width, height;
	List<ShipSize> sizeList;
	Socket socket;
	
	
	public PlayerBuilder setFieldDimension(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}
	
	public PlayerBuilder setSizeList(List<ShipSize> sizeList) {
		this.sizeList = sizeList;
		return this;
	}
	
	public PlayerBuilder setSocket(Socket socket) {
		this.socket = socket;
		return this;
	}
		
	public ServerPlayer build() {
		Field field = new Field(width, height);
		ShipsCheckList checkList = new ShipsCheckList(sizeList);
		return new ServerPlayer(field, checkList);
	}
}
