package hashedinNextflixCSVParser;

import java.io.BufferedReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CSVParser {
	private List<NetflixMovie> NetflixMovie = new ArrayList<NetflixMovie>();
	public CSVParser(BufferedReader reader) {
		super();
		NetflixCSVClassBuilder netflixCSVClassBuilder = new NetflixCSVClassBuilder(reader);
		netflixCSVClassBuilder.cleanNetflixCSV().forEach(movie -> {
			this.NetflixMovie.add(
					new NetflixMovie(movie.get(0), movie.get(1), movie.get(2), movie.get(3), movie.get(4), movie.get(5),
							movie.get(6), movie.get(7), movie.get(8), movie.get(9), movie.get(10), movie.get(11)));
		});
	}

	public Stream<NetflixMovie> firstNMovies(long n) {
		if ((n > 0) && (n <= this.NetflixMovie.size())) {
			return this.NetflixMovie.stream().limit(n).filter(movie -> movie.getType().toLowerCase().equals("tv show"));
		} else {
			System.out.println("\nEnter a number greaterthan 0\n");
		}
		return null;
	}
	
	public Stream<NetflixMovie> firstNMovies(long n, String listed_in) {
		if (((n > 0) && (n <= this.NetflixMovie.size())) && (listed_in != null) && (!listed_in.isEmpty())) {
			return this.NetflixMovie.stream().limit(n).filter(movie -> movie.getListed_in().toLowerCase().contains(listed_in.toLowerCase()) == true);
		} else {
			System.out.println("Invalid query param");
		}
		return null;
	}

	public Stream<NetflixMovie> firstNMovies(long n, String type, String country) {
		if (((n > 0) && (n <= this.NetflixMovie.size())) && ((type != null) && (!type.isEmpty()))
				&& (country != null)) {
			return this.NetflixMovie.stream().limit(n)
					.filter(movie -> (movie.getType().toLowerCase().contains(type.toLowerCase()))
							&& (movie.getCountry().toLowerCase().contains(country.toLowerCase())));
		} else {
			System.out.println("Invalid query param");
			}
		return null;
	}

	public Stream<hashedinNextflixCSVParser.NetflixMovie> firstNMovies(Stream<NetflixMovie> partialQuery,
			String startDate, String endDate) {
		DateFormat formatter = new SimpleDateFormat("MM-DD-yyyy");
		try {
			Date formattedStartDate = (Date) formatter.parse(startDate);
			Date formattedEndDate = (Date) formatter.parse(endDate);
//			partialQuery.filter(movie -> {
//				System.out.println();
//			});

		} catch (ParseException e) {
			e.printStackTrace();
		}

//		SimpleDateFormat newFormat = new SimpleDateFormat("MM-dd-yyyy");
//		String finalString = newFormat.format(date);

		return null;
	}

}
