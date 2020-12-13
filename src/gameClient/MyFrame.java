package gameClient;

import gameClient.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;

import api.*;

/**
 * This class represents a simple GUI for the game using extends for JFrame class
 * @Author Kfir Goldfarb and Nadav Keysar
 */
public class MyFrame extends JFrame {

	private Arena arena;
	private gameClient.util.Range2Range w2f;

	// using Image and Graphics classes for the GUI
	private Graphics graphics;
	private Image image;

	/**
	 * constructor
	 * @param a
	 */
	MyFrame(String a) { super(a); }

	/**
	 * update my frame by given arena
	 * @param ar
	 */
	public void update(Arena ar) {
		this.arena = ar;
		updateFrame();
	}

	/**
	 * update the frame, getting the graph from the arena
	 */
	private void updateFrame() {
		Range rx = new Range(20, this.getWidth() - 20);
		Range ry = new Range(this.getHeight() - 10, 150);
		Range2D frame = new Range2D(rx, ry);
		directed_weighted_graph g = this.arena.getGraph();
		this.w2f = Arena.w2f(g, frame);
	}

	/**
	 * create new image and get the graphics,
	 * draw game info, agents, pokemons and graph and using drawImage method
	 * @param graphics
	 */
	public void paint(Graphics graphics) {
		this.image = createImage(this.getWidth(), this.getHeight());
		this.graphics = this.image.getGraphics();
		drawInfo(this.graphics);
		drawAgents(this.graphics);
		drawPokemons(this.graphics);
		drawGraph(this.graphics);
		drawTime(this.graphics);
		graphics.drawImage(this.image, 0, 0, this);
	}

	/**
	 * draw info of the game
	 * @param g
	 */
	private void drawInfo(Graphics g) {
		List<String> str = this.arena.get_info();
		String dt = "none";
		for (int i = 0; i < str.size(); i++) {
			g.drawString(str.get(i) + " dt: " + dt, 100, 60 + i * 20);
		}
	}

	/**
	 * draw the graph of the game
	 * @param graphics
	 */
	private void drawGraph(Graphics graphics) {

		// getting the graph from the arena
		directed_weighted_graph graph = this.arena.getGraph();

		// going throw all the nodes of the graph
		for(node_data node : graph.getV()) {

			// draw the node, color of the node is blue
			graphics.setColor(Color.blue);
			drawNode(node, 5, graphics);

			// going throw all the edges of the node
			for(edge_data edge : graph.getE(node.getKey())) {

				// draw the edge, color of the edge is gray
				graphics.setColor(Color.gray);
				drawEdge(edge, graphics);
			}

		}
	}

	/**
	 * draw the pokemons from the arena to the frame
	 * @param graphics
	 */
	private void drawPokemons(Graphics graphics) {

		// getting the pokemon list from the arena
		List<CL_Pokemon> pks = arena.getPokemons();

		// if the list is not null
		if(pks != null) {

			// going throw all the pokemons in the list pks
			for(CL_Pokemon pk : pks) {

				// getting pokemon location
				Point3D point = pk.getLocation();

				// color of the pokemon is orange, but if the type of the pokemon is >= 0 the color is green
				graphics.setColor(Color.green);
				if (pk.getType() < 0) graphics.setColor(Color.orange);

				// if the pokemon location is not null
				if (point != null) {

					int r = 10;
					geo_location fp = this.w2f.world2frame(point);
					graphics.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);

				}
			}

		}
	}

	/**
	 * draw the agents from the arena to the frame
	 * @param graphics
	 */
	private void drawAgents(Graphics graphics) {

		// getting the agents from the arena
		List<CL_Agent> agents = this.arena.getAgents();

		// color of an agent is red
		graphics.setColor(Color.red);

		// going throw all the agents that not null
		int i = 0;
		while(agents != null && i < agents.size()) {

			// getting the agents location point
			geo_location point = agents.get(i).getLocation();
			int r = 8;
			i++;

			// if the location point is not null
			if (point != null) {

				geo_location fp = this.w2f.world2frame(point);
				graphics.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
			}
		}
	}

	/**
	 * draw the nodes to the frame
	 * @param node
	 * @param r
	 * @param graphics
	 */
	private void drawNode(node_data node, int r, Graphics graphics) {

		geo_location point = node.getLocation();
		geo_location fp = this.w2f.world2frame(point);
		graphics.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
		graphics.drawString("" + node.getKey(), (int) fp.x(), (int) fp.y() - 4 * r);
	}

	/**
	 * draw the edges to the frame
	 * @param e
	 * @param graphics
	 */
	private void drawEdge(edge_data e, Graphics graphics) {

		// getting the graph from the arena
		directed_weighted_graph graph = this.arena.getGraph();

		// src point location
		geo_location src = graph.getNode(e.getSrc()).getLocation();

		// dest point location
		geo_location dest = graph.getNode(e.getDest()).getLocation();
		geo_location srcPoint = this.w2f.world2frame(src);
		geo_location destPoint = this.w2f.world2frame(dest);
		graphics.drawLine((int) srcPoint.x(), (int) srcPoint.y(), (int) destPoint.x(), (int) destPoint.y());
	}

	public void drawTime(Graphics graphics) {

		long time = this.arena.getTime();
		int game_level = this.arena.getLevel();
		int grade = this.arena.getGrade();
		int moves = this.arena.getMoves();
		int pokemons = this.arena.getNumberOfPokemons();

		String output =
				"game level: " + game_level +
				", time left: " + time +
				", pokemons: " + pokemons +
				", grade: " + grade +
				", moves: " + moves;

		graphics.setColor(Color.decode("000000"));
		graphics.drawString("" + output, 310, 50);

	}
}