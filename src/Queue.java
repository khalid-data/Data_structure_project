public class Queue<T> {
    LinkedList<T> queue;
    //this linklist saves the head and tail

    public Queue()
    {
        queue = new LinkedList<T>();

    }

    void Enqueue(Node<T> node)
    {
        queue.addNode(node);
    }

    Node<T> Dequeue()
    {
        if (queue.head != null)
        {
            Node<T> temp = queue.head;
            queue.head = temp.next;
            queue.head.prev = null;
            return temp;
        }
        return null;
    }
}
