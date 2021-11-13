package hashedinNextflixCSVParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter limit n:");
		long n = sc.nextLong();
		sc.close();

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(
				"C:\\Users\\anand\\eclipse-workspace\\hashedinNextflixCSVParser\\src\\resources\\netflix_titles.csv"),
				StandardCharsets.UTF_8)) {
			CSVParser csvParser = new CSVParser(reader);
			csvParser.firstNRecords(n);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
