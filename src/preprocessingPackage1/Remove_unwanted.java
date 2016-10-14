package preprocessingPackage1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Remove_unwanted {
	
	
	public static String removeThemAll(String text){ //this function strips all URLs, time expression or any numeric values, all punctuations except apostophys.
		String returnString = text.replaceAll("(?<=\\s|[\\p{Punct}&&[^/]]|^)(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", ""); //strip all urls
		returnString = returnString.replaceAll("@\\w+", ""); //remove all user reference
		returnString = returnString.replaceAll("(?<=\\s|[\\p{Punct}&&[^/]]|^)(#[\\p{L}0-9-_]+)", ""); // remove hashtags
		returnString = returnString.replaceAll("(1[012]|[1-9])(:[0-5][0-9])?(\\s)?(?i)(am|pm)", ""); //remove 12 hour date. Sourece: http://www.mkyong.com/regular-expressions/10-java-regular-expression-examples-you-should-know/
		returnString=returnString.replaceAll("([01]?[0-9]|2[0-3]):[0-5][0-9]", ""); // remove 24 hour date. Same source as above
		returnString=returnString.replaceAll("\\b[0-9]*(\\.)?[0-9]*\\b", "");  //take out any number 123 or 123.23 or 124.5555555
		returnString = returnString.replaceAll("[\\p{Punct}&&[^']]{1,}", ""); //will take out # and @ too but will not take out '
		returnString = returnString.replaceAll("(?<![a-zA-Z])'|'(?![a-zA-Z])", "");		//will take out all ' except apostophys 
		returnString=returnString.replaceAll("[^\\p{Print}]", ""); // remove all non printable characters
		//.replaceAll("\\bRT\\b", "").trim(); //replace all RTs
		return returnString;
	}
	
	public static String keepHash_UserName(String text){ //this function strips all URLs, time expression or any numeric values, all punctuations except apostophys.
		String returnString = text.replaceAll("(?<=\\s|[\\p{Punct}&&[^/]]|^)(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", ""); //strip all urls
//		returnString = returnString.replaceAll("@\\w+", ""); //remove all user reference
//		returnString = returnString.replaceAll("(?<=\\s|[\\p{Punct}&&[^/]]|^)(#[\\p{L}0-9-_]+)", ""); // remove hashtags
		returnString = returnString.replaceAll("(1[012]|[1-9])(:[0-5][0-9])?(\\s)?(?i)(am|pm)", ""); //remove 12 hour date. Sourece: http://www.mkyong.com/regular-expressions/10-java-regular-expression-examples-you-should-know/
		returnString=returnString.replaceAll("([01]?[0-9]|2[0-3]):[0-5][0-9]", ""); // remove 24 hour date. Same source as above
		returnString=returnString.replaceAll("\\b[0-9]*(\\.)?[0-9]*\\b", "");  //take out any number 123 or 123.23 or 124.5555555
		returnString = returnString.replaceAll("[\\p{Punct}&&[^'#]]{1,}", ""); //will take out # and @ too but will not take out '
		returnString = returnString.replaceAll("(?<![a-zA-Z])'|'(?![a-zA-Z])", "");		//will take out all ' except apostophys 
		returnString=returnString.replaceAll("[^\\p{Print}]", ""); // remove all non printable characters
		//.replaceAll("\\bRT\\b", "").trim(); //replace all RTs
		return returnString;
	}
	
	public static String keepHashtags(String text){ //this function strips all URLs, time expression or any numeric values, all punctuations except apostophys.
		String returnString = text.replaceAll("(?<=\\s|[\\p{Punct}&&[^/]]|^)(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", ""); //strip all urls
		returnString = returnString.replaceAll("@\\w+", ""); //remove all user reference
//		returnString = returnString.replaceAll("(?<=\\s|[\\p{Punct}&&[^/]]|^)(#[\\p{L}0-9-_]+)", ""); // remove hashtags
		returnString = returnString.replaceAll("(1[012]|[1-9])(:[0-5][0-9])?(\\s)?(?i)(am|pm)", ""); //remove 12 hour date. Sourece: http://www.mkyong.com/regular-expressions/10-java-regular-expression-examples-you-should-know/
		returnString=returnString.replaceAll("([01]?[0-9]|2[0-3]):[0-5][0-9]", ""); // remove 24 hour date. Same source as above
		returnString=returnString.replaceAll("\\b[0-9]*(\\.)?[0-9]*\\b", "");  //take out any number 123 or 123.23 or 124.5555555
		returnString = returnString.replaceAll("[\\p{Punct}&&[^'#]]{1,}", ""); //will take out # and @ too but will not take out '
		returnString = returnString.replaceAll("(?<![a-zA-Z])'|'(?![a-zA-Z])", "");		//will take out all ' except apostophys 
		returnString=returnString.replaceAll("[^\\p{Print}]", ""); // remove all non printable characters
		returnString=returnString.replaceAll("^(\\p{Space}*)rt(\\p{Space}+)", ""); //remove leading RT for retweets
		//.replaceAll("\\bRT\\b", "").trim(); //replace all RTs
		return returnString;
	}
	
	public static String keepHashtagsTEMP(String text){ //this function strips all URLs, time expression or any numeric values, all punctuations except apostophys.
		String returnString = text.replaceAll("(?<=\\s|[\\p{Punct}&&[^/]]|^)(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", ""); //strip all urls
		returnString = returnString.replaceAll("@\\w+", ""); //remove all user reference
//		returnString = returnString.replaceAll("(?<=\\s|[\\p{Punct}&&[^/]]|^)(#[\\p{L}0-9-_]+)", ""); // remove hashtags
		returnString = returnString.replaceAll("(1[012]|[1-9])(:[0-5][0-9])?(\\s)?(?i)(am|pm)", ""); //remove 12 hour date. Sourece: http://www.mkyong.com/regular-expressions/10-java-regular-expression-examples-you-should-know/
		returnString=returnString.replaceAll("([01]?[0-9]|2[0-3]):[0-5][0-9]", ""); // remove 24 hour date. Same source as above
		returnString=returnString.replaceAll("\\b[0-9]*(\\.)?[0-9]*\\b", "");  //take out any number 123 or 123.23 or 124.5555555
		returnString = returnString.replaceAll("[\\p{Punct}&&[^'#]]{1,}", ""); //will take out # and @ too but will not take out '
		returnString = returnString.replaceAll("(?<![a-zA-Z])'|'(?![a-zA-Z])", "");		//will take out all ' except apostophys 
		returnString=returnString.replaceAll("[^\\p{Print}]", ""); // remove all non printable characters
//		returnString=returnString.replaceAll("^(\\p{Space}*)rt(\\p{Space}+)", ""); //remove leading RT for retweets
		//.replaceAll("\\bRT\\b", "").trim(); //replace all RTs
		return returnString;
	}
	
	
	private static String stripUrl(String text){
	
		return text.replaceAll("(?<=\\s|[\\p{Punct}&&[^/]]|^)(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", "");
	}
	
	private static String punctuationsFilter(String text) {		//This function removes all punctuations except apostophy 's 
		
		String returnString = text.replaceAll("[\\p{Punct}&&[^']]{1,}", ""); //will take out # and @ too but will not take out '
		returnString = returnString.replaceAll("(?<![a-zA-Z])'|'(?![a-zA-Z])", "");		//will take out all ' except apostophys 
		return returnString;
	}
	
	private static String removeRT(String text){
		return text.replaceAll("\\bRT\\b", "").trim();
		
	}
	
	private static String removeDigitsAlone(String text){
		String returnString = text.replaceAll("(1[012]|[1-9])(:[0-5][0-9])?(\\s)?(?i)(am|pm)", ""); //remove 12 hour date. Sourece: http://www.mkyong.com/regular-expressions/10-java-regular-expression-examples-you-should-know/
		returnString=returnString.replaceAll("([01]?[0-9]|2[0-3]):[0-5][0-9]", ""); // remove 24 hour date. Same source as above
		returnString=returnString.replaceAll("\\b[0-9]*(\\.)?[0-9]*\\b", "");  //take out any number 123 or 123.23 or 124.5555555
		return returnString;
	}
	
	private static String punctuationsFilterSlowVersion(String text) {		//This function removes all punctuations except apostophy 's 
		
		String regex = "[\\p{Punct}&&[^']]{1,}";// "\\p{Punct}{1,}" remove all punctuations other than '
		// "[\\p{Punct}&&[^']]{1,}" 		&&[^\\w'\\w+]]
		//[^((?<=[a-zA-Z])'(?=[a-zA-Z]))]]

		Pattern patt = Pattern.compile(regex);

		text = text.trim();

		Matcher matcher = patt.matcher(text);

		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {

			matcher.appendReplacement(sb, "");
		}
		matcher.appendTail(sb); // copy remainder
		
		text = sb.toString();
		//remove all 's taht are not apostophe 
		
		String regex2 = "(?<![a-zA-Z])'|'(?![a-zA-Z])";
		Pattern patt2 = Pattern.compile(regex2);
		Matcher matcher2 = patt2.matcher(text);

		StringBuffer sb2 = new StringBuffer();
		while (matcher2.find()) {

			matcher2.appendReplacement(sb2, "");
		}
		matcher2.appendTail(sb2);
		return sb2.toString();

	}

	private static String stripUrlSlowVersion(String text){
		int totalMatched=0;
		String returnString;
		String regex = "(?<=\\s|[\\p{Punct}&&[^/]]|^)(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		Pattern patt = Pattern.compile(regex);
		
		text = text.trim();

		Matcher matcher = patt.matcher(text);
		String reconstructedString = "";

		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			reconstructedString = reconstructedString
					+ matcher.group().trim().replaceAll("^\\p{Punct}", "")
					+ " ";
			matcher.appendReplacement(sb, "");
			totalMatched = totalMatched + 1;
		}
		matcher.appendTail(sb); // copy remainder
		
		if (totalMatched == 0){
			returnString = text;
		}
		else{
			returnString=sb.toString();	
		}		
		return returnString;
	}
}


