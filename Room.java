import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

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
    private HashMap<String, Room> exits;
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
        exits = new HashMap<String, Room>();

    }

    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     */
    private String getExitString()
    {
        StringBuilder returnString = new StringBuilder( "Exits:" );
        for ( String vS : exits.keySet() )
            returnString.append( " " + vS );
        return returnString.toString();
    }

    public void SetItem(List<Item>e)
    {
        item                = e;
    }

    public void AddItem(Item e)
    {
        item.add(e);
    }
    
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
   
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
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of this room, in the form:
     *     You are in the kitchen.
     *     Exits: north west
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    public String getImageName()
    {
      return imageName;
    }

}
