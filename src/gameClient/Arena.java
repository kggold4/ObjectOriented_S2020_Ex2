package gameClient;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a multi Agents Arena which move on a graph - grabs Pokemons and avoid the Zombies.
 * @author Kfir Goldfarb and Nadav Keysar
 */

public class Arena {

	// epsilon for calculate some algorithms with geo locations
	public static final double EPS1 = 0.01, EPS2 = EPS1 * EPS1;

	// current level graph
	private directed_weighted_graph graph;

	// lists of the agents, pokemons and info
	private List<CL_Agent> agents;
	private List<CL_Pokemon> pokemons;
	private List<String> info;

	// points
	private static Point3D MIN = new Point3D(0, 100,0);
	private static Point3D MAX = new Point3D(0, 100,0);

	// time to end
	private long time;

	// level number
	private int game_level;

	// grade of the game
	private int grade;

	// number of moves of the agents
	private int moves;

	// number of pokemons in the game
	private int numberOfPokemons;

	/**
	 * default constructor
	 */
	public Arena() {
		this.info = new ArrayList<String>();
	}

	/**
	 * constructor by given a graph, and lists of agents and pokemons
	 */
	public Arena(directed_weighted_graph graph, List<CL_Agent> agents, List<CL_Pokemon> pokemon) {
		this.info = new ArrayList<String>();
		setGraph(graph);
		setAgents(agents);
		setPokemons(pokemon);
	}

	/**
	 * setting the pokemons to the arena by given pokemons list
	 * @param pks
	 */
	public void setPokemons(List<CL_Pokemon> pks) {
		this.pokemons = pks;
	}

	/**
	 * setting the agents to the arena by given agents list
	 * @param ags
	 */
	public void setAgents(List<CL_Agent> ags) {
		this.agents = ags;
	}

	/**
	 * setting the graph to the arena by given graph
	 * @param graph
	 */
	public void setGraph(directed_weighted_graph graph) { this.graph = graph; }

	/**
	 * setting to the arena info by given list of strings
	 * @param info
	 */
	public void set_info(List<String> info) { this.info = info; }

	/**
	 * initialize function
	 */
	private void init() {
		this.MIN = null; this.MAX = null;
		double x0 = 0, x1 = 0, y0 = 0, y1 = 0;
		Iterator<node_data> iter = this.graph.getV().iterator();
		while(iter.hasNext()) {
			geo_location c = iter.next().getLocation();
			if(this.MIN == null) {x0 = c.x(); y0=c.y(); x1 = x0; y1 = y0; this.MIN = new Point3D(x0, y0); }
			if(c.x() < x0) {x0=c.x();}
			if(c.y() < y0) {y0=c.y();}
			if(c.x() > x1) {x1=c.x();}
			if(c.y() > y1) {y1=c.y();}
		}
		double dx = x1-x0, dy = y1-y0;
		MIN = new Point3D(x0-dx/10,y0-dy/10);
		MAX = new Point3D(x1+dx/10,y1+dy/10);

	}

	/**
	 * return the agents list of the arena
	 * @return
	 */
	public List<CL_Agent> getAgents() { return agents; }

	/**
	 * return the pokemons list of the arena
	 * @return
	 */
	public List<CL_Pokemon> getPokemons() { return pokemons; }

	/**
	 * return the graph of the arena
	 * @return
	 */
	public directed_weighted_graph getGraph() { return this.graph; }

	/**
	 * return the info as list of strings of the arena
	 * @return
	 */
	public List<String> get_info() { return info; }

	/**
	 * static function that gets a string in json format of agents and a graph,
	 * and return a list of the agents objects
	 * @param agentsString
	 * @param graph
	 * @return
	 */
	public static List<CL_Agent> getAgents(String agentsString, directed_weighted_graph graph) {
		ArrayList<CL_Agent> ans = new ArrayList<CL_Agent>();
		try {
			JSONObject agentJson = new JSONObject(agentsString);
			JSONArray agentsArray = agentJson.getJSONArray("Agents");
			for(int i = 0; i < agentsArray.length(); i++) {
				CL_Agent agent = new CL_Agent(graph, 0);
				agent.update(agentsArray.get(i).toString());
				ans.add(agent);
			}
		} catch (JSONException e) { e.printStackTrace(); }
	 	return ans;
	}

	/**
	 *
	 * @param fs
	 * @return
	 */
	public static ArrayList<CL_Pokemon> json2Pokemons(String fs) {
		ArrayList<CL_Pokemon> ans = new  ArrayList<CL_Pokemon>();
		try {
			JSONObject ttt = new JSONObject(fs);
			JSONArray ags = ttt.getJSONArray("Pokemons");
			for(int i=0;i<ags.length();i++) {
				JSONObject pp = ags.getJSONObject(i);
				JSONObject pk = pp.getJSONObject("Pokemon");
				int t = pk.getInt("type");
				double v = pk.getDouble("value");
				//double s = 0;//pk.getDouble("speed");
				String p = pk.getString("pos");
				//System.out.println("pk: !!!: " + pp);
				CL_Pokemon f = new CL_Pokemon(new Point3D(p), t, v, 0, null);
				ans.add(f);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
		return ans;
	}
	public static void updateEdge(CL_Pokemon fr, directed_weighted_graph g) {
		//	oop_edge_data ans = null;
		Iterator<node_data> itr = g.getV().iterator();
		while(itr.hasNext()) {
			node_data v = itr.next();
			Iterator<edge_data> iter = g.getE(v.getKey()).iterator();
			while(iter.hasNext()) {
				edge_data e = iter.next();
				boolean f = isOnEdge(fr.getLocation(), e,fr.getType(), g);
				if(f) {fr.set_edge(e);}
			}
		}
	}

	public static boolean isOnEdge(geo_location p, geo_location src, geo_location dest ) {
		boolean ans = false;
		double dist = src.distance(dest);
		double d1 = src.distance(p) + p.distance(dest);
		if(dist > d1 - EPS2) { ans = true; }
		return ans;
	}

	private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g) {
		geo_location src = g.getNode(s).getLocation();
		geo_location dest = g.getNode(d).getLocation();
		return isOnEdge(p,src,dest);
	}

	private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g) {
		int src = g.getNode(e.getSrc()).getKey();
		int dest = g.getNode(e.getDest()).getKey();
		if(type<0 && dest>src) {return false;}
		if(type>0 && src>dest) {return false;}
		return isOnEdge(p,src, dest, g);
	}

	private static Range2D GraphRange(directed_weighted_graph g) {
		Iterator<node_data> itr = g.getV().iterator();
		double x0=0,x1=0,y0=0,y1=0;
		boolean first = true;
		while(itr.hasNext()) {
			geo_location p = itr.next().getLocation();
			if(first) {
				x0=p.x(); x1=x0;
				y0=p.y(); y1=y0;
				first = false;
			}
			else {
				if(p.x()<x0) {x0=p.x();}
				if(p.x()>x1) {x1=p.x();}
				if(p.y()<y0) {y0=p.y();}
				if(p.y()>y1) {y1=p.y();}
			}
		}
		Range xr = new Range(x0,x1);
		Range yr = new Range(y0,y1);
		return new Range2D(xr,yr);
	}

	public static Range2Range w2f(directed_weighted_graph g, Range2D frame) {
		Range2D world = GraphRange(g);
		Range2Range ans = new Range2Range(world, frame);
		return ans;
	}

	public void setTime(long time) { this.time = time; }

	public long getTime() { return this.time / 1000; }

	public void setLevel(int game_level) { this.game_level = game_level; }

	public int getLevel() { return this.game_level; }

	public void setGrade(int grade) { this.grade = grade; }

	public int getGrade() { return this.grade; }

	public void setMoves(int moves) { this.moves = moves; }

	public int getMoves() { return this.moves; }

	public void setNumberOfPokemons(int numberOfPokemons) { this.numberOfPokemons = numberOfPokemons; }

	public int getNumberOfPokemons() { return this.numberOfPokemons; }

}