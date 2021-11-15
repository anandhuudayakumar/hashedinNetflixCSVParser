package hashedinNextflixCSVParser;

import java.io.BufferedReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class NetflixCSVService {
	private List<NetflixMovie> NetflixMovie = new ArrayList<NetflixMovie>();

	public NetflixCSVService(BufferedReader reader) {
		super();
		NetflixCSVParser netflixCSVParser = new NetflixCSVParser(reader);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
		DateConverterImpl dateConverterImpl = new DateConverterImpl();
		netflixCSVParser.cleanNetflixCSV().forEach(movie -> {
			this.NetflixMovie.add(
					new NetflixMovie(movie.get(0), movie.get(1), movie.get(2), movie.get(3), movie.get(4), movie.get(5),
							dateConverterImpl.dateFormatter(movie.get(6), formatter),
							Integer.parseInt(movie.get(7)),
							movie.get(8),
							movie.get(9), movie.get(10), movie.get(11)));
		});
	}

	public Stream<NetflixMovie> firstNMovies(long n) {
		if ((n > 0)) {
			return this.NetflixMovie.stream().limit(n).filter(movie -> movie.getType().toLowerCase().equals("tv show"));
		} else {
			System.out.println("\nEnter a number greaterthan 0\n");
		}
		return null;
	}
	
	public Stream<NetflixMovie> firstNMovies(long n, String listed_in) {
		if ((n > 0) && (listed_in != null) && (!listed_in.isEmpty())) {
			return this.NetflixMovie.stream().limit(n)
					.filter(movie -> movie.getListedIn().toLowerCase().contains(listed_in.toLowerCase()) == true);
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

	public Stream<NetflixMovie> firstNMovies(Stream<NetflixMovie> partialQuery,
			String startDate, String endDate) {
		List<NetflixMovie> queryResut = new ArrayList<NetflixMovie>();
		DateConverter dateTimeFormatter = new DateConverterImpl();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
		LocalDate formattedStartDate = dateTimeFormatter.dateFormatter(startDate, formatter);
		LocalDate formattedEndDate = dateTimeFormatter.dateFormatter(endDate, formatter);
		partialQuery.forEach(movie -> {
			if (movie.getDateAdded() != null) {
				if ((movie.getDateAdded().isAfter(formattedStartDate))
						&& (movie.getDateAdded().isBefore(formattedEndDate))) {
					queryResut.add(movie);
				}
			}
		});
		return queryResut.stream().sorted(new Comparator<NetflixMovie>() {
			@Override
			public int compare(NetflixMovie o1, NetflixMovie o2) {
				String[] duration1 = o1.getDuration().split(" ");
				String[] duration2 = o2.getDuration().split(" ");
				Integer duration1Value = Integer.parseInt(duration1[0]);
				String duration1Unit = duration1[1];
				Integer duration2Value = Integer.parseInt(duration2[0]);
				String duration2Unit = duration2[1];
				if ((duration1Unit.equals("Seasons")) || (duration1Unit.equals("Season"))) {
					duration1Value = duration1Value * 5000;
				}
				if ((duration2Unit.equals("Seasons")) || (duration2Unit.equals("Season"))) {
					duration2Value = duration2Value * 5000;
				}
				return duration1Value.compareTo(duration2Value);
			}
		});
	}

}
