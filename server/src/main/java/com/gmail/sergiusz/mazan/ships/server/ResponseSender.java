package com.gmail.sergiusz.mazan.ships.server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

import com.gmail.sergiusz.mazan.ships.communication.PlayerAndResponse;
import com.gmail.sergiusz.mazan.ships.model.ServerPlayer;

/*
 * This class is responsible for sending responses to clients
 */
public class ResponseSender extends Thread {
	
	private BlockingQueue<PlayerAndResponse> responseQueue;
	private ConcurrentMap<ServerPlayer, PlayerHandler> playerMap;
	
	public ResponseSender(BlockingQueue<PlayerAndResponse> responseQueue,
			ConcurrentMap<ServerPlayer, PlayerHandler> playerMap) {
		this.responseQueue = responseQueue;
		this.playerMap = playerMap;
	}
	
	@Override
	public void run() {
		try {
			Logger.getLogger("com.gmail.sergiusz.mazan.ships.server")
					.info("ResponseSender has started");
			while(true) {
				PlayerAndResponse par = responseQueue.take();
				PlayerHandler handler = playerMap.get(par.getPlayer());
				if(handler != null)
					handler.sendServerResponse(par.getResponse());
			}
		} catch (InterruptedException e) {
			Logger.getLogger("com.gmail.sergiusz.mazan.ships.server")
					.info("Sender has been interrupted, stopping sender");
		}
	}
}