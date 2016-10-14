package preprocessingPackage1;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;


public class commons {

	
	/*public static double entropy(String tweetString){
		
		HashMap<String, Integer> tweetTerms = new HashMap<String, Integer>();
		String[] terms = tweetString.split("\\p{Space}+");
		for(int i=0;i < terms.length; ++i){
			if(tweetTerms.containsKey(terms[i])){
				int oldFreq = tweetTerms.get(terms[i]);
				tweetTerms.put(terms[i], oldFreq+1);
			}
			else
				tweetTerms.put(terms[i], 1);
				
		}
		double entropyValue=0.0;
		Iterator<Map.Entry<String, Integer>> it = tweetTerms.entrySet().iterator();
		 while (it.hasNext()) {
			 Map.Entry<String, Integer> pairs = it.next(); 
			 double probabilityOfThisTerm=(double)pairs.getValue()/(double)terms.length;
			 double logProbability=Math.log(probabilityOfThisTerm)/Math.log(2);
			 entropyValue=entropyValue+(probabilityOfThisTerm*logProbability);
		 }
		 
		 return -entropyValue;
	}*/
	
	public static double entropy(String tweetString){
		
		HashMap<String, Integer> tweetTerms = new HashMap<String, Integer>();
		String[] terms = tweetString.split("\\p{Space}+");
		for(int i=0;i < terms.length; ++i){
			if(tweetTerms.containsKey(terms[i])){
				int oldFreq = tweetTerms.get(terms[i]);
				tweetTerms.put(terms[i], oldFreq+1);
			}
			else
				tweetTerms.put(terms[i], 1);
				
		}
		double entropyValue=0.0;
		Iterator<Map.Entry<String, Integer>> it = tweetTerms.entrySet().iterator();
		 while (it.hasNext()) {
			 Map.Entry<String, Integer> pairs = it.next(); 
			 double probabilityOfThisTerm=(double)pairs.getValue()/(double)terms.length;
			 double logProbability=Math.log(probabilityOfThisTerm)/Math.log(2);
			 entropyValue=entropyValue+(probabilityOfThisTerm*logProbability);
		 }
		 
		 return -entropyValue;
	}
	
	public static double simpsonDistance(String tweetText1, String tweetText2){//A distance of 0 means the tweets share all of their terms, whereas a distance of 1 means they have no terms in common.
		
		HashMap<String, Integer> firstTweetTerms = new HashMap<String, Integer>();
		HashMap<String, Integer> secondTweetTerms = new HashMap<String, Integer>();
		int minTerms=0;
		double sizeInterSect=0;
		String[] terms = tweetText1.split("\\p{Space}+");
		for(int i=0;i < terms.length; ++i){
			
			firstTweetTerms.put(terms[i], 0);
		}
		
		String[] terms2 = tweetText2.split("\\p{Space}+");
		for(int i=0;i < terms2.length; ++i){
			
			secondTweetTerms.put(terms2[i], 0);			
		}
		
		
		Iterator<Map.Entry<String, Integer>> it = firstTweetTerms.entrySet().iterator();
		 while (it.hasNext()) {
		        Map.Entry<String, Integer> pairs = it.next(); 
		        String term = pairs.getKey();
		        if(secondTweetTerms.containsKey(term))
		        	++sizeInterSect;
		 }
		
		if(sizeInterSect==0)
			return 1.0;
		
		minTerms = Math.min(firstTweetTerms.size(), secondTweetTerms.size());
		double distance = 1 - (sizeInterSect/minTerms);
		
		return distance;		
	}
	
	public static ArrayList<String> getFileNames(String folderPath) {
		ArrayList<String> fileList = new ArrayList<String>();
		 String path = folderPath; 
		 
		  String files;
		  File folder = new File(path);
		  File[] listOfFiles = folder.listFiles(); 
		 
		  for (int i = 0; i < listOfFiles.length; i++){		 
			   if (listOfFiles[i].isFile()){
				   files = listOfFiles[i].getName();
//				   System.out.println(files);
				   fileList.add(files);
			   }		
		  }
		  
		  return fileList;
	}
	
	public static String convertArrayListToString(
			ArrayList<String> list) {
		String listString = "";

		for (String s : list)
		{
		    listString += s + ",";
		}
		
		return listString.substring(0, listString.lastIndexOf(',')); //send string except the last comma
	}
	
	public static ResultSet getResultSet(String filePath, String fileName, String selectFileds, String additionalClause, String columnTypes){
		ResultSet results = null;
		 Properties props = new Properties();
		// load the driver into memory
	      try {
			Class.forName("org.relique.jdbc.csv.CsvDriver");
			 props.put("columnTypes", columnTypes);
//			 props.put("quoteStyle", "C");
			
			// create a connection. The first command line parameter is assumed to
		      //  be the directory in which the .csv files are held
		      Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + filePath, props);
		      
		   // create a Statement object to execute the query with
		      java.sql.Statement stmt = conn.createStatement();
		      String query = "SELECT "+ selectFileds + " FROM " + fileName.substring(0,fileName.length()-4) + additionalClause;
		      
		      results = stmt.executeQuery(query);
		      
		     
		      
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	
	public static ResultSet getResultSet(String filePath, String fileName, String selectFileds, String additionalClause, String columnTypes, boolean suppressHeaders, String headerLine){
		ResultSet results = null;
		 Properties props = new Properties();
		// load the driver into memory
	      try {
			Class.forName("org.relique.jdbc.csv.CsvDriver");
			 props.put("columnTypes", columnTypes);
			 
			 if(suppressHeaders){
				 props.put("suppressHeaders", "true");
				 props.put("headerline", headerLine);
			 }
//			 props.put("quoteStyle", "C");
			
			// create a connection. The first command line parameter is assumed to
		      //  be the directory in which the .csv files are held
		      Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + filePath, props);
		      
		   // create a Statement object to execute the query with
		      java.sql.Statement stmt = conn.createStatement();
		      String query = "SELECT "+ selectFileds + " FROM " + fileName.substring(0,fileName.length()-4) + additionalClause;
		      
		      results = stmt.executeQuery(query);
		      
		     
		      
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	
	
}
