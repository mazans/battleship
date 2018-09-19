package com.gmail.sergiusz.mazan.ships.communication;

public class ShotInformationCommand implements ServerCommand {

	private static final long serialVersionUID = 1L;
	private Point shotPosition;

	public ShotInformationCommand(Point shotPosition) {
		this.shotPosition = shotPosition;
	}

	@Override
	public void execute(ClientPlayer player) {
		player.getField().shootShip(shotPosition);
	}

}
