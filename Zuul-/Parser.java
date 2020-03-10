import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Parser 
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand(String inputLine) 
    {
           // will hold the full input line
        CommandWords  command = new CommandWords();
        CommandWords.CommandWord type;
        String word1 = null;
        String word2 = null;

       
        StringTokenizer tokenizer = new StringTokenizer(inputLine);

        // Find up to two words on the line.
      
    
        if(tokenizer.hasMoreTokens())
            word1 = tokenizer.nextToken();      // get first word
        else
            word1 = null;
        if(tokenizer.hasMoreTokens())
            word2 = tokenizer.nextToken();      // get second word
        else
            word2 = null;
        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        if(command.isCommand(word1)) {
                    type=command.get(word1);

            return new Command(type, word2);
        }
        else {
            return new Command(null, word2); 
        }
    }



   /* public void showAll()
        {                
            for(CommandWords.CommandWord command : commands.GetCommand()) {
               System.out.println(command + " ");
         }        
    }
*/
    public String GetCommandList()
    {
        return commands.GetCommandList();   
    }
}
