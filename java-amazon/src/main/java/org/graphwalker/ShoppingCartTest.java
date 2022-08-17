package org.graphwalker;

import com.codeborne.selenide.CollectionCondition;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.AfterExecution;
import org.graphwalker.java.annotation.BeforeExecution;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Selenide.*;

@GraphWalker()
public class ShoppingCartTest extends ExecutionContext implements ShoppingCart {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartTest.class);
    Integer numberOfAddedBooksByProgram = 0;

    @BeforeExecution
    public void setup() {
        logger.info("Setup happens here");
        browser = "chrome";
    }

    @AfterExecution
    public void cleanup() {
        logger.info("Cleanup happens here");
    }

    public void e_ClickBook() {
        $$("[data-component-type='s-search-result']").findBy(text("Practical Model-Based Testing: A Tools Approach")).shouldBe(visible).click();
    }

    public void e_AddBookToCart() {
        $("#mediaTab_heading_2").shouldBe(visible).click();
        $("[title='Add to Shopping Cart']").click();
        logger.debug("Number of added books by test: " + ++numberOfAddedBooksByProgram);
    }

    public void v_BookInformation() {
        $("#productTitle").shouldHave(text("Practical Model-Based Testing: A Tools Approach"));
    }

    public void v_AddedToCart() {
        $("h1.a-text-bold").shouldHave(text("Added to Cart"));
    }

    public void v_SearchResult() {
        $$("[data-component-type='s-search-result']").filter(visible).shouldHave(CollectionCondition.sizeGreaterThan(5));
        $$("[data-component-type='s-search-result']").findBy(text("Practical Model-Based Testing: A Tools Approach")).shouldBe(visible);
    }

    public void v_ShoppingCart() {
        Integer expected_num_of_books_by_graphwalker = getAttribute("num_of_books").asInt();

        // The number of books should be equal both in the model, and in this program
        Assert.assertEquals(expected_num_of_books_by_graphwalker, numberOfAddedBooksByProgram);


        if (expected_num_of_books_by_graphwalker == 0) {
            $(".sc-your-amazon-cart-is-empty").shouldHave(text("Your Amazon Cart is empty"));
            return;
        }

        $("h1").shouldHave(text("Shopping Cart"));
        $("#sc-subtotal-label-activecart").shouldHave(text("Subtotal (" + expected_num_of_books_by_graphwalker.toString() + " item"));
    }

    public void e_EnterBaseURL() {
        open("http://www.amazon.com");
    }

    public void v_Amazon() {
        $(".nav-search-field input[type='text']").shouldBe(visible);
    }


    public void e_SearchBook() {
        $(".nav-search-field input[type='text']").shouldBe(visible);
        $(".nav-search-field input[type='text']").sendKeys("'Practical Model-Based Testing: A Tools Approach'");
        $("#nav-search-submit-button").click();
    }

    public void e_ShoppingCart() {
        $("#nav-cart-count-container").shouldBe(visible).click();
    }
}
