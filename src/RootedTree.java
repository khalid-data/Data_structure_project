import java.io.DataOutputStream;
import java.io.IOException;

public class RootedTree {
    Node<Integer> root;
    //RootedTree next_tree;

    public RootedTree()
    {
        this.root = null;
    }

    public RootedTree(int root)
    {
        this.root = new Node<Integer>(root, true);
        // now we have a root and its children list
    }



    /*
    adds child to the root
     */
    void addChild ( int key)// adds child to
    {
        Node<Integer> nNode = new Node<Integer>(key, true);// this node has a key and a list of children
        root.children_list.addNode(nNode);
    }


    public void printByLayer(DataOutputStream out) throws IOException {
        Queue<Integer> queue = new Queue<Integer>();
        queue.Enqueue(root);
        queue.Enqueue(new Node<Integer>(-1));// new node with -1 as a indicator
        do {
            if(queue.queue.head.Node == -1)
            {
                out.writeBytes(System.lineSeparator());
                queue.Dequeue();
            }
            addToQueue(queue);
            Node<Integer> to_print = queue.Dequeue();
            out.writeInt(to_print.Node);
            out.writeChar(',');
        }
        while (queue.queue.head.Node != null);

    }

    void addToQueue( Queue<Integer> queue)
    {
        Node<Integer> current = queue.queue.head;
        while(current!=null)// ad all current queue head to queue
        {
            current = current.next;// next child of queue head
            queue.Enqueue(queue.queue.head);

        }
        queue.Enqueue(new Node<Integer>(-1));
    }

    private void print_tree(Node<Integer> root, DataOutputStream out) throws IOException {
        out.writeInt(root.Node);

        Node<Integer> current = root.children_list.head;
        while(current!=null)// ad all current queue head to queue
        {
            out.writeInt(current.Node);
            out.writeChar(',');
            current = current.next;// next child of queue head
            print_tree(current, out);
        }
    }

    public void preorderPrint(DataOutputStream out) throws IOException {
        print_tree(root, out);
    }
}
