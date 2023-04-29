package niffler.api.methods;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostCategory {
    @Step("Отправляем запрос POST /category. Получаем ответ")
    public static Response postCategory(Object body){
        ObjectMapper mapper = new ObjectMapper();
        return given()
                .when()
                .log().all()
                .filter(new AllureRestAssured())
                .contentType("application/json")
                .header("charset", "UTF-8")
                .body(mapper.valueToTree(body))
                .post("http://127.0.0.1:8093/category")
                .then()
                .log().all()
                .extract().response();
    }
}
