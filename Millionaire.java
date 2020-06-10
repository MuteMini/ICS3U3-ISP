/*
Min Kang
Ms.Krasteva
December 20th, 2019

REQUIRES - Times New Roman, Century Gothic
The shuffling of the answer positions have took from this website:      https://stackoverflow.com/questions/196017/unique-non-repeating-random-numbers-in-o1

This program allows the user to play a modified version of the famous trivia game, "Who Wants To Be A Millionaire". This program uses many logic
statments to check multiple user status, such as if the user has failed the question, has answered it, or has won the entire game.

The user will be able to go through these screens:

    SPLASHSCREEN          -  Allows the user to watch a simple animation to introduce the game.
    MAINMENU              -  Allows the user to choose between four screens, to play the game, to see instruction, to see highscores, and to exit the game.
    DISPLAY               -  Allows the user to see the question they must answer.   
    SHOWANSWER & GETNAME  -  Allows the user to see if their answer was correct or not. If the game is over, lets the user type in their name.
    INSTRUCTIONS          -  Shows the instructions on how the game plays and how it works.
    HIGHSCORE             -  Shows the user the 10 ten past highscores based on the file saved.
    GOODBYE               -  Shows the user some goodbye message and closes the console.

   -------------------------------------------------------Global Variable Table-------------------------------------------------------

	Variable Name                   Type                        Purpose
    
	c                               Console                     Lets the program use the HSA Console and its functions.                                                             functions.     
	  
	hasFinished                     boolean                     Stores if the game has been finished, by failing a question or 
								    by solving all of them.
	gameChoice                      char                        Stores the user choice inside display (Game).
	threeText                       String                      Stores the string created by the third lifeline.
	choice                          int                         Stores the user choice inside mainMenu.
	questionDif                     int                         Stores what level the user is currently at. Goes from 1 ~ 15.
	
	highScore                       String[][]                  Stores the highscore from the file and allows the program to edit it.   
	questionList                    String[][]                  Stores all of the questions from the "question.wwtmbam" file.
	answerPos                       int[]                       Stores the specific question that the program is looking at, as well as
								    the randomized answer positions.
	lineUsed                        int[]                       Stores the status of the lifelines used in the current run of the game. 0 means
								    never used, 1 means used in the question, and 2 means has been used.
					
	darkGreen                       Color                       Stores the custom colors used in the program.
	lightGreen
	aqua
	darkBlue
	lightBlue                                                            
*/  

import java.awt.*; //imports Java classes.
import java.io.*;
import javax.swing.JOptionPane;
import hsa.Console;

public class Millionaire {

    //globally declares the variables.
    Console c;
    boolean hasFinished;
    char gameChoice;
    String threeText;
    int choice;
    int questionDif;
    
    //globally declares the arrays.
    String[][] highScore = new String[10][3];
    String[][] questionList = new String[75][5];
    int[] answerPos = new int[5];
    int[] lineUsed = new int[3];

    //globally declares and initialises the colors used.
    Color darkGreen = new Color(5, 100, 64);
    Color lightGreen = new Color(180, 255, 190);
    Color aqua = new Color(60, 145, 232);
    Color darkBlue = new Color(30, 105, 200);
    Color lightBlue = new Color(173, 216, 230);
    
    /*
    CONSTRUCTOR METHOD
    
    This method contains the initialization of objects and variables used in the program that stays constant.
    */
    public Millionaire(){

	//initialises console.
	c = new Console();
	
	//initialises the game variables to the default ones.
	questionDif = 1;
	hasFinished = false;
	gameChoice = '1';
	threeText = "";
    }
    
    /*
    SPLASHSCREEN METHOD

    -------------------------------------------------Local Variable Table-------------------------------------------------

	Variable Name                   Type                        Purpose
	
	i                               int (loop)                  This loop counter allows for the animation of the loading
								    screen. It is used to add or subtract from the animated object.
	       
							    
    This method contains all of the code necessary to create a small animation to introduce the program. It uses Thread.sleep to 
    show the halt the program for a second before continuing on.
    
    LOOP USAGE
    
	Multiple for loops are used to animate the text and dollar signs on the screen.
    
    IF STATEMENT USAGE
    
	Lots of logic is used to make sure that the drawing that needs to stay is not covered based on the loop count.
	
    TRY BLOCK USAGE
    
	The try block is used in this case to halt the program to give a sense of animation. This may cause multitude of errors, so
	for the program to work properly, it needs to be caught.
    */
    public void splashScreen(){
    
	//draws the background color.
	c.setColor(lightBlue);
	c.fillRect(0, 0, 640, 500);

	//sets the font for the text in the for loop.
	c.setFont(new Font("Times New Roman", 1, 40));

	for(int i = 300; i >= 0; i-=2){
	
	    //draws the animated text
	    c.setColor(darkGreen);
	    c.drawString("Who", 120, 200+i);
	    c.drawString("Wants", 217, 200-i);
	    c.drawString("To", 345, 200+i);
	    c.drawString("Be", 412, 200-i);
	    c.drawString("A", 475, 200+i); 
	    
	    //freezed the program for 10 milliseconds.
	    try{
		Thread.sleep(10);
	    } catch(Exception e){
	    }  

	    //replaces the original drawing with text that exactly overlaps it with the background color ONLY IF the for loop is not at its last count.
	    if(i != 0){   
		c.setColor(lightBlue);
		c.drawString("Who", 120, 200+i);
		c.drawString("Wants", 217, 200-i);
		c.drawString("To", 345, 200+i);
		c.drawString("Be", 412, 200-i);
		c.drawString("A", 475, 200+i); 
	    }
	}

	for(int i = 60; i >= 0; i-=2){
	
	    //draws the animated text with a smaller font.
	    c.setColor(darkGreen);
	    c.setFont(new Font("Times New Roman", 1, 60-i));
	    c.drawString("MILLIONAIRE!", 105, 250);
	    
	    //freezes the program for 10 milliseconds.
	    try{
		Thread.sleep(10);
	    } catch(Exception e){
	    }  

	    //replaces the original drawing with text that exactly overlaps it with the background color ONLY IF the for loop is not at its last count.
	    if(i != 0){   
		c.setColor(lightBlue);
		c.drawString("MILLIONAIRE!", 105, 250);
	    }
	}

	for(int i = 0; i <= 10; i++){
	
	    //draws the first set of dollar signs - if the count is divisible by 2, it shows it, if not it covers it.
	    if(i % 2 == 0)
		c.setColor(darkGreen);
	    else
		c.setColor(lightBlue);
	    c.drawArc(40, 110, 40, 30, 0, 275);
	    c.drawArc(40, 140, 40, 30, 175, 275);
	    c.drawLine(65, 105, 65, 175);
	    c.drawLine(55, 105, 55, 175);
	    c.drawArc(560, 110, 40, 30, 0, 275);
	    c.drawArc(560, 140, 40, 30, 175, 275);
	    c.drawLine(585, 105, 585, 175);
	    c.drawLine(575, 105, 575, 175);
	    c.drawArc(300, 340, 40, 30, 0, 275);
	    c.drawArc(300, 370, 40, 30, 175, 275);
	    c.drawLine(325, 335, 325, 405);
	    c.drawLine(315, 335, 315, 405);
	    
	    //draws the second set of dollar signs with flipped logic.
	    if(i % 2 != 0)
		c.setColor(darkGreen);
	    else
		c.setColor(lightBlue);
	    c.drawArc(40, 260, 40, 30, 0, 275);
	    c.drawArc(40, 290, 40, 30, 175, 275);
	    c.drawLine(65, 255, 65, 325);
	    c.drawLine(55, 255, 55, 325);
	    c.drawArc(560, 260, 40, 30, 0, 275);
	    c.drawArc(560, 290, 40, 30, 175, 275);
	    c.drawLine(585, 255, 585, 325);
	    c.drawLine(575, 255, 575, 325);
	    c.drawArc(300, 30, 40, 30, 0, 275);
	    c.drawArc(300, 60, 40, 30, 175, 275);
	    c.drawLine(325, 25, 325, 95);
	    c.drawLine(315, 25, 315, 95);
	    
	    //freezes the program for 0.5 seconds.
	    try{
		Thread.sleep(500);
	    } catch(Exception e){
	    }
	}
	
	//freezes the program for a second to give it time to get into mainMenu.
	try{
	    Thread.sleep(1000);
	} catch(Exception e){
	}
    }
    
    /*
    FILELOADING METHOD

    -------------------------------------------------Local Variable Table-------------------------------------------------

	Variable Name                   Type                        Purpose
	
	in                              BufferedReader              Allows the program to read files.
					
	i                               int (loop)                  This loop counter allows for looping through each index of an array.
								    
	tempStr                         String                      This variable temporarly stores one file line as a string. 
								    It will also allow for error trapping against wrong data type inputs.
							    
								    
    This method contains the code to open the two files needed in the program. It first checks if the first two lines match what the
    program is specifically checking for. Then, it stores each needed information into two arrays. All of this is errortrapped so the
    program does not continue on without opening the needed files.
    
    LOOP USAGE
    
	Only one while loop is used to keep the program stuck if the questions file is corrupt or missing. Then, two nested for loops 
	are used to take each line and add it to the array that the program can use.
   
    IF STATEMENT USAGE
    
	Some if statements are used to check if the first two lines match exactly what is written in the code, while some other is used to
	turn "<>" as null.
   
    TRY BLOCK USAGE
    
	The try blocks is used in this case to catch any errors caused when using bufferedReader. BufferedReader throws an IOException,
	so it must have a try block to hold it. An FileNotFoundException is thrown if the file is not found and an Exception is thrown if 
	a line is missing or does not fit the front two lines.        
    */
    public void fileLoading(){
    
	//errortraps file loading, also acts as a text loader for mainMenu.
	while(true){
	    try{ 
		//trys to locate the file.
		BufferedReader in = new BufferedReader(new FileReader("questions.wwtbam"));
		
		//checks the first two lines to see if it is the correct file.
		if(!in.readLine().equals("Created By MIN KANG - Encrypted by ICSPOWER protocol"))
		    throw new Exception();
		if(!in.readLine().equals("DO NOT OPEN IF YOU DO NOT WANT TO BE ARRESTED"))
		    throw new Exception();
		 
		//skips a line.
		in.readLine();
		 
		//puts everything in the file to an array.
		for(int i = 0; i < 75; i++){
		    for(int j = 0; j < 5; j++){
			//declares and initialises tempStr as the line in the file.
			String tempStr = in.readLine();
			//adds the string into the array.
			questionList[i][j] = tempStr;
		    }
		}
		//closes the stream and breaks out of the loop.
		in.close();
		break;
	    }
	    //if the file cannot be found, run this.
	    catch(FileNotFoundException e){
		JOptionPane.showMessageDialog(null, "There is no file found with that name! Please re-ask Min for the accurate version.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    //if for any reason the bufferedreader broke, run this.
	    catch(Exception e){
		JOptionPane.showMessageDialog(null, "That was not a compatible file/the file is corrupted! Please re-ask Min for the accurate version." , "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	//errortraps file loading, also acts as a text loader for mainMenu.
	try{ 
	    //trys to locate the file.
	    BufferedReader in = new BufferedReader(new FileReader("highscore.wwtbam"));
		
	    //checks the first two lines to see if it is the correct file.
	    if(!in.readLine().equals("Created By MIN KANG - Encrypted by ICSPOWER protocol"))
		throw new Exception();
	    if(!in.readLine().equals("DO NOT OPEN IF YOU DO NOT WANT TO BE ARRESTED"))
		throw new Exception();
		 
	    //skips a line.
	    in.readLine();
		 
	    //puts everything in the file to an array.
	    for(int i = 0; i < 10; i++){
		for(int j = 0; j < 3; j++){
		    //declares and initialises tempStr as the line in the file.
		    String tempStr = in.readLine();
		    //adds the string into the array depending if it's a null or not.
		    if(tempStr.equals("<>"))
			highScore[i][j] = null;
		    else
			highScore[i][j] = tempStr; 
		}
	    }
	    //closes the stream and breaks out of the loop.
	    in.close();
	}
	//if for any reason the bufferedreader broke, run this.
	catch(Exception e){
	    JOptionPane.showMessageDialog(null, "A highscore file was not found/was corrupted. The program will create a new highscore file." , "Error", JOptionPane.ERROR_MESSAGE);
		
	    //empties all parts of the array.
	    for(int i = 0; i < 10; i++){
		for(int j = 0; j < 3; j++){
		    highScore[i][j] = null;
		}
	    }
	}
    }
    
    /*
    MAINMENU METHOD

    -----------------------------------------------Local Variable Table-----------------------------------------------

	Variable Name                   Type                        Purpose

	tempChoice                      String                      This variable temporarly stores the user input as a
								    String so the HSA Console will not crash. It allow for 
								    error trapping against wrong data type inputs.

								    
    This method will be reached after splashScreen is finished it's animation. It will ask a user for one of four inputs,
    1 for the playing the game, 2 for the instruction, and 3 to highscore, and 4 to exit. Anything outside of that given range 
    will be caught as an error. The choice variable will store this input.
    
    LOOP USAGE
    
	One while loop is used in this method to errortrap against wrong inputs. Anything outside of 1, 2, 3, and 4 is not a 
	valid input, therefore if it IS outside, an if statement throws an error, re-asking the user for an input. 
	
    TRY BLOCK USAGE
    
	The try block is used in this case to catch the thrown error when the input does not match the given range (1, 2, or 3) or is not an integer.
	The catch block will then pop out an error window and reset the while loop.
    */
    public void mainMenu(){
    
	//calls title into display.
	title();
	
	//draws the buttons the user can type.
	c.setColor(lightGreen);
	c.fillRoundRect(120, 125, 400, 60, 40, 40);
	c.fillRoundRect(120, 205, 400, 60, 40, 40);
	c.fillRoundRect(120, 285, 400, 60, 40, 40);
	c.fillRoundRect(120, 365, 400, 60, 40, 40);
	c.setColor(darkGreen);
	c.drawRoundRect(120, 125, 400, 60, 40, 40);
	c.drawRoundRect(120, 205, 400, 60, 40, 40);
	c.drawRoundRect(120, 285, 400, 60, 40, 40);
	c.drawRoundRect(120, 365, 400, 60, 40, 40);

	//draws the string onto the buttons.
	c.setFont(new Font("Century Gothic", 0, 30));
	c.drawString("1. PLAY GAME!", 210, 165);
	c.drawString("2. VIEW INSTRUCTIONS!", 160, 245);
	c.drawString("3. VIEW HIGHSCORES!", 163, 325);
	c.drawString("4. EXIT!", 270, 405);

	//errortraps user input.
	while(true){
	    try{
		//gathers the user input.
		String tempChoice = c.getChar() + "";
		choice = Integer.parseInt(tempChoice);

		//breaks if everything was successful.
		break;
	    }
	    //if parsing threw an exception, give an error box.
	    catch(NumberFormatException e){
		JOptionPane.showMessageDialog(null, "That was not a number! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    //if the number was not in the range, give an error box.
	    catch(ArithmeticException e){
		JOptionPane.showMessageDialog(null, "That was not a choice given! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }
    
    /*
    DISPLAY METHOD
    
    -----------------------------------------------Local Variable Table-----------------------------------------------

	Variable Name                   Type                        Purpose

	i                               int (loop)                  This loop counter allows for making the code smaller by allowing certain values to be
								    affected by the counter.
								    
	x1                              int[]                       Used to store the the positions of the polygons it draws.
	y1
	x2
	y2
	x3a
	x3b
	y3a
	y3b

	tempQuestion                    String[]                    Stores one row of the questionList that the game is looking at. The display can now use this 
								    to display multiple things.
	tempLine                        String                      Stores 9 words using split to print out the longer questions with well spaced out questions.
	
	spacer                          int                         Stores the increment of how much y value is added by when one set of words is printed.
	 
	xAdd                            int                         Stores the increments of certain objects that are repeated, such as the ovals for the lifelines and
	yAdd                                                        answer button crosses.
	
	
    This method can only be reached when the user presses 1 in mainMenu. It draws the question, draws the choices in random order, and prints out
    the other lifeLine text/crosses that it draws.
    
    LOOP USAGE
    
	Multiple for loops are used to draw some shapes that are repeatedly used in the program. It makes use of the loop counter and how it has a defined pattern, meaning
	that some values can be changed by multiplying or/and modulo the loop counter.
	
    IF STATEMENT USAGE
	
	In this code, most of the if statementschecks what the user has affected, and displays something according to this change. The first if statement in this 
	method show this. If the user entered one of the numbers, the program will generate another question, else a lifeline will be used. One if statement, which
	allows the splitting of long questions, check if the string to be printed is ready to be printed.
    */
    public void display(){

	//declares and initialises local variables.
	int[] x1 = {395, 400, 413, 418, 417, 403, 408};
	int[] y1 = {125, 130, 117, 122, 107, 107, 112};
	int[] x2 = {415, 410, 397, 392, 393, 407, 402};
	int[] y2 = {125, 120, 133, 128, 142, 143, 138};       
	int[] x3a = {40, 70, 270, 300, 270, 70};
	int[] x3b = {340, 370, 570, 600, 570, 370};
	int[] y3a = {280, 250, 250, 280, 310, 310};
	int[] y3b = {360, 330, 330, 360, 390, 390};

	//calls title.
	title();
	
	//if the user answered a question before, generate a question. Else, call useLifeline.
	if(gameChoice >= '1' && gameChoice <= '4')
	    generateQuestion();
	else
	    useLifeline();
	    
	//draws the price of the question displayed.
	c.setColor(darkGreen);
	c.setFont(new Font("Century Gothic", 1, 20));
	c.drawString("This question is worth $" + getScore(questionDif) + "!", 30, 140);
	
	//declares a local array to store the each word from the question generated.
	String[] tempQuestion = questionList[answerPos[0]][0].split(" ");
	
	//declares local variables to draw a string in large font without overflow.
	String tempLine = "";
	int spacer = 0;
	
	//for the length of the array, draw it in a way that it will not flow over the screen.
	for(int i = 0; i < tempQuestion.length; i++){
	    //adds a word into the string.
	    tempLine += tempQuestion[i] + " ";
	    
	    //if the word is ready to be drawn, draw the string, empty tempLine, and increment spacer.
	    if(i == tempQuestion.length-1 || (i != 0 && i%9 == 0)){
		c.setFont(new Font("Century Gothic", 0, 18));
		c.drawString(tempLine, 30, 180+spacer);
		tempLine = "";
		spacer+=20;
	    }
	}

	//draws the buttons (lifelines) that the user can use.
	c.setColor(aqua);
	c.drawArc(380, 100, 50, 50, 160, 310);
	c.drawArc(460, 100, 50, 50, 160, 300);
	c.drawArc(540, 100, 50, 50, 160, 310);
	c.setFont(new Font("Century Gothic", 1, 20));
	c.drawString("Q", 380, 115);
	c.drawString("W", 460, 115);
	c.drawString("E", 543, 115);
	c.setColor(darkBlue);
	c.fillPolygon(x1, y1, 7);
	c.fillPolygon(x2, y2, 7);
	c.setFont(new Font("Century Gothic", 1, 28));
	c.drawString("50", 469, 135);
	c.fillRoundRect(555, 110, 5, 30, 1, 10);
	c.fillRoundRect(555, 110, 10, 10, 10, 1);
	c.fillRoundRect(555, 130, 10, 10, 10, 1);
	c.drawArc(552, 117, 15, 15, -30, 60);
	c.drawArc(552, 115, 20, 20, -40, 80);
	c.drawArc(552, 112, 25, 25, -50, 90); 
	
	//goes through each boolean of the lineUsed array and draws an X if it has been used.
	for(int i = 0; i < 3; i++){
	    if(lineUsed[i] > 0){ 
		for(int j = 0; j < 5; j++){
		    //declares and initialises a value to hold the increment of the X's
		    int xAdd = 80 * i + j;
		    //draws the red X.
		    c.setColor(Color.red);
		    c.drawLine(375+xAdd, 100, 425+xAdd, 145);
		    c.drawLine(425+xAdd, 100, 375+xAdd, 145);
		}
	    }
	}
	
	//draws the answer buttons with the corresponding number keys.
	c.setColor(aqua);
	c.fillPolygon(x3a,y3a,6);
	c.fillPolygon(x3b,y3a,6);
	c.fillPolygon(x3a,y3b,6);
	c.fillPolygon(x3b,y3b,6);
	c.setColor(lightBlue);
	c.fillOval(55, 240, 30, 30);
	c.fillOval(355, 240, 30, 30);
	c.fillOval(55, 320, 30, 30);
	c.fillOval(355, 320, 30, 30);
	c.setColor(aqua);
	c.drawOval(55, 240, 30, 30);
	c.drawOval(355, 240, 30, 30);
	c.drawOval(55, 320, 30, 30);
	c.drawOval(355, 320, 30, 30);
	c.setFont(new Font("Century Gothic", 1, 22));
	c.drawString("1", 63, 263);
	c.drawString("2", 365, 263);
	c.drawString("3", 63, 343);
	c.drawString("4", 365, 343);
	
	//draws the text of the answer buttons.
	c.setColor(darkGreen);
	c.setFont(new Font("Century Gothic", 1, 20));
	c.drawString(questionList[answerPos[0]][answerPos[1]], 70, 288);
	c.drawString(questionList[answerPos[0]][answerPos[2]], 370, 288);
	c.drawString(questionList[answerPos[0]][answerPos[3]], 70, 368);
	c.drawString(questionList[answerPos[0]][answerPos[4]], 370, 368);
	
	//if the second line has been set to be used,
	if(lineUsed[1] == 1){
	    //for all positions of answerPos,
	    for(int i = 1; i < 5; i++){
		//if the position is bigger than 2 (targetting 3 and 4)
		if(answerPos[i] > 2){
		    //draws an X on the location of that fake answer. 
		    for(int j = 0; j < 5; j++){
			int xAdd = 300*((i-1)%2)+j;
			int yAdd = 80*((int)(i-1)/2)+j;
			c.setColor(Color.red);
			c.drawLine(40+xAdd, 250+yAdd, 300+xAdd, 310+yAdd);
			c.drawLine(40+xAdd, 249+yAdd, 300+xAdd, 309+yAdd);
			c.drawLine(40+xAdd, 310+yAdd, 300+xAdd, 250+yAdd);
			c.drawLine(40+xAdd, 311+yAdd, 300+xAdd, 251+yAdd);
		    }
		}
	    }
	}
	
	//if the third line has been set to be used, draw the friend's message on the screen.
	if(lineUsed[2] == 1) {
	    c.setColor(darkBlue);
	    c.setFont(new Font("Century Gothic", 3, 18));
	    c.drawString(threeText, 20, 440);
	}
    }
    
    /*
    ASKDATA METHOD
						    
    This method will only be reached when the user enters something from display. This holds the errortrapping for what the user enters in
    from display.
    
    LOOP USAGE
	
	One infinite while loop is used so that the program does not continue with a value that the program cannot handle.
    
    IF STATEMENT USAGE
    
	One if statement in this while loop checks if the value held by a variable is not part of the options given. If this statement is true,
	the statement throws an NumberFormatException.
	
    TRY BLOCK USAGE
    
	The try block is used in this case catch the NumberFormatException thrown by the code inside the try block. This will then show an error box 
	to tell the user to try again with a valid input.
    */
    public void askData(){

	//errortraps user input.
	while(true){
	    try{
		//gathers the user input.
		gameChoice = c.getChar();
		
		//if the character is not a certain ascii char, throw an exception.
		if((gameChoice < '1' || gameChoice > '4') && gameChoice != 'q' && gameChoice != 'Q' && gameChoice != 'w' && gameChoice != 'W' && gameChoice != 'e' && gameChoice != 'E')
		    throw new NumberFormatException();
		    
		//breaks if everything was successful.
		break;
	    }
	    //if parsing threw an exception, give an error box.
	    catch(NumberFormatException e){
		JOptionPane.showMessageDialog(null, "That was not one of the given options! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }

    /*
    SHOWANSWER METHOD

    -----------------------------------------------Local Variable Table-----------------------------------------------

	Variable Name                   Type                        Purpose

	i                               int (loop)                  This loop counter allows for going through each part of an array and animation.

	numChoice                       int                         This variable holds the numerical value that the user
								    entered. This is done by casting the char into a int and
								    subtracting 48.
								    
	ANIMATE_TEXT                    final String                This holds the animated text when you win, as it need to be
								    took a part at a time. This will not change, therefore this local variable is an
								    constant. 
			       
								    
    This method will be reached after the user answers the question given from display. This will check if the user got the correct answer and tell
    their status based on if they won or not.
    
    LOOP USAGE
    
	This program uses two for loops. The first one is used to fill an array if it is a certain value, and the other is used to animate the winning text with
	flashing colors.
    
    IF STATEMENT USAGE
    
       One if statement with another nested if statement is used in this program. The top checks if the user got the answer correct, and then the nested
       checks if the user has solved the last question.
	
    TRY BLOCK USAGE
    
	The try block is used in this case to halt the program to give a sense of animation. This may cause multitude of errors, so
	for the program to work properly, it needs to be caught. The first holds the message for a while to give suspension, while the second allows
	the final victory text to look animated.
    */
    public void showAnswer(){
	
	//calls title.
	title();
	
	//converts the character gameChoice into a number.
	int numChoice = (int)gameChoice - 48;
	
	//empties the threeText variable.
	threeText = "";
	
	//draws the text giving anticipation to the user.
	c.setColor(darkGreen);
	c.setFont(new Font("Century Gothic", 0, 30));
	c.drawString("You have...", 50, 200);
	
	//pauses the program for a second.
	try{
	    Thread.sleep(1000);
	} catch(Exception e){
	}
	
	//Tells you if the answer was correct or not.
	if(answerPos[numChoice] == 1){
	
	    //sets second and third lineUsed variable to store it's been used up if it's storing a 1.
	    for(int i = 1; i < 3; i++){
		if(lineUsed[i] == 1)
		    lineUsed[i] = 2;
	    }
	    
	    //if the question was the last one,
	    if(questionDif == 15){
		//declares a constant local variable to store the animation text.
		final String ANIMATE_TEXT = "WON THE GAME!!!";
	    
		//shows a little animation for the user.
		for(int i = 0; i <= 15; i++){
		    //changes the color of the text based on the loop counter.
		    if(i % 3 == 0)
			c.setColor(darkGreen);
		    else if(i % 3 == 1)
			c.setColor(Color.red);
		    else 
			c.setColor(Color.yellow);
		    c.drawString(ANIMATE_TEXT.substring(0,i), 220, 200);
		    //pauses the program for a second.
		    try{
			Thread.sleep(200);
		    } catch(Exception e){
		    }
		}
		
		//sets the finished varaible to true, ending the while loop.
		hasFinished = true;
	    } 
	    //if not, tell the user that the question has been solved.
	    else {
		c.drawString("SOLVED THE QUESTION!", 220, 200);
		questionDif++;
	    }
	} 
	//else the user did not get the correct answer, tells the user they have gotten it wrong and breaks the while loop.
	else {
	    c.drawString("Failed the question.", 220, 200);
	    hasFinished = true;
	}
	
	//calls pauseProgram.
	pauseProgram();
    }
    
    /*
    GETNAME METHOD
    
    -----------------------------------------------Local Variable Table-----------------------------------------------

	Variable Name                   Type                        Purpose

	i                               int (loop)                  This loop counter allows for going through each part of an array.

	tempInput                       char                        Stores the user key strokes.
	tempName                        String                      This variable stores the full user input, storing each character grabbed by 
								    tempInput as a connected string.
			       
	lineCount                       int                         Stores how much lines has bene used by the user.
	
	rowScore                        int                         Stores the parsed integers from the highScore array to be compared.
	rowLine
	
		
    This method will be reached after the user fails or wins the current game. This will ask the user for a name and then update the array, only storing the top 10.
    It tells the user with drawString and also draws the edited string as the program continues.
    
    LOOP USAGE
    
	The program uses multiple for loops to go through each row of arrays. A while loop is used to errortrap the name input, as the name cannot go past a certain letter
	range or length.
    
    IF STATEMENT USAGE
    
       If statements are used inside the while loop to throw errors or break when needed. Other if statements are used to compare scores with existing scores and
       to figure out what row the current score must be added.
	
    TRY BLOCK USAGE
    
	The try block is used in this case to errortrap the name writing, to tell the user if the name is too long or if the user is inputting non-alphabet ascii values.
    */
    public void getName(){
	
	//declares and initialises a temporary storage for the user name.
	String tempName = "";
	
	//tells the user to enter a text.
	c.setColor(darkGreen);
	c.setFont(new Font("Century Gothic", 2, 20));
	c.drawString("Enter your name here:", 50, 300);
	c.setFont(new Font("Century Gothic", 2, 14));
	c.drawString("Use backspace to delete, enter to confirm.", 50, 320);
	c.drawString("The name must be smaller than 15 characters.", 50, 336);
	c.setFont(new Font("Century Gothic", 1, 20));
	
	//gets the user name.
	while(true){

	    //draws over the previous text and draws the new updated.
	    c.setColor(lightBlue);
	    c.fillRect(280, 280, 280, 25);
	    c.setColor(darkGreen);
	    c.drawString(tempName, 280, 300);
	
	    try{
		//gathers the user input.
		char tempInput = c.getChar();
		
		//if the user entered a backspace, save the tempName as itself but a little bit shorter.
		if(tempInput == 8){
		    if(tempName.length() > 0)
			tempName = tempName.substring(0, tempName.length()-1);
		}
		//calls breaks if the user pressed the enter key.
		else if(tempInput == 10 && tempName.length() > 0)
		    break;
		//throws an error if the input was not a letter.
		else if((tempInput < 65 || tempInput > 90) && (tempInput < 97 || tempInput > 122))
		    throw new ArithmeticException();
		//throws an error if the input was not a letter.
		else if(tempName.length() >= 15)
		    throw new NumberFormatException();
		//else, meaning the input is a letter, add it to tempName.
		else
		    tempName += tempInput;
	    }
	    //if the input was not an alphabet, give an error box.
	    catch(ArithmeticException e){
		JOptionPane.showMessageDialog(null, "That was not the alphabet! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    //if the name is too large, give an error box.
	    catch(NumberFormatException e){
		JOptionPane.showMessageDialog(null, "The name is already at it's max length!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	//resets lineUsed array while counting how much has been used.
	int lineCount = 0;
	for(int i = 0; i < 3; i++){
	    if(lineUsed[i] != 0){
		lineCount++;
		lineUsed[i] = 0;
	    }
	}

	//for each row of the array, check if the score is higher than the current array being checked.
	for(int i = 0; i < 10; i++){

	    //if the current name in this row is empty, save the current score onto there.
	    if(highScore[i][0] == null){
	       highScore[i][0] = tempName;
	       highScore[i][1] = questionDif + "";
	       highScore[i][2] = lineCount + "";
	       break;
	    }   
	
	    //parses the two stored scores to compare.
	    int rowScore = Integer.parseInt(highScore[i][1]);
	    int rowLine = Integer.parseInt(highScore[i][2]);
	    
	    //if the rowScore is smaller or equal to questionDif,
	    if(rowScore <= questionDif){
	    
		//if the current lifeline use is lower, continue from the next for loop count.
		if(rowLine < lineCount && rowScore == questionDif)
		    continue;
		    
		//moves all scores down by 1.
		for(int j = 8; j >= i; j--){
		    highScore[j+1][0] = highScore[j][0];
		    highScore[j+1][1] = highScore[j][1];
		    highScore[j+1][2] = highScore[j][2];
		}
		//adds the new score in it's correct spot and then breaks out of the for loop.
		highScore[i][0] = tempName;
		highScore[i][1] = questionDif + "";
		highScore[i][2] = lineCount + "";
		break;
	    }
	}
	
	//calls setHighscore to edit the file.
	setHighscore();
	
	//resets instance variables used in the game.
	questionDif = 1;
	hasFinished = false;
    }

    /*
    INSTRUCTION METHOD
    
    This method will only be reached when the user enters a 2 as their input. It will use title to clear the screen, print out an
    introductory text onto the screen, and pause the program using pauseProgram.
    */
    public void instructions(){
    
	//calls title.
	title();
	
	//draws the box around the instruction text.
	c.setColor(darkGreen);
	c.fillRoundRect(50, 120, 540, 300, 30, 30);
	c.setColor(lightGreen);
	c.fillRoundRect(55, 125, 530, 290, 30, 30);
	
	//draws the text inside the box.
	c.setColor(darkGreen);
	c.setFont(new Font("Century Gothic", 0, 12));
	c.drawString("To play the game, press 1 in the main menu. You will recieve a question and 4 answers", 65, 145);
	c.drawString("to choose from. Find the correct answer and you will go to a higher level question worth", 65, 160);
	c.drawString("more then the last. When you fail to answer a question, you will cash out on the last", 65, 175);
	c.drawString("question's prize pool.", 65, 190);
	c.drawString("Three special lifelines exist - Switch The Question (Q), 50/50 (W), and Call a Friend (E).", 65, 215);
	c.drawString("To use the lifelines, press the key in the brackets. These lifelines are single use, and will", 65, 230);
	c.drawString("help you solve the question when stuck.", 65, 245);
	c.drawString("Switch The Question will change to another question in the same level that you are on.", 65, 275);
	c.drawString("Use this if you get stuck in a question!", 65, 290);
	c.drawString("50/50 removes 2 wrong answers, giving you a 50/50 chance of solving the question.", 65, 320);
	c.drawString("If you aren't sure your answer is correct, use this to double check!", 65, 335);
	c.drawString("Call a Friend brings up a Robo-Friend that will try its hand in solving the question.", 65, 365);
	c.drawString("Be careful, the Robo-Friend sometimes malfunction and fail to answer it correctly!", 65, 380);
	c.drawString("Try your best to get the elusive million dollar and you'll win bragging rights!", 65, 405);
	
	//calls pauseProgram.
	pauseProgram();
    }
    
    /*
    HIGHSCORE METHOD
    
    -----------------------------------------------Local Variable Table-----------------------------------------------

	Variable Name                   Type                        Purpose

	i                               int (loop)                  This loop counter allows for going through each index of an array,
	
	tempScore                       int                         Holds a parsed version of one of the highscore strings. This allows
								    it to be used in getScore method.
	
	tempInput                       char                        
								    
								    
    This program will be reached when the user enteres 3 in the mainmenu. It uses drawString and loops to print every values except null values.
    
    LOOP USAGE
    
	The program uses multiple for loops to go through each row of the highscore array.  A while loop is used to errortrap the user input, as the 
	only two inputs should be allowed.

    IF STATEMENT USAGE
    
	If statements are used inside the while loop to throw errors or break when needed. One other if statement is used to check if one highscore row has
	been filled out, and another is used to check if the user chose a 1 or a 2. 
	
    TRY BLOCK USAGE
    
	The try block is used in this case to errortrap the user input. The user is not able to enter anything than 1 and a 2. If they do, the catch block
	will tell the user to input another one from the choice given.
    */
    public void highScore(){
    
	char tempInput;
    
	//calls title.
	title();
	
	//draws the box around the instruction text.
	c.setColor(darkGreen);
	c.fillRoundRect(50, 120, 540, 300, 20, 20);
	c.setColor(lightGreen);
	c.fillRoundRect(55, 125, 530, 290, 20, 20);
	
	//draws the text inside the box.
	c.setColor(darkGreen);
	c.setFont(new Font("Century Gothic", 0, 16));
	
	//draws the table headers.
	c.drawString("Name", 70, 150);
	c.drawString("Score", 270, 150);
	c.drawString("Lifeline Used", 470, 150);
	
	//for each row of the array, check if the score is higher than the current array being checked.
	for(int i = 0; i < 10; i++){
	
	    //if the current name in this row is empty, break out of the for loop.
	    if(highScore[i][0] == null)
		break;
	    
	    //stores the score string as an integer to be manipulated.
	    int tempScore = Integer.parseInt(highScore[i][1]);  
	     
	    //draws the highscore values on the screen.
	    c.drawString(highScore[i][0], 70, 180+(i*24));
	    c.drawString("$"+getScore(tempScore), 270, 180+(i*24));
	    c.drawString(highScore[i][2], 470, 180+(i*24)); 
	}
	
	//draws two button that the user can press to clear the highscore file.
	c.setColor(darkGreen);
	c.fillRoundRect(50, 430, 250, 50, 20, 20);
	c.fillRoundRect(340, 430, 250, 50, 20, 20);
	c.setColor(lightGreen);
	c.fillRoundRect(52, 432, 246, 46, 20, 20);
	c.fillRoundRect(342, 432, 246, 46, 20, 20);
	c.setColor(darkGreen);
	c.setFont(new Font("Century Gothic", 0, 32));
	c.drawString("1. Exit", 130, 465);
	c.drawString("2. Clear Score", 360, 465);
	
	//errortraps user input.
	while(true){
	    try{
		//gathers the user input.
		tempInput = c.getChar();

		//if the user input is not 1 or a 2, throw an error.
		if(tempInput != '1' && tempInput != '2')
		    throw new ArithmeticException();
		
		//breaks if everything was successful.
		break;
	    }
	    //if the number was not in the range, give an error box.
	    catch(ArithmeticException e){
		JOptionPane.showMessageDialog(null, "That was not a choice given! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	//if the user wanted to clear the file,
	if(tempInput == '2'){
	
	    //set all values to null.
	    for(int i = 0; i < 10; i++){
		highScore[i][0] = null;
		highScore[i][1] = null;
		highScore[i][2] = null;
	    }
	    
	    //and edit the file to be all null.
	    setHighscore(); 
	    
	    //tells the user that the highscore file has been cleared and pauses the program for a little.
	    title();
	    c.setColor(darkGreen);
	    c.setFont(new Font("Century Gothic", 0, 28));
	    c.drawString("The Highscore file has been cleared.", 80, 250);
	    pauseProgram();
	}
    }

    /*
    GOODBYE METHOD
    
    This method can only be reached when the user enters 4 in mainmenu. It calls title to clear the screen and then prints a small
    farewell message to the user, thanking them for the use of the program. Then pauseProgram is used so the user can read the
    final message, and when the user does press, the console closes.
    */
    public void goodbye(){

	//calls title and generateQuestion into display.
	title();

	//draws the goodbye text, including some credits.
	c.setColor(darkGreen);
	c.setFont(new Font("Century Gothic", 1, 35));
	c.drawString("THANK YOU FOR PLAYING!", 95, 140);
	c.setFont(new Font("Century Gothic", 0, 25));
	c.drawString("This version of \"WHO WANTS TO BE A MILLIONAIRE\"", 25, 190);
	c.drawString("was created by the one and only:", 110, 210);
	c.setFont(new Font("Century Gothic", 3, 40));
	c.drawString("Min Kang!!", 215, 270);
	c.setFont(new Font("Century Gothic", 0, 20));
	c.drawString("Big thanks to:", 25, 325);
	c.drawString("My Parents - For being there", 25, 350);
	c.drawString("Ms.Krasteva - For teaching Java", 25, 375);
	c.drawString("Ready to Java - For creating HSA Console...", 25, 400);

	//halts the program and closes the console when it continues.
	pauseProgram();
	c.close();
    }

    /*
    TITLE METHOD
    
    This method contains the title that all of the main screens use, with the exception of splash screen. It contains a clear function to
    clear the Console, and draws the title screen using text, arc, and lines.
    */
    private void title(){

	//clears the screen.
	c.clear();

	//draws the background color
	c.setColor(lightBlue);
	c.fillRect(10, 10, 640, 500);

	//draws the two dollar signs on the side.
	c.setColor(darkGreen);
	c.drawArc(120, 30, 40, 30, 0, 275);
	c.drawArc(120, 60, 40, 30, 175, 275);
	c.drawLine(145, 25, 145, 95);
	c.drawLine(135, 25, 135, 95);
	c.drawArc(480, 30, 40, 30, 0, 275);
	c.drawArc(480, 60, 40, 30, 175, 275);
	c.drawLine(505, 25, 505, 95);
	c.drawLine(495, 25, 495, 95);

	//draws the text in different sizes.
	c.setFont(new Font("Times New Roman", 1, 20));
	c.drawString("Who Wants To Be A", 235, 45);
	c.setFont(new Font("Times New Roman", 1, 40));
	c.drawString("MILLIONAIRE!", 175, 85);
    }
    
    /*
    PAUSEPROGRAM METHOD
    
    This method contains the code to halt the program for certain methods. It prints out a message telling the user to press a keyboard key
    to progress and then uses getChar to freeze the program until a further input.
    */
    private void pauseProgram(){

	//draws the text to let the user know how to exit.
	c.setColor(darkGreen);
	c.setFont(new Font("Century Gothic", 2, 20));
	c.drawString("Press anything to continue.", 200, 480);

	//halts the program.
	c.getChar();
    }
    
    /*
    SETHIGHSCORE METHOD
    
    -----------------------------------------------Local Variable Table-----------------------------------------------

	Variable Name                   Type                        Purpose

	i                               int (loop)                  This loop counter allows for going through each index of an array.
	
	out                             PrintWriter                 Allows the program to create and write a file.
	
    
    This method allows the program to write all of the values in the array highScore into the file "highscore.wwtbam". Values that are null are saved as "<>" 
    to differentiate as the name "null".
    
    LOOP USAGE
    
	The first two for loops in the first lifeline is an almost a repeat of the code in generateQuestion except the last question
	generated cannot be used. The other for loop stores it in the answerPos array, changing the positions of the questions generated.
	Then, the next for loop allows the third lifeline to show the correct answer position based on the number generated in it.
    
    IF STATEMENT USAGE
    
	One if block checks if the array is empty or not. If it is, it puts in a special character into the line. If not, it puts the value in.
    
    TRY BLOCK USAGE
    
	The try block is used to errortrap PrintWriter, as it throws an IOException when run. The catch block will hold any exception thrown
	to check for errors in other parts of the code, such as the file was not found, or the value could not be written. This will give
	an error box to the user.
    */
    private void setHighscore(){
    
	//errortraps the highscore file writing.
	try{
	    PrintWriter out = new PrintWriter (new FileWriter("highscore.wwtbam"));
	    
	    out.println("Created By MIN KANG - Encrypted by ICSPOWER protocol");
	    out.println("DO NOT OPEN IF YOU DO NOT WANT TO BE ARRESTED");
	    out.println();
	    
	    //puts everything in the file to an array.
	    for(int i = 0; i < 10; i++){
		for(int j = 0; j < 3; j++){
		    //if the string is a null, add two special characters, representing null.
		    if(highScore[i][j] == null)
			out.println("<>");
		    //else, adds the string in the array to the file.
		    else
			out.println(highScore[i][j]); 
		}
	    }
	    
	    //closes the stream. 
	    out.close(); 
	}
	//if for any reason the PrintWriter broke, run this.
	catch(Exception e){
	    JOptionPane.showMessageDialog(null, "The file was unable to be written to!! Please re-ask Min for the accurate version." , "Error Message", JOptionPane.ERROR_MESSAGE);
	}
    }

    /*
    GENERATEQUESTION METHOD
    
    -----------------------------------------------Local Variable Table-----------------------------------------------

	Variable Name                   Type                        Purpose

	i                               int (loop)                  This loop counter allows for going through each index of an array.
	
	tempPos                         int[]                       Stores the array that will be shuffled and saved to answerPos.
	
	max                             int                         Multiple different uses to create the Fisher-Yates shuffle. Each saves different 
	rando                                                       values.
	tempA
	
	
    This method allows a randomized question to be created for the display to use. It uses math.random to create the question value to be looked at, while
    the Fisher-Yates shuffler is used to create the answer positions.
    
    LOOP USAGE
	
	Two for loops are used in this program. One, which has another inside it, is for the shuffle of the question array. This randomizes the locations
	of the question answers. The second for loop is used to transfer the values of the shuffled array into a instance array, letting the values be used in another
	method.
    */
    private void generateQuestion(){

	//creates two temporary integer array to be shuffled.
	int[] tempPos = {1,2,3,4};

	//saves a random number plus questionDif. (int)((Math.random()*5)-1)
	answerPos[0] = 4+(5*(questionDif-1));
       
	//using a modified version of Fisher-Yates shuffle again.
	for(int i = 0; i < 10; i++){
	    int max = 3;
	    for(int j = 0; j < 3; j++){
		int rando = (int)Math.ceil((Math.random()*(max+1)-1));
		int tempA = tempPos[rando];
		tempPos[rando] = tempPos[max];
		tempPos[max] = tempA;  
		max--;
	    }
	}
	
	//saves the shuffled positions into answerPos.
	for(int i = 0; i < 4; i++){
	    answerPos[i+1] = tempPos[i];
	}
    }

    /*
    USELIFELINE METHOD
    
    -----------------------------------------------Local Variable Table-----------------------------------------------

	Variable Name                   Type                        Purpose

	i                               int (loop)                  This loop counter allows for going through each index of an array.
	
	tempPos                         int[]                       Stores the array that will be shuffled and saved to answerPos.
	count                           int                         Stores what index of tempPos is looking at.
	max                             int                         Multiple different uses to create the Fisher-Yates shuffle. Each saves different 
	rando                                                       values.
	tempA
	
	randomNum                       int                         Stores what value the third lifeline is going to use to create the message.
    
	
    This method allows the program to use the one of the three lifelines to be used in display. It uses lots of the randomization methods and
    checks the lineUsed array to see if the aray has been set to be used or has been used.
    
    LOOP USAGE
    
	The first two for loops in the first lifeline is an almost a repeat of the code in generateQuestion except the last question
	generated cannot be used. The other for loop stores it in the answerPos array, changing the positions of the questions generated.
	Then, the next for loop allows the third lifeline to show the correct answer position based on the number generated in it.
    
    IF STATEMENT USAGE
    
	The main if statements check what button has been pressed, and if that button has not been used. This allows for the program to differenciate if the
	user is able to use a lifeline or is trying to use an used lifeline. The other if statements inside then check for other requirements, because some lifelines
	are not compatible with others when used in a certain order. One if block is used to check a random value, to see if the robo-friend gives you the 
	correct or wrong answer.
    */
    private void useLifeline(){

	//sets the text color and size for the if statements.
	c.setColor(darkGreen);
	c.setFont(new Font("Century Gothic", 1, 18));

	//if the user decided to use the Switch the Question lifeline,
	if((gameChoice == 'q' || gameChoice == 'Q') && lineUsed[0] == 0){
	
	    //if the other two lines have not been used in this question and the current lifeline has not been used as well,
	    if(lineUsed[1] != 1 && lineUsed[2] != 1){
		//create a temporary integer array to be shuffled.
		int[] tempPos = new int[4];
		int count = -1;            

		//fills an array to shuffle.
		for(int i = 0; i < 5; i++){
		    if(i != answerPos[0] % 5){
			count++;
			tempPos[count] = i;
		    }
		}

		//using a modified version of Fisher-Yates shuffle outlined in the top comments.
		for(int i = 0; i < 10; i++){
		    int max = 3;
		    for(int j = 0; j < 3; j++){
			int rando = (int)Math.ceil((Math.random()*(max+1)-1));
			int tempA = tempPos[rando];
			tempPos[rando] = tempPos[max];
			tempPos[max] = tempA;  
			max--;
		    }
		}
	    
		//takes the first tempPis index and sets it to the answerPos.
		answerPos[0] = tempPos[0] + 5*(questionDif-1);
	    
		//sets the first lifeline as used and draws a string that tells the user that they have used it.
		lineUsed[0] = 2;
		c.drawString("The computer has Switched The Question!©", 140, 480);
	    } 
	    //if the line has not been used yet, tell the user this cannot be used.
	    else
		c.drawString("You have other lifelines active that will be removed if you use this!", 40, 480);
	}
	//if the user decided to use the 50/50 lifeline,
	else if((gameChoice == 'w' || gameChoice == 'W') && lineUsed[1] == 0){
	
	    //sets the second lifeline as used and draws a message.
	    lineUsed[1] = 1;
	    c.drawString("The computer has removed two wrong answers!", 120, 480);
	}
	//if the user decided to use the Call A Friend lifeline,
	else if((gameChoice == 'e' || gameChoice == 'E') && lineUsed[2] == 0){  
	
	    //generates a random number.
	    int randomNum = (int)((Math.random()*12)-1);
	    
	    //edits the start of the text message storage.
	    threeText = "Robo-Friend: ";

	    //uses the number to put together a message.
	    switch(randomNum){
		case 0: case 1: case 2:
		    threeText += "I'm not sure, but it could be ";
		    break;
		case 3:
		    threeText += "Maybe it's ";
		    break;
		case 4: case 5: case 6: case 7: 
		    threeText += "Hmm, it seems like ";
		    break;
		case 8: case 9:
		    threeText += "I'm pretty sure it's ";
		    break;
		case 10:
		    threeText += "Oh, I know this one! It's ";
		    break;
	    }
		
	    //if the randomNum is bigger than 3, the robo-friend will give the right answer.
	    if(randomNum > 3){
		//finds the correct answer's position and adds it to the string.
		for(int i = 1; i < 5; i++){
		    if(answerPos[i] == 1){
			threeText += i + ".";
			break;
		    }
		}
	    }
	    //if not, the friend pick a random number based on the random number generated. Could be right or wrong.
	    else
		threeText += (randomNum%4+1) + "."; 
	
	    //sets the third lifeline as to be used and draws a message.
	    lineUsed[2] = 1;
	    c.drawString("The computer has used Call a Friend lifeline!", 130, 480); 
	} 
	//else if the lifeline have been used, tell the user that it all has been used.
	else
	    c.drawString("You have used the lifeline!", 200, 480);
    }

    /*
    GETSCORE METHOD - RETURN METHOD
    
    -----------------------------------------------Local Variable Table-----------------------------------------------
		
	Variable Name                   Type                        Purpose
	
	num                             int (Arguments)             Holds the value that the program enters as the integer to be
								    processed.
								    
    
    This method takes an integer as its arguments and uses that to return a number that equals to how much money that difficulty question
    is worth.
    
    IF STATEMENT USAGE
    
	One switch block is used to return each value as a certain money worth.
    */
    private int getScore(int num){

	//gets the level number and returns the amount of money the questions equals to.
	switch(num){
	    case 1:
		return 100;
	    case 2:
		return 200;
	    case 3:
		return 300;
	    case 4:
		return 500;
	    case 5:
		return 1000;
	    case 6:
		return 2000;
	    case 7:
		return 4000;
	    case 8:
		return 8000;
	    case 9:
		return 16000;
	    case 10:
		return 32000;
	    case 11:
		return 64000;
	    case 12:
		return 125000;
	    case 13:
		return 250000;
	    case 14:
		return 500000;
	    default:
		return 1000000;
	}
    }

    /*
    MAIN METHOD
    
    This method contains all of the methods that is used in the program. The current class is made as an object and
    it runs all of the methods. The methods that must be able to repeat is inside a while loop with if statements
    that marks what methods are run, and the once run methods, like splashscreen and goodbye, is run outside of it.
    
    LOOP USAGE

	One infinite while loop is used to make the program return back to main menu. Depending on the user input in the
	aforementioned method, it will exit or continue on with the while loop. Then, another while loop is used to run
	the main game until the user wins or failes the program.
    
    IF STATEMENT USAGE
    
	One if statement structure is used to check the user input from main menu and call the methods correctly. If the user
	entered 1, it will run the game, 2 will run the instructions, 3 will run the highscores, and 4 will exit the while loop 
	to reach the goodbye screen.
    */
    public static void main(String[] args){

	//declares and initializes the millionaire class.
	Millionaire m = new Millionaire();

	//calls splashScreen and fileLoading into main.
	m.splashScreen();
	m.fileLoading();
	
	//while the user does not want to exit,
	while(true){
	    //calls mainMenu into main.
	    m.mainMenu();
	    //if the choice is 1,
	    if(m.choice == 1){
		//while the user has not finished the game, 
		while(!m.hasFinished){
		    //calls display and askData.
		    m.display();
		    m.askData();
		    //if gamechoice is not q, w, or e.
		    if(m.gameChoice != 69 && m.gameChoice != 81 && m.gameChoice != 87 && m.gameChoice != 101 && m.gameChoice != 113 && m.gameChoice != 119)
			m.showAnswer();
		}   
		//calls getName.
		m.getName();
	    }
	    //if choice is 2, call instructions.
	    else if(m.choice == 2)
		m.instructions();
	    //if choice is 3, call highScore.
	    else if(m.choice == 3)
		m.highScore();
	    //if choice is 4, breaks out of the while loop. 
	    else if(m.choice == 4)
		break;
	}

	//calls goodbye at the end of the program.
	m.goodbye();
    }
}
