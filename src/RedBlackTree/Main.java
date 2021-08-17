package RedBlackTree;

public class Main {

    public static void main(String[] args) {
	// write your code
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<Integer>();
        redBlackTree.insert(new RedBlackNode<Integer>(8));
        redBlackTree.insert(new RedBlackNode<Integer>(18));
        redBlackTree.insert(new RedBlackNode<Integer>(5));
        redBlackTree.insert(new RedBlackNode<Integer>(15));
        redBlackTree.insert(new RedBlackNode<Integer>(17));
        redBlackTree.insert(new RedBlackNode<Integer>(25));
        redBlackTree.insert(new RedBlackNode<Integer>(40));
        redBlackTree.insert(new RedBlackNode<Integer>(80));
        redBlackTree.insert(new RedBlackNode<Integer>(25));


        Printer<Integer> printer = new Printer<Integer>();
        printer.print(redBlackTree);
    }
}
