package com.gmail.sergiusz.mazan.ships.client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class OpeningWindow extends JFrame {

	private static final long serialVersionUID = 1L;
		
	private JMenuBar menuBar = new JMenuBar();
	private JMenu serverMenu = new JMenu("Server");
	private JMenuItem ipItem = new JMenuItem("Set server IP address");
	private JMenuItem portItem = new JMenuItem("Set server port");
	private JMenuItem connectItem = new JMenuItem("Find game");
	private BufferedImage battleshipImage;
	
	public OpeningWindow(GameBuilder gameBuilder) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Battleship Game");		
		
		gameBuilder.setParent(this);
		
		ipItem.addActionListener(event -> {
			String ip = JOptionPane.showInputDialog(this, "Type in server's IP address:", "localhost");
			gameBuilder.setIp(ip);
		});
		
		portItem.addActionListener(event -> {
			String result = JOptionPane.showInputDialog(this, "Type in server's port:", "6000");
			try {
			int port = Integer.parseInt(result);
			gameBuilder.setPort(port);
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Port number must be integer", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		connectItem.addActionListener(event -> {
			gameBuilder.build().setVisible(true);
		});
		
		serverMenu.add(connectItem);
		serverMenu.add(ipItem);
		serverMenu.add(portItem);
		menuBar.add(serverMenu);
		setJMenuBar(menuBar);
		
		//Adding battleships picture to window
		try {
			battleshipImage = ImageIO.read(getClass().getClassLoader().getResource("battleship.png"));
			JPanel imagePanel = new JPanel() {

				@Override
				protected void paintComponent(Graphics g) {
					g.drawImage(battleshipImage, 0, 0, null);
				}
				
				@Override
				public Dimension getPreferredSize() {
					return new Dimension(battleshipImage.getWidth(), battleshipImage.getHeight());
				}
			};
			add(imagePanel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		pack();
		setResizable(false);
	}
}
