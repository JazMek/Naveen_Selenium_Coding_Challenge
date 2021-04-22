package solutionForNaveen;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Naveen_AutomationLabs {
    static WebDriver driver;
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver");
        driver= new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.noon.com/uae-en/");

        // List<String> resultList=Naveen_AutomationLabs_Function("Top picks in electronics");
           List<String> resultList=Naveen_AutomationLabs_Function("Top picks in electronics");
        // List<String> resultList=Naveen_AutomationLabs_Function("Save big on mobiles & tablets");
        //List<String> resultList=Naveen_AutomationLabs_Function("Recommended For You");
        System.out.println("The List of size is: "+ resultList.size());
        for(String str:resultList){
            System.out.println(str);
        }
        driver.quit();

    }

    public static List<String> Naveen_AutomationLabs_Function(String locatorValue) throws InterruptedException {
        boolean disable=false;
        Actions ac = new Actions(driver);
        List<String> ListElements = new ArrayList<>();
        while (! disable) {
            ac.sendKeys(Keys.PAGE_DOWN).build().perform();
            Thread.sleep(3000);
             try {
                 String moveToElem_XPATH =String.format("//*[contains(text(),'%s')]//parent::div//parent::div/parent::div[1]/div[2]/div[1]/div[1]/div[1]/div/a/div/div[2]",locatorValue);
                 //String moveToElem_XPATH ="//*[contains(text(),'"+locatorValue+"')]//parent::div//parent::div//parent::div[1]/div[2]/div[1]/div[1]/div[1]/div/a/div/div[2]";
                 WebElement moveToElem= driver.findElement(By.xpath(moveToElem_XPATH));
                 String swiper_button_next_XPATH =String.format("//*[contains(text(),'%s')]//parent::div//parent::div/parent::div[1]/div[2]/div[2]",locatorValue);
                 WebElement swiper_button_next= driver.findElement(By.xpath(swiper_button_next_XPATH));
                 ac.moveToElement(moveToElem).perform();
                 int count=1;
                 try {
                     while(swiper_button_next.isEnabled()) {
                         int i=0;
                         for(i=count; ;i++){
                             String Element_XPATH =String.format("//*[contains(text(),'%s')]//parent::div//parent::div/parent::div[1]/div[2]/div[1]/div/div["+i+"]/div/a/div",locatorValue);
                             WebElement Element =driver.findElement(By.xpath(Element_XPATH));
                             if(Element.isDisplayed()){
                                 String ElementTitle=Element.getAttribute("title");
                                 ListElements.add(ElementTitle);
                                 count++;
                             }else{
                                 disable=true;
                                 break;
                             }
                         }
                         swiper_button_next.click();
                         Thread.sleep(2000);
                     }
                 }catch (Exception e){}
             }catch (Exception e){}
             if(disable){ break;}
        }
        Collections.sort(ListElements);
        return ListElements;
    }
}
