package com.gmail.sergiusz.mazan.ships.communication;

import java.util.ArrayList;
import java.util.List;

import com.gmail.sergiusz.mazan.ships.model.Field;
import com.gmail.sergiusz.mazan.ships.model.GameState;
import com.gmail.sergiusz.mazan.ships.model.Point;
import com.gmail.sergiusz.mazan.ships.model.ServerPlayer;
import com.gmail.sergiusz.mazan.ships.model.ShotResult;

/*
 * This ClientCommand subclass represents shooting opponent's ship
 * When players hits opponent's ship server sends information to opponent about hit
 * And to attacking player about shot result (also when player missed)
 * Then server checks if opponent's fleet has submerged, if so server informs players about end of a game
 * Otherwise attacker and defender change their roles
 */
public class ShootShipCommand extends ClientCommand {

	private static final long serialVersionUID = 1L;
	Point shotPosition;
	
	public ShootShipCommand(Point shotPosition) {
		this.shotPosition = shotPosition;
	}

	@Override
	public List<PlayerAndResponse> execute() {
		List<PlayerAndResponse> result = new ArrayList<PlayerAndResponse>();
		ServerPlayer player = getPlayer();
		ServerPlayer opponent = player.getOpponent();
		Field opponentField = opponent.getField();
		if(player.getGameState() == GameState.ATTACKING) {
			if(opponentField.shootShip(shotPosition)) {
				result.add(new PlayerAndResponse(player, new ServerResponse(shotPosition, ShotResult.SHOT)));
				result.add(new PlayerAndResponse(opponent, new ServerResponse(shotPosition)));
				if(opponentField.isFleetSubmerged()) {
					setPlayerAsWinner(player, opponent, result);
				}
				else
					changeAttackerAndDefender(player, opponent, result);
			}
			else {
				result.add(new PlayerAndResponse(player, new ServerResponse(shotPosition, ShotResult.MISSED)));
				changeAttackerAndDefender(player, opponent, result);
			}
		}
		return result;
	}
	
	private void changeAttackerAndDefender(ServerPlayer player, ServerPlayer opponent,List<PlayerAndResponse> result) {
		player.setGameState(GameState.WAITING);
		opponent.setGameState(GameState.ATTACKING);
		result.add(new PlayerAndResponse(player, new ServerResponse(player.getGameState())));
		result.add(new PlayerAndResponse(opponent, new ServerResponse(opponent.getGameState())));
	}
	
	private void setPlayerAsWinner(ServerPlayer player, ServerPlayer opponent,List<PlayerAndResponse> result) {
		player.setGameState(GameState.WON);
		opponent.setGameState(GameState.LOST);
		result.add(new PlayerAndResponse(player, new ServerResponse(player.getGameState())));
		result.add(new PlayerAndResponse(opponent, new ServerResponse(opponent.getGameState())));
	}
}