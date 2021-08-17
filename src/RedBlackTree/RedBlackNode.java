package RedBlackTree;

public class RedBlackNode<T extends Comparable> {
    T key;
    Color color;
    RedBlackNode<T> parent;
    RedBlackNode<T> left;
    RedBlackNode<T> right;
}
