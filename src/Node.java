public class Node<T> {
    T Node;
    Node<T> next;
    Node<T> prev;
    Node<T> parent;
    LinkedList<T> children_list;

    public Node(T Node)
    {
        this.Node = Node;
    }//in case it has graph node

    public Node(T node, boolean flag){ //in case it has root node (key)
        this.Node = node ;
        this.children_list = new LinkedList<T>();
        next = null;
        prev = null;
        parent = null;
    }
}