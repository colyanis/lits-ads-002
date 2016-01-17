package career;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Career {
	int[][] memoized;
	public static void main(String[] args) throws Exception {
		String inputFile = "career.in";
		String outputFile = "career.out";
		
		if (args.length > 0) {
			inputFile = args[0];
			outputFile = args[1];
		}
		
		Career career = new Career();
		int[][] values = career.readFromFile(inputFile);
		
		long t1 = System.currentTimeMillis();
		int sum = career.calculate(values);
		long t2 = System.currentTimeMillis();
		
		System.out.println(t2 - t1);
		
		career.writeToFile(outputFile, sum);
	}
	
	private int calculate(int[][] values) {
		int hight = values.length;
		int width = values[hight - 1].length;
		
		// Create matrix for memoization of already calculated values
		memoized = new int[hight][width];
		for (int i = 0; i < hight; i++) {
			for (int j = 0; j < width; j++) {
				memoized[i][j] = Integer.MAX_VALUE;
			}
		}
		
		int sum = Integer.MIN_VALUE;
		for (int i = 0; i < width; i++) {
			int[][] reducedArray = copyCutArray(values, (hight - 1) - 1);
			sum = Math.max(sum, values[hight - 1][i] + calculate(reducedArray, i));
		}
		return sum;
	}
	
	private int calculate(int[][] values, int col) {
		if (values.length == 0) {
			return 0;
		}
				
		int sum = Integer.MIN_VALUE;
		int rows = values.length;
		
		for (int i = rows - 1; i >= 0; i--) {
			for (int j = 0; j < values[i].length; j++) {
				if (j == (col - 1) || j == col){						
					int[][] reducedArray = copyCutArray(values, i - 1);
					if (memoized[i][j] < Integer.MAX_VALUE) {
						int calculatedMaxPath = memoized[i][j];
						sum = Math.max(sum, values[i][j] + calculatedMaxPath);
					} else {
						int calculatedMaxPath = calculate(reducedArray, j);
						memoized[i][j] = calculatedMaxPath;
						sum = Math.max(sum, values[i][j] + calculatedMaxPath);
					}
				} 
			}
		}
		
		return sum;
	}
	
	private int[][] copyCutArray(int[][] array, int rowNumber) {
		int[][] finalArray = new int[rowNumber + 1][];
		for (int i = 0; i < finalArray.length; i++) {
			finalArray[i] = new int[array[i].length];
			for (int j = 0; j < finalArray[i].length; j++) {
				finalArray[i][j] = array[i][j];
			}
		}
		return finalArray;
	}
	
	private int[][] readFromFile(String path) throws Exception {
		Scanner scanner = null;
		try {
			File file = new File(path);
			scanner = new Scanner(file);
			int rows = scanner.nextInt();
			scanner.nextLine();
			int[][] values = new int[rows][];
			
			for (int i = 0; i < rows; i++) {
				String[] line = scanner.nextLine().split(" ");
				values[i] = new int[line.length];
				
				for (int j = 0; j < line.length; j++) {
					values[i][j] = Integer.parseInt(line[j].trim());
				}
			}
			
			return values;
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		return null;
	}
	
	private void writeToFile(String outputFile, int value) throws IOException {
		File file = new File(outputFile);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(Integer.toString(value));
		fileWriter.close();
	}
}
