package com.company.modelimplementations;


import com.company.OwnerInformationSharedState;
import com.company.helper.Helper;
import org.apache.commons.lang3.RandomStringUtils;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the model (and interface) OwnerInformationSharedState
 * The default path generator is Random Path.
 * Stop condition is 100% coverage of all edges.
 */
@GraphWalker(value = "random(edge_coverage(100))")
public class OwnerInformation extends ExecutionContext implements OwnerInformationSharedState {

    private static final Logger log = LoggerFactory.getLogger(OwnerInformation.class);

    /**
     * This method does not only implement the verification of the vertex, it also
     * fetches the value representing number of pets for the owner, and passes it
     * on to GraphWalker. In the model OwnerInformationSharedState.graphml the attribute
     * numOfPets decides whether the guards for the edges:
     * e_AddVisit
     * e_EditPet
     * are opened or closed.
     * <p/>
     * setAttribute is inherited from org.graphwalker.core.machine.ExecutionContext
     */
    @Override
    public void v_OwnerInformation() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "Owner Information"));
        setAttribute("numOfPets", Helper.getInstance().findElements(By.xpath("//table/tbody/tr/td//dl")).size());
        log.info("Number of pets: " + getAttribute("numOfPets"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/table[last()]/tbody/tr/td[2]/img")));
    }

    @Override
    public void e_UpdatePet() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=\"submit\"]"))).click();
    }

    @Override
    public void v_FindOwners() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "Find Owners"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/table/tbody/tr/td[2]/img")));
    }

    @Override
    public void e_EditPet() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.linkText("Edit Pet"))).click();
    }

    @Override
    public void e_AddNewPet() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.linkText("Add New Pet"))).click();
    }

    @Override
    public void e_AddVisit() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.linkText("Add Visit"))).click();
    }

    @Override
    public void e_FindOwners() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.className("icon-search"))).click();
    }

    @Override
    public void e_AddPetSuccessfully() {
        Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.id("birthDate"))).clear();
        Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.id("birthDate"))).sendKeys("2015/02/05" + Keys.ENTER);
        Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
        Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.id("name"))).clear();
        Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.id("name")))
            .sendKeys(RandomStringUtils.randomAlphabetic(Helper.getRandomInt(10)));
        new Select(Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.id("type")))).selectByVisibleText("dog");
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=\"submit\"]"))).click();
    }

    @Override
    public void v_NewPet() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "New Pet"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/table/tbody/tr/td[2]/img")));
    }

    @Override
    public void e_VisitAddedSuccessfully() {
        Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.id("description"))).clear();
        Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.id("description")))
            .sendKeys(RandomStringUtils.randomAlphabetic(Helper.getRandomInt(10)));
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=\"submit\"]"))).click();
    }

    @Override
    public void v_NewVisit() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "New Visit"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.id("visit")));
    }

    @Override
    public void v_Pet() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "Pet"));
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"pet\"]/div[5]/button")));
    }

    @Override
    public void e_AddPetFailed() {
        Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.id("name"))).clear();
        Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.id("birthDate"))).clear();
        Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.id("birthDate"))).sendKeys("2015/02/05");
        new Select(Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.id("type")))).selectByVisibleText("dog");
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=\"submit\"]"))).click();
    }

    @Override
    public void e_VisitAddedFailed() {
        Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.id("description"))).clear();
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=\"submit\"]"))).click();
    }
}
