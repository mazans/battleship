package com.gmail.sergiusz.mazan.ships.client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;

import com.gmail.sergiusz.mazan.ships.model.GameState;

public class GameStateView extends JTextField implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	
	private Map<GameState, String> informationMap;
	
	public GameStateView(Map<GameState, String> informationMap, PlayerModel model) {
		this.informationMap = new HashMap<GameState, String>(informationMap);
		setEditable(false);
		setText(informationMap.get(model.getGameState()));
		model.addPropertyChangeListener(this, "stateChange");
	}

	public void propertyChange(PropertyChangeEvent event) {
		this.setText(informationMap.get(event.getNewValue()));
	}
}
