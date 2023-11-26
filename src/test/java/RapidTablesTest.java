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

    //Test Case 1
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

    //Test Case 2
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

    //Test Case 3
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

    //Test Case 4
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

    //Test Case 5
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

    //Test Case 6
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

    //Test Case 7
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

    //Test Case 7
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

    //Test Case 8
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

    //Test Case 9
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

    //Test Case 10
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

    //Test Case 11
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

    //Test Case 12
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

    //Test Case 13
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

    //Test Case 14
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

    //Test Case 15
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


    //Test Case 28 (Emily)
    @Test
    public void baseCaseTwoBinaryToHexadecimalTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Binary");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Hexadecimal");
        driver.findElement(By.id("x")).sendKeys("-1");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Hex number (1 digit)", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("-1", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Decimal number (1 digit)", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("-1", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));

    }

    // Test Case 29 (Emily)
    @Test
    public void baseCaseTwoOctalToHexadecimalTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Octal");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Hexadecimal");
        driver.findElement(By.id("x")).sendKeys("-1");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Hex number (1 digit)", driver.findElement(By.xpath("//*[@for='y']")).getText());
        assertEquals("-1", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Decimal number (1 digit)", driver.findElement(By.xpath("//*[@for='y2']")).getText());
        assertEquals("-1", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
    }

    // Test Case 30 (Emily
    @Test
    public void baseCaseTwoHexadecimalToHexadecimalTest() {
        //setup & execute
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Hexadecimal");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Hexadecimal");

        //verify
        assertEquals("Number Conversion", driver.getTitle());
    }

    // Test Case 31 (Emily)
    @Test
    public void baseCaseTwoTextToHexadecimalTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Text");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Hexadecimal");
        driver.findElement(By.id("txt")).sendKeys("-1");

        //execute
        driver.findElement(By.xpath("//*[@id='doc']/form/div[6]/button[1]")).click();

        //verify
        assertEquals("2D 31", driver.findElement(By.xpath("//*[@id='hex']")).getAttribute("value"));
    }

    // Test Case 32 (Emily)
    @Test
    public void baseCaseTwoDecimalToBinaryTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Decimal");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Binary");
        driver.findElement(By.id("x")).sendKeys("-1");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Binary number (1 digit)", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("-1", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Binary signed 2's complement (16 digits)", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("1111111111111111", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Hex number (1 digit)", driver.findElement(By.xpath("//*[@id='y5label']")).getText());
        assertEquals("-1", driver.findElement(By.xpath("//*[@id='y5']")).getAttribute("value"));

    }

    // Test Case 34 (Emily)
    @Test
    public void baseCaseTwoDecimalToOctalTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Decimal");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Octal");
        driver.findElement(By.id("x")).sendKeys("-1");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Octal number (1 digit)", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("-1", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Hex number (1 digit)", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("-1", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));

    }

    // Test Case 35 (Emily)
    @Test
    public void baseCaseTwoDecimalToTextTest() {
        //setup
        captcha();

        //execute
        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Decimal");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Text");

        //verify
        assertEquals("Number Conversion", driver.getTitle());
    }

    // Test Case 36 (Emily)
    @Test
    public void baseCaseTwoEmptyValueTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Decimal");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Hexadecimal");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Hex number", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Hex signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Binary number", driver.findElement(By.xpath("//*[@id='y5label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y5']")).getAttribute("value"));
        assertEquals("rgba(255, 240, 240, 1)", driver.findElement(By.xpath("//*[@id='x']")).getCssValue("background-color"));
    }

    // Test Case 37 (Emily)
    @Test
    public void baseCaseTwoZeroValueTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Decimal");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Hexadecimal");
        driver.findElement(By.id("x")).sendKeys("0");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Hex number (1 digit)", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("0", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Hex signed 2's complement (4 digits)", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("0000", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Binary number (1 digit)", driver.findElement(By.xpath("//*[@id='y5label']")).getText());
        assertEquals("0", driver.findElement(By.xpath("//*[@id='y5']")).getAttribute("value"));
    }

    // Test Case 38 (Emily)
    @Test
    public void baseCaseTwoPositiveValueTest() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Decimal");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Hexadecimal");
        driver.findElement(By.id("x")).sendKeys("1");

        //execute
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[1]")).click();

        //verify
        assertEquals("Hex number (1 digit)", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Hex signed 2's complement (4 digits)", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("0001", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Binary number (1 digit)", driver.findElement(By.xpath("//*[@id='y5label']")).getText());
        assertEquals("1", driver.findElement(By.xpath("//*[@id='y5']")).getAttribute("value"));
    }

    // Test Case 39 (Emily)
    @Test
    public void baseCaseTwoNoConvert() {
        //setup
        captcha();

        Select from = new Select(driver.findElement(By.id("unit1")));
        from.selectByVisibleText("Decimal");
        Select to = new Select(driver.findElement(By.id("unit2")));
        to.selectByVisibleText("Hexadecimal");
        driver.findElement(By.id("x")).sendKeys("-1");

        //verify
        assertEquals("Hex number", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Hex signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Binary number", driver.findElement(By.xpath("//*[@id='y5label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y5']")).getAttribute("value"));
    }

    // Test Case 40 (Emily)
    @Test
    public void baseCaseTwoResetIsClickedTest() {
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

        //execute again
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[2]")).click();

        //verify again
        assertEquals("", driver.findElement(By.id("x")).getText());
        assertEquals("Hex number", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Hex signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Binary number", driver.findElement(By.xpath("//*[@id='y5label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y5']")).getAttribute("value"));
    }

    // Test Case 41 (Emily)
    @Test
    public void baseCaseTwoSwapIsClickedTest() {
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

        //execute again
        driver.findElement(By.xpath("//*[@id='calcform']/div[3]/button[3]")).click();

        //verify again
        assertEquals("Hexadecimal to Decimal Converter", driver.getTitle());
        assertEquals("", driver.findElement(By.id("x")).getText());
        assertEquals("Decimal number", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Decimal from signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Binary number", driver.findElement(By.xpath("//*[@id='y3label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y3']")).getAttribute("value"));
    }

    // Test Case 42 (Emily)
    @Test
    public void baseCaseTwoDigitGroupingIsCheckedTest() {
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

        //execute again
        driver.findElement(By.xpath("//*[@id='group']")).click();

        //verify again
        assertEquals("Hex number (1 digit)", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("-1", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Hex signed 2's complement (4 digits)", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("FF FF", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Binary number (1 digit)", driver.findElement(By.xpath("//*[@id='y5label']")).getText());
        assertEquals("-1", driver.findElement(By.xpath("//*[@id='y5']")).getAttribute("value"));
    }

    // Test Case 43 (Emily)
    @Test
    public void baseCaseTwoToChoiceToFromChoiceIsClickedTest() {
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

        //execute again
        driver.findElement(By.xpath("//*[@id='p1']/a")).click();

        //verify again
        assertEquals("Hexadecimal to Decimal Converter", driver.getTitle());
        assertEquals("", driver.findElement(By.id("x")).getText());
        assertEquals("Decimal number", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Decimal from signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Binary number", driver.findElement(By.xpath("//*[@id='y3label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y3']")).getAttribute("value"));
    }

    // Test Case 44 (Emily)
    @Test
    public void baseCaseTwoSeeAlsoButtonIsClickedTest() {
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

        //execute again
        driver.findElement(By.xpath("//*[@id='doc']/ul/li[10]/a")).click();

        //verify again
        assertEquals("Number Conversion", driver.getTitle());
    }

    // Test Case 45 (Emily)
    @Test
    public void baseCaseTwoImprovePageTextBoxNotEmptyTest() {
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

        //execute again
        driver.findElement(By.id("fdbkarea")).sendKeys("hi!");
        driver.findElement(By.xpath("//*[@id='fdbkform']/button")).click();

        //verify again
        assertEquals("hi!", driver.findElement(By.id("fdbkarea")).getAttribute("value"));
    }

    // Test Case 46 (Emily)
    @Test
    public void baseCaseTwoSubmitFeedbackIsClickedTest() {
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

        //execute again
        driver.findElement(By.xpath("//*[@id='fdbkform']/button")).click();

        //verify again
        assertEquals("true", driver.findElement(By.id("fdbkarea")).getAttribute("required"));
    }

    // Test Case 47 (Emily)
    @Test
    public void baseCaseTwoNumberConversionLinkIsClickedTest() {
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

        //execute again
        driver.findElement(By.xpath("//*[@id='rcol']/ul[1]/li[36]/a")).click();

        //verify again
        assertEquals("Scientific Notation Converter", driver.getTitle());
    }

    // Test Case 48 (Emily)
    @Test
    public void baseCaseTwoRapidsTablesSectionLinkIsClickedTest() {
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

        //execute again
        driver.findElement(By.xpath("//*[@id='rcol']/ul[2]/li[3]/a")).click();

        //verify again
        assertEquals("About - RapidTables.com", driver.getTitle());
    }

    // Test Case 49 (Emily)
    @Test
    public void baseCaseTwoFooterLinkIsClickedTest() {
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

        //execute again
        driver.findElement(By.xpath("//*[@id='footer']/p/a[3]")).click();

        //verify again
        assertEquals("Terms of Use", driver.getTitle());
    }

    // Test Case 50 (Emily)
    @Test
    public void baseCaseTwoBackButtonIsClickedTest() {
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

        //execute again
        driver.navigate().back();

        //verify again
        assertEquals("Number Conversion", driver.getTitle());
    }

    // Test Case 51 (Emily)
    @Test
    public void baseCaseTwoForwardButtonIsClickedTest() {
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

        //execute again
        driver.navigate().back();
        driver.navigate().forward();

        //verify again
        assertEquals("Decimal to Hexadecimal Converter", driver.getTitle());
        assertEquals("", driver.findElement(By.id("x")).getText());
        assertEquals("Hex number", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Hex signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Binary number", driver.findElement(By.xpath("//*[@id='y5label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y5']")).getAttribute("value"));
    }

    // Test Case 52 (Emily)
    @Test
    public void baseCaseTwoRefreshButtonIsClickedTest() {
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

        //execute again
        driver.navigate().refresh();

        //verify again
        assertEquals("", driver.findElement(By.id("x")).getText());
        assertEquals("Hex number", driver.findElement(By.xpath("//*[@id='ylabel']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y']")).getAttribute("value"));
        assertEquals("Hex signed 2's complement", driver.findElement(By.xpath("//*[@id='y2label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y2']")).getAttribute("value"));
        assertEquals("Binary number", driver.findElement(By.xpath("//*[@id='y5label']")).getText());
        assertEquals("", driver.findElement(By.xpath("//*[@id='y5']")).getAttribute("value"));

    }

}
