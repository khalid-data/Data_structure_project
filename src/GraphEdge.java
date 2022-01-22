

public class GraphEdge
{

    Node<GraphNode> from;
    Node<GraphNode> to;

    public GraphEdge(Node<GraphNode> from,Node<GraphNode> to)
    {
        this.from = from;
        this.to = to;
    }

    public Node<GraphNode> getSource() {
        return from;
    }

    public Node<GraphNode> getDestination(){ return to; }

    void deleteNode(Node<GraphNode> to_delete)
    {
        // we should connect the prev and next Nodes together
        // make the node null so it will be deleted by the garbage disposal
        to_delete.prev.next = to_delete.next;
        to_delete.next.prev = to_delete.prev;
        to_delete = null;
    }

    public void deleteEdge()
    {
        if(to.next == null && to.prev == null)//only node in the list
        {
            from.Node.children.head = null;
            from.Node.children.End = null;
        }
        if (to.prev == null && to.next!=null)// first but not only one
        {
            from.Node.children.head = to.next;
            to.next.prev = null;
        }
        if(to.next == null && to.prev != null)// last node in list
        {
            from.Node.children.End = to.prev;
            to.prev.next = null;
        }
        if (to.next != null && to.prev != null)// in the mid
        {
            deleteNode(to);
        }


        if (from.next == null && from.prev == null)
        {
            to.Node.parents.head = null;
            to.Node.parents.End = null;
        }
        if (from.prev == null && from.next != null)// first but not only one
        {
            to.Node.parents.head = from.next;
            from.next.prev = null;
        }
        if(from.next == null && from.prev != null)// last node in list
        {
            to.Node.parents.End = to.prev;
            from.prev.next = null;
        }
        if (from.next != null && from.prev != null)// in the mid
        {
            deleteNode(from);
        }
        to = null;
        from = null;
    }
}
