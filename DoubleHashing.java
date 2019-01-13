package sxm180018;
import java.util.HashMap;
import java.util.Random;

public class DoubleHashing<K> {
    private float loadFactor = (float) 0.75;
    int count =0;

    enum Status{
        Empty, Deleted, Filled
    }

    class HashEntry<K>
    {
        K key;

        Status status;

        /* Constructor */
        HashEntry(K key){
            this.key = key;
            this.status = Status.Filled;
        }
    }


    private HashEntry[] hashTable;
    private HashEntry[] arr;
    int size;
    int tableSize;


    public DoubleHashing(int ts)
    {

        tableSize = ts;
        size = 0;
        hashTable = new HashEntry[ts];
        arr = new HashEntry[ts];
    }

    static int hash(int h) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    static int indexFor(int h, int length) { // length = table.length is a power of 2
        return h & (length-1);
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }

        return !(object == null || getClass() != object.getClass());
    }

    @Override
    public int hashCode() {
        int hashcode;
        hashcode = 67 * hash(5) + this.tableSize;
        return hashcode;
    }


    public int hashCode2(K key){
        int y = getPrime(hashTable.length);
        int hash2 = (y - (key.hashCode() % y)) ;
        return hash2;

    }

    public int hash1(K key) {

        int index1 = indexFor(hash(key.hashCode()), hashTable.length);
        return index1;
    }

    /*public int hashCode2(K key) {
        int hash2 = 1 + ((key.hashCode()) % (hashTable.length-2));
        System.out.print("hash2 " + hash2+ "\t");
        return hash2;
    }*/

    public int hashFunction (K key, int k){
        int i = (hash1(key) + k * hashCode2(key)) % hashTable.length;
        return i;
    }

    public int getPrime(int x)
    {
	int prime=0;
	if(x!=0) {
	for(int i=x-1; i>0; i--) {
		int count=0;
		for(int j=1; j<x; j++) {
		if(i%j==0) {
			count++;
			prime=i;
		}
		}
		if(count==2) {
			return prime;
		}
	}
	}
	return 3;
    }


    private int find(K x){

    	int k = 0, i, start;
        int xSpot = -1;

        i = hashFunction(x, k);

        start = i;

        do{
            HashEntry<K> e = hashTable[i];

            if(e!= null){
                if(e.status == Status.Deleted && xSpot<0) {
                    i = (i + 1) % tableSize;
                    continue;
                }
                else if(x.equals(e.key)){
                        xSpot = i;
                        return xSpot;
                 }
                 i = (i+1)%tableSize; //To only get the values within the range of tableSize, and to rollover and start from the beginning

            }
            else
            {
                if(xSpot < 0)  xSpot = i;
                return xSpot;
            }
        } while (i!=start);
        return xSpot;
        }


    public boolean contains(K x) {
        int loc = find(x);
        if (hashTable[loc] != null)
            return true;
        else
            return false;

    }


    private void rehash(){

        HashEntry[] allElements = new HashEntry[size];
	for(int i = 0, j = 0; i < hashTable.length; i++) {
		if (hashTable[i] != null && hashTable[i].status == Status.Filled) {
			allElements[j] = hashTable[i];
			hashTable[i] = null;
                        j++;
		}
	}

	tableSize = 2 * tableSize;
	size = 0;
	hashTable = new HashEntry[tableSize];

	for (int i = 0; i < allElements.length; i++) {
		add((K)allElements[i].key);
	}
}
 public boolean add (K x){
        if(size/(float)tableSize > loadFactor){
            rehash();
        }

        int loc = find(x);


        if (hashTable[loc] == null) {
            hashTable[loc] = new HashEntry(x);
            size++;
            return true;
        }
        if (hashTable[loc].status == Status.Deleted) {
            hashTable[loc] = new HashEntry(x);
            size++;
            return true;
        }
        //return false;

        if (hashTable[loc].key.equals(x))
            return false;
 return false;
    }



    public int size() {
        return size;
    }

    public boolean isEmpty(){

        return size == 0;
    }

    public K remove(K key){
        int loc = find(key);
        K result;
        if (hashTable[loc]!=null && hashTable[loc].key.equals(key)){
            result = (K) hashTable[loc].key;
            hashTable[loc].key = null;
            hashTable[loc].status = Status.Deleted;
            size--;
            return result;
        }
        else
            return null;
    }

    public int distinctElements(K[] arr) {
        int count =0;
        for(int i = 0; i < arr.length; i++){
            if(!contains(arr[i]))
            {   count++;
                add(arr[i]);
            }

    }
        return count;
    }

    public int countDistinct(){
        int i =0, count =tableSize;

        while(i<tableSize){
        if (hashTable[i] == null)
            count--;
            i++;
        }

    return count;

    }

    public void showHashTable()
    {
        for(int i = 0; i < hashTable.length; i++)
        {
            if(hashTable[i] != null)

                System.out.println(i + " " + hashTable[i].key);
        }
    }


}
