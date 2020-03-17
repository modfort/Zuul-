import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.awt.image.*;


/**
 * This class implements a simple graphical user interface with a text entry
 * area, a text output area and an optional image.
 * 
 * @author Michael Kolling
 * @version 1.0 (Jan 2003)
 */
public class UserInterface implements ActionListener
{
    private Parser          parser;
    private Game            engine;
    private JFrame          myFrame;
    private JTextField      entryField;
    private JTextArea       log;
    private JLabel          image;
    private JPanel     button;
    private JButton    buttonN, buttonS ,buttonE, buttonW, buttonU, buttonD;
    private JButton    buttonEat, buttonLook, buttonHelp;

    /**
     * Construct a UserInterface. As a parameter, a Game Engine
     * (an object processing and executing the game commands) is
     * needed.
     * 
     * @param gameEngine  The GameEngine object implementing the game logic.
     */
    public UserInterface(Game gameEngine)
    {
        engine = gameEngine;
        createGUI();
    }

    /**
     * Print out some text into the text area.
     */
    public void print(String text)
    {
        log.append(text);
        log.setCaretPosition(log.getDocument().getLength());
    }

    /**
     * Print out some text into the text area, followed by a line break.
     */
    public void println(String text)
    {
        log.append(text + "\n");
        log.setCaretPosition(log.getDocument().getLength());
    }

    /**
     * Show an image file in the interface.
     */
    public void showImage(String imageName)
    {
        URL imageURL = getClass().getClassLoader().getResource(imageName);
        if(imageURL == null)
            System.out.println("image not found");
        else {
            ImageIcon icon = new ImageIcon(imageURL);
            image.setIcon(icon);
            myFrame.pack();
        }
    }

    /**
     * Enable or disable input in the input field.
     */
    public void enable(boolean on)
    {
        entryField.setEditable(on);
        if(!on)
            entryField.getCaret().setBlinkRate(0);
    }

    /**
     * Set up graphical user interface.
     */
    private void createGUI()
    {
        myFrame = new JFrame("My Game");
        entryField = new JTextField(34);

        log = new JTextArea();
        log.setEditable(false);
        JScrollPane listScroller = new JScrollPane(log);
        listScroller.setPreferredSize(new Dimension(200, 200));
        listScroller.setMinimumSize(new Dimension(100,100));

        JPanel panel = new JPanel();
        image = new JLabel();
        makeBoutonBar();

        panel.setLayout(new BorderLayout());
        panel.add(image, BorderLayout.NORTH);
        panel.add(listScroller, BorderLayout.CENTER);
        panel.add(entryField, BorderLayout.SOUTH);
        panel.add(button, BorderLayout.EAST );

        myFrame.getContentPane().add(panel, BorderLayout.CENTER);

        // add some event listeners to some components
        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        entryField.addActionListener(this);

        myFrame.pack();
        myFrame.setVisible(true);
        entryField.requestFocus();
    }

    public void makeBoutonBar()
    {
        button = new JPanel();
        button.setLayout(new GridLayout(0,1,3,5));
        
        buttonN = new JButton("north");        
        buttonN.addActionListener(this);
        buttonS = new JButton("south");
        buttonS.addActionListener(this);
        buttonE = new JButton("east");
        buttonE.addActionListener(this);
        buttonW = new JButton("west");
        buttonW.addActionListener(this);
        buttonEat = new JButton("eat"); 
        buttonEat.addActionListener(this);
        buttonLook = new JButton("look"); 
        buttonLook.addActionListener(this);
        buttonHelp = new JButton("help"); 
        buttonHelp.addActionListener(this);

        button.add( buttonN);
        button.add( buttonS);
        button.add( buttonE);
        button.add( buttonW);
        button.add( buttonEat);
        button.add( buttonHelp);
        button.add( buttonLook);

    }

    /**
     * Actionlistener interface for entry textfield.
     */
    public void actionPerformed(ActionEvent e) 
    {   
        if(e.getSource() == buttonN){ 
            engine.processCommand("go north");}
        if(e.getSource() == buttonS){ 
            engine.processCommand("go south");}
        if(e.getSource() == buttonE){ 
            engine.processCommand("go east");}
        if(e.getSource() == buttonW){ 
            engine.processCommand("go west");}
        if(e.getSource() == buttonEat){ 
            engine.processCommand("eat");}
        if(e.getSource() == buttonLook){ 
            engine.processCommand("look");}
        if(e.getSource() == buttonHelp){ 
            engine.processCommand("help");}
        if(e.getSource() == entryField)
        // no need to check the type of action at the moment.
        // there is only one possible action: text entry
        processCommand();
    }

    /**
     * A command has been entered. Read the command and do whatever is 
     * necessary to process it.
     */
    private void processCommand()
    {
        boolean finished = false;
        String input = entryField.getText();
        entryField.setText("");

        engine.processCommand(input);
    }

    
}
