package sxm180018;

import java.util.Iterator;
import java.util.Stack;
import java.util.Scanner;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {

    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
    }

    Entry<T> root;
    int size;
    Stack<Entry<T>> stackForParents;

    public BinarySearchTree() {
        root = null;
        size = 0;
        stackForParents = new Stack<>();
    }

    /**
     * Returns true if x contained in tree
     * @param x
     * @return boolean value
     */
    public boolean contains(T x) {
        Entry<T> t = find(x);
        if(t == null || (t.element.compareTo(x)!=0 ) )
            return false;
        else
            return true;
    }

    /**
     * Checks if there is an element that is equal to x in the tree?
     * Element in tree that is equal to x is returned, null otherwise.
     */
    public T get(T x) {
        Entry<T> t = find(x);
        if (t != null  && t.element.compareTo(x) == 0) {
            return t.element;
        } else {
            return null;
        }
    }

    /**
     * Adds x to tree. If tree contains a node with same key, replace
     * element by x. Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
        if (size == 0) {
            root = new Entry<>(x, null, null);
            size = 1;
            return true;
        }
        else {
            Entry<T> t = find(x);
            if (t.element.compareTo(x) == 0) {
                t.element = x; //replace
                return false;
            }
            else if (t.element.compareTo(x) == 1) {
                t.left = new Entry<>(x, null, null);
            }
            else {
                t.right = new Entry<>(x, null, null);
            }
            size++;
            return true;
        }

    }

    /**
     * Removes x from tree. Return x if found, otherwise return null
     */
    public T remove(T x) {

        if (root == null) {
            return null;
        }
        Entry<T> t = find(x);

        if (t.element.compareTo(x) != 0) {
            return null;
        }

        T result = t.element;

        if (t.left == null || t.right == null) {
            bypass(t);
            size--;

        } else {
            stackForParents.push(t);
            Entry<T> minRight = find(t.right, x);
            t.element = minRight.element;
            bypass(minRight);
            size--;
        }

        return result;
    }

    /**
     * Function bypasses the closest element in tree
     * @param t
     */
    public void bypass(Entry<T> t) {
        Entry<T> parent = stackForParents.pop();
        Entry<T> child = t.left == null ? t.right : t.left;

        if (parent == null) {
            root = child;
        } else {
            if (parent.left!=null && parent.left.equals(t) ) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        }
    }

    /**
     * Finds and returns the smallest element in tree
     * @return T
     */
    public T min() {
        Entry<T> t = root;
        if (size == 0) {
            return null;
        }
        while (t.left != null) {
            t = t.left;
        }
        return t.element;
    }

    /**
     * Finds and returns the largest element in tree
     * @return T
     */
    public T max() {
        Entry<T> t = root;
        if (size == 0) {
            return null;
        }
        while (t.right != null) {
            t = t.right;
        }
        return t.element;
    }

    // Create an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {

        Stack<Entry<T>> s_array = new Stack<>();
        Entry<T> current = root;
        int i = 0;
        Comparable[] arr = new Comparable[size];
        while ( current != null || s_array.size() > 0){
            while (current!=null){
                s_array.push(current);
                current = current.left;
            }

            current = s_array.pop();
            arr[i] = current.element;
            current = current.right;
            i++;
        }

        return arr;
    }

    /**
     * Finds the given element in tree
     * @param x
     * @return
     */
    public Entry<T> find(T x) {
        stackForParents.push(null);
        return find(root, x);
    }

    public Entry<T> find(Entry<T> node, T x) {
        if (node == null || node.element.equals(x) ) {
            return node;
        }

        while (true) {
            if (node.element.compareTo(x) == 1) {
                if (node.left == null) {
                    break;
                } else {
                    stackForParents.push(node);
                    node = node.left;
                }
            } else if (node.element.compareTo(x) == 0) {
                break;
            } else if (node.right == null) {
                break;
            } else {
                stackForParents.push(node);
                node = node.right;
            }
        }

        return node;
    }

// Start of Optional problem 2
    /**
     * Optional problem 2: Iterate elements in sorted order of keys Solve this
     * problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
        return null;
    }


    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int x = in.nextInt();
            if (x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if (x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for (int i = 0; i < t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                return;
            }
        }
    }

    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }

}
