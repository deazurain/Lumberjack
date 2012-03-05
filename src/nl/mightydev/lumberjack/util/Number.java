package nl.mightydev.lumberjack.util;

public class Number {
	
	public static int parse(String number, int default_value) {
		try {
			return Integer.parseInt(number);
		} catch (Exception e) {
			return default_value;
		}
	}
	
	public static double parse(String number, double default_value) {
		try {
			return Double.parseDouble(number);
		} catch (Exception e) {
			return default_value;
		}
	}

}
