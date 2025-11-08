package BYT;

import java.util.Calendar;
import java.util.Date;

public class DeleteLater {
    public static void main(String[] args) {
        for(int i=0;i<Table.tables.size();i++){
            Table.tables.remove(i);
        }
        Table table1=new Table("A1",3);
        Table table2=new Table("A2",3);
        Table table3=new Table("A3",4);
        Table table4=new Table("A4",4);
        Table table5=new Table("A5",5);
        Table table6=new Table("A6",5);
        Table table7=new Table("A7",6);
        Table table8=new Table("A8",6);
        Table table9=new Table("A9",6);
        Table.tables.add(table1);
        Table.tables.add(table2);
        Table.tables.add(table3);
        Table.tables.add(table4);
        Table.tables.add(table5);
        Table.tables.add(table6);
        Table.tables.add(table7);
        Table.tables.add(table8);
        Table.tables.add(table9);
        Calendar cal = Calendar.getInstance(); // bugünün tarihi
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date startDate = cal.getTime();
        Calendar cal2 = Calendar.getInstance(); // bugünün tarihi
        cal2.add(Calendar.DAY_OF_YEAR, 2);
        Date endDate = cal.getTime();
        Customer c1 = new Customer(1);

        Reservation.createReservation(startDate,endDate,c1,3);
        Reservation.createReservation(startDate,endDate,c1,6);
        for(Reservation r: Reservation.reservation){
            System.out.println(r);
        }

    }
}
