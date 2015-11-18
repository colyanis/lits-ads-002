package sigkey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Sigkey {
	public static void main(String[] args) throws IOException {
		String inputFile = "sigkey.in";
		String outputFile = "sigkey.out";
		
		if (args.length > 0) {
			inputFile = args[0];
			outputFile = args[1];
		}
		
		Sigkey sigkey = new Sigkey();
		sigkey.execute(inputFile, outputFile);
	}
	
	
	public void execute(String inputFile, String outputFile) throws IOException {		
		// Read from file
		File file = new File(inputFile);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		
		// Get total number of keys
		String totalKeysString = bufferReader.readLine();
		int totalKeys = Integer.parseInt(totalKeysString);
		
		// Initialize container which stores all expected keys
		String[] keysContainer = new String[totalKeys];
		
		// Keep reading from file
		String currentLine;
		int i = 0;
		while ((currentLine = bufferReader.readLine()) != null) {
			keysContainer[i++] = currentLine;
			
		}
		bufferReader.close();
		
		int totalPairs = calculate(keysContainer);
		writeToFile(outputFile, Integer.toString(totalPairs));
	}
	
	private int calculate(String[] keysContainer) {
		int totalPairs = 0;
		
		for (int i = 0; i < keysContainer.length - 1; i++) {
			String firstKey = keysContainer[i];
			for (int j = i + 1; j < keysContainer.length; j++) {
				String secondKey = keysContainer[j];
				String combinedKeys = firstKey + secondKey;
				char[] keysAsChars = combinedKeys.toCharArray();
				Arrays.sort(keysAsChars);
				String sortedKeys = new String(keysAsChars);
				int length = sortedKeys.length() - 1;
				int firstCharASCII = sortedKeys.charAt(0);
				int lastCharASCII = sortedKeys.charAt(length);
				
				if ((firstCharASCII == 97) && (lastCharASCII - firstCharASCII) == length) {
					totalPairs += getPair(sortedKeys);
				}
			}
		}
		
		return totalPairs;
	}
	
	private int getPair(String sortedKeys) {
		int pairCounter = 0;
		
		for (int i = 0; i < sortedKeys.length() - 1; i++) {
			if ((sortedKeys.charAt(i + 1) - sortedKeys.charAt(i)) == 1) {
				pairCounter = 1;
			} else {
				pairCounter = 0;
				break;
			}
		}
		
		return pairCounter;
	}
	
	private void writeToFile(String outputFile, String string) throws IOException {
		File file = new File(outputFile);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(String.valueOf(string));
		fileWriter.close();
	}
}
