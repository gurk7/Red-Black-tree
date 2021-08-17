package RedBlackTree;

public class RedBlackTree<T> {
    RedBlackNode<T> root;
    RedBlackNode<T> Nil;

    //Default Ctor - Initialize the root to be the Dummy Nil
    public RedBlackTree() {
        Nil = new RedBlackNode<T>();
        Nil.color = Color.BLACK;
        Nil.left = null;
        Nil.right = null;
        root = Nil;
    }

    private void LeftRotate(RedBlackNode<T> x) {
        RedBlackNode<T> right = x.right;
        x.right = right.left;
        if(right.left != Nil)
            right.left.parent = x;
        right.parent = x.parent;
        if(right.parent == Nil)
            root = right;
        else
            if(x == x.parent.left)
                x.parent.left = right;
            else
                x.parent.right = right;
        right.left = x;
        x.parent = right;
    }

    private void rightRotate(RedBlackNode<T> y) {
        RedBlackNode<T> left = y.left;
        y.left = left.right;
        if(left.right != Nil)
            left.right.parent = y;
        left.parent = y.parent;
        if(left.parent == Nil)
            root = left;
        else
            if(y == y.parent.left)
                y.parent.left = left;
            else
                y.parent.right = left;
        left.right = y;
        y.parent = left;
    }

    public void insert(T key) {

    }
}
