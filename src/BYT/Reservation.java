package BYT;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Reservation {
    public Date startAt;
    public Date endsAt;
    public int numberOfPeople;
    public Customer customer;
    public String tableNumber;
    public Reservation(Date startAt, Date endsAt,Customer customer,String tableNumber,int numberOfPeople) {
        this.startAt = startAt;
        this.endsAt = endsAt;
        this.tableNumber = tableNumber;
        this.customer = customer;
        this.numberOfPeople = numberOfPeople;

    }

    public static ArrayList<Reservation> reservation=new ArrayList<Reservation>();
    public static ArrayList<Table> getFreeTables(int maxNumberOfPeople, Date startAt, Date endsAt) {
        ArrayList<Table> freeTables = new ArrayList<>();

        for (Table t : Table.tables) {
            if (maxNumberOfPeople > t.maxNumberOfPeople) continue;

            boolean exactConflict = false;


            for (Reservation r : reservation) {
                if (t.tableNumber.equals(r.tableNumber)
                        && startAt.equals(r.startAt)
                        && endsAt.equals(r.endsAt)) {
                    exactConflict = true;
                    break;
                }
            }

            if (!exactConflict) {
                freeTables.add(t); // SADECE bir kez ekle
            }
        }
        return freeTables;
    }

    public static void createReservation(Date startAt,Date endsAt,Customer customer,int numberOfPeople) {
        ArrayList<Table> freeTables=getFreeTables(numberOfPeople,startAt,endsAt);
        int i=0;

        for(Table t:freeTables){
            System.out.println(i+"_" +t);
            i++;
        }
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter a free table : ");
        int index=sc.nextInt();
        reservation.add(new Reservation(startAt,endsAt,customer,freeTables.get(index).tableNumber,numberOfPeople));
    }
    public void cancelReservation(Date startAt,Date endsAt,int tableNumber){
        for(int i=0;i<reservation.size();i++){

        }
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "startAt=" + startAt +
                ", endsAt=" + endsAt +
                ", numberOfPeople=" + numberOfPeople +
                ", customer=" + customer +
                ", tableNumber='" + tableNumber + '\'' +
                '}';
    }
}
