package preprocessingPackage1;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonPreProcessors {
	
	public  static ArrayList<String> hashtagsFilter(String text) {
		//this function will return the following values
		//output[0] -- number of hashtags encountered
		//output[1] -- the text after extracting all hashtags
		//output[2] -- all extracted hashtags as one string separated by space
		//output[3] to output[n-1] the splitted hashtags
		
		
		ArrayList<String> output = new ArrayList<String>();
		
		output.add("reserved");
		output.add("reserved");
		output.add("reserved");
		String regex = "(?<=\\s|[\\p{Punct}&&[^/]]|^)(#[\\p{L}0-9-_]+)";
		// "(?:^|\\s|[\\p{Punct}&&[^/]])(#[\\p{L}0-9-_]+)";
		String returnString="", allHashString="";
		int total = 0;

		Pattern patt = Pattern.compile(regex);

		text = text.trim();

		Matcher matcher = patt.matcher(text);
		String reconstructedString = "";

		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String token = matcher.group().trim();
			output.add(token);
			allHashString=allHashString+token+" ";
			reconstructedString = reconstructedString + token + " ";
			matcher.appendReplacement(sb, "");
			total = total + 1;
		}
		matcher.appendTail(sb); // copy remainder

		output.set(0, Integer.toString(total));
		if (total == 0){
			returnString = text;
		}
		else{
			returnString=sb.toString();
		}

		output.set(1, returnString.trim());
		output.set(2, allHashString.trim());
		return output;
	}

}
