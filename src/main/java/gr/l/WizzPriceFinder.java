package gr.l;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class WizzPriceFinder {


    public static String priceForFlight(String fromDest, String toDest, int numAdults, int year, int month, int day, String monthStr) {
        //Set this for chrome driver
        //System.setProperty("webdriver.chrome.driver", "C:/chromedriver_win32/chromedriver.exe");
        //WebDriver

        WebDriver wd = new FirefoxDriver();
        wd.get("https://wizzair.com");

        //input departure place
        wd.findElement(By.id("search-departure-station")).clear();
        wd.findElement(By.id("search-departure-station")).sendKeys(fromDest);
        wd.findElement(By.id("search-departure-station")).sendKeys(Keys.ENTER);

        //input arrival place
        wd.findElement(By.id("search-arrival-station")).clear();
        wd.findElement(By.id("search-arrival-station")).sendKeys(toDest);
        wd.findElement(By.id("search-arrival-station")).sendKeys(Keys.ENTER);

        //click on calendar
        wd.findElement(By.id("search-departure-date")).click();
        //wait
        wd.manage().timeouts().implicitlyWait(250, TimeUnit.MILLISECONDS);

        //find month and click the date provided into. If month is not found click next
        while (true) {
            try {


                wd.findElement(By.xpath("//div[contains(text(),'" + monthStr + "')]")).isDisplayed();
                String textmonth = wd.findElement(By.xpath("//div[contains(text(),'" + monthStr + "')]")).getText();
                String textClass = wd.findElement(By.xpath(String.format("//button[@data-pika-year='%d'][@data-pika-month='%d'][@data-pika-day='%d']", year, (month - 1), day))).getText();
                int intClass = Integer.parseInt(textClass);
                //if found date equals searched day - click. Otherwise, click next (limit is 7 days ahead)

                for (int i = 0; i < 8; i++) {
                    if (intClass == day && textmonth.equals(monthStr.toUpperCase())) {
                        wd.findElement(By.xpath(String.format("//button[@data-pika-year='%d'][@data-pika-month='%d'][@data-pika-day='%d']", year, (month - 1), day))).click();
                    } else
                        wd.findElement(By.xpath(String.format("//button[@data-pika-year='%d'][@data-pika-month='%d'][@data-pika-day='%d']", year, (month - 1), day + (i + 1)))).click();
                    intClass++;
                }


                break;
            } catch (Exception e) {
                wd.findElement(By.xpath("//*[@class='pika-next']")).click();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    throw new RuntimeException(e1);
                }
            }
        }


        //select number of passengers
        wd.findElement(By.id("search-passenger")).click();

        for (int i = 1; i < numAdults; i++) {
            wd.findElement(By.id("search-passenger")).click();
            wd.findElement(By.xpath("//span[@class='stepper__content__text'][text()='child']//preceding::button[@class='stepper__button stepper__button--add']")).click();
        }


        //click search
        wd.findElement(By.xpath("//button[@data-test='flight-search-submit']")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String priceVal = wd.findElement(By.xpath("//strong[@class='rf-fare__price__current']")).getText();

        return priceVal;
    }
}
