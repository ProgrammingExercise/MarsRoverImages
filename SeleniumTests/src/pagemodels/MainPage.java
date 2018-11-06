package pagemodels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
    final WebDriver driver;

    @FindBy(how = How.CLASS_NAME, using = "mars_image")
    public List<WebElement> imageLinks;

    public MainPage(WebDriver driver) {
        this.driver = driver;

    }

    public ImagePage ViewFirstImage() {
        imageLinks.get(0).click();
        return PageFactory.initElements(driver, ImagePage.class);
    }
}
