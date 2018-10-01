package com.gmail.sergiusz.mazan.ships.communication;

import java.util.ArrayList;
import java.util.List;

import com.gmail.sergiusz.mazan.ships.model.Field;
import com.gmail.sergiusz.mazan.ships.model.GameState;
import com.gmail.sergiusz.mazan.ships.model.ServerPlayer;
import com.gmail.sergiusz.mazan.ships.model.Ship;
import com.gmail.sergiusz.mazan.ships.model.ShipsCheckList;

/*
 * This ClientCommand subclass represents adding Ship object to player's Field
 * When ship cannot be placed it is just not placed so client must be sure
 * that it can place a ship on specified position before sending a command
 * When all ships are placed, server checks if opponent also placed all its ships
 * If so, game starts and first player which placed all its ships is attacker
 */
public class AddShipCommand extends ClientCommand {

	private static final long serialVersionUID = 1L;
	private Ship newShip;
	
	public AddShipCommand(Ship newShip) {
		this.newShip = newShip;
	}
	
	
	@Override
	public List<PlayerAndResponse> execute() {
		List<PlayerAndResponse> result = new ArrayList<PlayerAndResponse>();
		ServerPlayer player = getPlayer();
		Field field = player.getField();
		ShipsCheckList list = player.getCheckList();
		ServerPlayer opponent = player.getOpponent();
		if(player.getGameState() == GameState.INITIALIZATING && list.containsShipSize(newShip.getSize())
				&& field.canBePlaced(newShip)) {
			list.check(newShip.getSize());
			field.addShip(newShip);
			//verifying if all ships are placed
			if(list.isAllChecked()) {
				player.setGameState(GameState.WAITING);
				result.add(new PlayerAndResponse(player, new ServerResponse(player.getGameState())));
				//verifying if opponent placed all ships
				if(opponent.getGameState() == GameState.WAITING) {
					opponent.setGameState(GameState.ATTACKING);
					player.setGameState(GameState.DEFEND);
					result.add(new PlayerAndResponse(player, new ServerResponse(player.getGameState())));
					result.add(new PlayerAndResponse(opponent, new ServerResponse(player.getOpponent().getGameState())));
				}
			}
		}
		return result;
	}
}
