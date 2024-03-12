package Automation;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FunctionalTesting {
	WebDriver driver;

	@Test
	public void functinalTest() throws InterruptedException, IOException{
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://demo.dealsdray.com/");
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
		driver.findElement(By.name("username")).sendKeys("prexo.mis@dealsdray.com");
		driver.findElement(By.name("password")).sendKeys("prexo.mis@dealsdray.com");
		driver.findElement(By.xpath("//button[text()='Login']")).submit();
		
		driver.findElement(By.xpath("//span[text()='Order']")).click();
		driver.findElement(By.xpath("//span[text()='Orders']")).click();
		driver.findElement(By.xpath("//button[text()='Add Bulk Orders']")).click();
		
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys("C:\\Users\\Dell\\Downloads\\demo-data.xlsx");
		driver.findElement(By.xpath("//button[text()='Import']")).click();
		
		driver.findElement(By.xpath("//button[text()='Validate Data']")).click();
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
		
		WebElement table = driver.findElement(By.xpath("//table[contains(@class,'MuiTable-root')]"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView()", table);
		Thread.sleep(2000);
		
		File src = table.getScreenshotAs(OutputType.FILE);
		File dest = new File("./FunctionalTesting/picture.png");
		FileUtils.copyFile(src, dest);
		Thread.sleep(3000);
		driver.quit();
	}
}