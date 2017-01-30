
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Simulation {
	
	public int bin_number ; // declaring the public instance variable 
	
	/*
	 * This method generates random number and each number are placed in the arrayList.
	 * the size of the arraylist is given as a parameter as well as the array list.
	 */
	public void generateNormalRandomNumbers (ArrayList<Double> array1,int num){
		  Random rand = new Random (); // creating a random object from the random class 
		  
		  // creating an ArrayList by putting each random variable as an element of the arraylist
		  // until we have the size of the by the given parameter.
		  for( int i = 0 ; i<num; i++){
			  array1.add(rand.nextGaussian());
		  } 	  
	}

	/*
	 * This function returns an array which contain  a number of bins passed by parameter and each bins
	 * contains the number of elements fall into these bins . 
	 */
	public int[] makeBins (ArrayList<Double> array1, int number){
		this.bin_number= number;// here we initializing the class instance variable with given 
		// parameter of the number of bins.
		
		int[] b_array= new int [bin_number] ; // initializing the array.
		double min = getMin(array1); // getting the min value of the arrayList.
		double max = getMax(array1); // getting the max value of the arrayList.
		double range  = getRange(array1);// getting the range of the 
		
		//  this enhanced for loop works as a loop is used to pass the each value to the getBin method
		// with max, min and range. that function returns the index the variable would go then we  
		// we keep track of how many value sits in a same bin in the bin array.
		for ( Double i : array1){
			int b = getbin(i, range, min, max);// calling the getBin method for index.
			b_array[b]++;// incrementing the value of the indexes each time.
		}
		return b_array; // returning the bin array.
	}
	
	/*
	 * this is the method where we can get the index number of the bin array where the 
	 * we can store the element. we take four parameters number as a value of the array, range , minimum and 
	 * maximum of the arrayList.
	 */
	public int getbin(double number, double range, double min, double max){
		int count = 0 ; // initializing the count to 0 . By this variable we will count the index.
		double i =0.0; // initialing another variable to run our loop.
		
		while ( (min+i) <= max){ 
			
			if ( number >= max-range && number <= max){// here we are comparing if the value is 
				// in the last boundary or equal to the maximum value.
				return count = bin_number - 1;// we are returning the last index of the array.
			}
			else if (number >= (min+i) && number< ((min+i)+range) ){ // here  we are observing if the 
				// value fits in any other boundaries starting from the range except the last boundary.
				return count;
				
			}
			else {
				count++; // incrementing the count.
				i = i + range;// incrementing the i with range. so that we can compute the next minimum value of
				// of the boundary.
			}
		}
		
		return count;
		
	}
	
	/*
	 * this method returns the maximum value of the arrayList that was passed by the parameteres.
	 */
	public double getMax (ArrayList<Double> array1){
		Collections.sort(array1);// sorting the elements of the randomized array.
		double b = array1.get(array1.size() - 1); // storing the last element of the array 
		return b;// returning the last element of the array which is maximum.
	}
	
	/*
	 * This method returns the minimum value of the arrayList that was passed by a parameter.
	 */
	public double getMin (ArrayList<Double>array1){
		Collections.sort (array1);// sorting each element of the array. 
		double min = array1.get(0);// storing the first element of the array which is the minimum.
		return min; // returning the minimum value.
		
	}
	
	/*
	 * This method returns the range by which we will create out bins.
	 */
	public double getRange (ArrayList<Double>array1){
		double  min = getMin(array1);//  calling the getMin method to get the minimum value of arraylist.
		double 	max = getMax(array1);// calling the getMin method to get the maximum value of arraylist.
		
		double range = (max -  min)/bin_number;// computing the range.
		
		return range;// returning the range.
	}
	
}


