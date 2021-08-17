package RedBlackTree;

public class RedBlackTree<T extends Comparable> {
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

    private void leftRotate(RedBlackNode<T> x) {
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

    public void insert(RedBlackNode<T> z) {
        RedBlackNode<T> y = Nil;
        RedBlackNode<T> x = root;

        while(x != Nil) {
            y = x;
            // if z.key < x.key
            if (z.key.compareTo(x.key) < 0)
                x = x.left;
            else
                x = x.right;
        }
        z.parent = y;
        // Case z is the only node in the tree - the root
        if(z.parent == Nil)
            root = z;
        else
            // if z.key < y.key
            if (z.key.compareTo(y.key) < 0)
                y.left = z;
            else
                y.right = z;
        z.left = Nil;
        z.right = Nil;
        z.color = Color.RED;

        insertFix(z);
    }

    private void insertFix(RedBlackNode<T> z) {
        while(z.parent.color == Color.RED) {
            // loop invariant - z.parent.parent.color is BLACK. (guaranteed by the RB properties).
            if(z.parent == z.parent.parent.left) {
                RedBlackNode<T> uncle = z.parent.parent.right;
                // Case 1
                if(uncle.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                }
                // Case 2
                else
                    // The uncle color is BLACK
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    // Case 3
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rightRotate(z.parent.parent);
            }
            // same as then clause with "right" and "left" exchanged
            else {
                RedBlackNode<T> uncle = z.parent.parent.left;
                // Case 4
                if(uncle.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                }
                // Case 5
                else {
                    // The uncle color is BLACK
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    // Case 6
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    leftRotate(z.parent.parent);
                }
            }

        }
        root.color = Color.BLACK;
    }
}
