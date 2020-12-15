package gameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents a very simple GUI class to present a
 * game on a graph - you are welcome to use this class - yet keep in mind
 * that the code is not well written in order to force you improve the
 * code and not to take it "as is".
 */
public class MyFrame extends JFrame {

	private Arena arena;
	GamePanel gamePanel;

	MyFrame(String a) {
		super(a);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initPanel() {
		gamePanel = new GamePanel(arena.getGraph(), arena);
		this.add(gamePanel);
	}

	public void update(Arena arena) {
		this.arena = arena;
		initPanel();
		gamePanel.updatePanel();
	}

	public void paint(Graphics graph) {
		gamePanel.updatePanel();
		gamePanel.repaint();
	}
}

