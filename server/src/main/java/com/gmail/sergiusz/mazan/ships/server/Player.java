package com.gmail.sergiusz.mazan.ships.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.gmail.sergiusz.mazan.ships.communication.ClientCommand;
import com.gmail.sergiusz.mazan.ships.communication.Field;
import com.gmail.sergiusz.mazan.ships.communication.GameState;
import com.gmail.sergiusz.mazan.ships.communication.ServerCommand;
import com.gmail.sergiusz.mazan.ships.communication.ServerPlayer;
import com.gmail.sergiusz.mazan.ships.communication.ShipsCheckList;
import com.gmail.sergiusz.mazan.ships.communication.StateChangeCommand;

public class Player extends ServerPlayer implements Runnable {
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public Player(Socket socket, Field field, ShipsCheckList list) throws IOException {
		super(field, list);
		this.socket = socket;
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
	}
	
	public void startGame() {
		setGameState(GameState.INITIALIZATING);
		Thread thread = new Thread(this);
		thread.start();
		sendServerCommand(new StateChangeCommand(getGameState()));
	}
	
	public void run() {
		while(getGameState() != GameState.ENDED) {
			try {
				
				ClientCommand command = (ClientCommand) in.readObject();
				command.execute(this);
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		closeResources();
	}
	
	private void closeResources() {
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendServerCommand(ServerCommand commmand) {
		try {
			out.writeObject(commmand);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
}
