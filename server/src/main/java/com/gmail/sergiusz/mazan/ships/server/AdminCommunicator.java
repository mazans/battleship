package com.gmail.sergiusz.mazan.ships.server;

import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

import com.gmail.sergiusz.mazan.ships.model.ServerPlayer;

/*
 * This class is responsible for communication with administrator on server
 * It reacts properly on admin commands from commandline
 */
public class AdminCommunicator {
	
	private Server server;
	private CommandExecutor executor;
	private ResponseSender sender;
	private ConcurrentMap<ServerPlayer, PlayerHandler> playerMap;
	
	public AdminCommunicator(Server server, CommandExecutor executor, ResponseSender sender,
			ConcurrentMap<ServerPlayer, PlayerHandler> playerMap) {
		this.server = server;
		this.executor = executor;
		this.sender = sender;
		this.playerMap = playerMap;
	}



	public void listen() {
		Logger.getLogger("com.gmail.sergiusz.mazan.ships.server").info("AdminCommunicator has started");
		Scanner in = new Scanner(System.in);
		while(true) {
			String command = in.nextLine();
			command = command.toUpperCase();
			if(command.equals("CLOSE")) {
				Logger.getLogger("com.gmail.sergiusz.mazan.ships.server").info("Closing application");
				server.close();
				executor.interrupt();
				sender.interrupt();
				playerMap.forEach((player,handler) -> {
					handler.disconnect();
				});
				break;
			}
		}
		in.close();
	}
}
