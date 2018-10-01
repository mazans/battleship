package com.gmail.sergiusz.mazan.ships.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import com.gmail.sergiusz.mazan.ships.communication.ShootShipCommand;
import com.gmail.sergiusz.mazan.ships.model.GameState;
import com.gmail.sergiusz.mazan.ships.model.Point;
import com.gmail.sergiusz.mazan.ships.model.ShotResult;

public class CheckFieldView extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private PlayerModel model;
	private int pixelSize;
	
	public CheckFieldView(int pixelSize, PlayerModel model) {
		this.model = model;
		this.pixelSize = pixelSize;
		
		setBackground(Color.WHITE);
		model.addPropertyChangeListener(this, "shotResult");
	}
	
	public void createCheckFieldController(final ConnectionHandler handler) {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(model.getGameState() == GameState.ATTACKING 
				&& model.getCheckField().getShotResult(new Point(e.getX()/pixelSize, e.getY()/pixelSize)) == ShotResult.NONE) {
					handler.sendCommand(new ShootShipCommand(new Point(e.getX()/pixelSize, e.getY()/pixelSize)));
					model.setGameState(GameState.WAITING);
				}
			}
		});
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		CheckField checkField = model.getCheckField();
		for(int i = 0; i < checkField.getHeight()+1; i++) {
			g.drawLine(0, i*pixelSize, model.getCheckField().getWidth()*pixelSize, i*pixelSize);
		}
		for(int i = 0; i < checkField.getWidth()+1; i++) {
			g.drawLine(i*pixelSize, 0, i*pixelSize, checkField.getHeight()*pixelSize);
		}
		for(int i = 0; i < checkField.getWidth(); i++) {
			for(int j = 0; j < checkField.getHeight(); j++) {
				ShotResult result = checkField.getShotResult(new Point(i, j));
				if(result == ShotResult.MISSED) {
					g.setColor(Color.RED);
					g.fillRect(i*pixelSize, j*pixelSize, pixelSize, pixelSize);
				}
				else if(result == ShotResult.SHOT) {
					g.setColor(Color.GREEN);
					g.fillRect(i*pixelSize, j*pixelSize, pixelSize, pixelSize);
				}
			}
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(pixelSize*model.getCheckField().getWidth(), pixelSize*model.getCheckField().getHeight()+1);
	}

	public int getPixelSize() {
		return pixelSize;
	}

	public void propertyChange(PropertyChangeEvent e) {
		repaint();
	}
}
