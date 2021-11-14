package hashedinNextflixCSVParser;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NetflixCSVClassBuilder {
	private BufferedReader reader;

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

	public NetflixCSVClassBuilder(BufferedReader reader) {
		super();
		this.reader = reader;

	}

	

}
