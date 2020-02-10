/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
import java.util.HashMap;
public class CommandWords
{
    // a constant array that holds all valid command words
    public enum CommandWord
    {
    // A value for each command word, plus one for unrecognized
    // commands.
    GO,
    QUIT,
    HELP,
    LOOK,
    EAT,
    SHOWALL,
    PRINTITEM,
    SHOWITEM,
    UNDO,
    TAKE,
    DROP,
    COOKIE,
    UNKNOWN;
    }
  //  public static final String[] validCommands = {
    //    "go", "quit", "look","help","eat","showall","printitem","undo","take","drop","showitem","cookie"
    //};
    private HashMap<String, CommandWord> validCommands;
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<String, CommandWord>();
        validCommands.put("go", CommandWord.GO);
        validCommands.put("help", CommandWord.HELP);
        validCommands.put("quit", CommandWord.QUIT);
        validCommands.put("showitem", CommandWord.SHOWITEM);
        validCommands.put("printitem", CommandWord.PRINTITEM);
        validCommands.put("look", CommandWord.LOOK);
        validCommands.put("drop", CommandWord.DROP);
        validCommands.put("take", CommandWord.TAKE);
        validCommands.put("eat", CommandWord.EAT);
        validCommands.put("cookie", CommandWord.COOKIE);

    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String ele)
    {
       return validCommands.containsKey(ele);
    }

    public CommandWord CommandWord(String key )
    {
        return validCommands.get(key);
    }
  
}
