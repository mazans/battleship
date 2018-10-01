package com.gmail.sergiusz.mazan.ships.model;

import java.io.Serializable;

public enum GameState implements Serializable {
	NEW,
	WAITING,
	INITIALIZATING,
	ATTACKING,
	DEFEND,
	WON,
	LOST,
	ENDED,
}
