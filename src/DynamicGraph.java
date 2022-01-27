public class DynamicGraph {
    static final int White = 0;// haven't been here yet
    static final int Grey = 1;// been here but not all children explored
    static final int Black = 2;// been hare and all children explored
    GraphNode head;
    GraphNode End;


    public DynamicGraph() {
        this.head = null;
        this.End = null;
    }

    void addGraphNode(GraphNode nGraphNode) {
        if(this.head == null)//graph is still empty
        {
            this.head = nGraphNode;
            this.End = nGraphNode;
            nGraphNode.prev = null;
            nGraphNode.next = null;
        }
        else
        {
            this.head.prev = nGraphNode;
            nGraphNode.next = this.head;
            nGraphNode.prev = null;
            this.head = nGraphNode;

        }
    }

    public GraphNode insertNode(int nodeKey) {
        GraphNode nNode = new GraphNode(nodeKey);
        addGraphNode(nNode);
        return nNode;
    }

    public void deleteNode(GraphNode node) {
        // we delete a node only if it has no edges

        //only one node in list
        if (node.children.head!= null || node.parents.head != null)
            return;
        if(node.next == null && node.prev == null) {
            node = null;
            this.head = null;
            this.End = null;
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
            node = null;
        }
    }

    public GraphEdge insertEdge(GraphNode from, GraphNode to) {
        Node<GraphNode> from_node = new Node<GraphNode>(from);
        Node<GraphNode> to_node = new Node<GraphNode>(to);
        return new GraphEdge(from_node, to_node);
    }

    public void deleteEdge(GraphEdge edge)
    {
        edge.deleteEdge();
    }

    public RootedTree bfs(GraphNode source) {
        bfs_init();
        source.color = Grey;
        source.d = 0;
        Queue<GraphNode> queue = new Queue<GraphNode>();
        Node<GraphNode> node = new Node<GraphNode>(source);
        queue.Enqueue(node);
        RootedTree tree = new RootedTree();
        tree.root = node;
        while (queue.list.head != null)// for each node in queue add it's children in graph
        {
            Node<GraphNode> u = queue.Dequeue();
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
                current = current.next;
            }
            u.Node.color = Black;
        }
        return tree;
    }

    private void bfs_init(){
        GraphNode Current = this.head;
        while (Current != null){
            Current.color = White;
            Current.d = -1;
            Current.parent = null;
            Current = Current.next;
        }
    }

    public RootedTree scc()
    {
        return dfs2(dfs1());
    }

    /*
    * returns a linked list
    * the linked list's nodes have graph nodes
    * in decreasing order of retraction time from them after running dfs algorithm */
    public LinkedList<GraphNode> dfs1(){
        DFS_init();
        LinkedList<GraphNode> ordered_nodes = new LinkedList<>();
        Time time = new Time(0);

        GraphNode U = this.head;
        while (U != null) {// GOES THROUGH ALL GRAPH NODES
            if (U.color == White) {
                dfs_visit(U, time, ordered_nodes);
            }
            U = U.next;
        }
        return ordered_nodes;
    }


    private void dfs_visit(GraphNode u, Time time, LinkedList<GraphNode> ordered_nodes) {


        time.value = time.value + 1;
        u.discovery_time = time.value;
        u.color = Grey;

        Node<GraphNode> v = u.children.head;
        while (v != null) {
            if (v.Node.color == White) {
                v.Node.parent = u;
                dfs_visit(v.Node, time, ordered_nodes);
            }
            v = v.next;
        }
        u.color = Black;
        time.value = time.value + 1;
        u.retraction_time = time.value;
        ordered_nodes.addInFirst(new Node<>(u));
    }


    void dfs_visit2(Time time, Node<GraphNode> parent) {
        time.value = time.value+1;
        parent.Node.discovery_time = time.value;
        parent.Node.color = Grey;

        Node<GraphNode> v = parent.Node.parents.head;
        while(v!= null)
        {
            if (v.Node.color == White)
            {
                Node<GraphNode> curr = new Node<GraphNode>(v.Node);
                parent.children_list.addNode(curr);
                v.Node.parent = parent.Node;
                dfs_visit2(time, curr);
            }
            v = v.next;
        }
        parent.Node.color = Black;
        time.value++;
        parent.Node.retraction_time = time.value;

    }

    /*
    * runs the dfs on the transpose graph in the decreasing order of retraction time for the nodes after first dfs run
    * looking at the parents list for each node (of the adjacent nodes) is equivalent to building the transpose
    * the method builds a tree with a root '0' and each son of zero is a scc on its own in the graph*/
    public RootedTree dfs2(LinkedList<GraphNode> ordered_nodes) {
        DFS_init();

        GraphNode zero_node = new GraphNode(0);
        RootedTree tree = new RootedTree(new Node<GraphNode>(zero_node));
        Time time = new Time(0);

        Node<GraphNode> U = ordered_nodes.head;
        while (U!= null) {
            if (U.Node.color == White) {
                Node<GraphNode> curr_parent = new Node<>(U.Node);
                tree.root.children_list.addNode(curr_parent);

                dfs_visit2(time, curr_parent);
            }
            U = U.next;
        }
        return tree;
    }

    public void DFS_init(){
        GraphNode curr = this.head;
        while (curr != null){
            curr.color = 0;
            curr.parent  = null;
            curr.retraction_time = 0;
            curr.discovery_time = 0;
            curr = curr.next;
        }
    }

}
