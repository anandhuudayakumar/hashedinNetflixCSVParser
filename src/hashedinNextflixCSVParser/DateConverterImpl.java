package hashedinNextflixCSVParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateConverterImpl implements DateConverter {
	/*
	 * function to convert date of format December 20, 2018 to yyyy-MM-dd format
	 * 
	 * @param date in string format
	 * 
	 * @return LocalDate object of correct format
	 */
	@Override
	public LocalDate dateFormatter(String date, DateTimeFormatter formatter) {
		date = date.replaceAll("^\"|\"$", "").trim();
		LocalDate formattedDate = null;
		if ((date != null) && (!date.isEmpty())) {
			try {
				formattedDate = LocalDate.parse(date, formatter);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format: use format MM/DD/YYYY");
				e.printStackTrace();
			}
	}
		return formattedDate;
	}

}
