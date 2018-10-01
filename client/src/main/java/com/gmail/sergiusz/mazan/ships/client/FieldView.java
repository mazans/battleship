package com.gmail.sergiusz.mazan.ships.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.gmail.sergiusz.mazan.ships.communication.AddShipCommand;
import com.gmail.sergiusz.mazan.ships.model.GameState;
import com.gmail.sergiusz.mazan.ships.model.Point;
import com.gmail.sergiusz.mazan.ships.model.Ship;

public class FieldView extends JPanel implements PropertyChangeListener {
	
	private static final long serialVersionUID = 1L;
	
	private int pixelSize;
	private PlayerModel model;
		
	public FieldView(int pixelSize, PlayerModel model) {
		this.pixelSize = pixelSize;
		this.model = model;
		
		setBackground(Color.WHITE);
		model.addPropertyChangeListener(this, "shipToBePlaced");
		model.addPropertyChangeListener(this, "addShip");
		model.addPropertyChangeListener(this, "shootShip");
	}
	
	public void createFieldController(final ConnectionHandler handler) {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					if(model.getGameState() == GameState.INITIALIZATING && model.canBePlacedShip()) {
						handler.sendCommand(new AddShipCommand(model.getShipToBePlaced()));
						model.placeShip();
					}
				}
				else if(SwingUtilities.isRightMouseButton(e)) {
					if(model.getGameState() == GameState.INITIALIZATING) {
						model.changeDirectionOfShip();
					}
				}
			}
		});
		
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(model.getGameState() == GameState.INITIALIZATING) {
					model.setPositionOfShip(new Point(e.getX()/pixelSize, e.getY()/pixelSize));
				}
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		for(int i = 0; i < model.getField().getHeight()+1; i++) {
			g.drawLine(0, i*pixelSize, model.getField().getWidth()*pixelSize, i*pixelSize);
		}
		for(int i = 0; i < model.getField().getWidth()+1; i++) {
			g.drawLine(i*pixelSize, 0, i*pixelSize, model.getField().getHeight()*pixelSize);
		}
		Collection<Ship> ships = model.getField().getShips();
		for(Ship ship : ships) {
			for(int i = 0; i < ship.getSize().toInt(); i++) {
				if(ship.isPartofShipDestroyed(i))
					g.setColor(Color.ORANGE);
				else
					g.setColor(Color.BLACK);
				g.fillRect(ship.getPos(i).getX()*pixelSize, ship.getPos(i).getY()*pixelSize, pixelSize, pixelSize);
			}
		}
		
		Ship shipToBePlaced = model.getShipToBePlaced();
		if(shipToBePlaced != null) {
			if(model.getField().canBePlaced(shipToBePlaced))
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.RED);
			for(int i = 0; i < shipToBePlaced.getSize().toInt(); i++)
				g.fillRect(shipToBePlaced.getPos(i).getX()*pixelSize, 
						shipToBePlaced.getPos(i).getY()*pixelSize, pixelSize, pixelSize);
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(pixelSize*model.getField().getWidth(), pixelSize*model.getField().getHeight()+1);
	}

	public int getPixelSize() {
		return pixelSize;
	}

	public void propertyChange(PropertyChangeEvent arg0) {
		repaint();
		
	}
}
