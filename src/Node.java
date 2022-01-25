public class Node<T> {
    T Node;
    Node<T> next;
    Node<T> prev;
    Node<T> parent;
    LinkedList<T> children_list;


    public Node(T node){ //in case it has root node (key)
        this.Node = node ;
        this.children_list = new LinkedList<T>();
        next = null;
        prev = null;
        parent = null;
    }

    public void add_child(Node<T> node)
    {
        this.children_list.addNode(node);
    }
}