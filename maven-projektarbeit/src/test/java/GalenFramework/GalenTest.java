package GalenFramework;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import com.google.common.io.Files;

import Texteditor.ReplaceText;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Johannes Schwegler.
 */
public class GalenTest {
	private WebDriver driver;

	@Before
	public void setUp() throws IOException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Bachelorarbeit\\git\\Projektarbeit\\maven-projektarbeit\\chromedriver.exe");
		// Create a Chrome Driver
		driver = new ChromeDriver();
		// Set the browser size for desktop
		driver.manage().window().setSize(new Dimension(1920, 1080));
		// Go to swtestacademy.com
		driver.get("C:\\Users\\Bachelorarbeit\\Desktop\\Grid\\index.html");
		aufgabe8();
		getImageWithRightFloat();
	}

	public int[] getImageWidthAndHeight() {
		int[] imgWidthHeight = new int[2];

		WebElement img = driver.findElement(By.cssSelector("img"));

		// Get width of element.

		int ImageWidth = img.getSize().getWidth();
		imgWidthHeight[0] = ImageWidth;
		// System.out.println("Image width Is " + ImageWidth + " pixels");

		// Get height of element.
		int ImageHeight = img.getSize().getHeight();
		imgWidthHeight[1] = ImageHeight;
		// System.out.println("Image height Is " + ImageHeight + " pixels");
		return imgWidthHeight;

	}

	public String returnParentXPath(String xpath) {
		char[] charArray = xpath.toCharArray();
		for (int i = charArray.length - 1; i < charArray.length; i--) {
			if (charArray[i] == '/') {
				charArray[i] = '0';

				break;

			} else {
				charArray[i] = '0';
			}

		}
		String parentXpath = "";
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] != '0') {

				String s = String.valueOf((charArray[i]));
				parentXpath += s;
			}

		}
		return parentXpath;
	}

	public String getImageWithRightFloat() {
		List<WebElement> elements = driver.findElements(By.cssSelector("img"));
		String xPath = "";

		for (WebElement webElement : elements) {

			String getItemWithFloat = webElement.getCssValue("float");

			if (getItemWithFloat.equals("right")) {
				String test = webElement.getAttribute("xpath");
				xPath = getElementXPath(driver, webElement);

			}

		}
		return xPath;

	}

	public void aufgabe8() throws IOException {

		String xPathImageRightFloat = getImageWithRightFloat();
		System.out.println(xPathImageRightFloat);
		String xPathParentImageRightFloat = returnParentXPath(xPathImageRightFloat);
		System.out.println(xPathParentImageRightFloat);

		int[] imgWidthHeight = getImageWidthAndHeight();
		System.out.println(Arrays.toString(imgWidthHeight));

		ReplaceText.createFile("Abgabe1", xPathParentImageRightFloat, xPathImageRightFloat, imgWidthHeight[0],
				imgWidthHeight[1]);

	}

	public String getElementXPath(WebDriver driver, WebElement element) {
		String xpath = (String) ((JavascriptExecutor) driver).executeScript(
				"gPt=function(c){if(c.id!==''){return'id(\"'+c.id+'\")'}if(c===document.body){return c.tagName}var a=0;var e=c.parentNode.childNodes;for(var b=0;b<e.length;b++){var d=e[b];if(d===c){return gPt(c.parentNode)+'/'+c.tagName+'['+(a+1)+']'}if(d.nodeType===1&&d.tagName===c.tagName){a++}}};return gPt(arguments[0]).toLowerCase();",
				element);

		return "/html/" + xpath;

	}

	@Test
	public void homePageLayoutTest() throws IOException {
		// Create a layoutReport object
		// checkLayout function checks the layout and returns a LayoutReport object
		System.out.println("heir");
		LayoutReport layoutReport = Galen.checkLayout(driver, "C:\\Users\\Bachelorarbeit\\git\\Projektarbeit\\maven-projektarbeit\\src\\test\\resources\\specs\\grid.gspec", Arrays.asList("desktop"));

		// Create a tests list
		List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();

		// Create a GalenTestInfo object
		GalenTestInfo test = GalenTestInfo.fromString("homepage layout");

		// Get layoutReport and assign to test object
		test.getReport().layout(layoutReport, "check homepage layout");

		// Add test object to the tests list
		tests.add(test);

		// Create a htmlReportBuilder object
		HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();

		// Create a report under /target folder based on tests list
		htmlReportBuilder.build(tests, "target");

		// If layoutReport has errors Assert Fail
		if (layoutReport.errors() > 0) {
			Assert.fail("Der Layouttest ist fehlgeschlagen");
		}
	}

	@After
	public void tearDown() {
		driver.quit();
	}

}