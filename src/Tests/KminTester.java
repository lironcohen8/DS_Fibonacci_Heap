package Tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KminTester {
    public static void main(String[] args){
       /* FibonacciHeap H = new FibonacciHeap();
        int j = 15; // Should be between 2 to 30
        double n = Math.pow(2, j)+1;

        List<Integer> numbers = new ArrayList<Integer>();

        int shift = (int) (Math.pow(2, j-1));
        for (int i = 0 ; i < n; i++){
            numbers.add(i-shift);
        }

        Collections.shuffle(numbers);

        for (int i = 0 ; i < n; i++){
            H.insert(numbers.get(i));
        }
        H.deleteMin();

        int k = (int)Math.pow(2, 10);
        System.out.println(Arrays.toString(FibonacciHeap.kMin(H, k)));*/
    

	for (int i = 1; i <= 15; i++) {
			//System.out.println(i);
			if (!TestKMin(getBinomialTreeOfRank(i))) 
					System.out.println("false");
		}
	System.out.println("done");
	}
	
	public static boolean TestKMin(FibonacciHeap H) {
		for (int i = 1; i < H.size(); i++) {
			if (!containsAll(H.kMin(H, i), i)) {
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean containsAll(int[] a, int num) {
		for (int i = 1; i <= num; i++) {
			if (a[i-1] != i) {
				return false;
			}
		}
		
		return true;
	}
	
	private static FibonacciHeap getBinomialTreeOfRank(int rank) {
		int size = (int) Math.pow(2, rank);
		FibonacciHeap h = new FibonacciHeap();
		
		for (int i = 0; i <= size; i++) {
			h.insert(i);
		}
		
		h.deleteMin();
		
		return h;
	}
}


