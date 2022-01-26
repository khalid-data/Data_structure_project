import java.awt.font.TextHitInfo;
import java.time.temporal.ChronoUnit;

public class DynamicGraph {
    static final int White = 0;// haven't been here yet
    static final int Grey = 1;// been here but not all children explored
    static final int Black = 2;// been hare and all children explored
    GraphNode head;
    GraphNode End;


    public DynamicGraph()
    {
        this.head = null;
        this.End = null;
    }

    void addGraphNode(GraphNode nGraphNode)
    {
        if(this.head == null)//graph is still empty
        {
            this.head = nGraphNode;
            nGraphNode.prev = null;
            nGraphNode.next = null;
        }
        else if (this.head != null && this.head.next == null)// there is only a head
        {
            this.End = nGraphNode;
            this.head.next = nGraphNode;
            this.End.prev = this.head;
            this.End.next = null;
        }
        else if(this.head!= null && this.End != null){
            this.End.next = nGraphNode;
            nGraphNode.prev = this.End;
            this.End = nGraphNode;
            this.End.next = null;
        }
        this.End = nGraphNode;
    }

    public GraphNode insertNode(int nodeKey)
    {
        GraphNode nNode = new GraphNode(nodeKey);
        addGraphNode(nNode);
        return nNode;
    }

    public void deleteNode(GraphNode node)
    {
        // we delete a node only if it has no edges
        // must check if it's head or end of graph this changes all

        //only one node in list
        if(node.next == null && node.prev == null) {
            node = null;
        }
        else if(node.next != null && node.prev == null) {// first node but not only
            this.head = node.next;
            this.head.prev = null;
            node = null;
        }
        else if(node.prev != null && node.next == null) {//last but not only
            this.End = node.prev;
            this.End.next = null;
            node = null;
        }
        else {//mid node
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    public GraphEdge insertEdge(GraphNode from, GraphNode to)
    {
        Node<GraphNode> from_node = new Node<GraphNode>(from);
        Node<GraphNode> to_node = new Node<GraphNode>(to);
        return new GraphEdge(from_node, to_node);
    }

    public void deleteEdge(GraphEdge edge)
    {
        edge.deleteEdge();
    }

    public RootedTree bfs(GraphNode source)
    {
        //Time distance = new Time(0);
        GraphNode Current = this.head;
        while (Current != null){
            Current.color = White;
            Current.d = -1;
            Current.parent = null;
            Current = Current.next;
        }
        source.color = Grey;
        source.d = 0;
        Queue<GraphNode> queue = new Queue<GraphNode>();
        Node<GraphNode> node = new Node<GraphNode>(source);
        queue.Enqueue(node);
        // initialization
        // remember to make an initialization function
        RootedTree tree = new RootedTree();
        tree.root = node;
        while (queue.list.head != null)// for each node in queue add it's children in graph
        {
            Node<GraphNode> u = queue.Dequeue();
            // check if the head isn't null before assigning it
            // go through it's neighbors in graph
            Node<GraphNode> current = u.Node.children.head;
            while (current != null) {
                if (current.Node.color == White) {
                    Node<GraphNode> V = new Node<GraphNode>(current.Node);
                    u.children_list.addNode(V);// add it as son of u because it was discovered by him
                    current.Node.color = Grey;
                    current.Node.d = u.Node.d+1;
                    current.Node.parent = u.Node;
                    queue.Enqueue(V);
                }
                current = current.next; // check if this is the right next
            }
            u.Node.color = Black;
        }
        return tree;
    }



    public void dfs_visit( GraphNode u, Time time, DynamicGraph nG)
    {
        time.value = time.value+1;
        u.discovery_time = time.value;
        u.color = Grey;
        Node<GraphNode> v = u.children.head;
        while(v!= null)
        {
            if (v.Node.color == White)
            {
                v.Node.parent = u;
                dfs_visit(v.Node,time, nG);
            }
            v = v.next;
        }
        u.color = Black;
        time.value = time.value+1;
        u.retraction_time = time.value;
        GraphNode nU = new GraphNode(u.key);
        nU.parents = u.parents;
        nU.children = u.children;
        nG.addGraphNode(nU);// in this graph the first edge will be the one with the smallest retraction time
        //and the last one will be the one with the biggest retraction time
    }


    public DynamicGraph dfs1() // by running this dfs we get a new graph that has vertexes in increasing order of retraction time
    {
        GraphNode Current = this.head;
        while (Current != null){
            Current.color = White;
            Current.parent = null;
            Current.retraction_time = Current.discovery_time = 0;
            Current = Current.next;
        }
        DynamicGraph nG= new DynamicGraph();
        Time time = new Time(0);
        GraphNode U = this.head;
        while (U != null) {// GOES THROUGH ALL GRAPH NODES
            if (U.color == White) {
                dfs_visit(U, time, nG);
            }
            U = U.next;
        }
        GraphNode V = nG.head;
        while (V!= null)
        {
            V.discovery_time= 0;
            V.retraction_time = 0;
            V=V.next;
        }
        return nG;
    }


    void dfs_visit2(DynamicGraph Gt, GraphNode u, Time time, RootedTree r,Node<GraphNode> parent)
    {
        time.value = time.value+1;
        u.discovery_time = time.value;
        u.color = Grey;

        Node<GraphNode> v = u.parents.head;
        while(v!= null)
        {
            if (v.Node.color == White)
            {
                Node<GraphNode> curr = new Node<GraphNode>(v.Node);
                parent.children_list.addNode(curr);
                v.Node.parent = parent.Node;
                dfs_visit2(Gt,u,time, r, curr);
            }
            v = v.next;
        }
    }


    public RootedTree dfs2(DynamicGraph Gt) {
        GraphNode zero_node = new GraphNode(0);
        RootedTree tree = new RootedTree(new Node<GraphNode>(zero_node));
        Time time = new Time(0);
        GraphNode U = Gt.End;
        while (U != null) {
            if (U.color == White) {
                Node<GraphNode> sonOfZero = new Node<GraphNode>(U);
                tree.root.children_list.addNode(sonOfZero);
                dfs_visit2(Gt,U, time,tree, sonOfZero);
            }
            U = U.prev;
        }
        return tree;
    }

    public RootedTree scc()
    {
        DynamicGraph gt = dfs1();// returns the transpose graph in the opposite of the wanted order
        return dfs2(gt);
    }

}
