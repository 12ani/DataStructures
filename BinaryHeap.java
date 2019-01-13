
package sxm180018;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Scanner;

 public class BinaryHeap <T extends Comparable<? super T>> {
    T[] pq;
    Comparator<T> comp;
    int size=0;
     // Constructor for building an empty priority queue using natural ordering of T
    public BinaryHeap(T[] q) {
        this(q, (T a, T b) -> a.compareTo(b));
    }
    // Constructor for building an empty priority queue with custom comparator
    public BinaryHeap(T[] q, Comparator<T> c) {
        pq = q;
        comp = c;
    }
     public void add(T x) { /* throw exception if pq is full */
        if(size==pq.length){
            throw new NoSuchElementException();
        } else {
            pq[size] = x;
            percolateUp(size);
            size++;
        }
    }
     public boolean offer(T x) { // return false if pq is full
        if(size==pq.length){
            return false;
        }
        else{
            add(x);
            return true;
        }
    }
     public T remove() { /* throw exception if pq is empty */
        if(size==0){
            throw new NoSuchElementException();
        }else{
        T min;
        min= pq[0];
        pq[0]=pq[size-1];
        size--;
        percolateDown(0);
        return min;
        }
    }
     public T poll() { /* return null if pq is empty */
        if(size==0){
            return null;
        }else{
            return remove();
        }
    }
     public T peek() { /* return null if pq is empty */
        if(size==0)
            return null;
        else
            return pq[0];
    }
     /** pq[i] may violate heap order with parent */
    void percolateUp(int i) { /* to be implemented */
        T x;
        x= pq[i];
         //pq[parent(i)] > x
        while( pq[ parent(i) ].compareTo(x) >0 && i >0){
            pq[i]=pq[parent(i)];
            i= parent(i);
        }
        pq[i]=x;
    }
     /** pq[i] may violate heap order with children */
    void percolateDown(int i) { /* to be implemented */
        T x;
        x = pq[i];
        int c = leftChild(i);
         while (c <= size-1){
            if( pq[c].compareTo(pq[c+1])>0 && c < size-1){
                c++;
            }
            if(x.compareTo(pq[c+1])<=0){
                break;
            }
            pq[i]=pq[c];
            i=c;
            c= 2*i+1;
        }
        pq[i]=x;
     }
     // Assign x to pq[i].  Indexed heap will override this method
    void move(int i, T x) {
        pq[i-1] = x;
    }
     int parent(int i) {
        return (i-1)/2;
    }
     int leftChild(int i) {
        return 2*i + 1;
    }
    void printHeap() {
        for (int i = 0; i <size ; i++) {
            System.out.print(" " + pq[i]);
        }
    }
// end of functions for team project
     public static void main(String[] args) {
        int heapSize =5;
        Integer[] arr;
        arr = new Integer[heapSize];
        BinaryHeap<Integer> binaryHeap= new BinaryHeap<>(arr);
         Scanner in = new Scanner(System.in);
        whileloop:
        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1://add element at rear
                    int x =in.nextInt();
                    try {
                        binaryHeap.add(x);
                    }catch (NoSuchElementException e){
                        System.out.println(e);
                    }
                     break;
                case 2:  // Removes the min element
                    try{
                        System.out.println(binaryHeap.remove());
                    }catch (NoSuchElementException e){
                        System.out.println(e);
                    }
                    break;
                case 3:  // see first element
                    System.out.println(binaryHeap.peek());
                    break;
                case 4: //print heap
                    binaryHeap.printHeap();
                    break;
                case 5: //move function
                    int i =in.nextInt();
                    int y =in.nextInt();
                    binaryHeap.move(i,y);
                    break;
                default:  // Exit loop
                    break whileloop;
            }
        }
     }
}
