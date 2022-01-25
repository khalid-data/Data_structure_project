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

        GraphEdge e1 = new GraphEdge(new Node<GraphNode>(n2), new Node<GraphNode>(n1));

        GraphEdge e3 = new GraphEdge(new Node<GraphNode>(n3), new Node<GraphNode>(n1));

        GraphEdge e2 = new GraphEdge(new Node<GraphNode>(n4), new Node<GraphNode>(n1));


        RootedTree tree = new RootedTree();
        tree.addChild(new Node<>(n1));
        tree.addChild(new Node<>(n2));
        Node<GraphNode> node = new Node<>(n3);
        tree.addChild(node);
        node.children_list.addNode(new Node<>(n4));

        DataOutputStream outStream = new DataOutputStream(out);
        tree.printByLayer(outStream);
        outStream.writeBytes(lineSeparator());
        outStream.writeBytes(String.valueOf(n1.getInDegree()));
        outStream.flush();
    }
}
