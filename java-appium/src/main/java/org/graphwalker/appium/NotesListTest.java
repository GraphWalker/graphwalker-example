package org.graphwalker.appium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.AfterExecution;
import org.graphwalker.java.annotation.BeforeExecution;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

@GraphWalker(value = "random(edge_coverage(100))", start = "v_NotesListView")
public class NotesListTest extends ExecutionContext implements NotesList {

  private AndroidDriver<WebElement> driver;
  private int expectedNumberOfNotes = 0;

  @Override
  public void e_EditTitle() {
    driver.pressKeyCode(AndroidKeyCode.KEYCODE_MENU);
    driver.findElementsByClassName("android.widget.TextView").get(2).click();
  }

  @Override
  public void e_Discard() {
    driver.pressKeyCode(AndroidKeyCode.KEYCODE_MENU);
    driver.findElementByClassName("android.widget.TextView").click();
  }

  @Override
  public void e_GoBack() {
    driver.pressKeyCode(AndroidKeyCode.KEYCODE_BACK);
  }

  @Override
  public void v_EditNoteView() {
    WebElement notesEditor = driver.findElementByClassName("android.widget.EditText");
    Assert.assertTrue(notesEditor.isDisplayed());
  }

  @Override
  public void e_GoToNewNoteView() {
    driver.pressKeyCode(AndroidKeyCode.KEYCODE_MENU);
    driver.findElementByClassName("android.widget.TextView").click();
  }

  @Override
  public void e_Create() {
    String noteName = "test+" + new Random().nextInt();
    driver.findElement(By.xpath(
        "//android.widget.LinearLayout[1]/android.widget.FrameLayout[2]/android.widget.EditText[1]"))
        .sendKeys(
            noteName);
    driver.hideKeyboard();
    driver.pressKeyCode(AndroidKeyCode.KEYCODE_BACK);

    expectedNumberOfNotes++;
  }

  @Override
  public void e_SelectNote() {
    String FIRST_NOTE_LOCATOR =
        "//android.widget.LinearLayout[1]/android.widget.FrameLayout[2]/" +
        "android.widget.ListView[1]/android.widget.TextView[1]";

    driver.findElement(By.xpath(FIRST_NOTE_LOCATOR)).click();
  }

  @Override
  public void e_Done() {
    driver.pressKeyCode(AndroidKeyCode.KEYCODE_BACK);
  }

  @Override
  public void v_EditTitleView() {
    assertTrue(driver.findElement(By.className("android.widget.TextView")).getText()
        .contains("Note title:"));
  }

  @Override
  public void v_NotesListView() {
    WebElement notesList = driver.findElementByClassName("android.widget.TextView");
    assertTrue(notesList.getText().equals("Note pad"));

    List<WebElement>
        notesInList =
        driver.findElement(By.className("android.widget.ListView"))
            .findElements(By.className("android.widget.TextView"));

    int actualNumberOfNotes = notesInList.size();
    assertEquals(expectedNumberOfNotes, actualNumberOfNotes);
  }

  @Override
  public void v_NewNoteView() {
    WebElement notesEditor = driver.findElementByClassName("android.widget.EditText");
    Assert.assertTrue(notesEditor.isDisplayed());
  }

  @BeforeExecution
  public void setup() {
    File classpathRoot = new File(System.getProperty("user.dir"));
    File appDir = new File(classpathRoot, "src/main/resources");
    File app = new File(appDir, "Notepad.apk");
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("deviceName", "Android Emulator");
    capabilities.setCapability("platformVersion", "4.4");
    capabilities.setCapability("app", app.getAbsolutePath());
    capabilities.setCapability("appPackage", "com.example.android.notepad");
    capabilities.setCapability("appActivity", ".NotesList");
    try {
      driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @AfterExecution
  public void tearDown() {
    driver.quit();
  }
}
