package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_Algo;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.game_service;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ex2_Test implements Runnable {

    // window is responsible about the game window settings
    private static MyFrame _window;

    // arena is responsible about what will appear in the game window
    private static Arena _arena;

    // current graph of the level number
    private directed_weighted_graph _graph;

    // this hashmap store for every pokemon the src and the dest nodes in the graph
    private HashMap<CL_Pokemon, ArrayList<Integer>> _pokemonLocation;

    // this hashmap store a path between an agent to a pokemon
    private HashMap<CL_Agent, List<Integer>> _agentsPath;

    private HashMap<CL_Pokemon, Boolean> _pokemonLock;

    // for every agent this _agentsMoving hashmap store true or false if the agent have a path
    private HashMap<CL_Agent, Boolean> _agentsMoving;


    public static void main(String[] args) {
        Thread game2 = new Thread(new Ex2_Test());
        game2.start();
    }

    @Override
    public void run() {

        int level_number = 1;
        game_service game = Game_Server_Ex2.getServer(level_number);
        // int id = 123456789
        // game.login(id)
        this.init(game);

        // starting game
        game.startGame();

        refreshAgents(game);
        refreshPokemon(game);

        //this._arena.getAgents();

        // set title to the game window
        this._window.setTitle("Ex2 - OOP");
        int counter = 0;
        long millis = 1000;

        //this._arena.setAgents();

        //JSONObject agentJson = JsonReader(agentString);

        // while game is running
        while(game.isRunning()) {
            //

            if(this._arena.getAgents().get(0).getSrcNode() == 8) {
                game.chooseNextEdge(0,9);
            } else {
                game.chooseNextEdge(0,8);
            }

            System.out.println(game.move());

            try {

                if(counter % 1 == 0) {
                    counter = 1;

                    //
                    //
                    //

                    this._window.repaint();
                } else counter = 0;

                Thread.sleep(millis);
                counter++;

            }
            catch(Exception e) { e.printStackTrace(); }
        }

        // exit program after game stop running game
        System.exit(0);
    }

    // initialize game to arena and window
    private void init(game_service game) {

        // getting the current graph and create new graph by the string _graph
        String string_graph = game.getGraph();
        dw_graph_algorithms graph_algo = new DWGraph_Algo(string_graph);

        // initialize graph
        this._graph = graph_algo.getGraph();
        this._pokemonLocation = new HashMap<>();
        this._agentsPath = new HashMap<>();
        this._agentsMoving = new HashMap<>();
        this._pokemonLock = new HashMap<>();

        String p = game.getPokemons();

        // create an arena and window
        this._arena = new Arena();
        this._arena.setGraph(this._graph);
        this._arena.setPokemons(Arena.json2Pokemons(p));
        this._window = new MyFrame("Ex2");
        this._window.setSize(1000, 700);
        this._window.update(this._arena);
        this._window.show();

        // getting the all game json to string
        String game_info = game.toString();

        try {

            // getting the game and the game server as json object
            JSONObject game_json = new JSONObject(game_info);
            JSONObject game_server = game_json.getJSONObject("GameServer");

            int numberOfAgents = game_server.getInt("agents");
            int src_node = 0;  // arbitrary node, you should start at one of the pokemon
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for(int a = 0; a < cl_fs.size(); a++) {
                Arena.updateEdge(cl_fs.get(a), this._graph);
            }
            for(int a = 0; a < numberOfAgents; a++) {
                int ind = a % cl_fs.size();
                CL_Pokemon c = cl_fs.get(ind);
                int nn = c.get_edge().getDest();
                if(c.getType()<0 ) {
                    nn = c.get_edge().getSrc();
                }

                game.addAgent(nn);
            }
        }
        catch (JSONException e) {e.printStackTrace();}

    }

    private void refreshAgents(game_service game) {

        // getting a list of the game agents in json format
        String agents = game.getAgents();
        List<CL_Agent> game_agents = Arena.getAgents(agents, this._graph);
        this._arena.setAgents(game_agents);
    }

    private void refreshPokemon(game_service game) {

        // getting pokemons from game
        String p = game.getPokemons();

        // getting from the arena the pokemons
        List<CL_Pokemon> arena_p = Arena.json2Pokemons(p);

        this._arena.setPokemons(arena_p);
    }
}