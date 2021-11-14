package hashedinNextflixCSVParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface DateConverter {
	LocalDate dateFormatter(String date, DateTimeFormatter formatter);
}
