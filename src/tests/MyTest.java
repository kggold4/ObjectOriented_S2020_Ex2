package tests;

import api.*;

public class MyTest {
    public static void main(String[] args) {

        node_data n0 = new NodeData(0);
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        node_data n3 = new NodeData(3);
        //node_data n4 = new NodeData(4);

        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(n0);g.addNode(n1);g.addNode(n2);g.addNode(n3);//g.addNode(n4);

        g.connect(0,1,23.36);
        g.connect(1,2,984.6);
        g.connect(2,3,46);
        g.connect(3,0,34);

        dw_graph_algorithms ga = new Algo_DWGraph(g);

        System.out.println(ga.isConnected());


    }
}
