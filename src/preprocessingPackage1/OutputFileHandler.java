package preprocessingPackage1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class OutputFileHandler {

	private static String fileLocation;
	private static String fileExtension;

	private static File actualFile;

	private static OutputFileHandler file = new OutputFileHandler();

	public static void createFileObject(String fileName) {
		// fileLocation = "C:\\TwitterDBFiles\\OnlyLoc\\" + fLocation;
		int dotInd = fileName.lastIndexOf('.');

		fileExtension = fileName.substring(dotInd + 1);

		Properties prop = new Properties();
		try {
			// load a properties file
			prop.load(new FileInputStream("config.properties"));

			fileLocation = prop.getProperty("filepath") + fileName;
			actualFile = new File(fileLocation);
			System.out.println("Creating file:" + actualFile.getAbsolutePath());

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static void fileName(String filePath, String fileName) {
		// fileLocation = "C:\\TwitterDBFiles\\OnlyLoc\\" + fLocation;
		int dotInd = fileName.lastIndexOf('.');

		fileExtension = fileName.substring(dotInd + 1);

		fileLocation = filePath + fileName;

		actualFile = new File(fileLocation);
	}
	
	public static void fileName(String fileLocation) {
				
		actualFile = new File(fileLocation);
	}

	/*public static void writeToFile(String str) {

		RandomAccessFile filePointer = null;

//		if (str != System.getProperty("line.separator")
//				&& fileExtension.equalsIgnoreCase("csv")) {
//
//			str = str + ",";
//		}
		byte[] data = str.getBytes();
		try {
			filePointer = new RandomAccessFile(actualFile, "rw");
			filePointer.seek(filePointer.length());
			filePointer.write(data, 0, data.length);
			filePointer.close();
			System.out.println("Closing file" + actualFile.getAbsolutePath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public static void writeToFileUTF8(String str){
		try {
			OutputStreamWriter writer = new OutputStreamWriter(
			        new FileOutputStream(actualFile, true), "UTF-8");
			BufferedWriter fbw = new BufferedWriter(writer);
            fbw.write(str);
            fbw.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeNewLine() {

		String lineSeparator = System.getProperty("line.separator");
		writeToFileUTF8(lineSeparator);
	}

	public static String readFileCommaDelim(String fileName) {

		String result = "";
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(fileName);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				// System.out.println (strLine);
				result = result + "," + strLine;
				// System.out.println(result);
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		// System.out.println(result);
		return result;
	}

	public static String readFileSpaceDelim(String fileURL) {

		String result = "";
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(fileURL);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				// System.out.println (strLine);
				result = result + " " + strLine;
				// System.out.println(result);
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		// System.out.println(result);
		return result;
	}

	public static boolean saveModelTAssign(String filename) {
		int i, j;
		System.out.println(filename);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

			// write docs with topic assignments for words

			writer.write("This fucker is writing");

			writer.close();
		} catch (Exception e) {
			System.out.println("Error while saving model tassign: "
					+ e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

}

