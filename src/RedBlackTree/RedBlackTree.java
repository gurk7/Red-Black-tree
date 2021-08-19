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

    //region Rotations
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
    //endregion

    //region Insertion
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
    //endregion

    //region Deletion
    //Replaces the subtree rooted at u with a subtree rooted at v
    private void transplant(RedBlackNode<T> u, RedBlackNode<T> v) {
        if(u.parent == Nil)
            root = v;
        else
            if(u == u.parent.left)
                u.parent.left = v;
            else
                u.parent.right = v;
        v.parent = u.parent;
    }

    //Returns the minimum node in the subtree rooted at x
    private RedBlackNode<T> minimum(RedBlackNode<T> x) {
        while(x.left != Nil)
            x = x.left;
        return x;
    }

    public RedBlackNode<T> search(T key) {
        RedBlackNode<T> cur = root;
        while(cur != Nil) {
            // if cur.key < key
            if (cur.key.compareTo(key) < 0) {
                cur = cur.right;
            }
            else {
                // if cur.key = key
                if(cur.key.compareTo(key) == 0) return cur;
                else cur = cur.left; // cur.key > key
            }
        }
        return cur;
    }
    //Delete node with key T from the RB-tree
    public void delete(T key) {
        RedBlackNode<T> z = search(key);
        if(z != Nil) delete(z);
    }
    //Delete node z from the RB-tree
    public void delete(RedBlackNode<T> z) {
        RedBlackNode<T> y = z, x;
        Color yOriginalColor = y.color;
        if(z.left == Nil) {
            x = z.right;
            transplant(z, z.right);
        }
        else {
            if (z.right == Nil) {
                x = z.left;
                transplant(z, z.left);
            } else {
                // z has two children
                y = minimum(z.right); // y is the successor of z
                yOriginalColor = y.color;
                x = y.right;
                if (y.parent == z) {
                    x.parent = y; // Handling for the sentinel Nil
                } else {
                    transplant(y, y.right);
                    y.right = z.right;
                    y.right.parent = y;
                }
                transplant(z, y);
                y.left = z.left;
                y.left.parent = y;
                y.color = z.color;
            }
        }
         if(yOriginalColor == Color.BLACK) {
             deletionFix(x);
         }
    }

    private void deletionFix(RedBlackNode<T> x) {
        while(x != root && x.color == Color.BLACK) {
            if (x == x.parent.left) {
                RedBlackNode<T> xSibling = x.parent.right;
                // Case 1 - x's sibling is red
                if(xSibling.color == Color.RED) {
                    xSibling.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    leftRotate(x.parent);
                    xSibling = x.parent.right;
                }
                // Case 2 - x's sibling is black, and both of sibling's children are black
                if(xSibling.left.color == Color.BLACK && xSibling.right.color == Color.BLACK) {
                    xSibling.color = Color.RED;
                    x = x.parent;
                }
                else {
                    // Case 3 - x's sibling is black, and the sibling's left child is red while his right child is black
                    if(xSibling.right.color == Color.BLACK) {
                        xSibling.left.color = Color.BLACK;
                        xSibling.color = Color.RED;
                        rightRotate(xSibling);
                        xSibling = xSibling.parent.right;
                    }
                    // Case 4 - x's sibling is black and the sibling's right child is red
                    xSibling.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    xSibling.right.color = Color.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }
            else {
                // same as then clause with "right" and "left" exchanged
                RedBlackNode<T> xSibling = x.parent.left;
                // Case 5 - x's sibling is red
                if(xSibling.color == Color.RED) {
                    xSibling.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    rightRotate(x.parent);
                    xSibling = x.parent.left;
                }
                // Case 6 - x's sibling is black, and both of sibling's children are black
                if(xSibling.left.color == Color.BLACK && xSibling.right.color == Color.BLACK) {
                    xSibling.color = Color.RED;
                    x = x.parent;
                }
                else {
                    // Case 7 - x's sibling is black, and the sibling's right child is red while his left child is black
                    if (xSibling.left.color == Color.BLACK) {
                        xSibling.right.color = Color.BLACK;
                        xSibling.color = Color.RED;
                        leftRotate(xSibling);
                        xSibling = xSibling.parent.left;
                    }
                    // Case 8 - x's sibling is black and the sibling's left child is red
                    xSibling.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    xSibling.left.color = Color.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
    }
    //endregion
}
