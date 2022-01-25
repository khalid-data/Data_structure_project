public class Queue<T> {
    LinkedList<T> list;
    //this linklist saves the head and tail

    public Queue()
    {
        list= new LinkedList<T>();
    }

    void Enqueue(Node<T> node)
    {
        Node<T> node1 = new Node<T>(node.Node);
        node1.children_list = node.children_list;
        list.addNode(node1);
    }

    Node<T> Dequeue()
    {
        if (list.head != null && list.head.next!= null)
        {
            Node<T> temp = list.head;
            list.head = temp.next;
            list.head.prev = null;
            return temp;
        }
        else if (list.head != null && list.head.next== null)
        {
            Node<T> temp = list.head;
            list.head = null;
            return temp;
        }
        return null;
    }
}
