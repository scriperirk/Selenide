package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.manager.TimeManager;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @BeforeEach
    void setupTest() {
        open("http://localhost:9999");
    }

    @Test
    void shouldOrderCardDeliveryByText() {
        open("http://localhost:9999");

        String bookDate(int days) {
            return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }

        String meetingDateNearest = bookDate(3);

        $("[data-test-id='city'] input").setValue("Иркутск");
        $("[data-test-id='date'] input").setValue(meetingDateNearest);

        $("[data-test-id=name] input").setValue("Афанасов Антон");
        $("[data-test-id=phone] input").setValue("+79000000880");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title")
                .shouldBe(Condition.appear, Duration.ofSeconds(15))
                .shouldHave((text("Успешно!")));
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(text("Встреча успешно забронирована на " + meetingDateNearest));

    }
}
