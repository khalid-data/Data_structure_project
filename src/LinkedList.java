public class LinkedList<T> {
    Node<T> head;
    Node<T> End;

    public LinkedList()
    {
        this.head = null;
        this.End = null;
    }

    void addNode(Node<T> nNode)
    {
        //divide to 2 options
        //1) we didnt add nodes yet to the list
        if(this.head == null)
        {
            nNode.prev = null;
            nNode.next = null;
            this.head = nNode;
            this.End = nNode;

        }
        //2)we already have some nodes in
        else
        {
            nNode.next = null;
            nNode.prev = this.End;
            this.End.next = nNode;
            this.End = nNode;

        }
    }

    int get_length()
    {
        int cnt = 0;
        if (head!= null)
        {
            cnt++;
            Node<T> current = head;
            while (current!= End)
            {
                cnt++;
                current= head.next;
            }
        }

        return cnt;
    }

}