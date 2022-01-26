import java.io.DataOutputStream;
import java.io.IOException;

/*
this class represents rooted treed
we would have prefered to make it generic so that the node could have anything and not just Graph nodes
but this makes it impossible to write the functions names we were asked to do exactly the way we were asked
we mean in function public RootedTree()
 */
public class RootedTree {
    Node<GraphNode> root;//each Node has linked list of Nodes (its children)
    //RootedTree next_tree;

    public RootedTree()
    {
        this.root = null;
    }

    public RootedTree(Node<GraphNode> node)
    {
        this.root = node;
        // now we have a root and its children list
    }

    public RootedTree(GraphNode node)
    {
        this.root = new Node<>(node);
        // now we have a root and its children list
    }

    /*
    adds child to the root
    note: to add node to roots child (its linked list) we do it in the node class
     */
    void addChild (Node<GraphNode> node)
    {
        if (root != null)
            root.children_list.addNode(node);
        else
            this.root = node;
    }


    public void printByLayer(DataOutputStream out) throws IOException {
        //make new que and put the root and indicator in it
        Queue<GraphNode> queue = new Queue<GraphNode>();
        queue.Enqueue(root);
        GraphNode indicator = new GraphNode(-1);
        queue.Enqueue(new Node<GraphNode>(indicator));// new node with graph nodes key -1 as a indicator

        //as long as the head isn't -1 or null keep adding the current's head children
        do {// we know the first time root isn't null
            if(queue.list.head != null && queue.list.head.Node.key == -1 && queue.list.head.next != null)
            {
                out.writeBytes(System.lineSeparator());
                // now we know we finished adding all of children in next level
                // and printing the ones in the current level
                queue.Enqueue(queue.Dequeue());
            }
            else if (queue.list.head != null && queue.list.head.Node.key == -1 && queue.list.head.next == null)
            {
                queue.Dequeue();
            }
            if (queue.list.head != null) {
                addToQueue(queue);//takes the head of the queue and adds all its children
                Node<GraphNode> to_print = queue.Dequeue();
                out.writeBytes(String.valueOf(to_print.Node.key));
                if (to_print.next.Node.key != -1){out.writeBytes(",");}
            }
        }
        while (queue.list.head != null);

    }

    /*
    for every node in the queue that is before -1 we add all its children
     */
    void addToQueue(Queue<GraphNode> queue)
    {
        Node<GraphNode> current = queue.list.head;
        Node<GraphNode> child_of_curr = current.children_list.head;

        while(child_of_curr != null)// ad all current queue head to queue
        {
            queue.Enqueue(child_of_curr);
            child_of_curr = child_of_curr.next;// next child of queue head
        }
       /* GraphNode indicator = new GraphNode(-1);
        queue.Enqueue(new Node<GraphNode>(indicator));//*/
    }

    private void print_tree(Node<GraphNode> root, DataOutputStream out, StringBuilder to_print) throws IOException {
        Node<GraphNode> current = root.children_list.head;
        while(current!=null)// ad all current queue head to queue
        {
            to_print.append(String.valueOf(current.Node.key)).append(",");

            print_tree(current, out, to_print);
            current = current.next;// next child of root aka right sibling of current

        }

    }

    public void preorderPrint(DataOutputStream out) throws IOException {
        StringBuilder to_print = new StringBuilder();
        to_print.append(String.valueOf(root.Node.key)).append(",");
        print_tree(this.root, out, to_print);
        int len = to_print.length();
        to_print.deleteCharAt(len-1);
        out.writeBytes(to_print.toString());
    }
}
