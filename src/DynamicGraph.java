import java.awt.font.TextHitInfo;

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
        if(this.head == null)
        {
            this.head = nGraphNode;
            nGraphNode.prev = null;
            nGraphNode.next = null;
            this.End = nGraphNode;
        }
        else
        {
            nGraphNode.prev = this.End;
            nGraphNode.next = null;
            this.End.next = nGraphNode;
            this.End = nGraphNode;
        }
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
        if (node.children.head == null && node.parents.head == null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next= null;
            node.prev = null;
        }
    }

    public GraphEdge insertEdge(GraphNode from, GraphNode to)
    {
        Node<GraphNode> from_node = new Node<GraphNode>(from);
        Node<GraphNode> to_node = new Node<GraphNode>(to);
        GraphEdge nEdge  = new GraphEdge(from_node, to_node);
        from.children.addNode(to_node);
        to.parents.addNode(from_node);
        // we should save edges somewhere
        return nEdge;
    }

    public void deleteEdge(GraphEdge edge)
    {
        edge.deleteEdge();
    }

    public RootedTree bfs(GraphNode source)
    {
        source.color = Grey;
        Queue<GraphNode> queue = new Queue<GraphNode>();
        Node<GraphNode> node = new Node<GraphNode>(source);
        queue.Enqueue(node);
        RootedTree tree = new RootedTree(source.key);
        while (queue.queue.head != null)
        {
           Node<GraphNode> u = queue.Dequeue();
           Node<GraphNode> current = u.children_list.head;
           while (current != null)
           {
               if (current.Node.color == White)
               {
                   current.Node.color = Grey;
                   current.Node.d ++;
                   current.Node.parent = u.Node;
                   queue.Enqueue(current);
                   u.children_list.addNode(current);
               }
               current = current.next; // check if this is the right next
           }
           u.Node.color = Black;
        }
        return tree;
    }

    public void dfs_visit(DynamicGraph G, GraphNode u, Time time, DynamicGraph nG)
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
                dfs_visit(G,u,time, nG);
            }
            v = v.next;
        }
        u.color = Black;
        u.retraction_time = time.value;
        GraphNode nU = new GraphNode(u.key);
        nU.parents = u.parents;
        nU.children = u.children;
        nG.addGraphNode(nU);// in this graph the first edge will be the one eith the smallest retraction time
        //and the last one will be the one with the biggest retraction time
    }


    public DynamicGraph dfs1(DynamicGraph G) // by running this dfs we get a new graph that has vertexes in increasing order of retraction time
    {
        DynamicGraph nG= new DynamicGraph();
        Time time = new Time(0);
        GraphNode U = G.head;
        while (U != null) {
            if (U.color != White) {
                dfs_visit(G,U, time, nG);
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
    void dfs_visit2(DynamicGraph Gt, GraphNode u, Time time, RootedTree r,Node<Integer> parent)
    {
        time.value = time.value+1;
        u.discovery_time = time.value;
        u.color = Grey;
        Node<Integer> new_node = new Node<Integer>(u.key);

        Node<GraphNode> v = u.parents.head;
        while(v!= null)
        {
            if (v.Node.color == White)
            {
                parent.children_list.addNode(new Node<Integer>(v.Node.key));
                v.Node.parent = u;
                dfs_visit2(Gt,u,time, r, new_node);
            }
            v = v.next;
        }
    }


    public RootedTree dfs2(DynamicGraph Gt) {
        RootedTree tree = new RootedTree(0);
        Time time = new Time(0);
        GraphNode U = Gt.End;
        while (U != null) {
            if (U.color != White) {
                Node<Integer> in_tree = new Node<Integer>(U.key);
                tree.root.children_list.addNode(in_tree);
                dfs_visit2(Gt,U, time,tree, in_tree);
            }
            U = U.prev;
        }
        return tree;
    }

    public RootedTree scc()
    {
        DynamicGraph gt = dfs1(this);// returns the transpose graph in the opposite of the wanted order
        return dfs2(gt);
    }

}
