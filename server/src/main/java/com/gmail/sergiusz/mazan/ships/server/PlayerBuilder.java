package com.gmail.sergiusz.mazan.ships.server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.gmail.sergiusz.mazan.ships.communication.Field;
import com.gmail.sergiusz.mazan.ships.communication.FieldImpl;
import com.gmail.sergiusz.mazan.ships.communication.ShipSize;
import com.gmail.sergiusz.mazan.ships.communication.ShipsCheckList;

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
	
	public Player build() throws IOException {
		Field field = new FieldImpl(width, height);
		ShipsCheckList checkList = new ShipsCheckListImpl(sizeList);
		return new Player(socket, field, checkList);
	}
}
