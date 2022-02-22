import java.util.Scanner;
/*
 * Highest exponent that can be used before stack overflow is 24.
 * This makes an effective h value of 1/16777216, which is a very small interval.
 * Future updates could be made to optimize the looping system in the main euler
 * method, allowing for even smaller h values and more accurate estimation, and adding a gui.
 */
public class eulerMethod {
	
	final static double x_0 = 0; //init val of x
	final static double y_0 = 0; //init val of y 
	final static double x_end = 2; //end point of x
	

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Please select mode:");
		System.out.println("[s] single h value");
		System.out.println("[d] decreasing h value");
		char mode = sc.next().charAt(0);
		
		System.out.println("Please select algorithm:");
		System.out.println("[i] improved euler method");
		System.out.println("[n] normal euler method");
		char algo = sc.next().charAt(0);
		boolean imp = false;
		
		if(algo == 'i') {
			imp = true;
		} 
		
		System.out.println("Enter int i, where step size h=1/(2^i)");
		int i = sc.nextInt();
		
		if (mode =='d') {
			euler_repeat(i, imp);
		}
		else if(mode == 's') {
			double h = 1/Math.pow(2, i);
			euler_method(h, true, imp);
			
		}
		else {
			System.out.println("Not a valid option idiot");
		}
		sc.close();
	}
	
	/*
	 * This method is used to cycle from a denominator of 2^0 to a max 
	 * denominator of 2^i. exponents of 2 are used to eliminate any rounding 
	 * errors from the how doubles are made in binary systems
	 */
	public static void euler_repeat(int i, boolean imp) {
		for(int j=0; j<=i; j++ ) {
			System.out.println("-------------- h value of (1/2^" + j + ") --------------\n");
			//may need to replace all double with float again
			double h = 1/Math.pow(2, j);
			euler_method(h, false, imp);
			//System.out.println("------------------------------------------------");
		}
	}
	/*
	 * Method used to do the bulk of the euler method. Uses a loop to
	 * continually calculate with the new x and y
	 */
	public static void euler_method(double h, boolean s, boolean imp) {
		int length = (int) Math.round((x_end-x_0)/h);//2 can be adjusted to final x val
		double[][] xy_array = new double[length+1][4];
		double x = x_0; //initial value of x. Can be changed
		double y = y_0; //initial value of y. can be changed
		double true_y = y_0;
		double error = 0;
		
		xy_array[0][0] = x;
		xy_array[0][1] = y;
		
		int row = 0;
		if (s) {
			while (x < x_end) {//may want to take out redundant loops
				x = x + h;
				y = diff_eq(x, y, h, imp);
				true_y = true_solution(x);
				error = y - true_y;
				

				row += 1;

				xy_array[row][0] = x;
				xy_array[row][1] = y;
				xy_array[row][2] = true_y;
				xy_array[row][3] = error;
			}
			print_array(xy_array);
			return;//maybe uneccesary
		}
		else {
			while (x < x_end) {//may want to take out redundant loops
				x = x + h;
				y = diff_eq(x, y, h, imp);
				true_y = true_solution(x);
				error = true_y - y;
			}
			System.out.print("[" + x + "]");
			System.out.println("   [" + y + "]"); //remove ln to print errors
//			System.out.print("   [" + true_y + "]");
//			System.out.println("   [" + error + "]");
			return;
		}
	}
	
	/*
	 * method used for easily changing the differential equation.
	 * Can be used for more broad applications this way.
	 * Paramter imp used to differentiate between improved and normal euler method
	 */
	public static double diff_eq(double x, double y, double h, boolean imp) {
		if(imp) {//true if using improved euler method
			double k_1 = f_x(x,y);
			double u_n = y + (h*k_1);
			double k_2 = f_x(x,u_n);
			double y_n = y + h*0.5*(k_1+k_2);
			
			return y_n;
		}
		else {
			double diff = f_x(x,y);
			double y_n = y + h*diff;
			return y_n;
		}
	}
	
	/*
	 * Used to more clearly edit the initial diff equation. This is F(x) in both
	 * the original euler and the improved.
	 */
	public static double f_x(double x, double y) {
		//double f_x = (2*Math.pow(x, 3)/y); //QUESTION 13
		double f_x = (x*x)+(y*y); //QUESTION 27 & 30 (27: include -1 at the end)
		//double f_x = 0.0225*y - 0.0003*Math.pow(y, 2); //QUESTION 26
		return f_x;
	}
	
	/*
	 * If the original solution is known enter it here to calculate error
	 */
	public static double true_solution(double x) {
		double p = Math.pow((Math.pow(x, 4) + 8),0.5);//SOLUTION 13
//		double y = Math.pow(p, 0.5);
		
		return p; //change to 0 if true solution is not known.
	}
	
	public static void print_array(double[][] xy) {//likely won't be used
		System.out.println("[x]        [y]                     [true y]              [error]");
		
		for (double[] row: xy) {
			System.out.print("["+row[0]+"]     ");
			System.out.println("["+row[1]+"]     ");
//			System.out.print("["+row[2]+"]     ");
//			System.out.println("["+row[3]+"]");
			System.out.println("-----------------------------------------------------------------------------------");
		}
	}
}
