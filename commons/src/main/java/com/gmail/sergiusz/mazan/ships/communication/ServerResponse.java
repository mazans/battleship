package com.gmail.sergiusz.mazan.ships.communication;

import java.io.Serializable;

import com.gmail.sergiusz.mazan.ships.model.GameState;
import com.gmail.sergiusz.mazan.ships.model.Point;
import com.gmail.sergiusz.mazan.ships.model.ShotResult;

/*
 * This class represents server response to client
 */
public class ServerResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ResponseType type;
	private GameState state;
	private Point position;
	private ShotResult result;
	
	public ServerResponse(GameState state) {
		this.type = ResponseType.GAME_STATE_CHANGE;
		this.state = state;
		this.position = null;
		this.result = null;
	}

	public ServerResponse(Point position) {
		this.type = ResponseType.SHOT_INFORMATION;
		this.state = null;
		this.position = position;
		this.result = null;
	}
	
	

	public ServerResponse(Point position, ShotResult result) {
		this.type = ResponseType.SHOT_RESULT;
		this.state = null;
		this.position = position;
		this.result = result;
	}

	public ResponseType getType() {
		return type;
	}

	public GameState getState() {
		return state;
	}

	public Point getPosition() {
		return position;
	}

	public ShotResult getResult() {
		return result;
	}
}
