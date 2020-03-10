import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;
import java.util.Deque;
import java.util.Set;
import java.util.HashMap;

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
    private Room currentRoom;
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
        gui.println(currentRoom.getLongDescription());
        gui.showImage(currentRoom.getImageName());

    }


    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
       
        //create the list of item
        List<Item> element  = new ArrayList<Item>();    
        Item        cookie          = new Item("Cookie",0.0," ");
        Item        battery         = new Item("Battery", 5.0," ");
        Item        heal            = new Item("Heal", 2.5," ");
        Item        keysecret       = new Item("Keysecret", 1.0," ");
        Item        keymain         = new Item("Keymain", 1.0," ");
        Item        flashlight      = new Item("Flashlight", 5.0," ");

        element.add(battery);
        element.add(heal);
        element.add(keysecret);
        element.add(keymain);
        element.add(flashlight);
        element.add(cookie);


        // create the rooms
        Room outside             = new Room("outside","images/outside.jpg");
        Room mainhall            = new Room("mainhall","images/mainhall.jpg");
        Room kitchen             = new Room("kitchen","images/kitchen.jpeg");
        Room living              = new Room("living","images/living.jpg");
        Room mainstairs          = new Room("mainstairs","images/mainstairs.jpg");
        Room firstfloor          = new Room("firstfloor","images/firstfloor.jpg");
        Room bedroom             = new Room("bedroom","images/bedroom.jpg");
        Room bathroom            = new Room("bathroom","images/bathroom.jpg");
        Room stairs              = new Room("stairs","images/stairs.jpg");
        Room secondfloor         = new Room("secondfloor","images/secondfloor.jpeg");
        Room attic               = new Room("attic","images/attic.jpg");
        Room corridor            = new Room("corridor","images/corridor.jpg");
        Room secret              = new Room("secret","images/secret.JPG");
        Room torture             = new Room("torture","images/torture.jpg");

        outside.setExit("north", mainhall);
        mainhall.setExit("north", mainstairs);
        mainhall.setExit("east", kitchen);
        mainhall.setExit("south", outside);
        mainhall.setExit("west", living);
        kitchen.setExit("west", mainhall);
        living.setExit("east", mainhall);
        mainstairs.setExit("north", firstfloor);
        mainstairs.setExit("south", mainhall);
        firstfloor.setExit("north", stairs);
        firstfloor.setExit("east", bedroom);
        firstfloor.setExit("south", mainstairs);
        firstfloor.setExit("west", bathroom);
        bedroom.setExit("north", torture);
        bedroom.setExit("west", firstfloor);
        torture.setExit("south", bedroom);
        bathroom.setExit("east", firstfloor);
        stairs.setExit("north", secondfloor);
        stairs.setExit("east", corridor);
        stairs.setExit("south", firstfloor);
        secondfloor.setExit("north", attic);
        secondfloor.setExit("east", corridor);
        secondfloor.setExit("south", stairs);
        attic.setExit("south", secondfloor);
        corridor.setExit("north", secret);
        corridor.setExit("west", secondfloor);
        secret.setExit("south", corridor);

        currentRoom = outside; // start game outside

        //initilase the item in a room
        kitchen.AddItem(battery);
        living.AddItem(cookie);
        bedroom.AddItem(keysecret);
        bathroom.AddItem(heal);
        attic.AddItem(battery);
        secret.AddItem(flashlight);
        secret.AddItem(keymain);
       
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
   

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
                gui.println(currentRoom.getLongDescription());  
        else if(command.getCommandWord().equals(CommandWords.CommandWord.PRINTITEM))
                gui.println(currentRoom.PrintItem());
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
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            gui.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            back.push(currentRoom);
            player.SetRoom(nextRoom); 
            gui.println(currentRoom.getLongDescription());
            if(currentRoom.getImageName() != null)
                gui.showImage(currentRoom.getImageName());
       
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
       Item e           = currentRoom.DeleteItem(item);
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
       currentRoom.AddItem(ele);

    }
    //-----------------------------------------------

    private void look() 
    {   
        gui.println(currentRoom.getShortDescription());
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
             gui.println(currentRoom.getShortDescription());
            if(currentRoom.getImageName() != null)
             gui.showImage(currentRoom.getImageName());
       
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
