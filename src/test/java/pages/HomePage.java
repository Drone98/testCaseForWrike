package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    private String baseURL = "https://www.wrike.com/va/?utm_expid=75732941-113._QwudDuLQTa0farhZW-FBA.1";

    public HomePage goToHomePage() {
        driver.get(baseURL);
        return this;
    }

    public HomePage goToCreateWrikeAccount() {
        driver.findElement(By.cssSelector("body > div.wg-layout.wg-layout--outline > header > div.wg-header__desktop > " +
                "div.wg-header__sticky-menu > div > div > div.wg-header__cell.wg-header__cell--xs-3.wg-header__cell--sm-6." +
                "wg-header__cell--md-5.wg-header__cell--xl-5 > div > form > button")).click();
        return this;
    }

    public void registerBusinessAccount() {
        driver.findElement(By.cssSelector("#modal-pro > form > label:nth-child(3) > input"))
                .sendKeys("qwerty+wpt@wriketask.qaa");
        driver.findElement(By.cssSelector("#modal-pro > form > label:nth-child(4) > button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.wg-layout.wg-layout--outline " +
                "> main > div > div > div.section.section-resend-main.section-resend-main-va.section-resend-main--survey " +
                "> div > div.wg-cell.wg-cell--md-6.wg-cell--lg-7 > div > form > h3")));
    }

    public String getBaseURL() {
        return baseURL;
    }
}