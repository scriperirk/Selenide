package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CardTest {

    @Test
    void shouldCardTest() {

        Configuration.holdBrowserOpen = true;

        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\Антон\\AppData\\Local\\Google\\Chrome SxS\\Application\\chrome.exe");
        options.addArguments("window-size=1920x1080");
        //options.addArguments("--headless");


        ChromeDriver driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
        //open("http://localhost:9999");

        String meetingDateNearest = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        $("[data-test-id='city'] input").setValue("Иркутск");
        $("[data-test-id='date'] input").setValue(meetingDateNearest);

        $("[data-test-id=name] input").setValue("Афанасов Антон");
        $("[data-test-id=phone] input").setValue("+79000000880");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title")
                .shouldBe(Condition.appear, Duration.ofSeconds(15))
                .shouldHave((text("Успешно!")));
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(text("Встреча успешно забронирована на " + meetingDateNearest));

    }
}
