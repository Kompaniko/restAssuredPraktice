import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Date;

import static org.hamcrest.Matchers.is;

@DisplayName("Тест")
public class TestRestApiClass {

    // get запросы api postman


    @Test
    @DisplayName("тест")
    public void postmanGetTest() {
        RestAssured
                // отправка get запроса по Url
                .when()
                .get("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
                // валидация что получили 200 и все успешно
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                // проверяют что в body имеется аргумент foo2 со значением bar2.
                .body("args.foo1", is("bar1"));
    }

    @Test
    public void webHokPostTest() throws JSONException {

        // создание переменной для заполнения тела запроса уникальными значениями
        String randmonString = String.format("%1$TH%1$TM%1$TS", new Date());
        /* создание новго json объекта с помощью которого, в дальнейшем,
        задаются отправляемые нами параметры
        */
        JSONObject requestBody = new JSONObject();
        requestBody.put("First name", randmonString);
        requestBody.put("Last name", randmonString);
        requestBody.put("UserName", randmonString);
        requestBody.put("Password", randmonString);
        requestBody.put("Email", randmonString + "@gmail.com");
/*
С помощью request.post отправляется запрос на тот URL,
что был сформирован на WebHook.
 */
        RequestSpecification request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestBody.toString());
        Response response = request.post("https://webhook.site/923c89fa-44e9-4735-a8d3-79e76ccfcaf7");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        System.out.println("The status code recived:" + statusCode);
    }
    @Test
    public void testPostTwo() throws JSONException {

        String name = String.format("%1$TH%1$TM%1$TS", new Date());
        JSONObject requestBody = new JSONObject();
        requestBody.put("firstname", name);
        requestBody.put("Last name", name);
        requestBody.put("UserName", name);
        requestBody.put("Passworld", name);
        requestBody.put("Email", name + "@gmail.com");

        RequestSpecification request = RestAssured.
                given()
                .header("Content-Type", "application/json")
                .body(requestBody.toString());

        Response response = request.post("https://webhook.site/923c89fa-44e9-4735-a8d3-79e76ccfcaf7");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        System.out.println("The status code recived:" + statusCode);
    }

    @Test
    public void updateRecordsPut() throws JSONException {

        int empid = 15410;

        RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1/";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "TestDate");
        requestParams.put("age", 23);
        requestParams.put("salary", 12000);

        request.body(requestParams.toString());
        Response response = request.put("/update/"+ empid);

        int statusCode = response.getStatusCode();
        System.out.println(response.asString());
        Assert.assertEquals(statusCode, 301);
    }
}
