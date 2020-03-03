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
    private UserInterface gui;

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

    public void setGUI(UserInterface userInterface)
    {
        gui = userInterface;
        printWelcome();
    }


    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, mainhall, gaming, living, mainstairs, torture, firstfloor, chamber, bathroom, stairs, secondfloor, attic, secret, corridor;

        //create the list of item
        List<Item> element  = new ArrayList<Item>();    
        Item        cookie          = new Item("Cookie",0.0);
        Item        battery         = new Item("Battery", 5.0);
        Item        heal            = new Item("Heal", 2.5);
        Item        keysecret       = new Item("Keysecret", 1.0);
        Item        keymain         = new Item("Keymain", 1.0);
        Item        flashlight      = new Item("Flashlight", 5.0);

        element.add(battery);
        element.add(heal);
        element.add(keysecret);
        element.add(keymain);
        element.add(flashlight);
        element.add(cookie);


        // create the rooms
        outside             = new Room("outside the main entrance of the house");
        mainhall            = new Room("You are in the main hall.");
        gaming              = new Room("You are in the gaming room.");
        living              = new Room("You are in the living room.");
        mainstairs          = new Room("You are at the mainstars.");
        firstfloor          = new Room("You are on the 1st floor.");
        chamber             = new Room("You are in the chamber.");
        bathroom            = new Room("You are in the bathroom.");
        stairs              = new Room("You are at the stairs of the 1st floor.");
        secondfloor         = new Room("You are on the 2nd floor.");
        attic               = new Room("You are in the attic.");
        corridor            = new Room("You are in the corridor.");
        secret              = new Room("You are in the secret chamber.");
        torture             = new Room("You are in the torture room.");

        outside.setExits(mainhall, null, null, null);
        mainhall.setExits(mainstairs, gaming, outside, living);
        gaming.setExits(null, null, null, mainhall);
        living.setExits(null, mainhall, null, null);
        mainstairs.setExits(firstfloor, null, mainhall, null);
        firstfloor.setExits(stairs, chamber, mainstairs, bathroom);
        chamber.setExits(torture, null, null, firstfloor);
        torture.setExits(null, null, chamber, null);
        bathroom.setExits(null, firstfloor, null, null);
        stairs.setExits(attic, corridor, firstfloor, null);
        secondfloor.setExits(attic, corridor, stairs, null);
        attic.setExits(null, null, secondfloor, null);
        corridor.setExits(secret, null, null, secondfloor);
        secret.setExits(null, null, corridor, null);


        //initilase the item in a room
        gaming.AddItem(battery);
        living.AddItem(cookie);
        chamber.AddItem(keysecret);
        bathroom.AddItem(heal);
        attic.AddItem(battery);
        secret.AddItem(flashlight);
        secret.AddItem(keymain);

        // start the game outside
        player.SetRoom(outside);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
   

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        gui.println("");
        gui.println("Welcome to Horror House !");
        gui.println("Horror House is a new, incredibly boring adventure game.");
        gui.println("Type 'help' if you need help.");
        gui.println("");
        gui.println("Exits: ");
        gui.println(player.GetRoom().printLocationInfo());
        gui.showImage(player.GetRoom().getImageName());

    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public boolean processCommand(String text) 
    {
        boolean wantToQuit = false;
        Command command     = parser.getCommand(text);

        if(command.isUnknown()) {
            gui.println("I don't know what you mean...");
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
                gui.println(player.GetRoom().printLocationInfo());  
        else if(command.getCommandWord().equals(CommandWords.CommandWord.PRINTITEM))
                gui.println(player.GetRoom().PrintItem());
        else if(command.getCommandWord().equals(CommandWords.CommandWord.SHOWITEM))
               gui.println(player.ShowItem());
        else if(command.getCommandWord().equals(CommandWords.CommandWord.UNDO))
                undo();
        else if(command.getCommandWord().equals(CommandWords.CommandWord.TAKE))
                take(command);
        else if (command.getCommandWord().equals(CommandWords.CommandWord.DROP))
                drop(command);
        else if(command.getCommandWord().equals(CommandWords.CommandWord.COOKIE))
                cookie();
        else if (command.getCommandWord().equals(CommandWords.CommandWord.QUIT))
                quit(command);
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
        gui.println("You are lost. You are alone. You wander");
        gui.println("in the horror house.");
        gui.println("\n");   
        gui.println("Your command words are:");
        gui.println(parser.GetCommandList());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            gui.println("Go where?");
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
            gui.println("There is no door!");
        }
        else {
            back.push(player.GetRoom());
            player.SetRoom(nextRoom); 
            gui.println(player.GetRoom().printLocationInfo());
            if(player.GetRoom().getImageName() != null)
                gui.showImage(player.GetRoom().getImageName());
       
        }
    }


    private void take(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            gui.println("take what?");
            return;
        }
       String item      = command.getSecondWord();
       Item e           = player.GetRoom().DeleteItem(item);
       if(e  ==  null)
            {   gui.println("we dont have this item\n");
                return;}
        player.AddItem(e);


    }

    private  void drop(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            gui.println("drop what?");
            return;
        }
       Item ele         = player.DropItem(command.getSecondWord());
       player.GetRoom().AddItem(ele);

    }
    //-----------------------------------------------

    private void look() 
    {   
        gui.println(player.GetRoom().getDescription());
    }
    //-----------------------------------------------
    private void eat()
    {
        gui.println("we have eat some meat");
    }
        //-----------------------------------------------

    private void showall()
    {
       gui.println(parser.GetCommandList());
    }
        //-----------------------------------------------

    private void undo()
    {   if(!back.isEmpty())
           { 
             player.SetRoom(back.pop());
             gui.println(player.GetRoom().getDescription());

           }
        else
        {
            gui.println("you have to move");
        } 
    }

    private void cookie()
    {
        gui.println(player.cookie());
    }

    //-----------------------------------------------

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private void quit(Command command) 
    {
            gui.println("Thank you for playing.  Good bye.");
           gui.enable(false);

    }
    
    
    

}
