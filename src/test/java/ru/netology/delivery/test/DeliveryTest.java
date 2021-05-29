package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.delivery.data.DataGenerator.*;


public class DeliveryTest {

    public String city = getCity();
    public String name = getName();
    public String date = getDate();
    public String newDate = getNewDate();
    public String phone = getPhone();

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999");
    }

    @Test
    void shouldPlanAndReplanMeeting() {
        $("[data-test-id='city'] .input__control").setValue(city);
        $(".menu-item_type_block").click();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] .input__control").setValue(date);
        $("[data-test-id='name'] .input__control").setValue(name);
        $("[data-test-id='phone'] .input__control").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible);
        String firstDate = $(".notification__content").getText();
        assertEquals("Встреча успешно запланирована на " + getDate(), firstDate);

        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] .input__control").setValue(newDate);
        $(byText("Запланировать")).click();

        $(byText("Перепланировать")).click();
        $(".notification_visible").shouldHave(text("Успешно!"));
        String secondDate = $("[data-test-id='success-notification'] .notification__content").getText();
        assertEquals("Встреча успешно запланирована на " + getNewDate(), secondDate);
    }

    @Test
    void shouldGetErrorMessageIfSendEmptyForm() {
        $(byText("Запланировать")).click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void shouldGetErrorMessageIfWrongCity() {
        $("[data-test-id='city'] .input__control").setValue("Париж");
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] .input__control").setValue(date);
        $("[data-test-id='name'] .input__control").setValue(name);
        $("[data-test-id='phone'] .input__control").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(byText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test
    void shouldGetErrorMessageIfDateEmpty() {
        $("[data-test-id='city'] .input__control").setValue(city);
        $(".menu-item_type_block").click();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='name'] .input__control").setValue(name);
        $("[data-test-id='phone'] .input__control").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(byText("Неверно введена дата")).shouldBe(visible);
    }

    @Test
    void shouldGetErrorMessageIfWrongName() {
        $("[data-test-id='city'] .input__control").setValue(city);
        $(".menu-item_type_block").click();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] .input__control").setValue(date);
        $("[data-test-id='name'] .input__control").setValue("456211");
        $("[data-test-id='phone'] .input__control").setValue("+79998887766");
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(visible);
    }

    @Test
    void shouldGetErrorMessageIfWrongPhone() {
        $("[data-test-id='city'] .input__control").setValue(city);
        $(".menu-item_type_block").click();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] .input__control").setValue(date);
        $("[data-test-id='name'] .input__control").setValue(name);
        $("[data-test-id='phone'] .input__control").setValue("4");
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test
    void shouldGetErrorMessageIfNotMarkCheckbox() {
        $("[data-test-id='city'] .input__control").setValue(city);
        $(".menu-item_type_block").click();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] .input__control").setValue(date);
        $("[data-test-id='name'] .input__control").setValue(name);
        $("[data-test-id='phone'] .input__control").setValue(phone);
        $(byText("Запланировать")).click();
        $("[data-test-id='agreement'] .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldClosePopup() {
        $("[data-test-id='city'] .input__control").setValue(city);
        $(".menu-item_type_block").click();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] .input__control").setValue(date);
        $("[data-test-id='name'] .input__control").setValue(name);
        $("[data-test-id='phone'] .input__control").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(".icon").click();
        $("[data-test-id='success-notification']").shouldHave(text("Успешно!")).shouldNotBe(visible);
    }
}