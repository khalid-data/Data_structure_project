public class LinkedList<T> {
    Node<T> head;
    Node<T> End;

    public LinkedList() {
        this.head = null;
        this.End = this.head;
    }

    void addInFirst(Node<T> nNode) {
        if(this.head == null)
        {
            this.head = nNode;
            this.End = this.head;
        }
        else{
            nNode.next = this.head;
            this.head.prev = nNode;
            this.head = nNode;
        }

    }

    void addNode(Node<T> nNode) {
        //divide to 2 options
        //1) we didnt add nodes yet to the list
        if(this.head == null)
        {
            nNode.prev = null;
            nNode.next = null;
            this.head = nNode;
            this.End = this.head;

        }
        //2)we already have some nodes in
        else
        {
            nNode.next = null;
            nNode.prev = this.End;
            this.End.next = nNode;

        }
        this.End = nNode;
    }

    int get_length() {
        int cnt = 0;
        if (head!= null) {
            Node<T> current = head;
            while (current != null) {
                cnt++;
                current = current.next;
            }

        }
        return cnt;
    }

    void print_children(){
        if(head.Node instanceof GraphNode) {
            Node<T> current = head;
            while (current != null) {

                System.out.println(((GraphNode)current.Node).key +", ");
                current = current.next;
            }
        }
    }
}