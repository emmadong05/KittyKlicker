import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

public class Main {
	//these are all attributes that are either constants or used throughout the entire class
	private int windowHeight = 800, windowWidth = 1200, iconSize = 100, beat = 1000, expectedScore = 0, lifeCount = 0, score;
	private JLabel scoreLabel ; 
	private IconHandler iHandler = new IconHandler();
	private PlayHandler pHandler = new PlayHandler();
	private InstructionHandler inHandler = new InstructionHandler();
	private ReturnHandler rHandler = new ReturnHandler();
	//private ArrayList <JPanel> currentPanels = new ArrayList<JPanel>();
	private JPanel iconPanel = new JPanel();
	private JFrame window;
	private JPanel lifePanel = new JPanel();
	Timer timer = new Timer();
	Clip clip;
	
	public static void main(String[] args) {
		new Main ();			//creates Main object
	}

	public Main() {
		createGUI(); 			// creates GUI
		setMenuScreen();
	}
	
	public void createGUI() { //creates 
		//creates window and initializes the size, how it closes, background color, and title
		window = new JFrame();
		window.setSize(windowWidth, windowHeight);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(myColors.lavender);
		window.setTitle("Kitty Klicker");
		window.setLayout(null);
		window.setVisible(true);
	}
	
	public void clearScreen() { //clears the entire frame
		window.getContentPane().removeAll();
		window.setVisible(true);
	}
	
	public void setMenuScreen() { 
		window.setVisible(false);
		JPanel menuPanel = new JPanel();
		menuPanel.setBounds(500, 250, 200, 200); //centered panel to add buttons to
		menuPanel.setBackground(myColors.purple);
		window.add(menuPanel);
		menuPanel.setLayout(new GridLayout(2,1)); 
		
		JButton playButton = new JButton("Play!"); //creates a Play! button
		playButton.setFont(myFonts.comicSansFont); 
		playButton.setFocusPainted(false);
		playButton.addActionListener(pHandler); //adds listener that waits for a click, which takes user to game screen
		menuPanel.add(playButton);
		

		JButton instructionButton = new JButton("Instructions"); //creates an Instructions button
		instructionButton.setFont(myFonts.comicSansFont);
		instructionButton.setFocusPainted(false);
		instructionButton.addActionListener(inHandler); //adds listener that waits for a click, which takes user to instructions screen
		menuPanel.add(instructionButton);
		window.setVisible(true); //displays all of the added panels
	}
	
	public void addIcon(){ //creates and adds an icon to the window done
		iconPanel = new JPanel();
		timer = new Timer();
		int x = (int) (Math.random()*900.0) + 100; //randomly generates location of the next icon
		int y = (int) (Math.random()*500.0) + 100;
		iconPanel.setBounds(x,y,iconSize,iconSize);
		iconPanel.setBackground(myColors.lavender);
			
		ImageIcon one = new ImageIcon(getClass().getClassLoader().getResource("one.png")); //creates and initializes a "1" Icon
	  	JButton oneButton = new JButton();
	  	oneButton.setBackground(myColors.lavender);
  		oneButton.setFocusPainted(false);
  		oneButton.setBorder(null);
  		oneButton.setIcon(one);

		
		ImageIcon two = new ImageIcon(getClass().getClassLoader().getResource("two.png")); //creates and initializes a "2" Icon
	  	JButton twoButton = new JButton();
	  	twoButton.setBackground(myColors.lavender);
  		twoButton.setFocusPainted(false);
  		twoButton.setBorder(null);
  		twoButton.setIcon(two);
		
		
		ImageIcon three = new ImageIcon(getClass().getClassLoader().getResource("three.png")); //creates and initializes a "3" Icon
	  	JButton threeButton = new JButton();
	  	threeButton.setBackground(myColors.lavender);
	  	threeButton.setFocusPainted(false);
	  	threeButton.setBorder(null);
  		threeButton.setIcon(three);
		
		iconPanel.add(oneButton); //displays "1" icon
		window.add(iconPanel);
		window.setVisible(true);
  		
  		TimerTask task1 = new TimerTask() {  //switches to "2" icon
		    public void run() {
		    	iconPanel.remove(oneButton);
		    	iconPanel.setBounds(x,y,iconSize,iconSize);
				iconPanel.setBackground(myColors.lavender);
		    	  iconPanel.add(twoButton);
		    	  window.setVisible(true);	    
		    }
		};
  		
		TimerTask task2 = new TimerTask() { //switches to "3" icon
		    public void run() {
		    		iconPanel.remove(twoButton);
		    		iconPanel.setBounds(x,y,iconSize,iconSize);
		    		iconPanel.setBackground(myColors.lavender);
		    	  iconPanel.add(threeButton);
		    	  window.setVisible(true);	
		    }
		};
		
		TimerTask taskCat = new TimerTask() { //switches to click-able cat icon
		      public void run() {
		    	  iconPanel.remove(threeButton);
		    	  	iconPanel.setBackground(myColors.lavender);
		    	  	ImageIcon kitten = new ImageIcon(getClass().getClassLoader().getResource("cat1.png"));
		    	  	JButton iconButton = new JButton();
		    	  	iconButton.setBackground(myColors.lavender);
			  		iconButton.setFocusPainted(false);
			  		iconButton.setBorder(null);
			  		iconButton.setIcon(kitten);
			  		iconButton.addActionListener(iHandler);
			  		iconPanel.add(iconButton);
			  		//currentPanels.add(iconPanel);	
			  		window.setVisible(true);  
		      }
		};
		
		TimerTask taskRemove = new TimerTask() { //removes icon
			public void run() {
				window.remove(iconPanel);
				window.repaint();
				window.validate();
				window.setVisible(true);
			}
		};
		
		timer.schedule(task1, 1000); //displays 1,2,3, and cat icon 1 second apart and removes the cat icon after 0.9 seconds
		timer.schedule(task2, 2000);
		timer.schedule(taskCat, 3000);
		timer.schedule(taskRemove, 3900);
	}
	
	
	public class IconHandler implements ActionListener{  //responds to mouse click for icons
		public void actionPerformed(ActionEvent event) {  //updates score, score display, and changes icon to be blank
			score+= 10;
			scoreLabel.setText(score + "  points           Lives:");
			iconPanel.removeAll();
			iconPanel.setBounds(0,0,iconSize,iconSize);
			iconPanel.setBackground(myColors.lavender);
			window.setVisible(true);
		}
	}
	
	
	public class PlayHandler implements ActionListener{  //responds to mouse click for play button
		
		public void actionPerformed(ActionEvent event) {  //switches to game screen
			clearScreen();
			try {
				setGameScreen();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class InstructionHandler implements ActionListener{  //responds to mouse click for play button
		public void actionPerformed(ActionEvent event) { //switches to instruction screen
			clearScreen();
			setInstructionScreen();
		}
	}
	
	public class ReturnHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) { //returns to menu
			clearScreen();
			setMenuScreen();
			timer.cancel(); //ends the timer 
			clip.stop();
		}
	}
	
 	public void setGameScreen() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		window.setVisible(false);
		
		score = 0;
		JPanel scorePanel = new JPanel();  //creates score panel
		scorePanel.setBounds(0,15,200,100);
		scorePanel.setBackground(myColors.lavender);
		window.add(scorePanel);
		
		scoreLabel = new JLabel(score + "  points           Lives:"); //sets up score display
		scoreLabel.setForeground(Color.black);
		scoreLabel.setFont(myFonts.comicSansFont);
		scorePanel.add(scoreLabel);
		
		lifePanel.removeAll();								//creates life display 
		lifePanel.setBounds(200,10,150,50);
		lifePanel.setBackground(myColors.lavender);
		window.add(lifePanel);
		
		lifePanel.setLayout(new GridLayout(1,3)); 			//sets up life display: creates the icons and seperate panels for each heart
		ImageIcon heart = new ImageIcon(getClass().getClassLoader().getResource("heart_50x50.png"));
		JButton heart1 = new JButton();
		heart1.setIcon(heart);
		heart1.setBackground(myColors.lavender);
		heart1.setFocusPainted(false);
		heart1.setBorder(null);
		JButton heart2 = new JButton();
		heart2.setIcon(heart);
		heart2.setBackground(myColors.lavender);
		heart2.setFocusPainted(false);
		heart2.setBorder(null);
		JButton heart3 = new JButton();
		heart3.setBackground(myColors.lavender);
		heart3.setFocusPainted(false);
		heart3.setBorder(null);
		heart3.setIcon(heart);
		lifePanel.add(heart1);
		lifePanel.add(heart2);
		lifePanel.add(heart3);
		
		JPanel returnPanel = new JPanel();			//creates and sets up return button
		returnPanel.setBounds(1000,0,200,100);
		returnPanel.setBackground(myColors.lavender);
		window.add(returnPanel);
		
		JButton returnButton = new JButton("Return to Menu");
		returnButton.setFont(myFonts.comicSansFont); 
		returnButton.setFocusPainted(false);
		returnButton.addActionListener(rHandler);
		returnPanel.add(returnButton);

		window.setVisible(true);
		startGame();								//starts the game
	}
	
 	public void gameOver() { //returns to menu screen
		clearScreen();
		setMenuScreen();
	}
 	
	public void setInstructionScreen() {
		window.setVisible(false);
		JPanel returnPanel = new JPanel();		//creates and sets up return button
		returnPanel.setBounds(1000,0,200,100);
		returnPanel.setBackground(myColors.lavender);
		window.add(returnPanel);
		
		JButton returnButton = new JButton("Return to Menu"); 
		returnButton.setFont(myFonts.comicSansFont); 
		returnButton.setFocusPainted(false);
		returnButton.addActionListener(rHandler);
		returnPanel.add(returnButton);
		
		JPanel instructionPanel = new JPanel();		//creates and displays instructions panel
		instructionPanel.setBounds(200,180,800,400);
		instructionPanel.setBackground(myColors.purple);
		instructionPanel.setLayout(new GridLayout(4,1));
		JLabel instructions1 = new JLabel("      ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~INSTRUCTIONS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		JLabel instructions2 = new JLabel("1. Every player had 3 hearts!");
		JLabel instructions3 = new JLabel("2. Click on each cat icon for points. You will lose a heart for every cat misssed!");
		JLabel instructions4 = new JLabel("3. There will be a 3, 2, 1 countdown before each cat appearing on the screen!");

		instructionPanel.add(instructions1);
		instructionPanel.add(instructions2);
		instructionPanel.add(instructions3);
		instructionPanel.add(instructions4);
		
		window.add(instructionPanel);
		
		window.setVisible(true);
	}
	
	public void startGame() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		File music= new File("music.wav");
		clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(music));
		clip.start();
		addIcon();
		expectedScore = 0;
		lifeCount = 3;
		TimerTask taskGame = new TimerTask(){
		      public void run(){
		    	  addIcon(); 	    	  
		    	  expectedScore+=10;
		    	  if(score != expectedScore) { //reduces a heart if the score doesn't match the expected score (indicates player missed an icon)
						expectedScore = score;
						lifeCount-=1;
						lifePanel.remove(lifeCount);
		    	  }
		    	  if(lifeCount == 0) { //ends the game, returns to menu, and stops the timer
		    		  timer.cancel();
		    		  clip.stop();
		    		  gameOver();	
		    	  }  
		      }
		};
		timer.scheduleAtFixedRate(taskGame, 4000, 4000);  //adds a new icon every 4 seconds and checks to see if the player should lose a heart
	}
	
}
