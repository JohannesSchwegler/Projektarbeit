package Texteditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReplaceText {

	public static void createFile(String fileName, String xPathContainerRechtesBild, String xPathImageRightFloat,
			int width, int height) throws IOException {

		File file = new File(
				"C:\\Users\\Bachelorarbeit\\eclipse-workspace\\maven-projektarbeit\\src\\test\\resources\\specs\\"
						+ fileName + ".gspec");

		// Create the file
		if (file.createNewFile()) {
			System.out.println("File is created!");
		} else {
			System.out.println("File already exists.");
			return;
		}

		File fileToBeModified = new File(
				"C:\\Users\\Bachelorarbeit\\eclipse-workspace\\maven-projektarbeit\\src\\test\\resources\\specs\\"
						+ fileName + ".gspec");

		String oldContent = "@objects\r\n" + "	    header-*              css        .column\r\n"
				+ "	    hero                  css        .hero__container\r\n"
				+ "	    float                css         .float__right\r\n"
				+ "	    img-*                  css       img\r\n"
				+ "	    containerIMG          xpath      ContainerDesRechtenBildes\r\n"
				+ "	    imgRight             xpath       rechtesBild\r\n" + "	    \r\n" + "	= Main section =\r\n"
				+ "	    header-1:\r\n" + "	         aligned horizontally all header-2\r\n"
				+ "	         aligned horizontally all header-3\r\n" + "	         aligned horizontally top header-4\r\n"
				+ "	         below hero\r\n" + "	    \r\n" + "\r\n" + "	    hero:\r\n"
				+ "	         width 100% of screen/width\r\n" + "	         height 300px\r\n" + "\r\n" + "\r\n"
				+ "	#Floats testen   \r\n" + "	    float:\r\n" + "	         inside header-2 0 px right  \r\n" + "\r\n"
				+ "	         \r\n" + "\r\n" + "	    @forEach [img-*] as IMG\r\n" + "	        ${IMG}:\r\n"
				+ "	            width widthIMG\r\n" + "                height heightIMG \r\n" + "	    \r\n"
				+ "	    imgRight:\r\n" + "	        inside containerIMG 0px right  ";

		BufferedReader reader = null;

		FileWriter writer = null;

		try {
			reader = new BufferedReader(new FileReader(fileToBeModified));

			// Reading all the lines of input text file into oldContent

			String line = reader.readLine();

			while (line != null) {
				oldContent = oldContent + line + System.lineSeparator();

				line = reader.readLine();
			}

			// Replacing oldString with newString in the oldContent

			String newContent = oldContent.replaceAll("ContainerDesRechtenBildes", xPathContainerRechtesBild)
					.replaceAll("rechtesBild", xPathImageRightFloat)
					.replaceAll("widthIMG", Integer.toString(width) + "px")
					.replaceAll("heightIMG", Integer.toString(height) + "px");

			// Rewriting the input text file with newContent

			writer = new FileWriter(fileToBeModified);

			writer.write(newContent);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Closing the resources

				reader.close();

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void deleteFile(Path url) throws IOException {

		try {
			Files.deleteIfExists(url);
		} catch (NoSuchFileException e) {
			System.out.println("No such file/directory exists");
		} catch (DirectoryNotEmptyException e) {
			System.out.println("Directory is not empty.");
		} catch (IOException e) {
			System.out.println("Invalid permissions.");
		}

		System.out.println("Deletion successful.");
	}

	public static void main(String[] args) throws IOException {
		deleteFile(Paths.get(
				"C:\\Users\\Bachelorarbeit\\eclipse-workspace\\maven-projektarbeit\\src\\test\\resources\\specs\\Abgabe 1.gspec"));
	}
}