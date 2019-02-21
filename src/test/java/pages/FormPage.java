package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.util.Random;

public class FormPage extends BasePage {
    public FormPage(WebDriver driver) {
        super(driver);
    }

    public void fillQASection() {
        /**
         * Я заметил что CSS selector кнопок, являющихся ответом на один вопрос, отличается лишь одной цифрой.
         * Чтобы реализовать рандомный выбор ответов, я приписывал отличающуюся цифру, генерируя её случайно.
         */
        Random random = new Random();
        int randomAnswer1 = random.nextInt(2) + 1;
        int randomAnswer2 = random.nextInt(5) + 1;
        int randomAnswer3 = random.nextInt(3) + 1;

        driver.findElement(By.cssSelector("body > div.wg-layout.wg-layout--outline > main > div > div > " +
                "div.section.section-resend-main.section-resend-main-va.section-resend-main--survey > div > " +
                "div.wg-cell.wg-cell--md-6.wg-cell--lg-7 > div > form > div:nth-child(6) > label:nth-child(" +
                String.valueOf(randomAnswer1) +
                ") > button")).click();

        driver.findElement(By.cssSelector("body > div.wg-layout.wg-layout--outline > main > div > div >" +
                " div.section.section-resend-main.section-resend-main-va.section-resend-main--survey > div > " +
                "div.wg-cell.wg-cell--md-6.wg-cell--lg-7 > div > form > div:nth-child(8) > label:nth-child(" +
                String.valueOf(randomAnswer2) +
                ") > button")).click();

        driver.findElement(By.cssSelector("body > div.wg-layout.wg-layout--outline > main > div > div > " +
                "div.section.section-resend-main.section-resend-main-va.section-resend-main--survey > div > " +
                "div.wg-cell.wg-cell--md-6.wg-cell--lg-7 > div > form > div:nth-child(10) > label:nth-child(" +
                String.valueOf(randomAnswer3) +
                ") > button")).click();

        /**
         * Выбирая ответ "Other" на третий вопрос, необходимо заполнить его фразой.
         */
        if (randomAnswer3 == 3) {
            driver.findElement(By.cssSelector("body > div.wg-layout.wg-layout--outline > main > div > div > " +
                    "div.section.section-resend-main.section-resend-main-va.section-resend-main--survey > div > " +
                    "div.wg-cell.wg-cell--md-6.wg-cell--lg-7 > div > form > div:nth-child(10) " +
                    "> label.switch.switch--text.switch--fullwidth > button > span > input"))
                    .sendKeys("we've never tried follow, but wish.");
        }

        WebElement surveyFormFormWebElement = driver.findElement(By.cssSelector("body > div.wg-layout.wg-layout--outline " +
                "> main > div > div > div.section.section-resend-main.section-resend-main-va.section-resend-main--survey " +
                "> div > div.wg-cell.wg-cell--md-6.wg-cell--lg-7 > div > form"));

        driver.findElement(By.cssSelector("body > div.wg-layout.wg-layout--outline > main > div > div > " +
                "div.section.section-resend-main.section-resend-main-va.section-resend-main--survey > div > " +
                "div.wg-cell.wg-cell--md-6.wg-cell--lg-7 > div > form > button")).click();

        wait.until(ExpectedConditions.invisibilityOf(surveyFormFormWebElement));
    }

    public void resendEmail() {
        driver.findElement(By.cssSelector("body > div.wg-layout.wg-layout--outline > main > div > div " +
                "> div.section.section-resend-main.section-resend-main-va.section-resend-main--survey " +
                "> div > div.wg-cell.wg-cell--order-1.wg-cell--md-5.wg-cell--md-5.wg-cell--md-offset-1" +
                ".wg-cell--lg-4.wg-cell--lg-offset-1 > p:nth-child(5) > button")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.wg-layout.wg-layout--outline " +
                "> main > div > div > div.section.section-resend-main.section-resend-main-va.section-resend-main--survey." +
                "section-resend-main--again > div > div.wg-cell.wg-cell--order-1.wg-cell--md-5.wg-cell--md-5." +
                "wg-cell--md-offset-1.wg-cell--lg-4.wg-cell--lg-offset-1 > p:nth-child(5) > button")));
    }

    public WebElement findButtonInTheSiteFooter() {
        WebElement bottomFooter = driver.findElement(By.cssSelector("body > div.wg-layout.wg-layout--outline > div > " +
                "div.wg-footer__bottom-section"));
        return bottomFooter.findElement(By.cssSelector("body > div.wg-layout.wg-layout--outline > div > " +
                "div.wg-footer__bottom-section > div > div.wg-footer__social-block > div > ul > li:nth-child(1)"));
    }

    /**
     * Создание временного скриншота, он удалится при завершении работы JVM, поэтому в дальнейшем необходимо
     * сделать его копию.
     */
    public File checkTwitterIcon(WebElement buttonTwitter) {
        return ((TakesScreenshot) buttonTwitter)
                .getScreenshotAs(OutputType.FILE);
    }

    public String checkTwitterLink(WebElement buttonTwitter) {
        buttonTwitter.click();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        wait.until(ExpectedConditions.titleIs("Wrike (@wrike) | Твиттер"));
        return driver.getCurrentUrl();
    }

}
