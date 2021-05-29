package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String getCity() {
        List<String> cityList = Arrays.asList("Абакан", "Воронеж", "Нижний Новгород", "Ставрополь", "Севастополь", "Краснодар");
        Random random = new Random();
        String randomElement = cityList.get(random.nextInt(cityList.size()));
        return randomElement;
    }

    public static String getDate() {
        LocalDate date = LocalDate.now().plusDays(3);
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String getNewDate() {
        LocalDate date = LocalDate.now().plusDays(7);
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String getName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String getPhone() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.phoneNumber().cellPhone();
    }
}
