import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Room 
{
    public String         description;
    public Room           northExit;
    public Room           southExit;
    public Room           eastExit;
    public Room           westExit;
    private List<Item>    item ;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description  = description;
        item              = new ArrayList<Item>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west) 
    {
        if(north != null)
            northExit       = north;
        if(east != null)
            eastExit        = east;
        if(south != null)
            southExit       = south;
        if(west != null)
            westExit        = west;
    }
    public void SetItem(List<Item>e)
    {
        item                = e;
    }

    public void AddItem(Item e)
    {
        item.add(e);
    }
    public Room getExits(String description) 
    {
       if(description.equals("north")  ){
          return northExit;
        }
        if(description.equals("east") ) {
        return eastExit;
        }
        if(description.equals("south") ) {
        return southExit;
        }
        if(description.equals("west")) {
        return westExit;
        }
        return null;
    }
   
    public void printLocationInfo(){

            System.out.println("You are " + description);
            System.out.print("Exits: ");
            if(northExit != null)
                System.out.print("north "+northExit.description);
            if(eastExit != null)
                System.out.print("east "+eastExit.description);
            if(southExit != null)
                System.out.print("south "+southExit.description);
            if(westExit != null)
                System.out.print("west "+westExit.description);
            System.out.println();
    
        }
    
        /*
            return the list of object of a room
        */
    public int PrintItem()
    { 
      if(this.item.isEmpty())
        System.out.println("the room doesnt have any item");
      for(Item e : this.item)
        System.out.println(e.GetDescription()+" "+e.GetWeight()+" kg");
      return 1;
    }

    public Item DeleteItem(String des)
    {
      for (Item e : this.item ) {
        if(e.GetDescription().equals(des))
            { 
               item.remove(item.indexOf(e));
               return e;
            }
      }
      System.out.println("we don't have this item");
      return null;
    }
     /* @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }


}
