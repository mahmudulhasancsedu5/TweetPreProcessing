package preprocessingPackage1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreProcessingMain {
	private static final int iterationOfLDA = 1000;
	private static final int samplingIteration = 6;
	private static final String outputBasedOnScore = "TextRankScore";

	public static void main(String[] args) {
		long lStartTime = new Date().getTime();

		System.setProperty("file.encoding", "UTF-8");

		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream("config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Pattern pattern = Pattern.compile("\\s");// ???
		Matcher matcher;

		String dir = prop.getProperty("dataFolder");
		if (dir.isEmpty()) {
			System.out.println("Error!! Exiting.....");
			System.out.println(
					"The property \"dataFolder\" in Configuration file \"config.properties\" is not defined properly!!");
			System.exit(0);
		} else if (pattern.matcher(dir).find()) {
			System.out.println("Error!! Exiting.....");
			System.out.println("Space in Folder name is not allowed!! Rename the folder: " + dir);
			System.exit(0);
		} else if (!new File(dir).exists()) {
			System.out.println("Error!! Exiting.....");
			System.out.println("Can't locate the dataFolder: " + dir);
			System.out.println("In case you are using single '\\' in the path, use a double '\\', like '\\\\'");
			System.exit(0);
		} else if (!dir.endsWith(File.separator))
			dir += File.separator;

		String programDirectory = prop.getProperty("programFolder");
		if (programDirectory.isEmpty()) {
			System.out.println("Error!! Exiting.....");
			System.out.println(
					"The property \"programFolder\" in Configuration file \"config.properties\" is not defined properly!!");
			System.exit(0);
		} else if (pattern.matcher(programDirectory).find()) {
			System.out.println("Error!! Exiting.....");
			System.out.println("Space in Folder name is not allowed!! Rename the folder: " + programDirectory);
			System.exit(0);
		} else if (!new File(programDirectory).exists()) {
			System.out.println("Error!! Exiting.....");
			System.out.println("Can't locate the dataFolder: " + programDirectory);
			System.out.println("In case you are using single '\\' in the path, use a double '\\', like '\\\\'");
			System.exit(0);
		} else if (!programDirectory.endsWith(File.separator))
			programDirectory += File.separator;

		String stopFile = prop.getProperty("stopWordFile");
		if (stopFile.isEmpty()) {
			System.out.println("Error!! Exiting.....");
			System.out.println(
					"The property \"stopWordFile\" in Configuration file \"config.properties\" is not defined properly!!");
			System.exit(0);
		} else if (!new File(dir + stopFile).exists()) {
			System.out.println("Error!! Exiting.....");
			System.out.println("Can't locate the stopword file: " + dir + stopFile);
			System.exit(0);
		}

		String inputFile = prop.getProperty("inputFile");
		if (inputFile.isEmpty()) {
			System.out.println("Error!! Exiting.....");
			System.out.println(
					"The property \"inputFile\" in Configuration file \"config.properties\" is not defined properly!!");
			System.exit(0);
		} else if (!new File(dir + inputFile).exists()) {
			System.out.println("Error!! Exiting.....");
			System.out.println("Can't locate the file containing the tweets: " + dir + inputFile);
			System.exit(0);
		}

		String oputputfileprefix = prop.getProperty("outputFilePrefix");
		if (oputputfileprefix.isEmpty()) {
			System.out.println("Error!! Exiting.....");
			System.out.println(
					"The property \"oputputfileprefix\" in Configuration file \"config.properties\" is not defined!! Please define it and try again.");
			System.exit(0);
		}
		String mintopix = prop.getProperty("minimumExpectedTopicsInTweetCorpus").trim();
		if (mintopix.isEmpty()) {
			System.out.println("Error!! Exiting.....");
			System.out.println(
					"The property \"minimumExpectedTopicsInTweetCorpus\" in Configuration file \"config.properties\" is not defined!! Please define it and try again.");
			System.exit(0);
		} else if (!isNumeric(mintopix)) {
			System.out.println("Error!! Exiting.....");
			System.out.println(
					"The property \"minimumExpectedTopicsInTweetCorpus\" in Configuration file \"config.properties\" should be an Integer!! Please define it properly and try again.");
			System.exit(0);
		}

		String maxtopix = prop.getProperty("maximumExpectedTopicsInTweetCorpus").trim();
		if (maxtopix.isEmpty()) {
			System.out.println("Error!! Exiting.....");
			System.out.println(
					"The property \"maximumExpectedTopicsInTweetCorpus\" in Configuration file \"config.properties\" is not defined!! Please define it and try again.");
			System.exit(0);
		} else if (!isNumeric(maxtopix)) {
			System.out.println("Error!! Exiting.....");
			System.out.println(
					"The property \"maximumExpectedTopicsInTweetCorpus\" in Configuration file \"config.properties\" should be an Integer!! Please define it properly and try again.");
			System.exit(0);
		} else if (Integer.parseInt(maxtopix) < Integer.parseInt(mintopix)) {
			System.out.println("Error!! Exiting.....");
			System.out.println(
					"Seems like the value of \"maximumExpectedTopicsInTweetCorpus\" in Configuration file \"config.properties\" is less than that of \"minimumExpectedTopicsInTweetCorpus\"!! Please define them properly and try again.");
			System.exit(0);
		}

		int K = 0;
		String tempK = prop.getProperty("summaryTweetsPerCluster").trim();
		if (tempK.isEmpty()) {
			System.out.println("Error!! Exiting.....");
			System.out.println(
					"The property \"summaryTweetsPerCluster\" in Configuration file \"config.properties\" is not defined!! Please define it and try again.");
			System.exit(0);
		} else if (!isNumeric(tempK)) {
			System.out.println("Error!! Exiting.....");
			System.out.println(
					"The property \"summaryTweetsPerCluster\" in Configuration file \"config.properties\" should be an Integer!! Please define it properly and try again.");
			System.exit(0);
		}

		int totalTopics = 0;
		int sizeOfVocabulary = 0;

		String outputFile = oputputfileprefix + "_op.txt";
		String documentMapFile = oputputfileprefix + "_documentMap.csv";
		String termDictionaryFile = oputputfileprefix + "_termDictionary.csv";
		String vocabularyFile = oputputfileprefix + "_vocab.txt";
		// -----------------------------------------------------
		// String POSFile=oputputfileprefix+"_termDictionary_POS.csv";
		// String LDAModelScoreFile=oputputfileprefix+"_LDAModelScore.csv";
		// --------------------------------------------------------

		// %%%%%%%%% PreProcessing of Input File %%%%%%%%%%%%%%%%%%
		System.out.print("Processing input tweet stream .....");
		sizeOfVocabulary = TextToLDAinput.converterAndGetTotalTerms(stopFile, inputFile, outputFile, dir);
		System.out.println(" done.");
		
	
		
		String bigramFile="DebateSegment-2_topic2_bigramStat.txt";
		vocabularyFile="DebateSegment-2_vocab.txt";
		
		/*
		System.out.println("Execute TextRank algorithm...\n");
		String program="TextRank.exe " +dir+bigramFile+" "+dir+vocabularyFile;
		runExecutable(programDirectory+"exes\\", program);
		System.out.println("done\n");
		*/
		System.out.println("Execute test...\n");
		String program="test.exe "+programDirectory+"data\\";
		runExecutable(programDirectory+"exes\\", program);
		System.out.println("done\n");

		
		
		long lEndTime = new Date().getTime();
		long difference = lEndTime - lStartTime;
		System.out.println("Elapsed seconds: " + difference / 1000 + "seconds");

	}
	private static void runExecutable(String dir, String program) {
		 Runtime r = Runtime.getRuntime();
        try {
			Process p = r.exec(dir+program); 
			
			InputStream stderr = p.getErrorStream();
           /*InputStreamReader isr = new InputStreamReader(stderr);
           BufferedReader br = new BufferedReader(isr);
           String line = null;
           System.out.println("<ERROR>");
           while ( (line = br.readLine()) != null)
               System.out.println(line);
           System.out.println("</ERROR>");*/
			p.waitFor();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	// %%%%%%%%%%%% Merge Summaries %%%%%%%%%%%%%%

	private static boolean isNumeric(String str) {
		try {
			int d = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
