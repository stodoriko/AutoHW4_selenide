package ru.netology;

import com.codeborne.selenide.Condition;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldAcceptsDelivery() {
        // выбираем город
        $(By.xpath("//span[contains(@class, 'input_has-autocomplete')]//input[@type='text']")).setValue("Москва");
        $$(By.xpath("//div[contains(@class, 'popup__content')]//div")).find(exactText("Москва")).click();

        // Дата - не ранее трёх дней с текущей даты
        $(By.xpath("//input[contains(@class, 'input__control')and @type='tel']")).setValue("27.11.2222");

        // Поле Фамилия и имя - разрешены только русские буквы, дефисы и пробелы
        $("span[data-test-id='name'] input").setValue("Тодорико Сергей");

        // Поле телефон - только цифры (11 цифр), символ + (на первом месте)
        $("span[data-test-id='phone'] input").setValue("+79012345678");

        // Флажок согласия должен быть выставлен
        $("label[data-test-id='agreement']").click();

        $$("button").find(exactText("Забронировать")).click();

        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована на")).shouldBe(visible);

    }

}
