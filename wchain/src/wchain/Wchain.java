package wchain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import javax.security.auth.callback.Callback;

public class Wchain {
	int[][] memoized;
	public static void main(String[] args) throws Exception {
		String inputFile = "wchain.in";
		String outputFile = "wchain.out";
		
		if (args.length > 0) {
			inputFile = args[0];
			outputFile = args[1];
		}
		
		Wchain wchain = new Wchain();
		String[] words = wchain.readFromFile(inputFile);
		wchain.sortByLength(words);
		
		int sum = wchain.calculate(words);
		System.out.println(sum);
//		wchain.writeToFile(outputFile, sum);
	}
	
	private int calculate(String[] words) {
		int sum = 1;
		String currentWord = words[0];
		
//		for (int i = 0; i < (words.length - 1) && (currentWord != null); i++) {
//		for (int i = 0; i < words.length - 1; i++) {
			for (int j = 1; j < words.length; j++) {
//				if (words[j].length() != (currentWord.length() - 1)) {
//					break;
//				} else {
					
				currentWord = getNextWord(currentWord, words[j]);
				if (currentWord != null) {
					sum++;
				} else {
					currentWord = words[j];
				}
					
//				}
			}
//		}
		return sum;
	}
	
	private String getNextWord(String currentWord, String followingWord) {
		char[] currentChars = currentWord.toCharArray();
		char[] followingChars = followingWord.toCharArray();
		
		int currentLength = currentWord.length();
		int followingLength = followingWord.length();
		
		int[][] matrix = new int[currentLength + 1][followingLength + 1];
		for (int i = 0; i <= currentLength; i++) {
			matrix[i][0] = 0;
		}
		for (int i = 0; i <= followingLength; i++) {
			matrix[0][i] = 0;
		}
		
		for (int i = 1; i <= currentLength; i++) {
			for (int j = 1; j <= followingLength; j++) {
				if (currentChars[i-1] == followingChars[j-1]) {
					matrix[i][j] = matrix[i - 1][j - 1] + 1;
				} else if (matrix[i - 1][j] >= matrix[i][j - 1]) {
					matrix[i][j] = matrix[i - 1][j];
				} else {
					matrix[i][j] = matrix[i][j - 1];
				}
			}
		}
		
		int overlapQuantity = matrix[currentLength][followingLength];
		
		if (overlapQuantity == followingLength) {
			return followingWord;
		} else {
			return null;
		}
	}
	
	private String[] sortByLength(String[] words) {
		Arrays.sort(words, new LengthComparator());
		return words;
	}
	
	private String[] readFromFile(String path) throws Exception {
		Scanner scanner = null;
		try {
			File file = new File(path);
			scanner = new Scanner(file);
			int size = scanner.nextInt();
			scanner.nextLine();
			String[] words = new String[size];
			
			for (int i = 0; i < size; i++) {
				words[i] = scanner.nextLine();
			}
			
			return words;
		
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
	
	class LengthComparator implements Comparator<String> {
		public int compare(String o1, String o2) {
			if (o1.length() < o2.length()) {
				return 1;
			} else if (o1.length() > o2.length()) {
				return -1;
			} else {
				return 0;
			}
			
		}
	}
}
