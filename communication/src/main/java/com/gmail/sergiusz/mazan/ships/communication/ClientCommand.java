package com.gmail.sergiusz.mazan.ships.communication;

import java.io.Serializable;

public interface ClientCommand extends Serializable {
	
	public void execute(ServerPlayer player);
	
}
