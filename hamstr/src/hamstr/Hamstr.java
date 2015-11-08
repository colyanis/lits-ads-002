package hamstr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Hamstr {
	private int[][] aux;
	private int hamstersNumber;
	private int dailyStock;
	
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
		
		dailyStock = Integer.parseInt(bufferReader.readLine());
		hamstersNumber = Integer.parseInt(bufferReader.readLine());
		aux = new int[hamstersNumber][2];
		
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
		
		int affordedHamsters = calculateConsumedFood(foodContainer);
		writeToFile(outputFile, Integer.toString(affordedHamsters));
	}
	
	private int calculateConsumedFood(int[][] foodContainer) {
		System.out.println("Daily stock: " + dailyStock);
		System.out.println("Number of hamsters: " + hamstersNumber);
		
		
		for (int i = hamstersNumber; i > 0; i--) {
			sort(i - 1, foodContainer);
			int affordableFood = 0;
			for (int hamsterInProgress = 1; hamsterInProgress <= i; hamsterInProgress++) {
				affordableFood = affordableFood + foodContainer[hamsterInProgress - 1][0] + (i - 1) * foodContainer[hamsterInProgress - 1][1];
				System.out.println("Hamster personal: " + foodContainer[hamsterInProgress - 1][0] + 
						" -- greedy: " + foodContainer[hamsterInProgress - 1][1]);
			}
			if (affordableFood <= dailyStock) {
				System.out.println("Affordable food: " + affordableFood);
				return i;
			}
		}
		return 0;
	}
	
	private void sort(int h, int[][] array) {
		sort(h, array, 0, array.length - 1);
	}
	
	private void sort(int h, int[][] array, int lo, int hi) {
		if (lo < hi) {
			int q = partition(h, array, lo, hi);
			sort(h, array, lo, q - 1);
			sort(h, array, q + 1, hi);
		}
	}
	
	private int partition(int h, int[][] array, int lo, int hi) {
		int pivot = array[hi][0] + h * array[hi][1];
		int i = lo - 1;
		
		for (int j = lo; j <= hi; j++) {
			if ((array[j][0] + h * array[j][1]) < pivot) {
				i += 1;
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
	
	private void writeToFile(String outputFile, String string) throws IOException {
		File file = new File(outputFile);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(String.valueOf(string));
		fileWriter.close();
	}
}
