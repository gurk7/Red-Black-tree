package RedBlackTree;

public class Printer<T extends Comparable> {
    private void printHelper(RedBlackTree<T> tree, RedBlackNode<T> root, String indent, boolean last) {
        // print the tree structure on the screen
        if (root != tree.Nil) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            String sColor = root.color.name();
            System.out.println(root.key + "(" + sColor + ")");
            printHelper(tree, root.left, indent, false);
            printHelper(tree, root.right, indent, true);
        }
    }
    public void print(RedBlackTree<T> tree) {
        printHelper(tree, tree.root, "", true);
    }
}
