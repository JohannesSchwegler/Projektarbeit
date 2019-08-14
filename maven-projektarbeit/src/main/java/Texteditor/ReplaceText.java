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
				"C:\\Users\\Bachelorarbeit\\git\\Projektarbeit\\maven-projektarbeit\\src\\test\\resources\\specs\\"
						+ fileName + ".gspec");

		// Create the file
		if (file.createNewFile()) {
			System.out.println("File is created!");
		} else {
			System.out.println("File already exists.");
			return;
		}

		File fileToBeModified = new File(
				"C:\\Users\\Bachelorarbeit\\git\\Projektarbeit\\maven-projektarbeit\\src\\test\\resources\\specs\\"
						+ fileName + ".gspec");

		String oldContent = "@objects\r\n" + 
				"    body1                 css         body\r\n" + 
				"    header-*             css        .column\r\n" + 
				"    hero                 css        .hero__container\r\n" + 
				"    float                css         .float__right\r\n" + 
				"    img-*                css         img\r\n" + 
				"    containerIMG         xpath       ContainerDesRechtenBildes\r\n" + 
				"    imgRight             xpath       rechtesBild\r\n" + 
				"    aside1               xpath       /html/body/aside\r\n" + 
				"    \r\n" + 
				"= Main section =\r\n" + 
				"    header-1:\r\n" + 
				"         aligned horizontally all header-2\r\n" + 
				"         aligned horizontally all header-3\r\n" + 
				"         aligned horizontally top header-4\r\n" + 
				"         below hero\r\n" + 
				"    \r\n" + 
				"\r\n" + 
				"    hero:\r\n" + 
				"         width 100% of screen/width\r\n" + 
				"         height 300px\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"#Floats testen   \r\n" + 
				"    float:\r\n" + 
				"         inside header-2 0 px right  \r\n" + 
				"\r\n" + 
				"         \r\n" + 
				"\r\n" + 
				"    @forEach [img-*] as IMG\r\n" + 
				"        ${IMG}:\r\n" + 
				"            width widthIMG \r\n" + 
				"    \r\n" + 
				"    imgRight:\r\n" + 
				"        inside containerIMG 0px right  \r\n" + 
				"        \r\n" + 
				"    aside1:\r\n" + 
				"       inside body1 90% top/viewport    ";

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
			System.out.println(newContent);
			// Rewriting the input text file with newContent

			// @objects
			// body css body
			// header-* css .column
			// hero css .hero__container
			// float css .float__right
			// img-* css img
			// containerIMG xpath ContainerDesRechtenBildes
			// imgRight xpath rechtesBild
			// aside xpath /html/body/aside
			//
			// =Main section=
			// header-1:
			// aligned horizontally all header-2
			// aligned horizontally all header-3
			// aligned horizontally top header-4
			// below hero
			//
			//
			// hero:
			// width 100% of screen/width
			// height 300px
			//
			//
			// #Floats testen
			// float:
			// inside header-2 0 px right
			//
			//
			//
			// @forEach [img-*] as IMG
			// ${IMG}:
			// width widthIMG
			// height heightIMG
			//
			// imgRight:
			// inside containerIMG 0px right
			//
			// aside:
			// inside body 90% top

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
				"C:\\Users\\Bachelorarbeit\\git\\Projektarbeit\\maven-projektarbeit\\src\\test\\resources\\specs\\Abgabe1.gspec"));

		ReplaceText.createFile("Abgabe1", "1", "23", 23, 23);
	}
}