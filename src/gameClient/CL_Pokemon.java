package gameClient;

import api.*;
import gameClient.util.Point3D;
import org.json.JSONObject;

public class CL_Pokemon {
	private edge_data _edge;
	private double _value;
	private int _type;
	private Point3D _pos;
	private double min_dist;
	private int min_ro;
	private double _speed;

	public CL_Pokemon(Point3D point, int type, double value, double speed, edge_data e) {
		this._type = type;
		this._speed = speed;
		this._value = value;
		set_edge(e);
		this._pos = point;
		this.min_dist = -1;
		this.min_ro = -1;
	}
	public static CL_Pokemon init_from_json(String json) {
		CL_Pokemon ans = null;
		try {
			JSONObject p = new JSONObject(json);
			int id = p.getInt("id");

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ans;
	}

	public edge_data get_edge() {
		return _edge;
	}

	public void set_edge(edge_data _edge) {
		this._edge = _edge;
	}

	public Point3D getLocation() { return _pos; }

	public edge_data getEdge() { return this._edge; }

	public int getType() {return _type;}

	public double getSpeed() {return _speed;}

	public double getValue() {return _value;}

	public double getMin_dist() {
		return min_dist;
	}

	public void setMin_dist(double mid_dist) {
		this.min_dist = mid_dist;
	}

	public int getMin_ro() {
		return min_ro;
	}

	public void setMin_ro(int min_ro) {
		this.min_ro = min_ro;
	}

	public String toString() {return "F:{v="+_value+", t="+_type+"}";}
}
