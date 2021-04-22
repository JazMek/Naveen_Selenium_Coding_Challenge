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

/**
 * BY KARIM and AMAR
 */
public class Naveen_AutomationLabs {
    static WebDriver driver;
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver");
        driver= new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.noon.com/uae-en/");

        String [] SectionNames={"Top picks in electronics","Save big on mobiles & tablets","Recommended For You"};

        for(String sectionName:SectionNames) {
            System.out.println("**************************************************************************\n");
            System.out.println("The list of items for the section >> "+sectionName+" << is:");

            List<String> CarouselItems =Naveen_AutomationLabs_Function(sectionName);
            System.out.println("List size is = " + CarouselItems.size());
            for (String Items : CarouselItems) {
                System.out.println(Items);
            }
        }
        driver.quit();

    }

    public static List<String> Naveen_AutomationLabs_Function(String SectionName) throws InterruptedException {
        boolean nextArrowIsEnable=false;
        Actions ac = new Actions(driver);
        List<String> CarouselItemsText = new ArrayList<>();
        while (! nextArrowIsEnable) {
            ac.sendKeys(Keys.PAGE_DOWN).build().perform();
            Thread.sleep(3000);
             try {
                 String elementDescription_XPATH =String.format("//*[contains(text(),'%s')]//parent::div//parent::div/parent::div[1]/div[2]/div[1]/div[1]/div[1]/div/a/div/div[2]",SectionName);
                 //String elementDescription_XPATH ="//*[contains(text(),'"+SectionName+"')]//parent::div//parent::div//parent::div[1]/div[2]/div[1]/div[1]/div[1]/div/a/div/div[2]";
                 WebElement elementDescription = driver.findElement(By.xpath(elementDescription_XPATH));
                 String nextArrow_XPATH =String.format("//*[contains(text(),'%s')]//parent::div//parent::div/parent::div[1]/div[2]/div[2]",SectionName);
                 WebElement nextArrow= driver.findElement(By.xpath(nextArrow_XPATH));
                 ac.moveToElement(elementDescription).perform();
                 int count=1;
                 try {
                     while(nextArrow.isEnabled()) {
                         int i=0;
                         for(i=count; ;i++){
                             String item_XPATH =String.format("//*[contains(text(),'%s')]//parent::div//parent::div/parent::div[1]/div[2]/div[1]/div/div["+i+"]/div/a/div",SectionName);
                             WebElement item =driver.findElement(By.xpath(item_XPATH));
                             if(item.isDisplayed()){
                                 String ElementTitle=item.getAttribute("title");
                                 CarouselItemsText.add(ElementTitle);
                                 count++;
                             }else{
                                 nextArrowIsEnable=true;
                                 break;
                             }
                         }
                         nextArrow.click();
                         Thread.sleep(2000);
                     }
                 }catch (Exception e){}
             }catch (Exception e){}
             if(nextArrowIsEnable){ break;}
        }
        Collections.sort(CarouselItemsText);
        return CarouselItemsText;
    }
}
