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
		int hamsters = 0;
		int affordableFood = 0;
		
		while (affordableFood <= dailyStock) {
			hamsters++;
			sort(hamsters - 1, foodContainer);
			affordableFood = 0;
			for (int humsterInProgress = 0; humsterInProgress < hamsters; humsterInProgress++) {
				affordableFood += foodContainer[humsterInProgress][0] + (hamsters - 1) * foodContainer[humsterInProgress][1];	
			}
		}
		
		return hamsters - 1;
	}
	
	private void sort(int hamster, int[][] array) {
		sort(hamster, array, 0, array.length - 1);
	}
	
	private void sort(int hamster, int[][] array, int lo, int hi) {
		if (lo < hi) {
			int mid = (lo + hi) / 2;
			sort(hamster, array, lo, mid);
			sort(hamster, array, mid + 1, hi);
			merge(hamster, array, lo, mid, hi);
			
		}
	}
	
	private void merge(int hamster, int[][] array, int lo, int mid, int hi) {
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
			} else if ((aux[left][0] + (hamster * aux[left][1])) < (aux[right][0] + (hamster * aux[right][1]))) {
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
