import java.util.ArrayList;

public class MonthlyReport {
    public  String getName(){
        switch (month){
            case 1 :
                return "Январь";
            case 2 :
                return "Февраль";
            case 3 :
                return "Март";
            case 4 :
                return "Апрель";
            case 5 :
                return "Май";
            case 6 :
                return "Июнь";
            case 7 :
                return "Июль";
            case 8 :
                return "Август";
            case 9 :
                return "Сентябрь";
            case 10 :
                return "Октябрь";
            case 11 :
                return "Ноябрь";
            case 12 :
                return "Декабрь";
            default:
                return "Номер месяца не корректен";
        }
    }
    int month;
    ArrayList<Transaction> Transactions = new ArrayList<>();
    public MonthlyReport(int month){
        this.month = month;
    }
    public void addTransaction(Transaction t){
        Transactions.add(t);
    }
    public int sum(){
        int sum = 0;
        for (Transaction transaction : Transactions){
            int koef = 1;
            if (transaction.is_expense){
                koef = -1;
            }
            sum += transaction.sum_of_one*transaction.quantity*koef;
        }
        return sum;
    }
    public String getMaxInfo(boolean checkExpense){
        int max = 0;
        String name = " ";
        for (Transaction transaction : Transactions){
            boolean isExpense = checkExpense && transaction.is_expense;
            boolean isNotExpense = !checkExpense && !transaction.is_expense;
            if (isExpense || isNotExpense){
                int sum = transaction.sum_of_one*transaction.quantity;
                if (sum > max){
                    max = sum;
                    name = transaction.item_name;
                }
            }

        }
        return   name + ", сумма " + max;
    }
}
