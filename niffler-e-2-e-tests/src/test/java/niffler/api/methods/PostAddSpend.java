package niffler.api.methods;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostAddSpend {
    @Step("Отправляем запрос POST /spend. Получаем ответ")
    public static Response postAddSpend(Object body){
        ObjectMapper mapper = new ObjectMapper();
        return given()
                .when()
                .log().all()
                .filter(new AllureRestAssured())
                .relaxedHTTPSValidation()
                .contentType("application/json")
                .header("charset", "UTF-8")
                .body(mapper.valueToTree(body))
                .post("http://127.0.0.1:8093/addSpend")
                .then()
                .log().all()
                .extract().response();
    }
}
