package com.gmail.sergiusz.mazan.ships.communication;

public class AddShipCommand implements ClientCommand {

	private static final long serialVersionUID = 1L;
	private Ship newShip;
	
	public AddShipCommand(Ship newShip) {
		this.newShip = newShip;
	}
	
	
	@Override
	public void execute(ServerPlayer player) {
		Field field = player.getField();
		ShipsCheckList list = player.getCheckList();
		ServerPlayer opponent = player.getOpponent();
		if(player.getGameState() == GameState.INITIALIZATING && list.containsShipSize(newShip.getSize())
		&& field.canBePlaced(newShip)) {
			list.check(newShip.getSize());
			field.addShip(newShip);
			if(list.isAllChecked()) {
				player.setGameState(GameState.WAITING);
				player.sendServerCommand(new StateChangeCommand(player.getGameState()));
				if(opponent.getGameState() == GameState.WAITING) {
					opponent.setGameState(GameState.ATTACKING);
					player.sendServerCommand(new StateChangeCommand(player.getGameState()));
				}
			}
		}
	}
}
