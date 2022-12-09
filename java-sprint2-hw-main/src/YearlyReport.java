import java.util.ArrayList;

public class YearlyReport {
    ///String numberOfYear;

    ArrayList<MonthlyTransaction> transactions = new ArrayList<>();
    public void addData(MonthlyTransaction transaction){
        transactions.add(transaction);
    }
    public boolean checkMonth(MonthlyReport report){
        int summa = report.sum();
        var transaction = getMonth(report.month);
        if (transaction == null){
            return false;
        }
        return summa == (transaction.income - transaction.outcome);
    }
    public  MonthlyTransaction getMonth(int number){
        for (MonthlyTransaction transaction : transactions){
            if (transaction.month == number){
                return transaction;
            }
        }
        return null;
    }
}
