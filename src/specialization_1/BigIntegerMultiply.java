package specialization_1;

import java.math.BigInteger;

public class BigIntegerMultiply {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BigInteger a = new BigInteger("3141592653589793238462643383279502884197169399375105820974944592");
		BigInteger b = new BigInteger("2718281828459045235360287471352662497757247093699959574966967627");
		BigInteger c = a.multiply(b);
		System.out.println(c);
		
		System.out.println(multiply("3141592653589793238462643383279502884197169399375105820974944592"
				,"2718281828459045235360287471352662497757247093699959574966967627"));
	}
	
	public static String multiply(String num1, String num2) {
	    int m = num1.length(), n = num2.length();
	    int[] pos = new int[m + n];
	   
	    for(int i = m - 1; i >= 0; i--) {
	        for(int j = n - 1; j >= 0; j--) {
	            int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0'); 
	            int p1 = i + j, p2 = i + j + 1;
	            int sum = mul + pos[p2];

	            pos[p1] += sum / 10;
	            pos[p2] = (sum) % 10;
	        }
	    }  
	    
	    StringBuilder sb = new StringBuilder();
	    for(int p : pos) if(!(sb.length() == 0 && p == 0)) sb.append(p);
	    return sb.length() == 0 ? "0" : sb.toString();
	}

}
