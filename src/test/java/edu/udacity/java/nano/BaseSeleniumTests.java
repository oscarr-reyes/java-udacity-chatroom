package edu.udacity.java.nano;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public abstract class BaseSeleniumTests {
	@Autowired
	private Environment environment;

	private static final String DRIVER_BINARY = "chromedriver";
	protected final String HOST_ENDPOINT = "http://localhost:8080";
	protected WebDriver webDriver;

	private String getDriverDirectory() {
		ClassLoader classLoader = getClass().getClassLoader();
		URL url = classLoader.getResource(DRIVER_BINARY);

		return Objects.requireNonNull(url).getFile();
	}

	@Before
	public void setUp() {
		String driverFile = environment.getProperty("selenium.webdriver");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeDriverService service = new ChromeDriverService.Builder()
			.usingDriverExecutable(new File(driverFile))
			.build();

		ChromeOptions options = new ChromeOptions();

		options.addArguments("--no-sandbox");
		options.addArguments("--headless");
		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("start-minimized");
		options.addArguments("disable-infobars");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-gpu");
		options.addArguments("--disable-dev-shm-usage");
		options.merge(capabilities);

		this.webDriver = new ChromeDriver(service, options);
	}

	@After
	public void tearDown() {
		if (this.webDriver != null) {
			this.webDriver.close();
		}
	}
}
