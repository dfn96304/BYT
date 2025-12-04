package BYT.Classes.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Chef extends Employee implements Serializable {
    private static final List<Chef> extent = new ArrayList<>();

    private Chef supervisor;
    private final List<Chef> supervisedChefs = new ArrayList<>();

    public Chef(String firstName, String lastName, String phoneNumber, String email, long salary) {
        super(firstName, lastName, phoneNumber, email, salary);
        extent.add(this);
    }

    public static Chef findOrCreate(String firstName, String lastName, String phoneNumber, String email, long salary) {
        for (Chef chef : extent) {
            if (chef.getPhoneNumber().equals(phoneNumber)) {
                System.out.println("Chef has been found!");
                return chef;
            }
        }
        return new Chef(firstName, lastName, phoneNumber, email, salary);
    }

    public Chef getSupervisor() {
        return supervisor;
    }

    public List<Chef> getSupervisedChefs() {
        return new ArrayList<>(supervisedChefs);
    }

    public void setSupervisor(Chef newSupervisor) {
        if (newSupervisor == this)
            throw new IllegalArgumentException("A Chef cannot supervise themselves");

        //generated to Prevent supervision cycles: A -> B -> C -> A
        if (createsLoop(newSupervisor))
            throw new IllegalArgumentException("Supervision loop detected! Operation not allowed.");

        if(this.supervisor != null) {
            this.supervisor.supervisedChefs.remove(this);
        }

        this.supervisor = newSupervisor;

        if (newSupervisor != null && !newSupervisor.supervisedChefs.contains(this)) {
            newSupervisor.supervisedChefs.add(this);
        }
    }

    public void addSupervisedChef(Chef chef) {
        if(chef == null) return;
        chef.setSupervisor(this);
    }

    public void removeSupervisedChef(Chef chef){
        if(chef == null) return;

        if(supervisedChefs.remove(chef)){
            chef.supervisor = null;
        }
    }

    // Detect a loop by climbing the supervisor chain
    //generated
    private boolean createsLoop(Chef potentialSupervisor) {
        Chef current = potentialSupervisor;

        while (current != null) {
            if (current == this)
                return true; // loop found!
            current = current.supervisor;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        // Cast after superclass check
        Chef chef = (Chef) o;
        // Compare Chef-specific fields here if we add new ones
        // Since Chef currently has no new fields, they’re automatically equal
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

}
