package com.gmail.sergiusz.mazan.ships.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends Thread {
	
	private ServerSocket serverSocket;
	private PlayerBuilder playerBuilder;
	private Player player1;
	private Player player2;
	
	private boolean done = false;
	
	public Server(ServerSocket serverSocket, PlayerBuilder playerBuilder) {
		this.serverSocket = serverSocket;
		this.playerBuilder = playerBuilder;
	}
	
	@Override
	public void run() {
		while(!done) {
			try {
				player1 = playerBuilder.setSocket(serverSocket.accept()).build();
				player2 = playerBuilder.setSocket(serverSocket.accept()).build();
				player1.setOpponent(player2);
				player2.setOpponent(player1);
				player1.startGame();
				player2.startGame();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}