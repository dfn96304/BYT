package BYT;

import java.util.ArrayList;

public class Table {
    private String tableNumber; // tableNumber could be "A123" etc.
    private int maxNumberOfPeople;
    
    public static ArrayList<Table> tables = new ArrayList<Table>();
    
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
