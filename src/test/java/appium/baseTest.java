package appium;

import java.io.File;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;


public class baseTest {
    AppiumDriverLocalService service;
    AndroidDriver driver;
    @BeforeClass
    public void configureAppium() throws MalformedURLException {
        File logDir = new File("/Users/aashok/IdeaProjects/appium_learnings/appium_logs");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        File logFile = new File(logDir, "appium-logfile.log");

        service = new AppiumServiceBuilder()
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                .withIPAddress("127.0.0.1")
                .withLogFile(logFile)
                .usingPort(4723)
                .build();
        service.start();
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel_3a_testEmulator");

//      Xpath, id, accessibilityId, className , androidUiAutomator
        options.setApp("//Users//aashok//IdeaProjects//appium_learnings//src//test//resources//ApiDemos-debug.apk");
//		AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(),options);
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"),options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    @AfterClass
    public void tearDown(){
        driver.quit();
        service.stop();
    }

    public void longPressAction(WebElement elem){
        ((JavascriptExecutor)driver).executeScript("mobile: longClickGesture",
                ImmutableMap.of("elementId",((RemoteWebElement)elem).getId(),
                        "duration",2000));
    }
    public void scrollToAnElement(String elementText){
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+elementText+"\"));"));
//        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"WebView\"));"));
    }
    public void scrollToEnd(){
        boolean canScrollMore;
        do {
            canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 200,
                    "direction", "down",
                    "percent", 1.0
            ));
        }while (canScrollMore);
    }
}
