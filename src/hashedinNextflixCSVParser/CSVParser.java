package hashedinNextflixCSVParser;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CSVParser {
	private BufferedReader reader;
	List<NetflixMovie> NetflixMovie = new ArrayList<NetflixMovie>();
	List<List<String>> movieDetails;

	public List<List<String>> cleanNetflixCSV() {
		String allCSV = this.reader.lines().skip(1).collect(Collectors.joining(System.lineSeparator()));
		StringBuffer result = new StringBuffer();
		Matcher m = Pattern.compile("\"[^\"]*\"").matcher(allCSV);
		while (m.find()) {
			if (m.group().contains("\n"))
				m.appendReplacement(result, m.group().replaceAll("\\R+", ""));
		}
		m.appendTail(result);
		List<String> lines = Arrays.asList(result.toString().split("\n"));
		List<List<String>> ret = lines.stream()
				.map(line -> Arrays.asList(line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")))
				.collect(Collectors.toList());
		return ret;

	}

	public CSVParser(BufferedReader reader) {
		super();
		this.reader = reader;
		this.cleanNetflixCSV().forEach(movie -> {
			this.NetflixMovie.add(new NetflixMovie(movie.get(0), movie.get(1), movie.get(2), movie.get(3),
					movie.get(4), movie.get(5), movie.get(6), movie.get(7), movie.get(8), movie.get(9), movie.get(10),
					movie.get(11)));
		});
	}

	public void firstNRecords(long n) {
		this.NetflixMovie.stream().limit(n).forEach(movie -> System.out.println(movie.toString()));
	}

	public void firstNHorrorMovies(long n) {
		reader.lines().limit(n).forEach(System.out::println);
	}
}
