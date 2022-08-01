// DrugItem				Author: AMH 
// Object representing a drug that might be implicated in an overdose case
// and the number of times it occurs within a particular dataset. Extends
// the Comparator class to provide a "compare" method that provides an
// ordering of DrugItem objects that can be used when sorting an array
// of DrugItem objects.

import java.util.Comparator;

public class DrugItem implements Comparator<DrugItem>{
	private String type;		// drug name as appears in data set
	private int count;			// number of occurrences of drug in current dataset
	
	// Constructor: blank item used in calling sort() method
	public DrugItem() {
		type="";
		count=0;
	}
	
	// Constructor: initializes an object for a particular drug assuming the first
	// occurrence of the drug has just been found
	public DrugItem(String s) {
		type = s;
		count = 1;
	}
	
	// increment the counter of occurrences of this drug having found an additional
	// occurrence in the current dataset
	public void increment() {
		count++;
	}
	
	// access the number of occurrences found of this drug
	public int getCount() {
		return count;
	}
	
	// access the name of this drug
	public String getType() {
		return type;
	}

	// compare method used in sorting
	// takes in two DrugItem objects and returns 1, 0, or -1 based on which argument
	// has more occurrences in the current dataset
	@SuppressWarnings(("unchecked"))
	public int compare(DrugItem first, DrugItem second) throws ClassCastException {
		int result;
		if (first.getCount()<second.getCount()) {
			return 1;
		} else {
			if (first.getCount()==second.getCount()) {
				return 0;
			} else {
				return -1;
			}
		}
	}
}