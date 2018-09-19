package com.gmail.sergiusz.mazan.ships.communication;

public class ShotResultCommand implements ServerCommand {

	private static final long serialVersionUID = 1L;
	private Point shotPoint;
	private ShotResult result;
	
	public ShotResultCommand(Point shotPoint, ShotResult result) {
		this.shotPoint = shotPoint;
		this.result = result;
	}

	@Override
	public void execute(ClientPlayer player) {
		player.getCheckField().setShotResult(shotPoint, result);
	}

}