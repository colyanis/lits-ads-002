import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;

public class Discnt {
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
		DecimalFormat df = new DecimalFormat("#0.00");
		writeToFile(outputFile, df.format(sum));
	}
	
	private void writeToFile(String outputFile, String string) throws IOException {
		File file = new File(outputFile);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(String.valueOf(string));
		fileWriter.close();
	}
	
	private void sort(int[] array) {
		for (int i = 1; i < array.length; i++) {
			for (int j = i; j > 0; j--) {
				if (array[j] < array[j - 1]) {
					swap(array, j, j - 1);
				}
			}
		}
	}
	
	private void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}