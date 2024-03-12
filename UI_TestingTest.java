package Automation;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class UI_TestingTest
{
	WebDriver driver ;
	@Parameters("BROWSER")
	
	@BeforeMethod
	public void browserSetups(String browser)
	{
		 
			switch (browser) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--remote-allow-origins=*");
				options.addArguments("--disable-notifications");
				driver = new ChromeDriver(options);
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				break;
			case "edge":
				WebDriverManager.safaridriver().setup();
				driver = new EdgeDriver();
				break;
			default:
				System.out.println("Invalid browser data");
				break;
			}
		System.out.println("BROWSER SETUP SUCESSFULL");
	}
		
	
	@Test()
	public void UITesting() throws IOException, InterruptedException 
	{
		
		driver.manage().window().maximize();
		driver.get("https://www.getcalley.com/page-sitemap.xml");
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		
		Set<String> urls = new HashSet<>();
		urls.add("https://www.getcalley.com/blog/");
		urls.add("https://www.getcalley.com/");
		urls.add("https://www.getcalley.com/free-trial-calley-teams-plan-v2/");
		urls.add("https://www.getcalley.com/free-trial-calley-pro-v2/");
		urls.add("https://www.getcalley.com/why-automatic-call-dialer-software/");

		Set<String> resolutions = new HashSet<>();
		resolutions.add("1920×1080");
		resolutions.add("1366×768");
		resolutions.add("1536×864");
		int i = 1;
		
		for (String url : urls)
		{
			for (String res : resolutions) 
			{
				String[] str = res.split("×");
				int x = Integer.parseInt(str[0]);
				int y = Integer.parseInt(str[1]);
				Dimension dim = new Dimension(x, y);
				driver.manage().window().setSize(dim);

				driver.get(url);
				File file = new File( "./Screenshot"+cap.getBrowserName()+"/"+cap.getBrowserName()+"_url"+i+"_"+res + "_" + getCurrentTime() + ".png");
				Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
						.takeScreenshot(driver);
				ImageIO.write(screenshot.getImage(), "PNG", file);
				
//				Thread.sleep(2000);
			}
			i++;
		}
		System.out.println("TEST EXECUTED SUCESSFUL");
	}
	
	@AfterMethod
	public void closeBroswer() 
	{
		System.out.println("Closed BROWSER SUCESSFUL");
		driver.quit();
		
	}
	public static String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_hh_mm_sss");
		return sdf.format(date);
	}

}


