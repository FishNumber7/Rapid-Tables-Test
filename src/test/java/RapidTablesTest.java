import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RapidTablesTest {

    private static final String url = "https://www.rapidtables.com/convert/number";
    private WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = WebDriverManager.chromedriver().create();
        driver.manage().window().setSize(new Dimension(1920,1080));
        driver.get(url);
    }

    @AfterEach
    public void teardown() {
        driver.close();
    }

    private void captcha() {
        //make sure captcha is bypassed if it shows up - sometimes it does, sometimes it doesn't
        Duration prevDuration = driver.manage().timeouts().getImplicitWaitTimeout();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        List<WebElement> captcha = driver.findElements(By.xpath("//*[@id='m2_bot_captcha']/div/span"));

        if (captcha.size() == 1) {
            captcha.get(0).click();
        }

        driver.manage().timeouts().implicitlyWait(prevDuration);
    }

    @Test
    public void baseCaseBinaryToDecimalTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Binary");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Decimal");
        driver.findElement(By.id("x")).sendKeys("1");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Decimal number (1 digit)", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Decimal from signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("N/A", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Hex number (1 digit)", driver.findElement(By.xpath("//*[@id='y3label']")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y3']")).getAttribute("value"));
    }

    @Test
    public void baseCaseDecimalToHexadecimalTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Decimal");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Hexadecimal");
        driver.findElement(By.id("x")).sendKeys("-1");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Hex number (1 digit)", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("-1", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Hex signed 2's complement (4 digits)", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("FFFF", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Binary number (1 digit)", driver.findElement(By.xpath("//*[@id='y5label']")).getText());
        assertEquals("-1", driver.findElement(By.xpath("//*[@id='y5']")).getAttribute("value"));
    }

    @Test
    public void baseCaseOneDecimalToDecimalTest() {
        //setup
        captcha();

        //execute
        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Decimal");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Decimal");

        //verify
        assertEquals("Number Conversion", driver.getTitle());
    }

    @Test
    public void baseCaseOneOctalToDecimalTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Octal");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Decimal");

        driver.findElement(By.id("x")).sendKeys("1");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Decimal number (1 digit)", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Hex number (1 digit)", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
    }

    @Test
    public void baseCaseOneHexadecimalToDecimalTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Hexadecimal");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Decimal");
        driver.findElement(By.id("x")).sendKeys("1");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Decimal number (1 digit)", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Decimal from signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("N/A", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Binary number (1 digit)", driver.findElement(By.xpath("//*[@id='y3label']")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y3']")).getAttribute("value"));
    }

    @Test
    public void baseCaseOneTextToDecimalTest() {
        //setup & execute
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Text");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Decimal");

        //verify
        assertEquals("Number Conversion", driver.getTitle());
   }

    @Test
    public void baseCaseOneBinaryToBinaryTest() {
        //setup & execute
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Binary");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Binary");

        //verify
        assertEquals("Number Conversion", driver.getTitle());
    }

    @Test
    public void baseCaseOneBinaryToOctalTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Binary");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Octal");
        driver.findElement(By.id("x")).sendKeys("1");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Octal number (1 digit)", driver.findElement(By.xpath("//*[@id='calcform']/div[4]/label")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Decimal number (1 digit)", driver.findElement(By.xpath("//*[@id='calcform']/div[5]/label")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
    }

    @Test
    public void baseCaseOneBinaryToHexadecimalTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Binary");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Hexadecimal");
        driver.findElement(By.id("x")).sendKeys("1");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Hex number (1 digit)", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Decimal number (1 digit)", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
    }

    @Test
    public void baseCaseOneBinaryToTextTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Binary");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Text");
        driver.findElement(By.xpath("//*[@id='bin']")).sendKeys("00101111");

        //execute
        driver.findElement(By.xpath("//*[@id='doc']/form/div[5]/button[1]")).click();

        //verify
        assertEquals("/", driver.findElement(By.xpath("//*[@id='txt']")).getAttribute("value"));
    }

    @Test
    public void baseCaseOneEmptyValueTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Binary");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Decimal");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Decimal number", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Decimal from signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Hex number", driver.findElement(By.xpath("//*[@id='y3label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y3']")).getAttribute("value"));
        assertEquals("rgba(255, 240, 240, 1)", driver.findElement(By.xpath("//*[@id='x']")).getCssValue("background-color"));
    }

    @Test
    public void baseCaseOneNegativeValueTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Binary");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Decimal");
        driver.findElement(By.id("x")).sendKeys("-1");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Decimal number (1 digit)", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("-1", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Decimal from signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("N/A", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Hex number (1 digit)", driver.findElement(By.xpath("//*[@id='y3label']")).getText());
        assertEquals("-1", driver.findElement(By.xpath("//*[@id='y3']")).getAttribute("value"));
    }

    @Test
    public void baseCaseOneZeroValueTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Binary");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Decimal");
        driver.findElement(By.id("x")).sendKeys("0");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Decimal number (1 digit)", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("0", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Decimal from signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("N/A", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Hex number (1 digit)", driver.findElement(By.xpath("//*[@id='y3label']")).getText());
        assertEquals("0", driver.findElement(By.xpath("//*[@id='y3']")).getAttribute("value"));
    }

    @Test
    public void baseCaseOneNoConvert() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Binary");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Decimal");
        driver.findElement(By.id("x")).sendKeys("1");

        //verify
        assertEquals("Decimal number", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Decimal from signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Hex number", driver.findElement(By.xpath("//*[@id='y3label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y3']")).getAttribute("value"));
    }

    @Test
    public void baseCaseOneResetIsClickedTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Binary");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Decimal");
        driver.findElement(By.id("x")).sendKeys("1");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Decimal number (1 digit)", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Decimal from signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("N/A", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Hex number (1 digit)", driver.findElement(By.xpath("//*[@id='y3label']")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y3']")).getAttribute("value"));

        //execute again
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[2]")).click();

        //verify again
        assertEquals("", driver.findElement(By.id("x")).getText());
        assertEquals("Decimal number", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Decimal from signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Hex number", driver.findElement(By.xpath("//*[@id='y3label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y3']")).getAttribute("value"));
    }

    @Test
    public void baseCaseOneSwapIsClickedTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Binary");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Decimal");
        driver.findElement(By.id("x")).sendKeys("1");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Decimal number (1 digit)", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Decimal from signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("N/A", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Hex number (1 digit)", driver.findElement(By.xpath("//*[@id='y3label']")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y3']")).getAttribute("value"));

        //execute again
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[3]")).click();

        //verify again
        assertEquals("Decimal to Binary Converter", driver.getTitle());
        assertEquals("", driver.findElement(By.id("x")).getText());
        assertEquals("Binary number", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Binary signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Hex number", driver.findElement(By.xpath("//*[@id='y5label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y5']")).getAttribute("value"));
    }
}
