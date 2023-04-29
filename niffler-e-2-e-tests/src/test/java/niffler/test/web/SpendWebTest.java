package niffler.test.web;

import com.codeborne.selenide.CollectionCondition;
import niffler.api.dto.CurrencyValues;
import niffler.api.dto.SpendJson;
import niffler.jupiter.GenerateCategory;
import niffler.jupiter.GenerateCategoryExtension;
import niffler.jupiter.GenerateSpend;
import niffler.jupiter.GenerateSpendExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

@ExtendWith(GenerateCategoryExtension.class)
@ExtendWith(GenerateSpendExtension.class)
public class SpendWebTest {
    private final String CATEGORY = "Обучение";
    private final String USERNAME = "sergei";

    @BeforeEach
    void preConditions(){
        open("http://127.0.0.1:3000/main");
        $("a[href*='redirect']").click();
        $("input[name='username']").val(USERNAME);
        $("input[name='password']").val("12345");
        $("button[type='submit']").click();
    }

    @GenerateCategory(
            category = CATEGORY,
            username = USERNAME
    )

    @GenerateSpend(
        description = "Описание",
        category = CATEGORY,
        amount = 100.00,
        currencyValues = CurrencyValues.RUB,
        username = USERNAME
    )

    @Test
    void spendWebTest(SpendJson spend){
        $(".spendings-table tbody")
                .$$("tr")
                .find(text(spend.getDescription()))
                .$("td",0)
                .scrollTo()
                .click();

        $$(".button_type_small")
                .find(text("Delete selected"))
                .click();

        $(".spendings-table tbody")
                .$$("tr")
                .shouldHave(CollectionCondition.size(0));
    }
}
