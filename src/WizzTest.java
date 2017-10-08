import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class WizzTest {


    public static void main(String[] args) throws InterruptedException {
        // INPUT fromDest and put it into a list
        ArrayList<String> fromDest=new ArrayList<String>();
        Scanner scan=new Scanner(System.in);
        System.out.println("Input destinations you want to go from:");
        String input = scan.nextLine();

        while(!input.equals("END")){
            fromDest.add(input);
            input = scan.nextLine();
        }
        //input toDest
        System.out.print("Enter toDest ");
        String toDest = scan.nextLine();
        //input date
        System.out.print("Enter date (MM/DD/YYYY) ");
        String selectdata = scan.nextLine();
        //input number of Adults
        System.out.print("Enter numAdults ");
        int numAdults = scan.nextInt();
        //close input
        scan.close();

        //Format date provided
        int month = monthIntFormatter(selectdata);
        int year = yearIntFormatter(selectdata);
        int day = dayIntFormatter(selectdata);
        String monthStr = monthStrFormatter(selectdata);

        //Print date selected and starting test line
        System.out.println("starting date is"+ monthStr + " "+ day+". Test in progress...");


        //run priceForFlight
        int findMinPrice = 0;
        String findDest = null;
        for(int i=0; i<fromDest.size();i++){
            String priceText = priceForFlight(fromDest.get(i), toDest, numAdults, year, month, day, monthStr);
            int priceInt = Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
            System.out.println(priceText + " from " + fromDest.get(i) + " to " + toDest );
            if (findMinPrice == 0 || findMinPrice > priceInt){
                findMinPrice = priceInt;
                findDest = fromDest.get(i);
            }
        }
        System.out.println("Min price is "+ findMinPrice + " from " + findDest +" to " + toDest);
    }

 //   @SuppressWarnings("deprecation")
    public static String monthStrFormatter(String selectdata) {
        Date d = new Date(selectdata);
        SimpleDateFormat dt = new SimpleDateFormat("MMMM/dd/yyyy", Locale.ENGLISH);
        String date = dt.format(d);
        String[] split = date.split("/");
        String monthString = split[0];

        return monthString;
    }
   // @SuppressWarnings("deprecation")
    public static int monthIntFormatter(String selectdata) {
        Date d = new Date(selectdata);
        SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        String date = dt.format(d);
        String[] split = date.split("/");
        String monthStr = split[0];
        int monthInt = Integer.parseInt(monthStr);
        return monthInt;
    }

 //   @SuppressWarnings("deprecation")
    public static int yearIntFormatter(String selectdata) {
        Date d = new Date(selectdata);
        SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        String date = dt.format(d);
        String[] split = date.split("/");
        String yearStr = split[2];
        int yearInt = Integer.parseInt(yearStr);
        return yearInt;
    }
  //  @SuppressWarnings("deprecation")
    public static int dayIntFormatter(String selectdata) {
        Date d = new Date(selectdata);
        SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        String date = dt.format(d);
        String[] split = date.split("/");
        String dayStr = split[1];
        int dayInt = Integer.parseInt(dayStr);
        return dayInt;
    }

  //  @SuppressWarnings("deprecation")
    public static String dayFormatter(String selectdata) {
        Date d = new Date(selectdata);
        SimpleDateFormat dt = new SimpleDateFormat("MMMM/dd/yyyy", Locale.ENGLISH);
        String date = dt.format(d);
        String[] split = date.split("/");
        String month = split[0];
        //System.out.println(month);
        return month;
    }




    public static String priceForFlight(String fromDest, String toDest, int numAdults, int year, int month, int day, String monthStr) throws InterruptedException{
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
        while (true){
            try{


                wd.findElement(By.xpath("//div[contains(text(),'"+monthStr+"')]")).isDisplayed();
                String textmonth = wd.findElement(By.xpath("//div[contains(text(),'"+monthStr+"')]")).getText();
                String textClass = wd.findElement(By.xpath(String.format("//button[@data-pika-year='%d'][@data-pika-month='%d'][@data-pika-day='%d']", year, (month-1), day))).getText();
                int intClass = Integer.parseInt(textClass);
                //if found date equals searched day - click. Otherwise, click next (limit is 7 days ahead)

                for(int i=0; i<8; i++){
                    if (intClass==day && textmonth.equals(monthStr.toUpperCase())){
                        wd.findElement(By.xpath(String.format("//button[@data-pika-year='%d'][@data-pika-month='%d'][@data-pika-day='%d']", year, (month-1), day))).click();
                    }
                    else  wd.findElement(By.xpath(String.format("//button[@data-pika-year='%d'][@data-pika-month='%d'][@data-pika-day='%d']", year, (month-1), day+(i+1)))).click();
                    intClass++;
                }


                break;
            }
            catch(Exception e){
                wd.findElement(By.xpath("//*[@class='pika-next']")).click();
                Thread.sleep(3000);
            }
        }


        //select number of passengers
        wd.findElement(By.id("search-passenger")).click();

        for(int i=1; i<numAdults;i++){
            wd.findElement(By.id("search-passenger")).click();
            wd.findElement(By.xpath("//span[@class='stepper__content__text'][text()='child']//preceding::button[@class='stepper__button stepper__button--add']")).click();
        }


        //click search
        wd.findElement(By.xpath("//button[@data-test='flight-search-submit']")).click();
        Thread.sleep(5000);
        String priceVal = wd.findElement(By.xpath("//strong[@class='rf-fare__price__current']")).getText();

        return priceVal;
    }
}

