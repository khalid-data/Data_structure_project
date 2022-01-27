

public class GraphNode
{
    static final int White = 0;// haven't been here yet
    static final int Grey = 1;// been here but not all children explored
    static final int Black = 2;// been hare and all children explored
    int color;
    int key;
    int d;//depth
    int discovery_time;
    int retraction_time;
    GraphNode parent;
    GraphNode next;
    GraphNode prev;
    LinkedList<GraphNode> children;// nodes that this node emits an edge to
    LinkedList <GraphNode>parents;//node that emits nodes to this node


    public GraphNode(int key) {
        this.key = key;
        this.color = White;
        this.d = -1;
        this.parent = null;
        this.discovery_time = 0;
        this.retraction_time = 0;
        children = new LinkedList<>();
        parents = new LinkedList<>();
    }

    public int getKey()
    {
        return this.key;
    }

    public int getOutDegree() { return this.children.get_length(); }

    public int getInDegree() { return this.parents.get_length(); }
}
