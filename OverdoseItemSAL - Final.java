//********************************************************************
//  OverdoseItemSAL.java       Author: Steven Li 
//
//********************************************************************
import java.util.Scanner;
import java.util.Random;

//Create the OverdoseItemXXX class including constructor and getter methods.
//• Read in the data file and populate the OverdoseItemXXX array.
//• Using the hardcoded set of drugs, create an initial visualization of the full set of data, scaling the bars in
//the bar chart properly.
//• Test your code on more than one data file. Create additional small test files as needed to aid the rest of
//your development

public class OverdoseItemSAL
{
	private String date; // keeps track of the date of the overdose
	private String typeOfCase; // all case types are accidents

	private int age; // age of the person in the overdose case
	private String gender; // their gender
	private String race; // their race

	private String extra; // extra info labeld case_dispo

	private String[] drugs = new String[10]; // stores up 10 drugs that were recorded in the overdase case

	private int year; // the year the overdose happended
	private String inZip; // incident zip
	private String decZip; // decedent zip

	public OverdoseItemSAL(String dateOfCase, String caseType, int caseAge, 
		String caseGender, String extraInfo, String caseRace,
		String[] caseDrugs, int caseYear, String caseZip1, String caseZip2)
    {
		date = dateOfCase;
		typeOfCase = caseType;

		age = caseAge;
		gender = caseGender;
		race = caseRace;
		extra = extraInfo;

		drugs = caseDrugs;

		year = caseYear;
		inZip = caseZip1;
		decZip = caseZip2;
	}

	public String getDrug(int num) {
		return drugs[num];
	}

	public String getGender() {
		return gender;
	}

	public String getRace() {
		return race;
	}

	public int getAge() {
		return age;
	}

	public int getYear() {
		return year;
	}

	
}