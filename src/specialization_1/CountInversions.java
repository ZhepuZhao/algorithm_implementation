package specialization_1;

import java.math.BigInteger;
import java.util.Scanner;

public class CountInversions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("start!");
		int[] A = new int[100000];
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < 100000; i++) {
			A[i] = sc.nextInt();
		}
		sc.close();
		System.out.println("end");
		BigInteger result = countInversions(A, A.length);
		System.out.println(result);
		/*
		 * test case
		 */
//		int[] A = {1,3,5,2};
//		BigInteger result = countInversions(A, A.length);
//		System.out.println(result);
		
		/*
		 * example of big integer
		 */
//		BigInteger bigInt = new BigInteger("10");
//		System.out.println(bigInt.add(BigInteger.valueOf(1029399)).add(BigInteger.valueOf(1)));
	}
	
	// count inversions in an array
	public static BigInteger countInversions(int[] A, int length) {
		if (length == 1) {
			return BigInteger.valueOf(0);
		} else {
			int[] firstHalf = new int[length/2];
			int[] secondHalf = new int[length - length/2];
			BigInteger x = new BigInteger("0");
			BigInteger y = new BigInteger("0");
			BigInteger z = new BigInteger("0");
			int j = 0;			
			for (int i = 0; i < length; i++) {
				if (i < length/2) {
					firstHalf[i] = A[i];
				} else {
					secondHalf[j] = A[i];
					j++;
				}
			}
			x = countInversions(firstHalf , length / 2);
			y = countInversions(secondHalf, length - length / 2);
			z = countSplitInv(A, length, firstHalf, secondHalf);
			return  x.add(y).add(z);
		}
	}
	
	public static BigInteger countSplitInv(int[] A, int length, int[] firstHalf,
			int[] secondHalf) {
		int j = 0;
		int k = 0;
		BigInteger z = new BigInteger("0");
		for (int i = 0; i < length; i++) {
			if ( j != firstHalf.length && (k == secondHalf.length || firstHalf[j] <= secondHalf[k])) {
				A[i] = firstHalf[j];
				j++;
			} else {
				A[i] = secondHalf[k];
				z = z.add(BigInteger.valueOf(firstHalf.length - j));
				k++;
			}
		}
		return z;
	}
	

}
