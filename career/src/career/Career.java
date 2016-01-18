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
		
		// Perform main calculation
		int sum = Integer.MIN_VALUE;
		for (int i = 0; i < width; i++) {
			sum = Math.max(sum, values[hight - 1][i] + calculate(values, i, (hight - 1) - 1));
		}
		return sum;
	}
	
	// Recursively calculate sum of elements above current row.
	// Elements to be calculated must be in the column equal to one
	// under element to add or by one column less (if element is not in edge
	// column)
	private int calculate(int[][] values, int col, int rowReduced) {
		if (rowReduced == -1) {
			return 0;
		}				
		
		int sum = Integer.MIN_VALUE;		
		for (int j = 0; j < values[rowReduced].length; j++) {
			if (j == (col - 1) || j == col) {
				if (memoized[rowReduced][j] < Integer.MAX_VALUE) {
					int calculatedMaxPath = memoized[rowReduced][j];
					sum = Math.max(sum, values[rowReduced][j]
							+ calculatedMaxPath);
				} else {
					int calculatedMaxPath = calculate(values, j, rowReduced - 1);
					memoized[rowReduced][j] = calculatedMaxPath;
					sum = Math.max(sum, values[rowReduced][j]
							+ calculatedMaxPath);
				}
			}
		}
	
		return sum;
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