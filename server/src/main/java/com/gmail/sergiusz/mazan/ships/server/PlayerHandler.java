package com.gmail.sergiusz.mazan.ships.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gmail.sergiusz.mazan.ships.communication.ClientCommand;
import com.gmail.sergiusz.mazan.ships.communication.ServerResponse;
import com.gmail.sergiusz.mazan.ships.model.GameState;
import com.gmail.sergiusz.mazan.ships.model.ServerPlayer;

/*
 * This class is responsible for communication with client
 */
public class PlayerHandler extends Thread {
	
	private static Logger logger = Logger.getLogger("com.gmail.sergiusz.mazan.ships.server");
	
	private ServerPlayer player;
	private Server server;
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	boolean closedByServer = false;
	
	public PlayerHandler(Socket socket, ServerPlayer player, Server server) throws IOException {
		this.server = server;
		this.player = player;
		this.socket = socket;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		start();
	}
	
	public void startGame() {
		try {
			ClientCommand stateCommand = new StateChangeCommand(GameState.INITIALIZATING);
			stateCommand.setPlayer(player);
			server.getCommandQueue().put(stateCommand);
		} catch (InterruptedException e) {
			logger.log(Level.FINE, "Thread interrupted during adding ServerResponse or ClientCommand to queue", e);
		}
	}
	
	public void run() {
		ClientCommand command;
		BlockingQueue<ClientCommand> commandQueue = server.getCommandQueue();
		try {
			while((command = (ClientCommand) in.readObject()) != null) {
				command.setPlayer(player);
				commandQueue.put(command);
			}
		} catch (ClassNotFoundException e) {
			logger.warning("Client sent unknown class");
		} catch (IOException e) {
			logger.info("Connection with client is lost");
			
			try {
				if(!closedByServer) {
					ClientCommand quit = new QuitCommand();
					quit.setPlayer(player);
					commandQueue.put(quit);
				}
			} catch (InterruptedException e1) {
				logger.info("Thread interrupted during sending QuitCommand");
			}
		}  catch (InterruptedException e) {
			logger.log(Level.FINE, "Thread is interrupted, closing a thread", e);
		}
		server.getPlayerMap().remove(player);
		closeResources();
	}
	
	private void closeResources() {
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			logger.log(Level.FINE, "Error occured during closing some PlayerHandler socket", e);
		}
	}

	public void sendServerResponse(ServerResponse response) {
		try {
			out.writeObject(response);
		} catch (IOException e) {
			logger.info("Error occured during writing ServerResponse");
		}		
	}

	public boolean isClosed() {
		if(socket != null)
			return socket.isClosed();
		else
			return true;
	}
	
	public void disconnect() {
		try {
			socket.shutdownInput();
			closedByServer = true;
		} catch (IOException e) {
			logger.log(Level.FINE, "Error occured during closing socket Input", e);
		}
	}
	
}