// Assignment1
// Name : Asif Saifullah

package benford;

import java.util.Scanner;
import java.io.*;
import java.text.DecimalFormat;

public class Benford {
	/* declaring variables in private so that user cannot modify these variables.
	 * and since these variables are static, they have a default value.
	 */
	
    private static int lines;
    private static int num1,num2,num3,num4,num5,num6,num7,num8,num9;
    
	public static void main (String[] args) throws IOException{
		
       String[] text = readarray("data.txt");
  
      doCalculation(text);
      doPrint();
	   
	}
	
	/* this method read the text files by line and then stores
	* the elements in a string array and returns it back to the main.
	* This method also count the number of lines in the Dataset which will be used
	* in the next part of the program to compute average.
	*/
	public static String[] readarray (String file) throws IOException{
		
		Scanner line = new Scanner(new File(file));
		
		while (line.hasNextLine()){
			lines++;
			line.next();
		}
		
		String[] numbers = new String[lines] ;
		
		Scanner line2 = new Scanner(new File(file));
		
			for(int i=0; i<lines;i++){
				numbers[i] = line2.next();
			}
		return numbers;	
	}
	
	/* This method takes an string array as a parameter and for each element 
	 * in the string array is it calls another method which which converts the string
	 * into characters. 
	 */
	public static void doCalculation(String[] file){

		int j =0;
		while (j != lines){
			doChar(file[j]);
			j++;
		}
	}
    /*
     * In this method we take each element from the string array as a parameter and
     * converts the string into characters than find the most significant decimal value 
     * of the numbers in the text and counted using our private variables.
     */
	public static void doChar(String s){
		
		char[] a = s.toCharArray();
		
		boolean loop = true;
	    int j = 0;
	    
		while (loop){
			if (j< a.length){             // checking if the index goes out of bounds.
					if ( a[j] == '0' ||a[j] == '-'|| a[j] == '.'){
						j++;	
					}
					else if (a[j] == '1'){
						num1++;
						loop = false;
					}
					else if (a[j] == '2'){
						num2++;
						loop = false;
					}
					else if (a[j] == '3'){
						num3++;
						loop = false;
					}
					else if (a[j] == '4'){
						num4++;
						loop = false;
					}
					else if (a[j] == '5'){
						num5++;
						loop = false;
					}
					else if (a[j] == '6'){
						num6++;
						loop = false;
					}
					else if (a[j] == '7'){
						num7++;
						loop = false;
					}
					else if (a[j] == '8'){
						num8++;
						loop = false;
					}
					else if (a[j] == '9'){
						num9++;
						loop = false;
					}
					else { loop = false;}
					}
					
			else {
				loop= false;
			}	
		}	
	}
	
	/*
	 * This method takes the private static variables and prints the output.
	 */
	
	public static void doPrint(){
		
		// this is the object use for converting our double numbers to 3 decimal values.
		
	    DecimalFormat dformat = new DecimalFormat("0.000");
	    
	    double nmbr[] = new double [9];
	    
	     nmbr[0]= (((double)num1/lines)*100);
	     nmbr[1]= ((double)num2/lines)*100;
	     nmbr[2]= ((double)num3/lines)*100;
	     nmbr[3]= ((double)num4/lines)*100;
	     nmbr[4]= ((double)num5/lines)*100;
	     nmbr[5]= ((double)num6/lines)*100;
	     nmbr[6]= ((double)num7/lines)*100;
	     nmbr[7]= ((double)num8/lines)*100;
	     nmbr[8]= ((double)num9/lines)*100;
	     
	     String p[] = new String[9];
	     
	     for (int i= 0 ; i < p.length ; i++){
	    	 p[i] = dformat.format(nmbr[i]);
	    	 System.out.print( (i+1) +"\t"+"("+ p[i] + "%) \t"+":"+"  ");
	    	 
	    	 for ( int k =0; k< Math.round(nmbr[i]);k++){
	 			System.out.print("*");
	 			} 
	    	 System.out.println();
	 			
	     }   
	}

}
