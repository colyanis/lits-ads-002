package bugtrk;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Bugtrk {
	public static void main(String[] args) throws IOException {
		String inputFile = "bugtrk.in";
		String outputFile = "bugtrk.out";
		
		if (args.length > 0) {
			inputFile = args[0];
			outputFile = args[1];
		}
		
		Bugtrk bugtrk = new Bugtrk();
		bugtrk.execute(inputFile, outputFile);
	}
	
	
	public void execute(String inputFile, String outputFile) throws IOException {		
		// Read from file
		File file = new File(inputFile);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		
		// Get total number of keys
		String[] input = bufferReader.readLine().split(" ");
		bufferReader.close();
		long totalCards = Integer.parseInt(input[0]);
		long width = Integer.parseInt(input[1]);
		long height = Integer.parseInt(input[2]);
	
		long size = width * totalCards;
		long rows = 1;
		long columns = totalCards;
		long currentColumns = 0;
		while ((rows * height) < size) {
			size--;
			if (size < width * columns) {
				if (currentColumns == 0) {
					rows++;
				}
				
				columns--;
				currentColumns += (rows - 1);
				
				if (currentColumns == columns) {
					currentColumns = 0;
				}
			}
		}
		System.out.println(size);
		//writeToFile(outputFile, Integer.toString(totalPairs));
	}
	
	
	private void writeToFile(String outputFile, String string) throws IOException {
		File file = new File(outputFile);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(String.valueOf(string));
		fileWriter.close();
	}
}
