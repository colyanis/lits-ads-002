import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;

public class Discnt {
	private int[] arrayGlobal;
	
	public static void main(String[] args) throws IOException {
		String inputFile = "discnt.in";
		String outputFile = "discnt.out";
		
		if (args.length > 0) {
			inputFile = args[0];
			outputFile = args[1];
		}
		
		Discnt discount = new Discnt();
		discount.proceedData(inputFile, outputFile);
		
	}
	
	private void proceedData(String inputFile, String outputFile) throws IOException {
		File file = new File(inputFile);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		
		// Read from file
		String pricesStr = bufferReader.readLine();
		String discountStr = bufferReader.readLine();
		bufferReader.close();
				
		// Convert to working data
		int discountPercent = Integer.parseInt(discountStr);
		
		String[] pricesStrArray = pricesStr.split(" ");
		arrayGlobal = new int[pricesStrArray.length];
		int[] prices = new int[pricesStrArray.length];
		for (int i = 0; i < pricesStrArray.length; i++) {
			try {
				prices[i] = Integer.valueOf(pricesStrArray[i]);
			} catch (NumberFormatException e) {
				System.err.println("Couldn't convert String to int");
				e.printStackTrace();
			}
		}
		
		sort(prices);
		calculateFinalAmount(prices, discountPercent, outputFile);
	}

	private void calculateFinalAmount(int[] prices, int discountPercent, String outputFile) throws IOException {
		double sum = 0;
		int discountedItems = prices.length / 3;		
		
		for (int i = 0; i < prices.length; i++) {
			if (i < (prices.length - discountedItems)) {
				sum += prices[i];
			} else {
				sum += prices[i] - ((double)prices[i] * discountPercent / 100); 
			}
		}
		
		
		Locale.setDefault(Locale.US);
		DecimalFormat df = new DecimalFormat("0.00");
		writeToFile(outputFile, df.format(sum));
	}
	
	private void writeToFile(String outputFile, String string) throws IOException {
		File file = new File(outputFile);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(String.valueOf(string));
		fileWriter.close();
	}
	
	private void sort(int[] array) {
		sort(array, 0, array.length - 1);
	}
	
	private void sort(int[] array, int lo, int hi) {
		if (lo < hi) {
			int mid = lo + (hi - lo)/2;
			sort(array, lo, mid);
			sort(array, mid + 1, hi);
			merge(array, lo, mid, hi);
		}
	}
	
	private void merge(int[] array, int lo, int mid, int hi) {
		int i = lo;
		int j = mid + 1;
		
		for (int k = lo; k <= hi; k++) {
			arrayGlobal[k] = array[k];
		}
		
		for (int k = lo; k <= hi; k++) {
			if (i > mid) {
				array[k] = arrayGlobal[j++];
			} else if (j > hi){
				array[k] = arrayGlobal[i++];
			} else if (arrayGlobal[i] < arrayGlobal[j]) {
				array[k] = arrayGlobal[i++];
			} else {
				array[k] = arrayGlobal[j++]; 
			} 
		}
		
	}
	
	private void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}