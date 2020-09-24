package com.prestashop.modelimplementation;


import com.prestashop.PrestaShop;
import org.graphwalker.core.machine.ExecutionContext;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Selenide.*;


public class SelenideImpl extends ExecutionContext implements PrestaShop {

    @Override
    public void v_ConfirmOrder() {
        $$(".step-title").shouldHave(size(4));
        $(".step-title").shouldHave(text("1 PERSONAL INFORMATION"));

        Integer itemsInCart = getAttribute("itemsInCart").asInt();
        String str = itemsInCart.toString() + " item";
        if (itemsInCart > 1) {
            str += "s";
        }
        $(".cart-summary-products p").shouldHave(matchText(str));
    }

    @Override
    public void e_Checkout() {
        $(".checkout .btn-primary").click();
    }

    @Override
    public void v_Product() {
        $(".breadcrumb").shouldHave(text("Home  Clothes  Men  Hummingbird printed t-shirt"));
        $("#product-description-short-1").shouldHave(text("Regular fit, round neckline, short sleeves. Made of extra long staple pima cotton."));
    }

    @Override
    public void e_Select_Product() {
        $("[data-id-product='1']").scrollTo().click();
    }

    @Override
    public void v_HomePage() {
        $(".products-section-title").shouldHave(text("POPULAR PRODUCTS"));
    }

    @Override
    public void e_Cart() {
        $("#_desktop_cart").click();
    }

    @Override
    public void v_Cart() {
        $(".card.cart-container .h1").shouldHave(text("SHOPPING CART"));
        $("[data-id_customization='0']").shouldHave(text("Hummingbird printed t-shirt"));
    }

    @Override
    public void e_AddProductToCart() {
        $("[data-button-action='add-to-cart']").click();
        $(".modal-header").shouldHave(text("Product successfully added to your shopping cart"));
        $(".btn[data-dismiss='modal']").click();
    }

    @Override
    public void e_Start() {
        browser = "firefox";
        open("http://localhost");
    }
}
