package com.gmail.sergiusz.mazan.ships.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gmail.sergiusz.mazan.ships.communication.ClientCommand;
import com.gmail.sergiusz.mazan.ships.communication.PlayerAndResponse;
import com.gmail.sergiusz.mazan.ships.communication.ServerResponse;
import com.gmail.sergiusz.mazan.ships.model.ServerPlayer;

/*
 * This class is responsible for accepting connections with clients and creating games
 */
public class Server extends Thread {
	
	private static Logger logger = Logger.getLogger("com.gmail.sergiusz.mazan.ships.server");
	
	private BlockingQueue<ClientCommand> commandQueue;
	private ConcurrentMap<ServerPlayer, PlayerHandler> playerMap;
	private BlockingQueue<PlayerAndResponse> responseQueue;
	private ServerSocket serverSocket;
	private PlayerBuilder playerBuilder;
	


	public Server(BlockingQueue<ClientCommand> commandQueue, ConcurrentMap<ServerPlayer, PlayerHandler> playerMap,
			BlockingQueue<PlayerAndResponse> responseQueue, ServerSocket serverSocket, PlayerBuilder playerBuilder) {
		
		this.commandQueue = commandQueue;
		this.playerMap = playerMap;
		this.responseQueue = responseQueue;
		this.serverSocket = serverSocket;
		this.playerBuilder = playerBuilder;
	}

	@Override
	public void run() {
		logger.info("Server has started");
		
		PlayerHandler firstHandler;
		PlayerHandler secondHandler;
		ServerPlayer firstPlayer;
		ServerPlayer secondPlayer;
		while(true) {
			try {
				firstPlayer = playerBuilder.build();
				firstHandler = new PlayerHandler(serverSocket.accept(), firstPlayer, this);
				playerMap.put(firstPlayer, firstHandler);
				firstHandler.sendServerResponse(new ServerResponse(firstPlayer.getGameState()));
				logger.info("Added first player");
				
				while(true) {
					secondPlayer = playerBuilder.build();
					secondHandler = new PlayerHandler(serverSocket.accept(), secondPlayer, this);
					playerMap.put(secondPlayer, secondHandler);
					secondHandler.sendServerResponse(new ServerResponse(secondPlayer.getGameState()));
					
					//If first player disconnected, server must find another opponent for new player
					if(!firstHandler.isClosed()) {
						logger.info("Added second player");
						break;
					}
					else {
						firstPlayer = secondPlayer;
						firstHandler = secondHandler;
						logger.info("First player has disconnectd waiting for another player");
					}
				}
				
				
				commandQueue.put(new SetOpponentCommand(firstPlayer, secondPlayer));
				commandQueue.put(new SetOpponentCommand(secondPlayer, firstPlayer));
				firstHandler.startGame();
				secondHandler.startGame();
				logger.info("Game has started");
			} catch (SocketException e) {
				if(serverSocket.isClosed()) {
					logger.info("Socket has been closed");
					break;
				}
				else
					logger.warning("Error occured during creating game");
			} catch (IOException e) {
				logger.warning("Error occured during creating game");
			} catch (InterruptedException e) {
				logger.log(Level.FINE, "Server thread interrupted");
			} 
		}
	}
	
	public void close() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			logger.log(Level.FINE, "Error occured during closing ServerSocket", e);		}
	}

	public BlockingQueue<ClientCommand> getCommandQueue() {
		return commandQueue;
	}

	public ConcurrentMap<ServerPlayer, PlayerHandler> getPlayerMap() {
		return playerMap;
	}

	public BlockingQueue<PlayerAndResponse> getResponseQueue() {
		return responseQueue;
	}
}