package preprocessingPackage1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



public class TextToLDAinput{
	private static final int MAXVOCABULARY=20000;
	private static HashMap<String, Integer> termDictionary = new HashMap<String, Integer>();
	private static HashMap<String, Integer> docRepository = new HashMap<String, Integer>();
	private static HashMap<Integer, Integer> localTermFreq = new HashMap<Integer, Integer>();
//	private static ArrayList<Integer> globalTermFrequency = new ArrayList<Integer>();
	private static int[] globalTermFrequency;
	private static String outputString = "";
	private static String documentMapString="DocId,ProcessedTweet,OriginalTweet"+  System.getProperty("line.separator");
	private static String documentMapStringWithResonance="DocId,ProcessedTweet,OriginalTweet,RetweetCount"+  System.getProperty("line.separator");
	private static int currentTermId=1;
	private static int currentDocId=1;
	private static final int minimumWordsPerDoc=10;
	private static final int stopWordMarker=-1;
	private static String folderPath=".\\";
	private static final double entropyBarrier=2.5;
	
	
	private static void clearDataStructures(){
		termDictionary.clear();
		docRepository.clear();
		localTermFreq.clear();
		globalTermFrequency =  new int[MAXVOCABULARY];
		outputString="";
		documentMapString="DocId,ProcessedTweet,OriginalTweet"+  System.getProperty("line.separator");
	    currentTermId=1;
		currentDocId=1;
	}
	
	private static int addToTermDictionary(String term){ //this function will check if term exists in dictionary. If not it will add. In either case, it will return the Id of the term in dictionary.
		int termID = -1;
		if(termDictionary.containsKey(term)){
			termID = termDictionary.get(term);
			if(termID != stopWordMarker)
				globalTermFrequency[termID]=globalTermFrequency[termID]+1;
		}else{
			termID = currentTermId;
			termDictionary.put(term, termID);
			globalTermFrequency[termID]=1;
			currentTermId = currentTermId + 1;
		}
			
		return termID;
	}
	
	public static void converter(String stopWordFile, String inputFile, String outputFile, String folderPah){ //this function will parse the input file line by line and send each tweet for further processing. After every 1000 tweets processed, it will write the output
		//finally it also writes the term dictionary and term frequency in two separate files
		System.out.print("Processing the tweet corpus ......");
		clearDataStructures();
		folderPath=folderPah;
		markStopWords(stopWordFile);
		int totalProcessed=0;
		BufferedReader input;
		try {
			FileInputStream fstream = new FileInputStream(folderPath+inputFile);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
//			input = new BufferedReader(new FileReader(
//					fileName));
			 input = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = null;		
//			line = input.readLine();  //skip the column header
			
			while ((line = input.readLine()) != null) {	
				 String originalLine=line;
				 String[] terms = line.split(",");
				 line=terms[0];  //the first field is text
				
				
				String preProcessedTweet = Remove_unwanted.keepHashtags(line.toLowerCase());
//				String preProcessedTweet = RemoveUnWanted.keepHashtagsTEMP(line.toLowerCase());
				
				
				double entropyOfTweet = commons.entropy(preProcessedTweet);
				if(entropyOfTweet<=entropyBarrier )
					continue;

				if(isDuplicateTweet(preProcessedTweet))  //if the tweet is a repetation of some previous tweet
					continue;
				
				
				if(!processIndividualTweet(preProcessedTweet))  // if the tweet does not contain enough terms
					continue;
				
				line=line.replaceAll("â€�", "");
//				line=line.replaceAll("â€™", "");
				line=line.replaceAll("\"", "");
				
				line=line.replaceAll("[^\\p{Print}]", "");
				line = line.replaceAll(",", "--").trim();
				
				preProcessedTweet=preProcessedTweet.replaceAll("\\p{Space}{2,}", " ");
				documentMapString = documentMapString + (currentDocId-1) + "," + preProcessedTweet.trim() + "," + line +  System.getProperty("line.separator");
//				documentMapString = documentMapString + (currentDocId-1) + "," + preProcessedTweet.trim() + System.getProperty("line.separator");
				documentMapStringWithResonance+= (currentDocId-1) + "," + preProcessedTweet.trim() + "," + originalLine +  System.getProperty("line.separator");
				++totalProcessed;
				if(totalProcessed == 1000){
					totalProcessed = 0;
					writeToOutputFile(outputFile);
				}
				}						
			} catch (FileNotFoundException e) {
				e.printStackTrace();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }	
		writeToOutputFile(outputFile);
		writeTermDictionary(outputFile);
		/*System.out.println("Please check the FOUR output files ......");
		System.out.println("1. " + outputFile + ": Text file with word-document counts. Input for LDA Model.");
		System.out.println("2. " + outputFile.substring(0,outputFile.indexOf("_")) + "_termDictionary.csv" + ": Term Dictionary with term frequency");
		System.out.println("3. " + outputFile.substring(0,outputFile.indexOf("_")) + "_documentMap.csv" + ": Preprocessed Tweets with Document Id map");
		System.out.println("4. " + outputFile.substring(0,outputFile.indexOf("_")) + "_vocab.txt" + ": Text file with each line containg one word of vocabulary sorted in ascending order. Input for LDA Model.");*/
		
	}
	//-----------------------we use this----------------------
	public static int converterAndGetTotalTerms(String stopWordFile, String inputFile, String outputFile, String folderPah){ //this function will parse the input file line by line and send each tweet for further processing. After every 1000 tweets processed, it will write the output
		//finally it also writes the term dictionary and term frequency in two separate files
		clearDataStructures();
		folderPath=folderPah;
		markStopWords(stopWordFile);
		int totalProcessed=0;
		BufferedReader input;
		try {
			FileInputStream fstream = new FileInputStream(folderPath+inputFile);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
//			input = new BufferedReader(new FileReader(
//					fileName));
			 input = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = null;		
//			line = input.readLine();  //skip the column header
			
			while ((line = input.readLine()) != null) {	
				 String originalLine=line;
				 String[] terms = line.split(",");
				 line=terms[0];  //the first field is text
				
				
				String preProcessedTweet = Remove_unwanted.keepHashtags(line.toLowerCase());
//				String preProcessedTweet = RemoveUnWanted.keepHashtagsTEMP(line.toLowerCase());
				
				
				double entropyOfTweet = commons.entropy(preProcessedTweet);
				if(entropyOfTweet<=entropyBarrier )
					continue;

				if(isDuplicateTweet(preProcessedTweet))  //if the tweet is a repetation of some previous tweet
					continue;
				
				
				if(!processIndividualTweet(preProcessedTweet))  // if the tweet does not contain enough terms
					continue;
				
				line=line.replaceAll("â€�", "");
//				line=line.replaceAll("â€™", "");
				line=line.replaceAll("\"", "");
				
				line=line.replaceAll("[^\\p{Print}]", "");
				line = line.replaceAll(",", "--").trim();//???????
				
				preProcessedTweet=preProcessedTweet.replaceAll("\\p{Space}{2,}", " ");
				documentMapString = documentMapString + (currentDocId-1) + "," + preProcessedTweet.trim() + "," + line +  System.getProperty("line.separator");
//				documentMapString = documentMapString + (currentDocId-1) + "," + preProcessedTweet.trim() + System.getProperty("line.separator");
				documentMapStringWithResonance+= (currentDocId-1) + "," + preProcessedTweet.trim() + "," + originalLine +  System.getProperty("line.separator");
				++totalProcessed;
				if(totalProcessed == 1000){
					totalProcessed = 0;
					writeToOutputFile(outputFile);//-----------------*****
				}
				}						
			} catch (FileNotFoundException e) {
				e.printStackTrace();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }	
		writeToOutputFile(outputFile);//*****---------------------------***
		writeTermDictionary(outputFile);//****----------------
		/*System.out.println("Please check the FOUR output files ......");
		System.out.println("1. " + outputFile + ": Text file with word-document counts. Input for LDA Model.");
		System.out.println("2. " + outputFile.substring(0,outputFile.indexOf("_")) + "_termDictionary.csv" + ": Term Dictionary with term frequency");
		System.out.println("3. " + outputFile.substring(0,outputFile.indexOf("_")) + "_documentMap.csv" + ": Preprocessed Tweets with Document Id map");
		System.out.println("4. " + outputFile.substring(0,outputFile.indexOf("_")) + "_vocab.txt" + ": Text file with each line containg one word of vocabulary sorted in ascending order. Input for LDA Model.");*/
		return currentTermId-1;
	}

	

	private static void markStopWords(String stopWordFile) {
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(
					folderPath+stopWordFile));
			String line = null;			
			while ((line = input.readLine()) != null) {
				termDictionary.put(line.trim(), stopWordMarker);
			}
			}catch (FileNotFoundException e) {
				e.printStackTrace();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }	
		
	}

	private static void writeTermDictionary(String outputFile) {
		String[] vocabulary = new String[currentTermId];
		String writeString="Term,Term-id,Term-frequency"+ System.getProperty("line.separator");
		Iterator<Map.Entry<String, Integer>> it = termDictionary.entrySet().iterator();
		 while (it.hasNext()) {
		        Map.Entry<String, Integer> pairs = it.next(); 
		        String term = pairs.getKey();
		        int termId = pairs.getValue();
		        if(termId == stopWordMarker)
		        	continue;
		        int frequency = globalTermFrequency[termId];
		        vocabulary[termId]=term;
		        writeString = writeString + term + "," + termId + ","+ frequency + System.getProperty("line.separator"); 
		 }
		 String fileName = outputFile.substring(0,outputFile.indexOf("_")) + "_termDictionary.csv";		 
		 OutputFileHandler.fileName(folderPath+fileName);
		 OutputFileHandler.writeToFileUTF8(writeString);
		 
		 writeString="";
		 for(int i=1;i<currentTermId;++i)
			 writeString=writeString+vocabulary[i]+ System.getProperty("line.separator");
		 
		 fileName = outputFile.substring(0,outputFile.indexOf("_")) + "_vocab.txt";
		 OutputFileHandler.fileName(folderPath+fileName);
		 OutputFileHandler.writeToFileUTF8(writeString);
	}

	private static void writeToOutputFile(String outputFile) {	
		
		OutputFileHandler.fileName(folderPath+outputFile);
		OutputFileHandler.writeToFileUTF8(outputString);
		OutputFileHandler.fileName(folderPath+outputFile.substring(0,outputFile.indexOf("_")) + "_documentMap.csv");
		OutputFileHandler.writeToFileUTF8(documentMapString);
		OutputFileHandler.fileName(folderPath+outputFile.substring(0,outputFile.indexOf("_")) + "_docMapReson.csv");
		OutputFileHandler.writeToFileUTF8(documentMapStringWithResonance);
		outputString="";
		documentMapString="";
		documentMapStringWithResonance="";
	}

	private static boolean processIndividualTweet(String text){
		localTermFreq.clear();
		boolean documentProcessed = false;
		int termid;
		int oldfreq;
		ArrayList<String> hashtagAnatomy = CommonPreProcessors.hashtagsFilter(text);
		int totalHashtags = Integer.parseInt(hashtagAnatomy.get(0));
		String[] terms = text.trim().split("\\p{Space}+");
		
		if((terms.length-totalHashtags) > minimumWordsPerDoc){ //discard all documents having less than 10 words
			documentProcessed = true;
			for(int i=0; i<terms.length; ++i){
				termid = addToTermDictionary(terms[i]);
				
				if(termid == stopWordMarker)
					continue;
				else if(localTermFreq.containsKey(termid)){
					oldfreq = localTermFreq.get(termid);
					localTermFreq.put(termid, oldfreq+1);				
				}
				else
					localTermFreq.put(termid,1);
			}
			
			Iterator<Map.Entry<Integer, Integer>> it = localTermFreq.entrySet().iterator();
			 while (it.hasNext()) {
			        Map.Entry<Integer, Integer> pairs = it.next();     
			        outputString = outputString + currentDocId + " "+ pairs.getKey() + " " + pairs.getValue() + System.getProperty("line.separator"); 
			 }	
			 
			++currentDocId;
		}
		return documentProcessed;
	}
	
	private static boolean isDuplicateTweet(String preProcessedTweet) {
		boolean returnValue=false;
		
		if(docRepository.containsKey(preProcessedTweet))
			returnValue = true;
		else
			docRepository.put(preProcessedTweet, 0);
		return returnValue;
	}
	
}
