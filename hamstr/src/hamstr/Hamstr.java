package hamstr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class Hamstr {
	private int[][] aux;
	private int hamstersNumber;
	private BigInteger dailyStock;
	
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
		
		String dailyStockString = bufferReader.readLine();
		dailyStock = new BigInteger(dailyStockString);
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
			System.out.println(i);
			foodContainer[i][0] = Integer.parseInt(singleHamsterFoodString[0]);
			foodContainer[i++][1] = Integer.parseInt(singleHamsterFoodString[1]);
		}
		bufferReader.close();
		
		int affordedHamsters = calculateConsumedFood(foodContainer);
		writeToFile(outputFile, Integer.toString(affordedHamsters));
	}
	
	private int calculateConsumedFood(int[][] foodContainer) {		
		for (int i = 1; i < hamstersNumber; i++) {
			System.out.println("Entering:");
			sort(i - 1, foodContainer);
			System.out.println("Exit:");
			BigInteger affordableFood = BigInteger.valueOf(0);
			for (int hamsterInProgress = 1; hamsterInProgress <= i; hamsterInProgress++) {
				BigInteger self = BigInteger.valueOf(foodContainer[hamsterInProgress - 1][0]);
				BigInteger compatitors = BigInteger.valueOf(i - 1);
				BigInteger greed = BigInteger.valueOf(foodContainer[hamsterInProgress - 1][1]).multiply(compatitors);
				affordableFood = affordableFood.add(self);
				affordableFood = affordableFood.add(greed);

				System.out.println("Hamster personal: " + foodContainer[hamsterInProgress - 1][0] + 
						" -- greedy: " + foodContainer[hamsterInProgress - 1][1]);
			}
			if (affordableFood.compareTo(dailyStock) > 0) {
				System.out.println("Affordable food: " + affordableFood);
				return i - 1;
			}
		}
		return 0;
	}
	
	private void sort(int h, int[][] array) {
		sort(h, array, 0, array.length - 1);
	}
	
	private void sort(int h, int[][] array, int lo, int hi) {
		if (lo < hi) {
			int mid = (lo + hi) / 2;
			sort(h, array, lo, mid);
			sort(h, array, mid + 1, hi);
			merge(h, array, lo, mid, hi);
		}
	}
	
	private void merge(int h, int[][] array, int lo, int mid, int hi) {
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
			} else if ((aux[left][0] + h * aux[left][1]) < (aux[right][0] + h * aux[right][1])) {
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
	
	private void writeToFile(String outputFile, String string) throws IOException {
		File file = new File(outputFile);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(String.valueOf(string));
		fileWriter.close();
	}
}
