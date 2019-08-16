package praktischeArbeiten;

import static org.junit.Assert.*;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;

import Texteditor.ReplaceText;

public class WS1819 {
	private WebDriver driver;

	@Before
	public void setUp() throws IOException {

		String pathDriver = "chromedriver.exe";
		String pathPage = "C:\\Users\\Bachelorarbeit\\git\\Projektarbeit\\maven-projektarbeit\\src\\test\\resources\\WS1819\\Projektdateien\\ws1819.html";

		System.setProperty("webdriver.chrome.driver", pathDriver);

		// Erstellen eines neuen Drivers
		driver = new ChromeDriver();
		// Größe des Drivers festlegen
		driver.manage().window().setSize(new Dimension(1920, 1080));
		// index.html
		driver.get(pathPage);

	}

	@Test
	public void homePageLayoutTest() throws IOException {

		String gspecLocation = "C:\\Users\\Bachelorarbeit\\git\\Projektarbeit\\maven-projektarbeit\\src\\test\\resources\\WS1819\\Projektdateien\\ws1819.gspec";

		// Erstellen eines LayoutReport Objektes
		// Die checkLayout-Funktion überprüft das Layout und gibt ein
		// Layoutreport-Objekt zurück

		LayoutReport layoutReport = Galen.checkLayout(driver, gspecLocation, Arrays.asList("desktop"));

		// Erstellt eine Liste mit GalenTestInfos
		List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();

		// Erstellen eines GalenTestInfo-Objektes
		GalenTestInfo test = GalenTestInfo.fromString("ws1819 layout");

		// // Holt den Layoutreport und ordnet es einem Test Objekt zu
		test.getReport().layout(layoutReport, "check ws1819 layout");

		// Fügt ein Objekt zur Testliste hinzu
		tests.add(test);

		// Erstellen eines htmlReportbuilderObjektes
		HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();

		// Erstellt einen Bericht(report) im /ws1819target Ordner
		htmlReportBuilder.build(tests, "ws1819target");

		// Wenn Fehler im Test auftreten, schlägt dieser fehl
		if (layoutReport.errors() > 0) {
			Assert.fail("Der Layouttest ist fehlgeschlagen");
		}
		openFile(true);
	}

	public void openFile(boolean oeffnen) throws IOException {
		if (oeffnen) {
			File datei = new File(
					"C:\\Users\\Bachelorarbeit\\git\\Projektarbeit\\maven-projektarbeit\\ws1819target\\report.html");

			// Checkt ob der Desktop unterstützt wird
			if (!Desktop.isDesktopSupported()) {
				System.out.println("Desktop is not supported");
				return;
			}

			Desktop desktop = Desktop.getDesktop();
			if (datei.exists())
				desktop.open(datei);

		} else {
			return;
		}

	}

	@After
	public void tearDown() {
		driver.quit();
	}

}
