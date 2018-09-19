package com.gmail.sergiusz.mazan.ships.communication;

import java.io.Serializable;

public interface ServerCommand extends Serializable {
	
	public void execute(ClientPlayer player);
	
}
