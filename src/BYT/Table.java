package BYT;

import java.util.ArrayList;

public class Table {
    public String tableNumber; // tableNumber could be "A123" etc.
    public int maxNumberOfPeople;
    ;
    public static ArrayList<Table> tables=new ArrayList<Table>();
    public Table(String tableNumber,int maxNumberOfPeople) {
        this.tableNumber = tableNumber;
        this.maxNumberOfPeople = maxNumberOfPeople;

    }

    @Override
    public String toString() {
        return "Table{" +
                "tableNumber='" + tableNumber + '\'' +
                ", maxNumberOfPeople=" + maxNumberOfPeople +
                '}';
    }
}
