package com.gmail.sergiusz.mazan.ships.client;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GameWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private static final String HELP_MSG = "Upper field is used for choosing position of shooting\n"
			+ "To shoot, click left mouse button on selected position\n"
			+ "Lower field is used for placing a ship/n"
			+ "To place a ship, click left mouse button on selected position\n"
			+ "To change ship direction, click right mouse button";

	public GameWindow(JFrame parent, FieldView fieldView, CheckFieldView checkFieldView, GameStateView gameStateView) {
		super(parent, true);
		
		setLayout(new BorderLayout());
		
		add(checkFieldView, BorderLayout.NORTH);
		add(gameStateView, BorderLayout.CENTER);
		add(fieldView, BorderLayout.SOUTH);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu helpMenu = new JMenu("Help");
		JMenuItem helpItem = new JMenuItem("Rules");
		helpItem.addActionListener(event -> {
			JOptionPane.showMessageDialog(this, HELP_MSG, "Help", JOptionPane.INFORMATION_MESSAGE);
		});
		helpMenu.add(helpItem);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
				
		pack();
		setTitle("Game");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void createWindowListener(final ConnectionHandler handler) {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent event) {
				try {
						handler.connect();
					} catch (IOException e) {
					JOptionPane.showMessageDialog(event.getWindow(), "Error occured during connecting to server");
					event.getWindow().dispatchEvent(new WindowEvent(event.getWindow(), WindowEvent.WINDOW_CLOSING));
				}
			}
			
			@Override
			public void windowClosing(WindowEvent event) {
				try {
					if(handler != null && !handler.isClosed()) {
						handler.disconnect();
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(event.getWindow(), "Error occured during disconnecting to server");
				}
			}
		});
	}

	public void informAboutError() {
		JOptionPane.showMessageDialog(this, "Error occured during connection");
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
}
