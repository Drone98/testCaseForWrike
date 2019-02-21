package tests;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.FormPage;
import pages.HomePage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FirstTest {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Учёба\\TestCases\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    /**
     * Проверка: переход на новую страницу после нажатия на клавишу "Create my Wrike account".
     * Реализация: сравнение URL-адресов начальной страницы и загрузившейся после нажатия на клавишу страницей.
     */
    @Test
    public void check1MovingToTheNextPage() {
        System.out.println("test number 1");
        HomePage homePage = new HomePage(driver);
        String oldURL = homePage.getBaseURL();
        homePage.goToHomePage().goToCreateWrikeAccount().registerBusinessAccount();
        Assert.assertFalse(oldURL.equals(driver.getCurrentUrl()));
    }

    /**
     * Проверка: приняты ли ответы на небольшую анкету.
     * Реализация: появилась ли form с сообщением благодарности за заполненную анкету.
     */
    @Test
    public void check2AnswersAreSubmitted() {
        System.out.println("test number 2");
        FormPage formPage = new FormPage(driver);
        formPage.fillQASection();
        WebElement surveySuccessFormWebElement = driver.findElement(By.cssSelector("body > div.wg-layout.wg-layout--outline > main > div > div " +
                "> div.section.section-resend-main.section-resend-main-va.section-resend-main--survey > div " +
                "> div.wg-cell.wg-cell--md-6.wg-cell--lg-7 > div > div"));
        Assert.assertEquals(false, ExpectedConditions.invisibilityOf(surveySuccessFormWebElement).apply(driver));
    }

    /**
     * Проверка: проверить переотправку сообщения.
     * Что происходит: изначально есть объект form1 и объект button1, после нажатия на button поверх имеющихся 2 объектов
     * появляются объект form2 и объект button2, которых ранее не было. Я заметил только одно отличие между form1 и
     * form2 - в тексте второго обекта в середину добавляется слово "again", отличие button1 от button2 -
     * CSS значение непрозрачности.
     * Реализация: сравниваем непрозрачности button1, button2.
     */
    @Test
    public void check3ResendEmail() {
        System.out.println("test number 3");
        FormPage formPage = new FormPage(driver);
        WebElement buttonResendEmail = driver.findElement(By.cssSelector("body > div.wg-layout.wg-layout--outline > main " +
                "> div > div > div.section.section-resend-main.section-resend-main-va.section-resend-main--survey > div " +
                "> div.wg-cell.wg-cell--order-1.wg-cell--md-5.wg-cell--md-5.wg-cell--md-offset-1.wg-cell--lg-4." +
                "wg-cell--lg-offset-1 > p:nth-child(5) > button"));
        String startOpacityOfButtonResendEmail = buttonResendEmail.getCssValue("opacity");
        formPage.resendEmail();
        Assert.assertTrue(!startOpacityOfButtonResendEmail.equals(buttonResendEmail.getCssValue("opacity")));
    }

    /**
     * Стандартный метод для копирования файлов.
     */
    private static void copyFileUsingChannel(File source, File dest) throws IOException {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } finally {
            assert sourceChannel != null;
            sourceChannel.close();
            assert destChannel != null;
            destChannel.close();
        }
    }

    /**
     * Проверка: после нажатия на "Twitter" button корректен ли URL-адрес и корректна ли иконка кнопки.
     * Реализация: сохранение скриншота иконки на диск, ручная проверка скриншота, сравнение URL-адресов страницы
     * после нажатия на кнопку и действительным Твиттер-аккаунтом компании Wrike.
     */
    @Test
    public void check4ButtonFollowUsTwitter() {
        System.out.println("test number 4");
        FormPage formPage = new FormPage(driver);

        File dest = new File("newOne.png");
        try {
            copyFileUsingChannel(formPage.checkTwitterIcon(formPage.findButtonInTheSiteFooter()), dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String wrikeInTwitterURL = "https://twitter.com/wrike";
        Assert.assertTrue(wrikeInTwitterURL.equals(formPage.checkTwitterLink(formPage.findButtonInTheSiteFooter())));
    }

    @AfterClass
    public static void teardown() {
        driver.quit();
    }
}
