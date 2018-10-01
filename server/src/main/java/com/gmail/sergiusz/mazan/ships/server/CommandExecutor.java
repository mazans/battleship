package com.gmail.sergiusz.mazan.ships.server;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import com.gmail.sergiusz.mazan.ships.communication.ClientCommand;
import com.gmail.sergiusz.mazan.ships.communication.PlayerAndResponse;

/*
 * This class executes commands which sends client and commands created by server
 * This is only class on server side which directly changes state of ServerPlayer object
 * After executing command it puts responses for client to queue
 */
public class CommandExecutor extends Thread {
	
	private BlockingQueue<ClientCommand> commandQueue;
	private BlockingQueue<PlayerAndResponse> responseQueue;	

	public CommandExecutor(BlockingQueue<ClientCommand> commandQueue, 
			BlockingQueue<PlayerAndResponse> responseQueue) {
		this.commandQueue = commandQueue;
		this.responseQueue = responseQueue;
	}

	@Override
	public void run() {
		Logger.getLogger("com.gmail.sergiusz.mazan.ships.server")
				.info("CommandExecutor has started");
		try {
			while(true) {
				ClientCommand command = commandQueue.take();
				List<PlayerAndResponse> responses = command.execute();
				if(responses != null)
					for(PlayerAndResponse par : responses)
						responseQueue.put(par);
			}
		} catch (InterruptedException e) {
			Logger.getLogger("com.gmail.sergiusz.mazan.ships.server")
					.info("Executor has been interrupted, stopping executor");
		}
	}
}