package hashedinNextflixCSVParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class App {
	public static void main(String[] args) {
		Stream<NetflixMovie> queryResult = null;
		Scanner sc = new Scanner(System.in);
		double duration = 0;
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(
				"C:\\Users\\anand\\eclipse-workspace\\hashedinNextflixCSVParser\\src\\resources\\netflix_titles.csv"),
				StandardCharsets.UTF_8)) {
			NetflixCSVService netflixCSVService = new NetflixCSVService(reader);
			System.out.println(
					"Select Query : eg::\n 1.List the first n records where type: TV Show\n 2. List the first n records where listed_in: Horror Movies \n 3. List the first n type: Movie where country: India \n 4. Above queries with start date and end date, eg: 1,11/22/2021,12/22/2021\n ");
			int selectedQuery = sc.nextInt();
			System.out.println("Enter number of records to fetch:");
			long numberOfRecords = sc.nextLong();
			if (selectedQuery == 1) {
				long startTime = System.nanoTime();
				queryResult = netflixCSVService.firstNMovies(numberOfRecords);
				long endTime = System.nanoTime();
				duration = (double) (endTime - startTime) / 1000000;
			} else if (selectedQuery == 2) {
				long startTime = System.nanoTime();
				queryResult = netflixCSVService.firstNMovies(numberOfRecords, "Horror Movies");
				long endTime = System.nanoTime();
				duration = (double) (endTime - startTime) / 1000000;
			} else if (selectedQuery == 3) {
				long startTime = System.nanoTime();
				queryResult = netflixCSVService.firstNMovies(numberOfRecords, "Movie", "India");
				long endTime = System.nanoTime();
				duration = (double) (endTime - startTime) / 1000000;
			} else if (selectedQuery == 4) {
				long startTime = System.nanoTime();
				String userInput = sc.next();
				String[] userInputArray = userInput.split(",");
				Stream<NetflixMovie> partialQuery = null;
				if (userInputArray[0].equals("1")) {
					partialQuery = netflixCSVService.firstNMovies(numberOfRecords);
				} else if (userInputArray[0].equals("2")) {
					partialQuery = netflixCSVService.firstNMovies(numberOfRecords, "Horror Movies");
				} else if (userInputArray[0].equals("3")) {
					partialQuery = netflixCSVService.firstNMovies(numberOfRecords, "Movie", "India");
				}
				queryResult = netflixCSVService.firstNMovies(partialQuery, userInputArray[1], userInputArray[2]);
				long endTime = System.nanoTime();
				duration = (double) (endTime - startTime) / 1000000;
			} else {
				System.out.println("Invalid Query selected");
			}
			if (queryResult != null) {
				queryResult.forEach(movie -> System.out.println(movie.toString()));
			} else {
				System.out.println("No record found");
			}
			System.out.println("Query Execution Time :" + duration + " ms");
			sc.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
