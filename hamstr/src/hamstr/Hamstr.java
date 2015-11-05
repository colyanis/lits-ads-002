package hamstr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Hamstr {
	private int[][] aux;
	private int hamstersNumber;
	
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
		hamstersNumber = Integer.parseInt(bufferReader.readLine());
		
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
		
		aux = new int[foodContainer.length][2];
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
		
		writeToFile(outputFile, Integer.toString(affordedHamsters));
		
	}
	
	private int calculateConsumedFood(int[][] foodContainer, int hamsterInProgress) {
		int totalFoodConsumed = 0;
		int[] tempArray = new int[hamsterInProgress + 1];
		for (int i = 0; i <= hamsterInProgress; i++) {
			tempArray[i] = foodContainer[i][0] + (hamsterInProgress) * foodContainer[i][1];
		}
		
		for (int i = 0; i < tempArray.length - 1; i++) {
			if (tempArray[i] > tempArray[i + 1]) {
				swap(foodContainer, i, i + 1);
			}
		}
		
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
	
	private void sortLinear(int[][] array) {
		for (int i = 0; i < array.length - 1; i++) {
			if ((array[i][0] + (hamstersNumber * array[i][1])) == (array[i+1][0] + (hamstersNumber * array[i+1][1]))) {
				if (array[i][1] > array[i + 1][1]) {
					swap(array, i, i + 1);
				}
			}
		}
	}
	
	private void sort(int[][] array) {
		sort(array, 0, array.length - 1);
	}
	
	private void sort(int[][] array, int lo, int hi) {
		if (lo < hi) {
			int mid = (lo + hi) / 2;
			sort(array, lo, mid);
			sort(array, mid + 1, hi);
			merge(array, lo, mid, hi);
			
		}
	}
	
	private void merge(int[][] array, int lo, int mid, int hi) {
		int left = lo;
		int right = mid + 1;
		
		for (int i = lo; i <= hi; i++) {
			aux[i] = array[i];
		}
		
		for (int i = lo; i <= hi; i++) {
			if (left > mid) {
				array[i] = aux[right++];
			} else if (right > hi) {
				array[i] = aux[left++];
			} else if ((aux[left][0] + (hamstersNumber * aux[left][1])) < (aux[right][0] + (hamstersNumber * aux[right][1]))) {
				array[i] = aux[left++];
			} else {
				array[i] = aux[right++];
			}
		}
	}
	
	private void swap(int[][] array, int i, int j) {
		int temp[] = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}
