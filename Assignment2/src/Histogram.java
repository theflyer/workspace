
import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
//import java.util.ArrayList;

//import javax.swing.JFrame;
import javax.swing.JPanel;


public class Histogram extends JPanel {
	final int TOP_MARGIN = 20;
	final int BOTTOM_MARGIN = 20;
	final int LEFT_MARGIN = 20;
	final int RIGHT_MARGIN = 20;
    // Declarations of instance variables:
	private double max;// private instance maximum variable of the bin array.
	private double min;// private instance minimum value of the arrayList from the class Simulation. 
    private int bins[];// private instance declaring integer array.
    private int binWidth;// private instance variable that will contain the value width of each bin in the picture.
    private double binSize;// private instance variable that will contain the range of bin array
    // from the Simulation class.
    
    // constructor
	/*
	 *this the constructor of the class that where i instantiated all the instances of the class.
	 */
	public Histogram(Simulation s) { 
		setBackground(Color.WHITE);
		
		this.bins = s.makeBins(s.array, s.bin_number);// initializing the bins array by the 
		// array we got it makeBins method from the simulation class.
	
		this.max = getmaximum(bins);//  initializing the instance variable that will contain
		// the maximum element is the bin array.
		this.min = s.getMin(s.array);// initializing the instance variable by the getMin returns 
		// from the simulation class.

		this.binSize = s.getRange(s.array);// initializing the instance variable by the getRange returns 
		// from the simulation class.

	}
	
	public void paintComponent(Graphics g) { 
	super.paintComponent(g);
    drawXAxis(g);
    drawYAxis(g);
    drawBins(g);
    drawXLabels(g);
    drawYLabels(g);
	}
	
	private void drawXAxis(Graphics g) { 
		int x1 = LEFT_MARGIN;
		int y1 = getHeight() - BOTTOM_MARGIN;
		int x2 = getWidth() - RIGHT_MARGIN;
		int y2 = y1;
		g.drawLine(x1, y1, x2, y2);
	}
	
	// drawYAxis Draws the y-axis
	private void drawYAxis(Graphics g) { 
		int x1 = LEFT_MARGIN;
		int y1 = getHeight() - BOTTOM_MARGIN;
		int x2 = x1;
		int y2 = TOP_MARGIN;
		g.drawLine(x1, y1, x2, y2);
	}
	
	// drawBins draws the bins
	private void drawBins(Graphics g) { 
		g.setColor(Color.GRAY);
	     
	  
	  int total_width =(int)((getWidth()- LEFT_MARGIN - RIGHT_MARGIN)/bins.length);// this the value
	  // that contains the width for each bin in the output.
		
	//binWidth = ((getWidth()- LEFT_MARGIN - RIGHT_MARGIN)/bins.length);
	  double binheight = (getHeight()-TOP_MARGIN-60)/max;
	  int i = 0;
	 for ( int n : bins){
	      int x = LEFT_MARGIN + (i*total_width);// this variable x will initialize the each value of
	      // of the bin array.
	      double heights = scaleY(n, binheight)-TOP_MARGIN;// the variable heights is initializing
	      // the height for the each value in the array.
	      int y = (getHeight() - TOP_MARGIN - (int)heights);// from this variable we are obtaining the 
	      // y value of the bin.
	      g.fillRect(x, y, total_width-1, getHeight()-1-BOTTOM_MARGIN-y);// drawing the bin using
	      // the fill rect method from the graphics.
	      i++;
	 }

	}
	// drawXLabels draws the labels along the x-axis
	private void drawXLabels(Graphics g) { 
		g.setColor(Color.BLACK);
		binWidth = ((getWidth()- LEFT_MARGIN - RIGHT_MARGIN)/bins.length);
		DecimalFormat formatter = new DecimalFormat();
		formatter.setMinimumFractionDigits(2); 
		formatter.setMaximumFractionDigits(2);
		// Sample code (which you may or may not choose to use)
		double labelVal = min;
		String label = formatter.format(labelVal);
		int x = LEFT_MARGIN;
		int y = getHeight() - BOTTOM_MARGIN + 12; 
		for (int b : bins) {
			g.drawString(label, x-12, y);
			x += binWidth;
			labelVal += binSize;
			label = formatter.format(labelVal);
			}
			g.drawString(label, x-12, y);
	}
	
	// drawYLabels draws the labels along the y-axis,
	// i.e., draws the count of the bins on top of the bins private 
	void drawYLabels(Graphics g) {
		
	g.setColor(Color.BLUE);
	
	DecimalFormat formatter = new DecimalFormat("#,###");

	int x = LEFT_MARGIN + 2; // setting the starting point in the x axis; 
	//String label;
	 double binheight = (getHeight()-TOP_MARGIN-60)/max;// this binheight is used to calculate the
	 // graphical equivalent of the avlue from the array.
	for ( int i : bins){
		String label = formatter.format(i);// formatting the value in bins array.
		double heights = scaleY(i, binheight)-TOP_MARGIN;// getting the height of the in
		// the scalY method returns the graphical equivalent of the value from the bin array 
	    int y = (getHeight() - TOP_MARGIN - (int)heights);// this y contains the value of y point 
	    // where we are going the draw the lab.
	    
		g.drawString(label, x , (y-4));// drawing the label 
		x+= binWidth;// adding with binWidth with x value so that we can move on to
		//the next element. 
	}
	
	
	}
	/*
	 * This method takes a integer element and double value of the ratio of the height 
	 * that will return the graphical equivalent of the value that will be used during 
	 * drawing the rectangle.
	 */
	private double scaleY(int value, double height){
		 double result = value *height; // multiplying the value and height to compute the range.
		 //Math.ceil(result);
		 return result+20; // returning the value, here i am adding 10 just to make the small bins
		 // noticeable in our picture.
	}
	/*
	 * This method is used to get the maximum value for the bins array that is containing the elements
	 * is each bin and returns the maximum value.
	 */
	private int getmaximum(int[] array){
	     int max1 = 0;
	     for ( int i = 0; i < array.length; i ++ ){
	    	 if ( max1 < array[i])// comparing if the value in the array is less than the max. 
	    	 {
	    		 max1= array[i]; // then maximum is equal to array.
	    	 }
	    	 
	     }
	     return max1;
	}
	
}
