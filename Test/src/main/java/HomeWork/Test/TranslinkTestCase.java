package HomeWork.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TranslinkTestCase 
{
	public static WebDriver driver;
	
    public static void main( String[] args ) throws InterruptedException
    {
    	TranslinkTestCase tt = new TranslinkTestCase();
    	tt.testCase1();

    }
    public void testCase1() throws InterruptedException
    {
		//option 1 - set property using chrome driver executable
		//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\lib\\chromedriver.exe");
		//option 2 - set chrome driver using WebDriver Manager utility
		WebDriverManager.chromedriver().setup();
		Map<String,Object> preferences = new HashMap<String, Object>();
		preferences.put("credentials_enable_service", false);
		preferences.put("profile.password_manager_enabled", false);
		preferences.put("safebrowsing.enabled", false);
		preferences.put("javascript.enabled", "true");
		ChromeOptions ch_options = new ChromeOptions();
		ch_options.setExperimentalOption("prefs", preferences);
		ch_options.addArguments("disable-infobars");
		ch_options.addArguments("safebrowsing-disable-download-protection");
		ch_options.addArguments("--disable-notifications");
		DesiredCapabilities chrome_capabilities = DesiredCapabilities.chrome();
		chrome_capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		chrome_capabilities.setCapability(ChromeOptions.CAPABILITY, ch_options);
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		//launch Translink
		driver.get("https://www.translink.ca/");
		driver.findElement(By.xpath("//button[text()='Next Bus']")).click();
		//search for next bus with 99
		driver.findElement(By.xpath("//*[@id='NextBusSearchTerm']")).sendKeys("99");
		//click on find my next bus
		driver.findElement(By.xpath("//*[text()='Find my next bus']")).click();
		//click on add fav icon

		try{
		driver.findElement(By.xpath("//*[@class='AddDelFav flexContainer flexColumn horizontallyCenteredContent bottomAlignedContent']")).click();
		WebElement TextAreaElement = driver.findElement(By.xpath("//textarea[(@name='newFavourite') and @aria-invalid='false']"));
		TextAreaElement.clear();
		TextAreaElement.sendKeys("Translink Auto Homework");
		//click on add to favorite button
		driver.findElement(By.xpath("//button[(text()='Add to Favourites') and parent::div[parent::section]]")).click();
		}
		catch(Exception e)
		{
			driver.findElement(By.xpath("//textarea[(@name='newFavourite') and @aria-invalid='true']")).sendKeys("Translink Auto Homework");
			driver.findElement(By.xpath("//button[(text()='Add to Favourites') and parent::div[parent::section]]")).click();
		}
		//click on my fav icon
		driver.findElement(By.xpath("//a[@href='/next-bus/favourites']")).click();
		//Validate the added favorite
		WebElement favtLinkElement = driver.findElement(By.xpath("//*[@class=' verticallyCenteredItem']"));
		String addedFavt= favtLinkElement.getText();
		System.out.println(addedFavt);
		Assert.assertEquals(addedFavt, "Translink Auto Homework");
		System.out.println("'Translink Auto Homework' is present");
		//click on the favorite link
		favtLinkElement.click();
		//Validate “99 Commercial-Broadway / UBC (B-Line)” is displayed on page
		driver.switchTo().frame(0);
		WebElement CommercialElement = driver.findElement(By.xpath("//*[contains(@href,'EAST')]"));
		String CommElementText= CommercialElement.getText();
		System.out.println(CommElementText);
		Assert.assertEquals(CommElementText, "To Comm'l-Bdway Stn / Boundary B-Line");
		System.out.println("'99 Commercial-Broadway / UBC (B-Line)' is present");
		//Click on To “Comm'l-Bdway Stn / Boundary B-Line”
		driver.findElement(By.xpath("//a[contains(text(),'Bdway Stn')]")).click();
		Thread.sleep(3000);
		//click on "UBC Exchange Bay 7"
		driver.findElement(By.xpath("//a[contains(text(),'UBC Exchange Bay 7')]")).click();
		// Validate “Stop # 61935” is displaying
		Thread.sleep(3000);
		WebElement stopNumElement= driver.findElement(By.xpath("//*[@class='stopNo']"));
		String StopNum= stopNumElement.getText();
		Assert.assertEquals(StopNum, "Stop # 61935");
		System.out.println("'Stop # 61935' is present");
		driver.quit();
    }
}
