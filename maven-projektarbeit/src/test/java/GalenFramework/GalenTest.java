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
import java.util.ArrayList;
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
		driver.get(
				"C:\\Users\\Bachelorarbeit\\git\\Projektarbeit\\maven-projektarbeit\\src\\test\\resources\\WS1819\\Projektdateien\\ws1819.html");
		generierungSpecsDatei();

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

	public String CSS(WebElement element) {
		final String JS_BUILD_CSS_SELECTOR = "for(var e=arguments[0],n=[],i=function(e,n){if(!e||!n)return 0;f"
				+ "or(var i=0,a=e.length;a>i;i++)if(-1==n.indexOf(e[i]))return 0;re"
				+ "turn 1};e&&1==e.nodeType&&'HTML'!=e.nodeName;e=e.parentNode){if("
				+ "e.id){n.unshift('#'+e.id);break}for(var a=1,r=1,o=e.localName,l="
				+ "e.className&&e.className.trim().split(/[\\s,]+/g),t=e.previousSi"
				+ "bling;t;t=t.previousSibling)10!=t.nodeType&&t.nodeName==e.nodeNa"
				+ "me&&(i(l,t.className)&&(l=null),r=0,++a);for(var t=e.nextSibling"
				+ ";t;t=t.nextSibling)t.nodeName==e.nodeName&&(i(l,t.className)&&(l"
				+ "=null),r=0);n.unshift(r?o:o+(l?'.'+l.join('.'):':nth-child('+a+'" + ")'))}return n.join(' > ');";
		;

		String selector = "";
		JavascriptExecutor js = (JavascriptExecutor) driver;

		selector = (String) js.executeScript(JS_BUILD_CSS_SELECTOR, element);
		return selector;
	}

	public String[] getImageWithRightFloat() {

		// Auffinden aller Bilder auf der Webseite
		List<WebElement> bilder = driver.findElements(By.cssSelector("img"));
		String xPath = "";
		String cssSelektor = "";

		// Schleife über alle Elemente, um ein Bild mit "float:right" zu finden
		for (WebElement webElement : bilder) {

			// Speichern der
			String bildRechterFloat = webElement.getCssValue("float");

			if (bildRechterFloat.equals("right")) {

				// Aufrufen der Funktion, die den absoluten xPath zurückliefert
				xPath = getAbsoluteXPath(webElement);

				// Auffinden des Elten-Elements -> könnte man sich theoretisch sparen, da das
				// Eltern-Element auch im absoluten Pfad vorhanden ist
				JavascriptExecutor js = (JavascriptExecutor) driver;
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				WebElement parentElement = (WebElement) executor.executeScript("return arguments[0].parentNode;",
						webElement);

				boolean rightParent = false;

				// Schleife, um den richtigen Container zu finden
				while (rightParent == false) {
					// Wenn das Element einer der erlaubten Inhaltselemente ist, wird der
					// CSS-Selektor für das Element gespeichert und die Schleife beendet
					System.out.println(parentElement.getCssValue("display") + parentElement.getTagName());
					if (parentElement.getTagName().contains("header") || parentElement.getTagName().contains("section")
							|| parentElement.getTagName().contains("article")
							|| parentElement.getTagName().contains("aside")
							|| parentElement.getTagName().contains("nav")
							|| parentElement.getTagName().contains("footer")) {

						cssSelektor = CSS(parentElement);

						rightParent = !false;
					} else {

						String path = getAbsoluteXPath(parentElement);
						WebElement element = driver.findElement(By.xpath(path));
						parentElement = (WebElement) executor.executeScript("return arguments[0].parentNode;", element);

					}

				}
				break;
			}
		}
		// Speichern der Strings in einem Array
		String[] childParent = new String[2];
		childParent[0] = xPath;
		childParent[1] = cssSelektor;
		return childParent;

	}

	public List<int[]> getMarginsOfElements() {
		List<int[]> marginList = new ArrayList<int[]>();
		marginList.add(getMarginOfElement("/html/body/header"));
		marginList.add(getMarginOfElement("/html/body/nav"));
		marginList.add(getMarginOfElement("/html/body/article"));
		marginList.add(getMarginOfElement("/html/body/aside"));
		marginList.add(getMarginOfElement("/html/body/footer"));

		return marginList;

	}

	/*
	 * Liefert die Außenabstände eines Elementes zurück
	 */
	public int[] getMarginOfElement(String xpath) {
		int[] margin = new int[4];

		WebElement element = driver.findElement(By.xpath(xpath));
		String marginTop = element.getCssValue("margin-top");

		margin[0] = (int) Double.parseDouble(marginTop.replaceAll("px", ""));

		String marginLeft = element.getCssValue("margin-left");
		margin[1] = (int) Double.parseDouble(marginLeft.replaceAll("px", ""));

		String marginBottom = element.getCssValue("margin-bottom");
		margin[2] = (int) Double.parseDouble(marginBottom.replaceAll("px", ""));

		String marginRight = element.getCssValue("margin-right");
		margin[3] = (int) Double.parseDouble(marginRight.replaceAll("px", ""));

		return margin;

	}

	public String getImageWithRightFloat2() {

		final String JS_BUILD_CSS_SELECTOR = "for(var e=arguments[0],n=[],i=function(e,n){if(!e||!n)return 0;f"
				+ "or(var i=0,a=e.length;a>i;i++)if(-1==n.indexOf(e[i]))return 0;re"
				+ "turn 1};e&&1==e.nodeType&&'HTML'!=e.nodeName;e=e.parentNode){if("
				+ "e.id){n.unshift('#'+e.id);break}for(var a=1,r=1,o=e.localName,l="
				+ "e.className&&e.className.trim().split(/[\\s,]+/g),t=e.previousSi"
				+ "bling;t;t=t.previousSibling)10!=t.nodeType&&t.nodeName==e.nodeNa"
				+ "me&&(i(l,t.className)&&(l=null),r=0,++a);for(var t=e.nextSibling"
				+ ";t;t=t.nextSibling)t.nodeName==e.nodeName&&(i(l,t.className)&&(l"
				+ "=null),r=0);n.unshift(r?o:o+(l?'.'+l.join('.'):':nth-child('+a+'" + ")'))}return n.join(' > ');";

		;
		JavascriptExecutor js = (JavascriptExecutor) driver;

		List<WebElement> elements = driver.findElements(By.cssSelector("img"));
		String xPath = "";
		String selector = "";
		for (WebElement webElement : elements) {

			String getItemWithFloat = webElement.getCssValue("float");

			if (getItemWithFloat.equals("right")) {

				// build the Css selector for the targeted element
				selector = (String) js.executeScript(JS_BUILD_CSS_SELECTOR, webElement);

				String test = webElement.getAttribute("xpath");
				// xPath = getElementXPath(driver, webElement);
				xPath = getAbsoluteXPath(webElement);

			}

		}

		// display the result
		System.out.println(xPath);

		System.out.println("Unique Css selector: " + selector);
		return selector;

	}

	public String getElementXPath(WebDriver driver, WebElement element) {
		String xpath = (String) ((JavascriptExecutor) driver)
				.executeScript("gPt=function(c){if(c.id!==''){return'id(\"'+c.id+'\")'}if(c===document.body)"
						+ "{return c.tagName}var a=0;var e=c.parentNode.childNodes;"
						+ "for(var b=0;b<e.length;b++){var d=e[b];if(d===c)"
						+ "{return gPt(c.parentNode)+'/'+c.tagName}if(d.nodeType===1&&d.tagName===c.tagName){a++}}};"
						+ "return gPt(arguments[0]).toLowerCase();", element);

		return "/html/" + xpath.replaceAll("\\(", "[").replaceAll("\\)", "]");

	}// *[@id="section2"]/a/img

	public String getAbsoluteXPath(WebElement element) {
		return (String) ((JavascriptExecutor) driver)
				.executeScript("function absoluteXPath(element) {" + "var comp, comps = [];" + "var parent = null;"
						+ "var xpath = '';" + "var getPos = function(element) {" + "var position = 1, curNode;"
						+ "if (element.nodeType == Node.ATTRIBUTE_NODE) {" + "return null;" + "}"
						+ "for (curNode = element.previousSibling; curNode; curNode = curNode.previousSibling) {"
						+ "if (curNode.nodeName == element.nodeName) {" + "++position;" + "}" + "}" + "return position;"
						+ "};" +

						"if (element instanceof Document) {" + "return '/';" + "}" +

						"for (; element && !(element instanceof Document); element = element.nodeType == Node.ATTRIBUTE_NODE ? element.ownerElement : element.parentNode) {"
						+ "comp = comps[comps.length] = {};" + "switch (element.nodeType) {" + "case Node.TEXT_NODE:"
						+ "comp.name = 'text()';" + "break;" + "case Node.ATTRIBUTE_NODE:"
						+ "comp.name = '@' + element.nodeName;" + "break;" + "case Node.PROCESSING_INSTRUCTION_NODE:"
						+ "comp.name = 'processing-instruction()';" + "break;" + "case Node.COMMENT_NODE:"
						+ "comp.name = 'comment()';" + "break;" + "case Node.ELEMENT_NODE:"
						+ "comp.name = element.nodeName;" + "break;" + "}" + "comp.position = getPos(element);" + "}" +

						"for (var i = comps.length - 1; i >= 0; i--) {" + "comp = comps[i];"
						+ "xpath += '/' + comp.name.toLowerCase();" + "if (comp.position !== null) {"
						+ "xpath += '[' + comp.position + ']';" + "}" + "}" +

						"return xpath;" +

						"} return absoluteXPath(arguments[0]);", element);
	}

	public void generierungSpecsDatei() throws IOException {
		String[] childAndContainer = getImageWithRightFloat();
		String xPathImageRightFloat = childAndContainer[0];
		String cssParentImageRightFloat = childAndContainer[1];

		List<int[]> margins = getMarginsOfElements();
		ReplaceText.createFile("Abgabe1", cssParentImageRightFloat, xPathImageRightFloat, margins);

	}

	@Test
	public void homePageLayoutTest() throws IOException {
		// Create a layoutReport object
		// checkLayout function checks the layout and returns a LayoutReport object

		LayoutReport layoutReport = Galen.checkLayout(driver,
				"C:\\Users\\Bachelorarbeit\\git\\Projektarbeit\\maven-projektarbeit\\src\\test\\resources\\WS1819\\Projektdateien\\Abgabe1.gspec",
				Arrays.asList("desktop"));

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