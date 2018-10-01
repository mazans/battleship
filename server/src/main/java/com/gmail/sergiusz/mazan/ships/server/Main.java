package com.gmail.sergiusz.mazan.ships.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import com.gmail.sergiusz.mazan.ships.communication.ClientCommand;
import com.gmail.sergiusz.mazan.ships.communication.PlayerAndResponse;
import com.gmail.sergiusz.mazan.ships.model.ServerPlayer;
import com.gmail.sergiusz.mazan.ships.model.ShipSize;


public class Main {
	
	private static final int PORT = 6000;
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
	
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			
			List<ShipSize> sizeList = new ArrayList<ShipSize>();
			sizeList.add(ShipSize.SIX);
			sizeList.add(ShipSize.FOUR);
			sizeList.add(ShipSize.FOUR);
			sizeList.add(ShipSize.THREE);
			sizeList.add(ShipSize.THREE);
			sizeList.add(ShipSize.TWO);
			
			PlayerBuilder builder = new PlayerBuilder();
			builder.setFieldDimension(WIDTH, HEIGHT)
				   .setSizeList(sizeList);
			
			BlockingQueue<ClientCommand> commandQueue = new LinkedBlockingQueue<ClientCommand>();
			BlockingQueue<PlayerAndResponse> responseQueue = new LinkedBlockingQueue<PlayerAndResponse>();
			ConcurrentMap<ServerPlayer, PlayerHandler> playerMap = new ConcurrentHashMap<ServerPlayer, PlayerHandler>();
			
			CommandExecutor executor = new CommandExecutor(commandQueue, responseQueue);
			ResponseSender sender = new ResponseSender(responseQueue, playerMap);
			Server server = new Server(commandQueue, playerMap, responseQueue, serverSocket, builder);
			
			executor.start();
			sender.start();
			server.start();
			
			new AdminCommunicator(server, executor, sender, playerMap).listen();
			
		} catch (IOException e) {
			Logger.getLogger("com.gmail.sergiusz.mazan.ships.server")
							.warning("Unable to create server socket");
		}
	}
}
