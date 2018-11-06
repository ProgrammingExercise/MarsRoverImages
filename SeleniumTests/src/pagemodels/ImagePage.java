package pagemodels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class ImagePage {
    final WebDriver driver;

    @FindBy(how = How.ID, using = "mars_image")
    public WebElement image;

    @FindBy(how = How.ID, using = "camera")
    public WebElement camera;

    @FindBy(how = How.ID, using = "earth_date")
    public WebElement earth_date;

    @FindBy(how = How.ID, using = "photo_id")
    public WebElement photo_id;

    @FindBy(how = How.ID, using = "rover")
    public WebElement rover;

    public ImagePage(WebDriver driver) {
        this.driver = driver;
    }

}
