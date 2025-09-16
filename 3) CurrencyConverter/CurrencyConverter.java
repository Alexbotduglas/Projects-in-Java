import java.util.Scanner;

public class CurrencyConverter {

    // Класс для хранения информации о валюте
    static class Currency {
        String code;     // Код валюты, например, RUB
        String name;     // Название, например, "Российский рубль"
        double rateToRUB; // Курс относительно рубля (1 единица валюты = ? рублей)

        Currency(String code, String name, double rateToRUB) {
            this.code = code;
            this.name = name;
            this.rateToRUB = rateToRUB;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // База валют
        Currency[] currencies = {
                new Currency("RUB", "Российский рубль", 1.0),
                new Currency("USD", "Доллар США", 95.0),
                new Currency("EUR", "Евро", 101.0),
                new Currency("CNY", "Китайский юань", 13.0),
                new Currency("KZT", "Казахстанский тенге", 0.20)
        };

        System.out.println("=== Конвертер валют ===");
        System.out.println("Доступные валюты:");

        // Вывод списка валют с номерами
        for (int i = 0; i < currencies.length; i++) {
            System.out.printf("%d - %s (%s)\n", i + 1, currencies[i].code, currencies[i].name);
        }

        // Выбор валюты ввода
        System.out.print("Выберите валюту ИЗ которой конвертировать (1-" + currencies.length + "): ");
        int fromIndex = scanner.nextInt() - 1;

        // Проверка корректности
        if (fromIndex < 0 || fromIndex >= currencies.length) {
            System.out.println("Ошибка: неверный номер валюты.");
            return;
        }

        // Выбор валюты вывода
        System.out.print("Выберите валюту В которую конвертировать (1-" + currencies.length + "): ");
        int toIndex = scanner.nextInt() - 1;

        if (toIndex < 0 || toIndex >= currencies.length) {
            System.out.println("Ошибка: неверный номер валюты.");
            return;
        }

        // Ввод суммы
        System.out.print("Введите сумму для конвертации: ");
        double amount = scanner.nextDouble();

        // Получаем валюты из базы
        Currency fromCurrency = currencies[fromIndex];
        Currency toCurrency = currencies[toIndex];

        // Конвертация: сначала в рубли, потом в целевую валюту
        double amountInRubles = amount * fromCurrency.rateToRUB;
        double result = amountInRubles / toCurrency.rateToRUB;

        // Вывод результата
        System.out.printf("Результат: %.2f %s = %.2f %s\n",
                amount, fromCurrency.code, result, toCurrency.code);
    }
}
