
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;



public class MonteCarlo {
	final static int num = 100000; // declaring final static integer value for the number of 
	// element in the Normal randomized array.
	
	final static int  bin_number = 11;// declaring final static integer value for the number of
	// bins in the bin array.
	
	public static void main (String[] args) {
		
     Simulation s = new Simulation(); // initializing the object for simulation class.
     ArrayList <Double> r_array= new ArrayList<Double>();// declaring a local arraList .
        
     s.generateNormalRandomNumbers(r_array,num);// initializing the arrayList r_array with the void
     // Norman random generating function from the simulation class.
     
	 int array2[] = s.makeBins(r_array, bin_number);// declaring and also initializing another Integer 
	 // array which contains the return array from the makeBin method from the simulation class.
	 // 
	 
	 // here i am printing out the values of the array2 in console which contains the number of elements each
	 // bin contains.
	 for ( int i : array2){
		 System.out.println(i);
	 }
	 
	 /* here we are printing out the values in a text file.*/
	 try{
		 FileOutputStream text = new FileOutputStream("Gauss.txt");// initializing of an object text
	 // of File output stream which initializing the file name that we are going to output the value.
		 PrintStream write_output = new PrintStream(text);//initializing the print stream object 
	 // that will print the value to the output stream.
	 
	 /* printing out all the values of the array in output stream.*/
		 for (int i : array2){
			 write_output.println(i);
		 
		 }
		 write_output.close();// closing the output file.
	 }
	 	catch (IOException e){ // this will throw an exception if there is any error.
	 		e.printStackTrace();
		 
	 } 
	 
	 Metrics mec = new Metrics();// creating an object of the metrics class
	 
	 for ( double i = 1.0 ; i <= 3.0; i++){
		 DecimalFormat formatter = new DecimalFormat("0.000");// formatting the value upto three decimal points.
		 double p = mec.verifyDistribution(r_array,0.0, 1.0, i);// getting the return value of the
		 // verify distribution method of the from the Metrics class.
		 String num = formatter.format(p);
		 System.out.println("(0.0, 1.0,"+ i + ") has Precentage of "+ num +"%");
	 }
	 
       
	}

	
}
