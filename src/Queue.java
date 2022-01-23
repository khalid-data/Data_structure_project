public class Queue<T> {
    LinkedList<T> list;
    //this linklist saves the head and tail

    public Queue()
    {
        list= new LinkedList<T>();
    }

    void Enqueue(Node<T> node)
    {
        list.addNode(node);
    }

    Node<T> Dequeue()
    {
        if (list.head != null)
        {
            Node<T> temp = list.head;
            list.head = temp.next;
            list.head.prev = null;
            return temp;
        }
        return null;
    }
}
