import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static void printMonthsInfo(){
        for (MonthlyReport report : monthlyReports){
            String name = report.getName();
            String incomeInfo = report.getMaxInfo(false);
            String outcomeInfo = report.getMaxInfo(true);
            print(name);
            print("Самый крупный доход: " + incomeInfo);
            print("Самый крупный расход: " + outcomeInfo + "\n");
        }
    }
    private static void printYearInfo(){
        int sumIncome = 0;
        int sumOutcome = 0;
        int monthsCount = yearlyReport.transactions.size();
        print("2021");
        for (MonthlyTransaction transaction : yearlyReport.transactions){
            int income = transaction.income;
            int outcome = transaction.outcome;
            sumIncome += income;
            sumOutcome += outcome;
            print("Месяц " + transaction.month + ", доход " + (income - outcome));
        }
        print("Средний расход: " + (sumOutcome/monthsCount));
        print("Средний доход: " + (sumIncome/monthsCount) + "\n");
    }
    private static void print(String message) {
        System.out.println(message);
    }
    public static void readYearlyReport() throws IOException{
        String path = "./resources/y.2021.csv";
        String[] lines = getLines(path);
        for (int i = 1; i < lines.length; i+=2){
             String[] dataIncome = lines[i].split(",");
             String[] dataOutcome = lines[i+1].split(",");
             var transaction = new MonthlyTransaction();
             transaction.month = parseInt(dataIncome[0]);
             transaction.income = parseInt(dataIncome[1]);
             transaction.outcome = parseInt(dataOutcome[1]);
             yearlyReport.addData(transaction);
        }
    }


    private static int parseInt(String data) {
        return Integer.parseInt(data);
    }

    private static String[] getLines(String fileName) throws IOException {
        return Files.readString(Path.of(fileName)).split("\n");
    }

    public static void readMonthlyReports()throws IOException {
        for (int i = 1; i < 4 ; i++){
            String path = "./resources/m.20210" + i + ".csv";
            String[] lines = getLines(path);
            var report = new MonthlyReport(i);
            for (int j = 1; j < lines.length ; j++){
                String[] data = lines[j].split(",");
                var transaction = new Transaction();
                transaction.item_name = data[0];
                transaction.is_expense = Boolean.parseBoolean(data[1]);
                transaction.quantity = Integer.parseInt(data[2]);
                transaction.sum_of_one = Integer.parseInt(data[3]);
                report.addTransaction(transaction);
            }
            monthlyReports.add(report);
        }
    }
    private static void checkReport(){
        var monthlyReportsCount = monthlyReports.size();
        if (monthlyReportsCount == 0){
            print("Сначала считайте месячные отчёты");
            return;
        }
        var countYearReport = yearlyReport.transactions.size();
        if (countYearReport == 0) {
            print("Сначала считайте годовой отчёт");
            return;
        }
        boolean errorsExists = false;
        for (int i = 1; i < monthlyReports.size(); i++){
            var report = monthlyReports.get(i);
            if (yearlyReport.checkMonth(report) == false){
                print("Данные по " + report.month + " месяцу не верны.");
                errorsExists = true;
            }
        }
        if (!errorsExists){
            print("Ошибок нет.");
        }
    }
    static ArrayList<MonthlyReport> monthlyReports = new ArrayList<>();

    static YearlyReport yearlyReport = new YearlyReport();
    public static void printMenu(){
        print("1.Считать все месячные отчёты\n"+
                "2.Считать годовой отчёт\n"+
                "3.Сверить отчёты\n"+
                "4.Вывести информацию о всех месячных отчётах\n"+
                "5.Вывести информацию о годовом отчёте\n" +
                "6.Выход");
    }

    public static void main(String[] args)throws IOException {
        Scanner scanner = new Scanner(System.in);
        int input;
        do {
            printMenu();
            input = scanner.nextInt();
            switch (input){
                case 1:
                    readMonthlyReports();
                    System.out.println("Все месячные отчёты были записаны");
                    break;
                case 2:
                    readYearlyReport();
                    System.out.println("Годовой отчёт был записан");
                    break;
                case 3:
                    checkReport();
                    break;
                case 4:
                    printMonthsInfo();
                    break;
                case 5:
                    printYearInfo();
                    break;
            }
        }
        while (input != 6);
    }
}

