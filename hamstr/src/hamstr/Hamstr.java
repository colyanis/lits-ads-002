package hamstr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hamstr {
	
	public static void main(String[] args) throws IOException {
		String inputFile = "hamstr.in";
		String outputFile = "hamstr.out";
		
		if (args.length > 0) {
			inputFile = args[0];
			outputFile = args[1];
		}
		
		Hamstr hamstr = new Hamstr();
		hamstr.execute(inputFile, outputFile);
	}
	
	public void execute(String inputFile, String outputFile) throws IOException {		
		// Read from file
		File file = new File(inputFile);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		
		int foodDailyStock = Integer.parseInt(bufferReader.readLine());
		int hamstersNumber = Integer.parseInt(bufferReader.readLine());
		
		// Initialize container which stores all expected food
		// consumption properties for each hamster
		int[][] foodContainer = new int[hamstersNumber][2];
		
		// Keep reading from file
		String currentLine;
		int i = 0;
		while ((currentLine = bufferReader.readLine()) != null) {
			String[] singleHamsterFoodString = currentLine.split(" ");
			foodContainer[i][0] = Integer.parseInt(singleHamsterFoodString[0]);
			foodContainer[i++][1] = Integer.parseInt(singleHamsterFoodString[1]);
		}
		bufferReader.close();
		
		sort(foodContainer);

		int affordedHamsters = 0;
		for (int j = 0; j < hamstersNumber; j++) {
			int totalFoodConsumed = calculateConsumedFood(foodContainer, j);
			if (totalFoodConsumed <= foodDailyStock) {
				affordedHamsters = j + 1;
			} else {
				break;
			}
		}
		
		System.out.println(affordedHamsters);
		
	}
	
	private int calculateConsumedFood(int[][] foodContainer, int hamsterInProgress) {
		int totalFoodConsumed = 0;
		for (int i = 0; i <= hamsterInProgress; i++) {
			totalFoodConsumed += foodContainer[i][0] + (hamsterInProgress) * foodContainer[i][1];
		}
		
		return totalFoodConsumed;
	}
	
	private void writeToFile(String outputFile, String string) throws IOException {
		File file = new File(outputFile);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(String.valueOf(string));
		fileWriter.close();
	}
	
	private void sort(int[][] array) {
		sort(array, 0, array.length - 1);
	}
	
	private void sort(int[][] array, int lo, int hi) {
		if (lo < hi) {
			int mid = partition(array, lo, hi);
			sort(array, lo, mid - 1);
			sort(array, mid + 1, hi);
		}
	}
	
	private int partition(int[][] array, int lo, int hi) {
		int pivot = hi;
		int i = lo - 1;
		
		for (int j = lo; j < hi; j++) {
			if ((array[j][1] + array[j][1]) <= (array[pivot][0] + array[pivot][1])) {
				i = i + 1;
				swap(array, i, j);
			}
		}
		swap(array, i + 1, hi);
		return i + 1;
	}
	
	private void swap(int[][] array, int i, int j) {
		int temp[] = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}
