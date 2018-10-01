package com.gmail.sergiusz.mazan.ships.client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.gmail.sergiusz.mazan.ships.communication.ClientCommand;
import com.gmail.sergiusz.mazan.ships.communication.ResponseType;
import com.gmail.sergiusz.mazan.ships.communication.ServerResponse;

/*
 * This class is responsible for sending messages to server and receceiving server's responses 
 */
public class ConnectionHandler extends Thread {

	private PlayerModel player;
	private GameWindow window = null;
	
	private String ip = "localhost";
	private int port = 6000;
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private boolean disconnectedByClient = false;
	
	public ConnectionHandler(PlayerModel player) {
		this.player = player;
	}
	
	public void setWindow(GameWindow window) {
		this.window = window;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void connect() throws UnknownHostException, IOException {
		socket = new Socket(ip, port);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		start();
	}
	
	public boolean isClosed() {
		if(socket != null) {
			return socket.isClosed();
		}
		else
			return true;
	}
	
	public void sendCommand(ClientCommand command) {
		try {
			out.writeObject(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		ServerResponse response;
		try {
			while((response = (ServerResponse) in.readObject()) != null) {
				ResponseType type = response.getType();
				if(type == ResponseType.GAME_STATE_CHANGE)
					player.setGameState(response.getState());
				else if(type == ResponseType.SHOT_INFORMATION)
					player.shootShip(response.getPosition());
				else if(type == ResponseType.SHOT_RESULT)
					player.setShotResult(response.getPosition(), response.getResult());
			}
		} catch (EOFException e) {
			if(window != null && !disconnectedByClient)
				window.informAboutError();
		} catch (IOException | ClassNotFoundException e) {
			if(window != null)
				window.informAboutError();
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
	
	public void disconnect() throws IOException {
		disconnectedByClient = true;
		socket.shutdownInput();
	}
}
