package com.prestashop.modelimplementation;

import com.prestashop.PrestaShop;
import com.prestashop.helper.Helper;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.AfterExecution;
import org.graphwalker.java.annotation.BeforeExecution;
import org.junit.Assert;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Screen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SikuliImpl extends ExecutionContext implements PrestaShop {

    Screen screen;

    @BeforeExecution
    public void setup() {
        screen = new Screen();
        ImagePath.add("/home/krikar/dev/graphwalker/graphwalker-example/java-prestashop/src/main/resources/images");
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
            screen.wait("personalInformation.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void e_Checkout() {
        try {
            screen.click("proceedToCheckout.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void v_Product() {
        try {
            screen.wait("productPage.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void e_Select_Product() {
        try {
            screen.click("product.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void v_HomePage() {
        try {
            screen.wait("homePage.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void e_Cart() {
        try {
            screen.click("cartButton.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void v_Cart() {
        try {
            screen.wait("shoppingCart.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void e_AddProductToCart() {
        try {
            screen.click("addToCart.png");
            screen.click("continueShopping.png");
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail();
        }
    }
}
