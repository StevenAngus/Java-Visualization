// OverdoseDisplayXXX		Author: AMH 
// 
// A graphics application that visualizes data about the relative frequency of
// various drugs implications in accidental drug overdose deaths via an
// interactive bar chart.
//
// Data Source: https://data.wprdc.org/dataset/allegheny-county-fatal-accidental-overdoses

//  
// java --module-path "C:\Users\liste\OneDrive\Desktop\CIS220\javafx-sdk-17.0.0.1\lib" --add-modules javafx.controls OverdoseDisplaySAL

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.Scanner;
import java.io.*;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import java.util.Arrays;

public class OverdoseDisplaySAL extends Application {
	// filename in current directory to use as data source
	private final String filename = "casesSmall1.csv";
	
	private final int PLOTSIZE = 400;	// width/height of square plot area
	private final int BORDER = 50;	// border width on all sides of plot area


	private Group root;		// parent Group to hold all graphics elements
	private TextField pickYear;	// text field for entering year of cases
	//private Label showing;		// label used to report which data is being displayed
	private Text showing;

	private Label[] displayDrugs;	// collection of labels for names of drugs
	private Rectangle[] bars;		// collection of Rectangles for each bar in chart
	private int[] drugCounts;		// count of how many times each drug occurs in
									// current dataset or subset of dataset

	private DrugItem[] allDrugs;	// array used for counting occurrences of each
									// drug across dataset for finding most frequent drugs

	// ADD ADDITIONAL INSTANCE DATA HERE TO KEEP TRACK OF CASE DATA AND
	// NECESSARY FACTS ABOUT THE DATA CURRENTLY BEING DISPLAYED

	private OverdoseItemSAL[] cases;
	int drugsOnChart;
	// might want to add drugs currently displayed array, consisting of those cases
	

	// set up Graphics application with all needed initialization for program
    public void start(Stage primaryStage) {		

		// TO DO: process file to count how many cases are in the file
		String line;
		String firstLine;
		int numOfCases = 0;
		try {
			Scanner filescan = new Scanner(new File(filename));
			firstLine = filescan.nextLine();
			while(filescan.hasNext()) {
				line = filescan.nextLine();
				numOfCases++;
			}
			//System.out.println("Number of Cases: " + numOfCases);
			//System.out.println();

		// this has to go at the very end of the code
		//} catch (IOException e) {System.out.println(e);}; 
		
		// TO DO: instantiate array of OverdoseItemXXX objects to be of correct size
		cases = new OverdoseItemSAL[numOfCases];
		
		// TO DO: process file again to read in data about each case and use it
		// to initialize the array of OverdoseItemXXX objects
		Scanner filescan2 = new Scanner(new File(filename));
			filescan2.useDelimiter(",|\\n");
			firstLine = filescan2.nextLine();
			while(filescan2.hasNext()) {
				//line2 = filescan2.next();
				//System.out.println(line2);
				for(int i=0; i < cases.length; i++){
						String date = filescan2.next();
						String typeOfCase = filescan2.next();

						String age = filescan2.next();
						String gender = filescan2.next();
						String race = filescan2.next();
						String extra = filescan2.next();

						String drug1 = filescan2.next();
						String drug2 = filescan2.next();
						String drug3 = filescan2.next();
						String drug4 = filescan2.next();
						String drug5 = filescan2.next();
						String drug6 = filescan2.next();
						String drug7 = filescan2.next();
						String drug8 = filescan2.next();
						String drug9 = filescan2.next();
						String drug10 = filescan2.next();
						
						String year = filescan2.next();		
						String inZip = filescan2.next();				
						String decZip = filescan2.next();		

						cases[i] = new OverdoseItemSAL(date, typeOfCase, age, 
							gender, extra, race, drug1, drug2, drug3, drug4, drug5, drug6, 
							drug7, drug8, drug9, drug10, year, inZip, decZip);

						//numOfDataPoints++;
						//System.out.println(cases[i]);
					//}
				}

			}

			//System.out.println(cases[0].getGender());
			//System.out.println("Number of Data Points: " + numOfDataPoints);
		

		Rectangle plot = new Rectangle(BORDER, BORDER, PLOTSIZE, PLOTSIZE);
		plot.setFill(Color.WHITE);
		plot.setStroke(Color.BLACK);
		
		
		// default start, show bar chart for hardcoded list of drugs
		// ALCOHO, CODEI, HEROIN, FENTAN, OXYCOD
		displayDrugs = new Label[5];
		displayDrugs[0] = new Label("ALCOHO");
		displayDrugs[1] = new Label("CODEI");
		displayDrugs[2] = new Label("HEROIN");
		displayDrugs[3] = new Label("FENTAN");
		displayDrugs[4] = new Label("OXYCOD");		
		
		// call to method to populate allDrugs with DrugItems in sorted order
		// getMostFrequent();
		
		// count how many times each of these five drugs occurs in the data set
		// RIGHT HERE
		drugCounts = new int[5];
		getCounts();

		// create plot
		Group plotBars = new Group();
		bars = new Rectangle[5];
		
		int labelPos = 60;
		int rectPos = 50;
		for (int i=0; i<bars.length; i++) {
			//
			// first, controls start height, second controls length of rectangle
			bars[i] = new Rectangle(rectPos, (450-((double) drugCounts[i]/ (double) drugsOnChart)*400), 80, ((double) drugCounts[i]/ (double) drugsOnChart)*400);
			bars[i].setFill(Color.BLUE);
			//
			displayDrugs[i].setTranslateX(labelPos);
			displayDrugs[i].setTranslateY(BORDER+PLOTSIZE+10);
			// add each Rectangle and each Label for each drug to the Group
			plotBars.getChildren().add(bars[i]);
			plotBars.getChildren().add(displayDrugs[i]);
			labelPos = labelPos + 80;
			rectPos = rectPos + 80;
		}
		
		// instantiate and intialize graphical interface components
		MenuButton ageChoice = new MenuButton("Age");
		ageChoice.setTranslateX(5);
		ageChoice.setTranslateY(5);
		String[] ages = {"0-18", "19-25", "26-45", "46-65", ">65"};
		for (int i=0; i<ages.length; i++) {
			MenuItem newItem = new MenuItem(ages[i]);
			ageChoice.getItems().add(newItem);
			newItem.setOnAction(this::ageAction);
		}
		
		MenuButton genderChoice = new MenuButton("Gender");
		genderChoice.setTranslateX(70);
		genderChoice.setTranslateY(5);
		String[] gender = {"M", "F"};
		for (int i=0; i<gender.length; i++) {
			MenuItem newItem = new MenuItem(gender[i]);
			genderChoice.getItems().add(newItem);
			newItem.setOnAction(this::genderAction);
		}
		
		MenuButton raceChoice = new MenuButton("Race");
		raceChoice.setTranslateX(150);
		raceChoice.setTranslateY(5);
		String[] race = {"A", "B", "H", "I", "M", "O", "U", "W"};
		for (int i=0; i<race.length; i++) {
			MenuItem newItem = new MenuItem(race[i]);
			raceChoice.getItems().add(newItem);
			newItem.setOnAction(this::raceAction);
		}

		Label yearLabel = new Label("Year:");
		yearLabel.setTranslateX(250);
		yearLabel.setTranslateY(5);
		pickYear = new TextField();
		pickYear.setPrefColumnCount(5);
		pickYear.setTranslateX(280);
		pickYear.setTranslateY(5);
		
		Button reset = new Button("Reset");
		reset.setTranslateX(400);
		reset.setTranslateY(5);
		
		showing = new Text("All data, " + drugsOnChart + " items");
		showing.setTranslateX(200);
		//showing.setTranslateY(BORDER+PLOTSIZE+30);
		showing.setTranslateY(BORDER+PLOTSIZE+40);
		
 		root = new Group(plot, plotBars, ageChoice, genderChoice, raceChoice, yearLabel, pickYear, reset, showing);
		
        Scene scene = new Scene(root, PLOTSIZE+BORDER*2, PLOTSIZE+BORDER*2,
								Color.WHITE);
		
        primaryStage.setTitle("Overdose Case Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
	} catch (IOException e) {System.out.println(e);}; 
	}

	// update drugCounts to count how many times each of the five drugs to be
	// displayed occurs amongst the cases in the array of OverdoseItemXXX objects
	// RIGHT HERE
	public void getCounts() {
		int alcohoCount = 0;
		int codeiCount = 0;
		int heroinCount = 0;
		int fentanCount = 0;
		int oxycodCount = 0;

		int totalDrugs = 0;

		for(int i=0; i < cases.length; i++) {
			for(int j=0; j < 10; j++) {
				if (cases[i].getDrug(j).equalsIgnoreCase("ALCOHO")) {
					// maybe store these spearate cases in another array
					alcohoCount++;
				}
				if (cases[i].getDrug(j).equalsIgnoreCase("CODEI")) {
					codeiCount++;
				}
				if (cases[i].getDrug(j).equalsIgnoreCase("HEROIN")) {
					heroinCount++;
				}
				if (cases[i].getDrug(j).equalsIgnoreCase("FENTAN")) {
					fentanCount++;
				}
				if (cases[i].getDrug(j).equalsIgnoreCase("OXYCOD")) {
					oxycodCount++;
				}
			}
		}
		drugCounts[0] = alcohoCount;
		drugCounts[1] = codeiCount;
		drugCounts[2] = heroinCount;
		drugCounts[3] = fentanCount;
		drugCounts[4] = oxycodCount;
		drugsOnChart = alcohoCount + codeiCount + heroinCount + fentanCount + oxycodCount;
	}

	// optional helper method to use to set the location and size of Rectangle
	// objects based on new drugCounts information
	public void setRectangles() {

	}

	public void getMostFrequent() {
		allDrugs = new DrugItem[200];
		
		int numDrugs = 0;	// number of unique drugs added to allDrugs

		// TO DO: write code to populate allDrugs array

		Arrays.sort(allDrugs, 0, numDrugs-1, new DrugItem());		
	}

	public void ageAction(ActionEvent event) {
		drugsOnChart = 1000;
		showing.setText("All data, " + drugsOnChart + " items");
		// choice stores which menu item was selected
		String choice = ((MenuItem)(event.getSource())).getText();
		System.out.println(choice);	// print to command line for testing purposes

		// for example
		// when you press this button, i want to return the cases that involved overdoses under 18 (inclusive) 
		// then i want to count the specific drugs involving only these cases
		// this way drugCounts gets updated with new case values and changes the bar chart
		
	}
	
	public void genderAction(ActionEvent event) {
		// choice stores which menu item was selected
		String choice = ((MenuItem)(event.getSource())).getText();
		System.out.println(choice);	// print to command line for testing purposes
		
	}

	public void raceAction(ActionEvent event) {
		// choice stores which menu item was selected
		String choice = ((MenuItem)(event.getSource())).getText();
		System.out.println(choice);	// print to command line for testing purposes
		
	}
	
    public static void main(String[] args)
    {
        launch(args);
    }
}
