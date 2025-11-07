package BYT;

import java.time.LocalDate;
import java.util.List;

public class Menu {
    // The Menu is VALID from [releaseDate, endDate] - bounds included:
    // When the date is equal releaseDate or endDate, the menu is VALID.
    public LocalDate releaseDate;
    public LocalDate endDate;

    public Menu(LocalDate releaseDate, LocalDate endDate){
        LocalDate today = LocalDate.now();

        if(today.isAfter(releaseDate))
            throw new IllegalArgumentException("The releaseDate cannot be *before* today. It must be at the earliest equal to today.");

        if (today.isAfter(endDate))
            throw new IllegalArgumentException("The endDate cannot be *before* today. It must be at the earliest equal to today.");

        if(endDate.isBefore(releaseDate))
            throw new IllegalArgumentException("The endDate cannot be before the releaseDate. The endDate must be equal (1-day menu) or after the releaseDate.");

        this.releaseDate = releaseDate;
        this.endDate = endDate;
    }

    //public static List<Menu> getActiveMenus(){

    //}

    public static void createNewMenu(Menu newMenu){

    }

    //public List<MenuItem> getMenuItemList(){

    //}

    public void createMenuItem(MenuItem newMenuItem){

    }

    public void delete(){

    }

    // /Status derived attribute (converted to method)
    public MenuStatus getMenuStatus(){
        LocalDate today = LocalDate.now();

        if(today.isAfter(endDate)){
            return MenuStatus.ENDED;
        }else if(today.isAfter(releaseDate) || today.isEqual(releaseDate)){
            return MenuStatus.CURRENTLYVALID;
        }else{
            return MenuStatus.CREATED;
        }
    }
}
