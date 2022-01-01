package selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumTest {

	private WebDriver driver;
	
	private static final String URL = "https://hk.sz.gov.cn/passInfo/confirmOrder?checkinDate=2022-01-08";
	
	@BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\ProgramFiles\\eclipse-workspace\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
	
	@Test
	public void testSZHK() throws InterruptedException {
		driver.get("https://hk.sz.gov.cn/userPage/login");
		Thread.sleep(1000);
		Select select_certificate = new Select(driver.findElement(By.id("select_certificate")));
		select_certificate.selectByValue("2");
		driver.findElement(By.id("input_idCardNo")).sendKeys("C67065784");
		driver.findElement(By.id("input_pwd")).sendKeys("98301004jH");
		Thread.sleep(5000);
		driver.findElement(By.id("btn_login")).click();
		Thread.sleep(10000);
		while(true) {
			driver.get("https://hk.sz.gov.cn/passInfo/confirmOrder?checkinDate=2022-01-07");
			WebElement element = driver.findElement(By.id("divNoData"));
			if (element != null) {
				System.out.println("text = " + element.getText());
				Thread.sleep(1000);
				continue;
			}
			else {
				break;
			}
		}
	}
    
    //@AfterEach
    public void tearDown() {
        driver.quit();
    }
}
