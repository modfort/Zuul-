    
/**
 * Write a description of class Test here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Test
{
    private UserInterface gui;
	private Game engine;

    /**
     * Create the game and initialise its internal map.
     */
    public Test() 
    {
		engine = new Game();
		gui = new UserInterface(engine);
		engine.setGUI(gui);
    }
    public static void main(String args[])
    {
        Test t = new Test();
    }
}
