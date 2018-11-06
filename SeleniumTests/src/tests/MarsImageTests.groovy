package tests

import org.junit.Assert
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.PageFactory
import pagemodels.ImagePage
import pagemodels.MainPage

class MarsImageTests extends GroovyTestCase {
    void testViewSingleImage(){
        WebDriver driver = new FirefoxDriver()

        driver.get("http://localhost:32768")

        MainPage mainPage = PageFactory.initElements(driver, MainPage.class)
        Assert.assertEquals(mainPage.imageLinks.size() > 0, true)
        ImagePage imagePage = mainPage.ViewFirstImage()
        Assert.assertEquals(imagePage.image.getAttribute("src") != null && !imagePage.image.getAttribute("src").isEmpty(), true)
        Assert.assertEquals(imagePage.camera.getText().isEmpty(), Boolean.FALSE)
        Assert.assertEquals(imagePage.earth_date.getText().isEmpty(), Boolean.FALSE)
        Assert.assertEquals(imagePage.photo_id.getText().isEmpty(), Boolean.FALSE)
        Assert.assertEquals(imagePage.rover.getText().isEmpty(), Boolean.FALSE)
    }
}