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
import java.util.List;

import GalenFramework.GalenTest;

public class ReplaceText {

	public static void createFile(String fileName, String cssContainerRechtesBild, String xPathImageRightFloat,
			List<int[]> margins) throws IOException {

		File file = new File(
				"C:\\Users\\Bachelorarbeit\\git\\Projektarbeit\\maven-projektarbeit\\src\\test\\resources\\WS1819\\Projektdateien\\"
						+ fileName + ".gspec");

		// Create the file

		int[] marginHeader = margins.get(0);
		int[] marginNavigation = margins.get(1);
		int[] marginArticle = margins.get(2);
		int[] marginAside = margins.get(3);
		int[] marginFooter = margins.get(4);

		if (file.createNewFile()) {
			System.out.println("File is created!");
		} else {
			System.out.println("File already exists.");
			return;
		}

		File fileToBeModified = new File(
				"C:\\Users\\Bachelorarbeit\\git\\Projektarbeit\\maven-projektarbeit\\src\\test\\resources\\WS1819\\Projektdateien\\"
						+ fileName + ".gspec");

		String oldContent = "@objects\r\n" + 
				"    body          xpath      /html/body\r\n" + 
				"    header        xpath      /html/body/header\r\n" + 
				"    nav           xpath      /html/body/nav\r\n" + 
				"    article       xpath      /html/body/article\r\n" + 
				"    aside         xpath      /html/body/aside\r\n" + 
				"    footer        xpath      /html/body/footer\r\n" + 
				"    img           css        img\r\n" + 
				"    Container     css        ContainerDesRechtenBildes\r\n" + 
				"    imgRight      xpath      RechtesBild\r\n" + 
				"    li-*          css        .navigation li\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"= Main section =\r\n" + 
				"     \r\n" + 
				"    header:\r\n" + 
				"       width <<${viewport.width() - marginHeader} px>>    \r\n" + 
				"       \r\n" + 
				"        \r\n" + 
				"    nav:\r\n" + 
				"       below header\r\n" + 
				"       inside body <<marginNavigationLeft>>px left\r\n" + 
				"       width ~ 32% of viewport/width\r\n" + 
				"\r\n" + 
				"    article:\r\n" + 
				"       below nav\r\n" + 
				"       inside body <<marginArticleLeft>>px left\r\n" + 
				"       width ~ 65% of viewport/width\r\n" + 
				"\r\n" + 
				"    aside:\r\n" + 
				"       below nav\r\n" + 
				"       inside body <<marginAsideRight>>px right \r\n" + 
				"       width ~ 32% of viewport/width  \r\n" + 
				"\r\n" + 
				"     \r\n" + 
				"    footer:\r\n" + 
				"       width <<${viewport.width() - marginFooter} px>>\r\n" + 
				"\r\n" + 
				"    img:\r\n" + 
				"       width 450px\r\n" + 
				"       height 450px  \r\n" + 
				"\r\n" + 
				"    imgRight:\r\n" + 
				"       inside Container 0px right  \r\n" + 
				"\r\n" + 
				"    @for [1-5] as index\r\n" + 
				"        li-1:\r\n" + 
				"            aligned horizontally all li-${index + 1}   \r\n" + 
				"";

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
System.out.println();
			String newContent = oldContent.replaceAll("ContainerDesRechtenBildes", cssContainerRechtesBild)
					.replaceAll("RechtesBild", xPathImageRightFloat)
					.replaceAll("marginHeader", Integer.toString(marginHeader[1] + marginHeader[3]))
					.replaceAll("marginNavigationLeft", Integer.toString(marginNavigation[1]))
					.replaceAll("marginArticleLeft", Integer.toString(marginArticle[1]))
					.replaceAll("marginAsideRight", Integer.toString(marginAside[3]))
					.replaceAll("marginFooter", Integer.toString(marginFooter[1] + marginFooter[3]))
					.replaceAll("<<", "").replaceAll(">>", "");

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
				"C:\\Users\\Bachelorarbeit\\git\\Projektarbeit\\maven-projektarbeit\\src\\test\\resources\\WS1819\\Projektdateien\\Abgabe1.gspec"));

	}
}