    import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;
import java.util.Deque;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

public class Game 
{   
    private Parser          parser;
    private Deque<Room>     back ;
    private Player          player;
    private CommandWords    commandword;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser              = new Parser();
        back                = new ArrayDeque<Room>();
        player              = new Player();
        commandword         = new CommandWords();
        createRooms();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;
        //create the list of item
        List<Item> element  = new ArrayList<Item>();
        Item       meat     = new Item("Meat",2.2);
        Item       knife    = new Item("Knife",0.9);
        Item       gun      = new Item("Gun",1.3); 
        Item       cookie   = new Item("Cookie",0.0);

        element.add(meat);
        element.add(knife);
        element.add(gun);
        element.add(cookie);

        // create the rooms
        outside             = new Room("outside the main entrance of the university");
        theatre             = new Room("in a lecture theatre");
        pub                 = new Room("in the campus pub");
        lab                 = new Room("in a computing lab");
        office              = new Room("in the computing admin office");
        
        // initialise room exits
        outside.setExits(null, theatre, lab, pub);
        theatre.setExits(null, null, null, outside);
        pub.setExits(null, outside, null, null);
        lab.setExits(outside, office, null, null);
        office.setExits(null, null, null, lab);

        //initilase the item in a room
        outside.SetItem(element);
        pub.AddItem(meat);
        office.AddItem(gun);

        player.SetRoom(outside);  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command     = parser.getCommand();
            finished            = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.print("Exits: ");
        player.GetRoom().printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        
        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (command.getCommandWord().equals(CommandWords.CommandWord.HELP))
                printHelp();
        else if (command.getCommandWord().equals(CommandWords.CommandWord.GO))
                goRoom(command);
        else if (command.getCommandWord().equals(CommandWords.CommandWord.LOOK))
                look();
        else if (command.getCommandWord().equals(CommandWords.CommandWord.EAT))
                eat();
        else if(command.getCommandWord().equals(CommandWords.CommandWord.SHOWALL))
                showall();
        else if(command.getCommandWord().equals(CommandWords.CommandWord.INFO))
                player.GetRoom().printLocationInfo();  
        else if(command.getCommandWord().equals(CommandWords.CommandWord.PRINTITEM))
                player.GetRoom().PrintItem();
        else if(command.getCommandWord().equals(CommandWords.CommandWord.SHOWITEM))
                player.ShowItem();
        else if(command.getCommandWord().equals(CommandWords.CommandWord.UNDO))
                undo();
        else if(command.getCommandWord().equals(CommandWords.CommandWord.TAKE))
                take(command);
        else if (command.getCommandWord().equals(CommandWords.CommandWord.DROP))
                drop(command);
        else if(command.getCommandWord().equals(CommandWords.CommandWord.COOKIE))
                cookie();
        else if (command.getCommandWord().equals(CommandWords.CommandWord.QUIT))
                wantToQuit = quit(command);
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();   
        System.out.println("Your command words are:");
        System.out.println(parser.GetAllCommand());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("north")) {
           nextRoom = player.GetRoom().northExit;

        }
        if(direction.equals("east")) {
            nextRoom = player.GetRoom().eastExit;
        }
        if(direction.equals("south")) {
            nextRoom = player.GetRoom().southExit;
        }
        if(direction.equals("west")) {
            nextRoom = player.GetRoom().westExit;
        }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            back.push(player.GetRoom());
            player.SetRoom(nextRoom); 
            player.GetRoom().printLocationInfo();
        }
    }


    private void take(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("take what?");
            return;
        }
       String item      = command.getSecondWord();
       Item e           = player.GetRoom().DeleteItem(item);
       if(e  ==  null)
            return;
        player.AddItem(e);


    }

    private  void drop(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("drop what?");
            return;
        }
       Item ele         = player.DropItem(command.getSecondWord());
       player.GetRoom().AddItem(ele);

    }
    //-----------------------------------------------

    private void look() 
    {   
        System.out.println(player.GetRoom().getDescription());
    }
    //-----------------------------------------------
    private void eat()
    {
        System.out.println("we have eat some meat");
    }
        //-----------------------------------------------

    private void showall()
    {
       System.out.println(parser.GetAllCommand());
    }
        //-----------------------------------------------

    private void undo()
    {   if(!back.isEmpty())
           { 
             player.SetRoom(back.pop());
             System.out.println(player.GetRoom().getDescription());

           }
        else
        {
            System.out.println("you have to move");
        } 
    }

    private void cookie()
    {
        player.cookie();
    }

    //-----------------------------------------------

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    

}
