package com.company.modelimplementations;


import com.company.OwnerInformationSharedState;
import com.company.helper.Helper;
import org.apache.commons.lang3.RandomStringUtils;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
        Assert.assertTrue(Helper.WaitForElement(By.tagName("h2")).getText().matches("Owner Information"));
        setAttribute("numOfPets", Helper.WaitForElements(By.xpath("//th[2]")).size());
        log.info("Number of pets: " + getAttribute("numOfPets"));
    }

    @Override
    public void e_UpdatePet() {
        Helper.WaitForElement(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Override
    public void v_FindOwners() {
        Assert.assertTrue(Helper.WaitForElement(By.tagName("h2")).getText().matches("Find Owners"));
    }

    @Override
    public void e_EditPet() {
        Helper.WaitForElement(By.linkText("Edit Pet")).click();
    }

    @Override
    public void e_AddNewPet() {
        Helper.WaitForElement(By.linkText("Add New Pet")).click();
    }

    @Override
    public void e_AddVisit() {
        Helper.WaitForElement(By.linkText("Add Visit")).click();
    }

    @Override
    public void e_FindOwners() {
        Helper.WaitForElement(By.className("icon-search")).click();
    }

    @Override
    public void e_AddPetSuccessfully()  {
        Helper.WaitForElement(By.id("birthDate")).clear();
        Helper.WaitForElement(By.id("birthDate")).sendKeys("2015/02/05" + Keys.ENTER);
        Helper.WaitForElement(By.id("name")).clear();
        Helper.WaitForElement(By.id("name")).sendKeys(RandomStringUtils.randomAlphabetic(Helper.getRandomInt(10)));
        new Select(Helper.WaitForElement(By.id("type"))).selectByVisibleText("dog");
        Helper.WaitForElement(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Override
    public void v_NewPet() {
        Assert.assertTrue(Helper.WaitForElement(By.tagName("h2")).getText().matches("New Pet"));
    }

    @Override
    public void e_VisitAddedSuccessfully() {
        Helper.WaitForElement(By.id("description")).clear();
        Helper.WaitForElement(By.id("description")).sendKeys(RandomStringUtils.randomAlphabetic(Helper.getRandomInt(10)));
        Helper.WaitForElement(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Override
    public void v_NewVisit() {
        Assert.assertTrue(Helper.WaitForElement(By.tagName("h2")).getText().matches("New Visit"));
    }

    @Override
    public void v_Pet() {
        Assert.assertTrue(Helper.WaitForElement(By.tagName("h2")).getText().matches("Pet"));
    }

    @Override
    public void e_AddPetFailed() {
        Helper.WaitForElement(By.id("name")).clear();
        Helper.WaitForElement(By.id("birthDate")).clear();
        Helper.WaitForElement(By.id("birthDate")).sendKeys("2015/02/05");
        new Select(Helper.WaitForElement(By.id("type"))).selectByVisibleText("dog");
        Helper.WaitForElement(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Override
    public void e_VisitAddedFailed() {
        Helper.WaitForElement(By.id("description")).clear();
        Helper.WaitForElement(By.cssSelector("button[type=\"submit\"]")).click();
    }
}
