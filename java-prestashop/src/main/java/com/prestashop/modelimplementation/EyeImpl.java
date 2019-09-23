package com.prestashop.modelimplementation;

import com.prestashop.PrestaShop;
import org.graphwalker.core.machine.ExecutionContext;

import eye.Eye;
import eye.Match;
import org.graphwalker.java.annotation.BeforeExecution;
import org.junit.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EyeImpl extends ExecutionContext implements PrestaShop {

    private Eye eye;

    @BeforeExecution
    public void setup() {
        eye = new Eye();
    }

    @Override
    public void e_Start() {
        BufferedImage image = getBufferedImage("/images/localhost_icon.png");
        Match match = eye.findImage(image);
        Assert.assertTrue("Could not click on Spotify desktop icon", eye.click(match.getCenterLocation()));
    }

    @Override
    public void v_ConfirmOrder() {
        BufferedImage image = getBufferedImage("/images/personalInformation.png");
        Assert.assertNotNull("Could not find the personal information page", eye.findImage(image));
    }

    @Override
    public void e_Checkout() {
        BufferedImage image = getBufferedImage("/images/proceedToCheckout.png");
        Match match = eye.findImage(image);
        Assert.assertTrue("Could not click on the proceed to checkout button", eye.click(match.getCenterLocation()));
    }

    @Override
    public void v_Product() {
        BufferedImage image = getBufferedImage("/images/productPage.png");
        Assert.assertNotNull("Could not find the product page", eye.findImage(image));
    }

    @Override
    public void e_Select_Product() {
        BufferedImage image = getBufferedImage("/images/product.png");
        Match match = eye.findImage(image);
        Assert.assertTrue("Could not click on the product", eye.click(match.getCenterLocation()));
    }

    @Override
    public void v_HomePage() {
        BufferedImage image = getBufferedImage("/images/homePage.png");
        Assert.assertNotNull("Could not find the Home Page", eye.findImage(image));
    }

    @Override
    public void e_Cart() {
        BufferedImage image = getBufferedImage("/images/cartButton.png");
        Match match = eye.findImage(image);
        Assert.assertTrue("Could not click on the cart button", eye.click(match.getCenterLocation()));
    }

    @Override
    public void v_Cart() {
        BufferedImage image = getBufferedImage("/images/shoppingCart.png");
        Assert.assertNotNull("Could not find the Home Page", eye.findImage(image));
    }

    @Override
    public void e_AddProductToCart() {
        BufferedImage image = getBufferedImage("/images/addToCart.png");
        Match match = eye.findImage(image);
        Assert.assertTrue("Could not click on the add to cart button", eye.click(match.getCenterLocation()));

        image = getBufferedImage("/images/continueShopping.png");
        match = eye.findImage(image);
        Assert.assertTrue("Could not click on the continue shopping button", eye.click(match.getCenterLocation()));
    }

    private BufferedImage getBufferedImage(String fileName) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResourceAsStream(fileName));
        } catch (IOException e) {
            Assert.fail("Could not load image: " + fileName + " Caused by: " + e.getMessage());
        }
        return image;
    }
}
