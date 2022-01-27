import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static java.lang.System.lineSeparator;
import static java.lang.System.out;

public class Main {
    public static void main(String[] args) throws IOException {
        DynamicGraph G = new DynamicGraph();
        GraphNode n1 = new GraphNode(1);
        G.head = n1;
        GraphNode n2 = new GraphNode(2);
        G.addGraphNode(n2);
        GraphNode n3 = new GraphNode(3);
        G.addGraphNode(n3);
        GraphNode n4 = new GraphNode(4);
        G.addGraphNode(n4);
        GraphNode n5 = new GraphNode(5);
        G.addGraphNode(n5);
        GraphNode n6 = new GraphNode(6);
        G.addGraphNode(n6);
        GraphNode n7 = new GraphNode(7);
        G.addGraphNode(n7);
        GraphNode n8 = new GraphNode(8);
        G.addGraphNode(n8);

        GraphEdge e1 = new GraphEdge(new Node<GraphNode>(n1), new Node<GraphNode>(n2));

        GraphEdge e3 = new GraphEdge(new Node<GraphNode>(n1), new Node<GraphNode>(n3));

        GraphEdge e2 = new GraphEdge(new Node<GraphNode>(n2), new Node<GraphNode>(n4));

        GraphEdge e5 = new GraphEdge(new Node<GraphNode>(n2), new Node<GraphNode>(n5));


        GraphEdge e4 = new GraphEdge(new Node<GraphNode>(n3), new Node<GraphNode>(n4));


        GraphEdge e6 = new GraphEdge(new Node<GraphNode>(n3), new Node<GraphNode>(n6));

        GraphEdge e7 = new GraphEdge(new Node<GraphNode>(n5), new Node<GraphNode>(n8));

        GraphEdge e8 = new GraphEdge(new Node<GraphNode>(n6), new Node<GraphNode>(n5));

        GraphEdge e9 = new GraphEdge(new Node<GraphNode>(n6), new Node<GraphNode>(n8));

        GraphEdge e10 = new GraphEdge(new Node<GraphNode>(n6), new Node<GraphNode>(n7));

        GraphEdge e11 = new GraphEdge(new Node<GraphNode>(n7), new Node<GraphNode>(n8));
        GraphEdge e12 = new GraphEdge(new Node<GraphNode>(n8), new Node<GraphNode>(n1));




        DataOutputStream outStream = new DataOutputStream(out);

        RootedTree tree = new RootedTree();
        Node<GraphNode> XI = new Node<GraphNode>(n1);
        tree.root = XI;
        Node<GraphNode> X2 = new Node<GraphNode>(n2);

        XI.children_list.addNode(X2);
        Node<GraphNode> X3 = new Node<GraphNode>(n3);
        XI.children_list.addNode(X3);
        Node<GraphNode> X4 = new Node<GraphNode>(n4);
        X2.children_list.addNode(X4);




        tree.printByLayer(outStream);

        outStream.flush();
    }
}
