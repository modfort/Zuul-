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
     private String imageName;

    private List<Item>    item ;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description,String image) 
    {
        this.description  = description;
        item              = new ArrayList<Item>();
        imageName         = image;

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
   
    public String printLocationInfo(){
            String s =new String();
           s+="You are " + description+"\n";
           s+="Exits: "+"\n";
            if(northExit != null)
               s+="north "+northExit.description+"\n";
            if(eastExit != null)
              s+="east "+eastExit.description+"\n";
            if(southExit != null)
                s+="south "+southExit.description+"\n";
            if(westExit != null)
                s+="west "+westExit.description+"\n";
           return s+"\n";
    
        }
    
        /*
            return the list of object of a room
        */
    public String PrintItem()
    { String s = new String();
      if(this.item.isEmpty())
        return "the room doesnt have any item";
      for(Item e : this.item)
        s+=e.GetDescription()+" "+e.GetWeight()+" kg";
      return s+"\n";
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
      return null;
    }
     /* @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
  public String getImageName()
  {
    return imageName;
  }

}
