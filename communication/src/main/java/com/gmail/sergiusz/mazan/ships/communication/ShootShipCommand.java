package com.gmail.sergiusz.mazan.ships.communication;

public class ShootShipCommand implements ClientCommand {

	private static final long serialVersionUID = 1L;
	Point shotPosition;
	
	public ShootShipCommand(Point shotPosition) {
		this.shotPosition = shotPosition;
	}

	@Override
	public void execute(ServerPlayer player) {
		ServerPlayer opponent = player.getOpponent();
		Field opponentField = opponent.getField();
		if(player.getGameState() == GameState.ATTACKING) {
			if(opponentField.shootShip(shotPosition)) {
				player.sendServerCommand(new ShotResultCommand(shotPosition, ShotResult.SHOT));
				opponent.sendServerCommand(new ShotInformationCommand(shotPosition));
				if(opponentField.isFleetSubmerged()) {
					setPlayerAsWinner(player, opponent);
				}
				else
					changeAttackerAndDefender(player, opponent);
			}
			else {
				player.sendServerCommand(new ShotResultCommand(shotPosition, ShotResult.MISSED));
				changeAttackerAndDefender(player, opponent);
			}
		}
	}
	
	private void changeAttackerAndDefender(ServerPlayer player, ServerPlayer opponent) {
		player.setGameState(GameState.WAITING);
		opponent.setGameState(GameState.ATTACKING);
		player.sendServerCommand(new StateChangeCommand(player.getGameState()));
		opponent.sendServerCommand(new StateChangeCommand(opponent.getGameState()));
	}
	
	private void setPlayerAsWinner(ServerPlayer player, ServerPlayer opponent) {
		player.setGameState(GameState.WON);
		opponent.setGameState(GameState.LOST);
		player.sendServerCommand(new StateChangeCommand(player.getGameState()));
		opponent.sendServerCommand(new StateChangeCommand(opponent.getGameState()));
	}
}