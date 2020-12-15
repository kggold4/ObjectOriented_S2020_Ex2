package gameClient;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class GamePanel extends JPanel {

    private Graphics2D g2D;
    private Image agent, pokaball1, pokaball2, back;
    private Arena _ar;
    directed_weighted_graph graph;
    private gameClient.util.Range2Range _w2f;
    private static String FONT = "Arial";

    public GamePanel(directed_weighted_graph g, Arena a) {
        super();
        _ar = a;
        graph = g;
        agent = new ImageIcon("./images/player.png").getImage();
        pokaball1 = new ImageIcon("./images/pokaball1.png").getImage();
        pokaball2 = new ImageIcon("./images/pokaball2.png").getImage();
        back = new ImageIcon("./images/background5.png").getImage();
    }

    public void updatePanel() {
        double j = ((this.getHeight() * this.getWidth()) / 4000);
        double k = ((this.getHeight() * this.getWidth()) / 100000);
        Range rx = new Range(k, this.getWidth() - 10);
        Range ry = new Range(this.getHeight() - 10, j);
        Range2D frame = new Range2D(rx, ry);
        directed_weighted_graph g = _ar.getGraph();
        _w2f = Arena.w2f(g, frame);
    }

    public void paint(Graphics g) {
        g2D = (Graphics2D) g;
        int w = this.getWidth();
        int h = this.getHeight();
        g.clearRect(0, 0, w, h);
        g2D.drawImage(back, 0, 0, w, h, null);
        drawGraph(g);
        drawPokemons(g);
        drawAgants(g);
        drawInfo(g);
    }

    public void drawNode(node_data n, Graphics g) {
        geo_location p = n.getLocation();
        geo_location f = this._w2f.world2frame(p);
        g.fillOval((int) f.x() - 5, (int) f.y() - 5, 2 * 5, 2 * 5);
        g.drawString("" + n.getKey(), (int) f.x(), (int) f.y() - 4 * 5);
    }

    public void drawGraph(Graphics graphics) {
        g2D = (Graphics2D)graphics;
        for(node_data node : graph.getV()) {
            Font font = new Font(FONT, Font.BOLD, 12);
            graphics.setFont(font);
            graphics.setColor(Color.CYAN);
            drawNode(node, graphics);
            for (edge_data e : graph.getE(node.getKey())) {
                g2D.setStroke(new BasicStroke(2));
                g2D.setColor(Color.WHITE);
                drawEdge(e, g2D, graph);
            }
        }
    }

    private void drawInfo(Graphics g) {

        Graphics2D g2D = (Graphics2D)g;

        g2D.setColor(Color.BLACK);
        g2D.setFont(new Font(FONT, Font.PLAIN, (this.getHeight() * this.getWidth()) / 40000));

        int x0 = this.getWidth() / 70;
        int y0 = this.getHeight() / 20;

        // print time left
        g2D.drawString("Time left: " + String.valueOf(_ar.getTime() / 1000), x0 * 30, y0 - 10);
        g2D.setFont(new Font(FONT, Font.PLAIN, (this.getHeight() * this.getWidth()) / 40000));

        int game_level = this._ar.getLevel();
        int grade = this._ar.getGrade();
        int moves = this._ar.getMoves();

        // print game level
        g2D.drawString("Level: " + game_level, x0 * 30, y0 + 30);
        g2D.setFont(new Font(FONT, Font.PLAIN, (this.getHeight() * this.getWidth()) / 40000));

        // print grade
        g2D.drawString("Grade " + grade, x0 * 30, y0 + 10);
        g2D.setFont(new Font(FONT, Font.PLAIN, (this.getHeight() * this.getWidth()) / 40000));

        // print moves
        g2D.drawString("Moves: " + moves, x0 * 30, y0 + 50);
        g2D.setFont(new Font(FONT, Font.PLAIN, (this.getHeight() * this.getWidth()) / 40000));
    }

    public void drawEdge(edge_data e, Graphics g, directed_weighted_graph gr) {
        geo_location src = gr.getNode(e.getSrc()).getLocation();
        geo_location dest = gr.getNode(e.getDest()).getLocation();
        geo_location s0 = this._w2f.world2frame(src);
        geo_location d0 = this._w2f.world2frame(dest);
        g.drawLine((int) s0.x(), (int) s0.y(), (int) d0.x(), (int) d0.y());
    }

    private void drawPokemons(Graphics g) {
        g2D = (Graphics2D) g;
        List<CL_Pokemon> fs = _ar.getPokemons();
        if (fs != null) {
            Iterator<CL_Pokemon> itr = fs.iterator();
            while (itr.hasNext()) {
                CL_Pokemon f = itr.next();
                Point3D c = f.getLocation();
                int r = 10;
                if (c != null) {
                    geo_location fp = this._w2f.world2frame(c);
                    if (f.getType() < 0) {
                        g2D.drawImage(pokaball1, (int) fp.x() - 3 * r, (int) fp.y() - r, 3 * r, 3 * r, null);
                    } else {
                        g2D.drawImage(pokaball2, (int) fp.x() - r, (int) fp.y() - r, 3 * r, 3 * r, null);
                    }
                }
            }
        }
    }

    private void drawAgants(Graphics g) {
        g2D = (Graphics2D) g;
        List<CL_Agent> rs = _ar.getAgents();
        g.setColor(Color.red);
        int i = 0;
        while (rs != null && i < rs.size()) {
            geo_location c = rs.get(i).getLocation();
            int r = 8;
            i++;
            if (c != null) {
                geo_location fp = this._w2f.world2frame(c);
                g2D.drawImage(agent, (int) fp.x() - 2 * r, (int) fp.y() - r, 5 * r, 5 * r, null);
            }
        }
    }
}
