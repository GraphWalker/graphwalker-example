package com.prestashop.modelimplementation;

import com.prestashop.PrestaShop;
import com.prestashop.helper.Helper;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.AfterExecution;
import org.graphwalker.java.annotation.BeforeExecution;
import org.junit.Assert;
import org.sikuli.script.*;

public class SikuliImpl extends ExecutionContext implements PrestaShop {

    Screen screen;
    Region region;

    @BeforeExecution
    public void setup() {
        screen = new Screen();
        ImagePath.add("src/main/resources/images");
        Helper.setup();
    }

    @AfterExecution
    public void cleanup() {
        Helper.tearDown();
    }

    @Override
    public void e_Start() {
        Helper.getInstance().get("http://localhost:8001");
    }

    @Override
    public void v_ConfirmOrder() {
        try {
            region.wait("personalInformation.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void e_Checkout() {
        try {
            region.click("proceedToCheckout.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void v_Product() {
        try {
            region.wait("productPage.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void e_Select_Product() {
        try {
            region.click("product.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void v_HomePage() {
        try {
            region = App.focusedWindow();
            region.wait("homePage.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void e_Cart() {
        try {
            region.click("cartButton.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void v_Cart() {
        try {
            region.wait("shoppingCart.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void e_AddProductToCart() {
        try {
            region.click("addToCart.png");
            region.wait("continueShopping.png");
            region.click("continueShopping.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }
}
