package sxm180018;

import java.util.Scanner;
 public class BoundedQueue<T>{
     private Object[] arr;
    private int rear, front, queueSize,size;
     public BoundedQueue(int size) {
      arr= new Object[size];
      this.size=size;
      queueSize =0;
      rear=-1;
      front=0;
    }
    //Add in the queue the rear
    public boolean offer(T x){
        if(queueSize ==size){ //if full
            return false;
        } else {
            if(isEmpty()){  //queue empty
                rear =0;
            } else{
                rear= (rear+1)% size;
            }
            arr[rear]=x;
            queueSize++;
            return true;
        }
    }
    //Remove from front
    public T poll() {
        if (isEmpty()) { //Empty queue
            return null;
        } else {
            T temp = (T) arr[front];
            queueSize--;
            if (front == rear) {
                front = 0;
                rear = -1;
            } else {
                front =(front+1)% size;
            }
            return temp;
        }
    }
     //Printing First
    public T peek(){
        if(isEmpty())
            return null;
        else{
            return (T) arr[front];
        }
    }
     //return the size of queue
    public int size(){
        return queueSize;
    }
     //Clear the queue
    public void clear(){
        front=-1;
        rear=-1;
    }
     //Check if queue is empty
    public boolean isEmpty(){
        if(queueSize == 0){
            return true;
        }else
            return false;
    }
    public void toArray(T[] a){
        if(!isEmpty()){
        int j=0;
        if (front<=rear) {
            for(int i=front; i<=rear; i++)
                a[j]=(T)arr[i];
                j++;
        } else{
            for (int i=front; i<size; i++){
               a[j]=(T)arr[i];
                j++;}
            for(int i=0; i<=rear; i++){
                a[j]=(T)arr[i];
                j++;}
        }
        }
    }
     //Helper function to print the queue
    public void printQueue()
    {
        if(isEmpty()) {
            System.out.println("Queue is Empty.");
         }else if (front<=rear) {
            for(int i=front; i<=rear; i++)
                System.out.print(arr[i]+" ");
        } else{
            for (int i=front; i<=size-1; i++)
                System.out.print(arr[i]+" ");
            for(int i=0; i<=rear; i++)
                System.out.print(arr[i]+" ");
        }
    }
     public static void main(String[] args){
        //size of queue
        int size=5;
        BoundedQueue<Integer> queue= new BoundedQueue<Integer>(size);
         //user defined array
        Integer[] a= new Integer[size];
         Scanner in = new Scanner(System.in);
         whileloop:
        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1://Add an element at rear
                    System.out.println(queue.offer(in.nextInt()));
                    break;
                case 2:// Remove an element from front
                    System.out.println(queue.poll());
                    break;
                case 3:// Returns front element, without removing it
                    System.out.println(queue.peek());
                    break;
                case 4:// Get the size of queue
                    System.out.println(queue.size());
                    break;
                case 5:// Check if queue is empty
                    System.out.println(queue.isEmpty());
                    break;
                case 6: // Clear all queue
                    queue.clear();
                    break;
                case 7:  // Cast it toArray
                    queue.toArray(a);
                    queue.printQueue();
                    break;
                default:  // Exit loop
                    break whileloop;
            }
        }
    }
}
