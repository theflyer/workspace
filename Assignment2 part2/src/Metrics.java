import java.util.ArrayList;

public class Metrics {
	/*
	 * this method verifies that if the Normal randomized array follows the Gaussian Distribution.
	 */
	public double verifyDistribution(ArrayList<Double> Array, double mean, double std, double num_std){
		 int count = 0; // initializing the variable count to 0 which will be used to count the 
		 // the number of values that which we initialize in below .
		 double low_value = mean - (std * num_std); // initializing the lowest value 
		 double high_value = mean +(std * num_std);// initializing the highest value.
		 // 
		 for ( double value : Array){
			 if ( value <= high_value && value >= low_value){ // comparing each value if it's fits between the
				 // highest value and the lowest value. 
				 count++;// incrementing the counter.
			 }
		 }
		 double p =  ((double)count/Array.size())*100;// getting the percentage of how many value
		 // fits between the range.
	return p;
	}
	

}
