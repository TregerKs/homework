package com.maven1;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        /// Парсим JSON в коллекцию

        //String pathToFileString = "E:\\Code\\homework\\maven\\shares.json";
        String pathToFileString = "..\\maven\\shares.json";
        Path pathToFile = Paths.get(pathToFileString);
        String absolutePath = pathToFile.toAbsolutePath().toString();
        Reader fileReader = new FileReader(absolutePath);


        JsonObject root = JsonParser.parseReader(fileReader).getAsJsonObject();
        JsonArray companiesJson = root.get("companies").getAsJsonArray();

        SimpleDateFormat formatter = new SimpleDateFormat("d.M.y", Locale.ENGLISH);

        Stream<JsonElement> stream = StreamSupport.stream(companiesJson.spliterator(), true);
        List<Company> listCompanies = stream.map(x -> {
            JsonObject comp = x.getAsJsonObject();
            int id = comp.get("id").getAsInt();
            String name = comp.get("name").getAsString();
            String address = comp.get("address").getAsString();
            String phoneNumber = comp.get("phoneNumber").getAsString();
            String inn = comp.get("inn").getAsString();
            String foundedString = comp.get("founded").getAsString();
            Date founded = null;

            try {
                founded = formatter.parse(foundedString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            JsonArray securitiesJson = comp.get("securities").getAsJsonArray();
            Stream<JsonElement> streamSecurities = StreamSupport.stream(securitiesJson.spliterator(), true);
            List<Security> securities1 = streamSecurities.map(y -> {
                JsonObject security = y.getAsJsonObject();
                String securitiesName = security.get("name").getAsString();

                List<String> currencies = new ArrayList<>();
                JsonArray currencyJson = security.get("currency").getAsJsonArray();
                for (int i = 0; i < currencyJson.size(); i++) {
                    currencies.add(currencyJson.get(i).getAsString());
                }
                String code = security.get("code").getAsString();
                String date = security.get("date").getAsString();

                return new Security(securitiesName, currencies, code, date);
            }).collect(Collectors.toList());

            return new Company(id, name, address, phoneNumber, inn, founded, securities1);
        }).collect(Collectors.toList());


        // Используем методы
        System.out.println("Краткий список компаний");
        nameAndFounded(listCompanies);
        System.out.println();
        System.out.println();

        Scanner s = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Введите дату для фильтрации компаний:");
        String dateString = s.nextLine();  // Read user input
        Date dateAfter = parseDate(dateString);
        System.out.println(searchCompanyAfterDate(listCompanies, dateAfter));
        System.out.println();

        System.out.println("Список просроченных ценных бумаг и их суммарное количество");
        overdueSecurities(listCompanies);
        System.out.println();

        //System.out.println("Компании использующие валюту RUB");
        searchCurrency(listCompanies);
    }

    public static void nameAndFounded(List<Company> list) {
        list.forEach(x -> System.out.println("Название компании " + x.name + ", дата основания " + x.founded));
    }

    public static void overdueSecurities(List<Company> list) throws ParseException {
        Date date = new Date();
        Date date1;
        System.out.println(date);
        for (Company company : list) {
            System.out.println("Компания " + company.name + " имеет просроченные акции: ");
            int count = 0;
            for (int j = 0; j < company.securities.size(); j++) {
                date1 = new SimpleDateFormat("dd.MM.yyyy").parse(company.securities.get(j).date);
                if (date.after(date1)) {
                    count++;
                    System.out.println(" компании " + company.securities.get(j).name + ", код: " + company.securities.get(j).code + ", дата истечения: " + company.securities.get(j).date);
                }
            }
            System.out.println("Всего просроченных акций у компании " + count);
        }
    }

    public static void searchCurrency(List<Company> list) {
        System.out.println("Введите код валюты для поиска акций, использующих эту валюту: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equals("EU") || input.equals("RUB") || input.equals("USD")){
            for (Company company : list) {
                System.out.println("ID компании " + company.id);
                for (int j = 0; j < company.securities.size(); j++) {
                    for (int k = 0; k < company.securities.get(j).currency.size(); k++) {
                        if (input.equals(company.securities.get(j).currency.get(k))) {
                            System.out.println("Код акции " + company.securities.get(j).code + ", использует валюту " + input);
                        }
                    }
                }
            }
        } else {
            System.out.println(input + " не существует");
        }
    }

    public static List<Company> searchCompanyAfterDate(List<Company> list, Date date) {
        return list.stream().filter(x -> x.founded.after(date)).collect(Collectors.toList());
    }

    public static Date parseDate(String dateString) throws ParseException {
        // паттерн 01.01.1990
        Pattern pattern = Pattern.compile("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}");
        Matcher matcher = pattern.matcher(dateString);
        if (matcher.matches()) {
            return new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
        }

        // паттерн 01/01/1990
        pattern = Pattern.compile("[0-9]{2}/[0-9]{2}/[0-9]{4}");
        matcher = pattern.matcher(dateString);
        if (matcher.matches()) {
            return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        }

        // паттерн 01/01/90
        pattern = Pattern.compile("[0-9]{2}/[0-9]{2}/[0-9]{2}");
        matcher = pattern.matcher(dateString);
        if (matcher.matches()) {
            return new SimpleDateFormat("dd/MM/yy").parse(dateString);
        }

        throw new ParseException("Incorrect date format", 0);
    }
}