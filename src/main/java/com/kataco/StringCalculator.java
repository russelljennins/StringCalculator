package com.kataco;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class StringCalculator {

	private static Logger logger = Logger.getLogger(StringCalculator.class.getName());

	private static Pattern numericPattern = Pattern.compile("-?\\d+(\\.\\d+)?");

	public static int add(String numbers) throws Exception {
		int retValue = 0;
		// Check for passed delimiter
		String delimiterString = ",";
		List<String> delimiterList = null;
		if (numbers.length() > 1 && numbers.substring(0, 2).equals("//")) {
			delimiterString = numbers.substring(0, numbers.indexOf('\n'));
			if (delimiterString.charAt(2) == '[' && delimiterString.charAt(delimiterString.length() - 1) == ']') {
				delimiterList = getDelimiters(delimiterString);

				numbers = replaceDelimtersWithComma(numbers.substring(numbers.indexOf('\n') + 1, numbers.length()),
						delimiterList);

			} else if (delimiterString.length() == 3) {
				// Use single character delimiter
				numbers = numbers.replace(delimiterString.charAt(2), ',');
			}
			numbers = numbers.substring(numbers.indexOf('\n') + 1, numbers.length());
		}
		numbers = numbers.replace("\n", delimiterString);

		if (numbers != null && numbers.length() != 0) {
			String[] splits = numbers.split(",");

			for (int i = 0; i < splits.length; i++) {
				if (numericPattern.matcher(splits[i]) != null) {
					if (Integer.parseInt(splits[i]) < 0) {
						// Retrieve all negative values
						StringBuffer sb = new StringBuffer();
						sb.append(splits[i]);
						for (int i1 = i + 1; i1 < splits.length; i1++) {
							if (Integer.parseInt(splits[i1]) < 0) {
								sb.append("," + splits[i1]);
							}
						}
						throw new Exception("Negatives not allowed:" + sb.toString());
					} else {
						if (Integer.parseInt(splits[i]) < 1001) {
							retValue += Integer.parseInt(splits[i]);
						} else {
							logger.info("Value " + splits[i] + " ignored as greater than 1000");
						}
					}
				} else {
					throw new Exception("Value not an integer");
				}
			}
		}
		return retValue;
	}

	static protected List<String> getDelimiters(String delimiterString) {
		List<String> retValue = new ArrayList<>();
		if (delimiterString.length() > 0 && delimiterString.substring(0, 2).equals("//")) {
			String delimiters = delimiterString.substring(2, delimiterString.length());

			if (delimiterString.length() == 3) {
				// Single character delimiter e.g. \\%
				retValue.add(delimiterString.substring(2, 3));
			} else {

				for (int i = 0; i < delimiters.length(); i++) {
					String delimiter = null;
					delimiter = delimiters.substring(delimiters.indexOf("[") + 1 + i, delimiters.indexOf("]") + i);
					if (delimiter != null && delimiter.length() > 0) {
						retValue.add(delimiter);
					}
					i += delimiter.length() + 1;
				}
			}
		}
		return retValue;
	}

	static protected String replaceDelimtersWithComma(String numbers, List<String> delimiterList) {
		StringBuffer retValue = null;
		for (int i = 0; i < delimiterList.size(); i++) {

			retValue = new StringBuffer();

			int pos = 0;
			for (int i1 = 0; i1 + delimiterList.get(i).length() < numbers.length(); i1++) {
				if (numbers.substring(i1, i1 + delimiterList.get(i).length()).equals(delimiterList.get(i))) {

					retValue.append(numbers.substring(pos, i1));
					retValue.append(",");
					i1 += delimiterList.get(i).length() - 1;
					pos = i1 + 1;
				}
			}
			retValue.append(numbers.substring(pos, numbers.length()));
			numbers = retValue.toString();
		}
		return retValue.toString();
	}
}