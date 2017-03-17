package view;

import javax.swing.*;
import javax.swing.border.Border;
import controller.SudokuController.WriteChecking;
import java.awt.*;
import java.awt.event.*;

// This class describes the view part of my MVC project

public class SudokuView extends JFrame{

	private JPanel gameMenu = new JPanel(); // panel, that shows as buttons of our game menu

	private JLabel title = new JLabel("Sudoku game"); // label, that shows as title of our program in the main menu
	
	//
	// BUTTONS FROM MAIN MENU
	//

	private JButton menuEasy = new JButton("EASY");

	private JButton menuNormal = new JButton("NORMAL");

	private JButton menuHard = new JButton("HARD");

	//
	// BUTTONS FROM CHOICE MENUS
	//

	private JButton[] easyPuzzles = new JButton[3];

	private JButton[] normalPuzzles = new JButton[3];

	private JButton[] hardPuzzles = new JButton[3];

	private JButton getFromEasy = new JButton("Get from easy");

	private JButton getFromNormal = new JButton("Get from normal");

	private JButton getFromHard = new JButton("Get from hard");
	
	//
	// PANELS OF CHOICE MENU
	//

	private JPanel choiceEasyMenu = new JPanel();

	private JPanel choiceNormalMenu = new JPanel();

	private JPanel choiceHardMenu = new JPanel();
	
	//
	// ELEMENTS OF GAMEFIELD
	//

	private JPanel gameField = new JPanel(); // panel of game cells

	private JPanel choice = new JPanel(); // panel of CHECK, ENDGAME buttons and timer of the game

	private JButton endGame = new JButton("END GAME");

	private JButton checkBut = new JButton("CHECK");

	private JLabel time = new JLabel();
	
	//
	// VARIABLES
	//

	private int row; // row number of the game cell

	private int col; // column number of the game cell
	

	private static final int GRID_SIZE = 9; // describes the size of our game board

	private static final int SUBGRID_SIZE = 3; // Size of the sub-grid
	

	private static final int FRAME_HEIGHT = 600; // height of the main frame

	private static final int FRAME_WIDTH = 800; // width of the main frame
	
	//
	// Name-constants for UI control (sizes, colors and fonts)
	//

	private static final int CELL_SIZE = 60; // size (width and height) of cell in pixels

	private static final int CANVAS_WIDTH  = CELL_SIZE * GRID_SIZE; // width of our board in pixels

	private static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE; // height of our board in pixels


	private static final Color OPEN_CELL_BGCOLOR = Color.YELLOW; // cell is editable -> paint in yellow

	private static final Color OPEN_CELL_TEXT_YES = new Color(0, 255, 0);  // RGB ||| cell is correct

	private static final Color OPEN_CELL_TEXT_NO = Color.RED; // cell is incorrect -> paint in red

	private static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB ||| cell is uneditable
	

	private static final Color BACKGROUND_MENU = new Color(255, 255, 204); // background menu buttons color

	private static final Color BACKGROUND_EASY = new Color(204, 255, 255); // easy menu buttons color

	private static final Color BACKGROUND_NORMAL = new Color(204, 230, 255); // normal menu buttons color

	private static final Color BACKGROUND_HARD = new Color(204, 204, 255); // hard menu buttons color

	private static final Color CHOICE = new Color(204, 255, 204); // easy, normal, hard choices menu background color
	

	private static final Color TIME = new Color(100, 204, 255); // timer's background

	private static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20); // font of numbers in cell

	private static final Font MENU = new Font("Arial", Font.BOLD, 40); // font of the title in main menu
	
	//
	// GAME CELLS
	//

	private static JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE]; // textFields of the gameCells

	//
	// BORDERS OF SUDOKU CELLS
	//
	// definition of color and width of the borders of our gameField

	Border jTextBorderLeftTop = BorderFactory.createMatteBorder(4, 4, 1, 1, Color.DARK_GRAY);
	
	Border jTextBorderLeft = BorderFactory.createMatteBorder(1, 4, 1, 1, Color.DARK_GRAY);

	Border jTextBorderLeftBottom = BorderFactory.createMatteBorder(1, 4, 4, 1, Color.DARK_GRAY);

	Border jTextBorderBottom = BorderFactory.createMatteBorder(1, 1, 4, 1, Color.DARK_GRAY);

	Border jTextBorderRightBottom = BorderFactory.createMatteBorder(1, 1, 4, 4, Color.DARK_GRAY);

	Border jTextBorderRight = BorderFactory.createMatteBorder(1, 1, 1, 4, Color.DARK_GRAY);

	Border jTextBorderRightTop = BorderFactory.createMatteBorder(4, 1, 1, 4, Color.DARK_GRAY);

	Border jTextBorderTop = BorderFactory.createMatteBorder(4, 1, 1, 1, Color.DARK_GRAY);

	Border jTextBorderCenter = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY);
	
	//constructor of our view part
	// here we are setting our game menu, choices menus, gamefield panel
	public SudokuView(){
		// SETTING GAMEMENU PANEL
		setMainMenu();
		
		// SETTING CHOICEMENU PANEL
		setChoiceMenu();
		
		// SETTING GAMEFIELD PANEL
		setGamefieldPanels();
		
		this.setTitle("Sudoku");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	// setting main menu
	public void setMainMenu(){
		gameMenu.setLayout(null);
		gameMenu.setBackground(BACKGROUND_MENU);
		
		title.setFont(MENU);
		title.setBounds(260, 50, 280, 60);
		gameMenu.add(title);
		
		setJButtonField(menuEasy, BACKGROUND_EASY);
		menuEasy.setBounds(300, 150, 200, 40);
		gameMenu.add(menuEasy);
		
		setJButtonField(menuNormal, BACKGROUND_NORMAL);
		menuNormal.setBounds(300, 210, 200, 40);
		gameMenu.add(menuNormal);
		
		setJButtonField(menuHard, BACKGROUND_HARD);
		menuHard.setBounds(300, 270, 200, 40);
		gameMenu.add(menuHard);

		this.add(gameMenu);
		
		this.setResizable(false);
		this.revalidate();
		this.repaint();
	}

	// setting choices menu
	public void setChoiceMenu(){
		choiceEasyMenu.setLayout(null);
		choiceEasyMenu.setBackground(BACKGROUND_EASY);
		
		choiceNormalMenu.setLayout(null);
		choiceNormalMenu.setBackground(BACKGROUND_NORMAL);
		
		choiceHardMenu.setLayout(null);
		choiceHardMenu.setBackground(BACKGROUND_HARD);
		
		for(int i = 0; i < 3; i++){
			easyPuzzles[i] = new JButton("" + (i+1));
			normalPuzzles[i] = new JButton("" + (i+1));
			hardPuzzles[i] = new JButton("" + (i+1));
			
			easyPuzzles[i].setBounds(100 + (180*i), 200, 120, 120);
			normalPuzzles[i].setBounds(100 + (180*i), 200, 120, 120);
			hardPuzzles[i].setBounds(100 + (180*i), 200, 120, 120);
		
			setJButtonField(easyPuzzles[i], CHOICE);
			setJButtonField(normalPuzzles[i], CHOICE);
			setJButtonField(hardPuzzles[i], CHOICE);
			
			choiceEasyMenu.add(easyPuzzles[i]);
			choiceNormalMenu.add(normalPuzzles[i]);
			choiceHardMenu.add(hardPuzzles[i]);
		}
		
		setJButtonField(getFromEasy, BACKGROUND_MENU);
		setJButtonField(getFromNormal, BACKGROUND_MENU);
		setJButtonField(getFromHard, BACKGROUND_MENU);
		
		getFromEasy.setBounds(650, 20, 130, 25);
		getFromNormal.setBounds(650, 20, 130, 25);
		getFromHard.setBounds(650, 20, 130, 25);
		
		choiceEasyMenu.add(getFromEasy);
		choiceNormalMenu.add(getFromNormal);
		choiceHardMenu.add(getFromHard);
	}

	// setting gamefield
	public void setGamefieldPanels(){
		
		// SETTING PANEL WITH OUR GAME CELLS
		gameField.setLayout(new GridLayout(getGridSize(), getGridSize()));
		settingTextFields(); // setting text fields
		settingBorders(); //setting borders
		gameField.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		
		// SETTING PANEL WITH OUR GAME BUTTONS AND TIMER
		choice.setLayout(null);
		choice.setBackground(BACKGROUND_MENU);

		time.setLayout(null);
		time.setBackground(TIME);

		setJButtonField(checkBut, CHOICE);
		setJButtonField(endGame, CHOICE);

		checkBut.setBounds(80, 220, 100, 50);
		endGame.setBounds(80, 320, 100, 50);
		
		time.setBounds(80, 120, 100, 50);
		time.setFont(FONT_NUMBERS);

		choice.add(checkBut);
		choice.add(endGame);
		choice.add(time);
		choice.setPreferredSize(new Dimension(FRAME_WIDTH - CANVAS_WIDTH, FRAME_HEIGHT - CANVAS_HEIGHT));
	}


	// setting text fields into our game cells
	public void settingTextFields(){
		for(row = 0; row < getGridSize(); row++){
			for(col = 0; col < getGridSize(); col++){
				tfCells[row][col] = new JTextField(); // allocate element of array
				
				gameField.add(tfCells[row][col]);
				
				tfCells[row][col].setEditable(true);
				tfCells[row][col].setBackground(getOpenCellBgcolor());
			
				tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
				tfCells[row][col].setFont(FONT_NUMBERS);
			}
		}
	}
	

	// setting borders of our game field
	public void settingBorders(){
		
		// setting borders depends on position of the cell, because we need to get the right representation of cells
		
		for(row = 0; row < getGridSize(); row++){
			for(col = 0; col < getGridSize(); col++){
				
				if((row+1)%SUBGRID_SIZE == 1){
					if((col+1)%SUBGRID_SIZE == 1) tfCells[row][col].setBorder(jTextBorderLeftTop);
					else if ((col+1)%SUBGRID_SIZE == 2) tfCells[row][col].setBorder(jTextBorderTop);
					else tfCells[row][col].setBorder(jTextBorderRightTop);
				}
				
					else if ((row+1)%SUBGRID_SIZE == 2){
						if((col+1)%SUBGRID_SIZE == 1) tfCells[row][col].setBorder(jTextBorderLeft);
						else if ((col+1)%SUBGRID_SIZE == 2) tfCells[row][col].setBorder(jTextBorderCenter);
						else tfCells[row][col].setBorder(jTextBorderRight);
					}
				
						else {
							if((col+1)%SUBGRID_SIZE == 1) tfCells[row][col].setBorder(jTextBorderLeftBottom);
							else if ((col+1)%SUBGRID_SIZE == 2) tfCells[row][col].setBorder(jTextBorderBottom);
							else tfCells[row][col].setBorder(jTextBorderRightBottom);
						}
			}
		}
		
	}
	
	//setting color of our button
	public void setJButtonField(JButton but, Color col){
		but.setFocusPainted(false);
		but.setBorderPainted(true);
		but.setBackground(col);
	}
	
	//return text field of our cell
	public JTextField getTextField(int r, int c){
		return tfCells[r][c];
	}
	
	
	// when we win the game, we color every cell in green
	public void colorEverything(){
		for(int i = 0; i < getGridSize(); i++){
			for(int j = 0; j < getGridSize(); j++){
				tfCells[i][j].setBackground(OPEN_CELL_TEXT_YES);
			}
		}
	}
	
	// our cells are empty on default
	public void setEmptyEverything(){
		for(int i = 0; i < getGridSize(); i++){
			for(int j = 0; j < getGridSize(); j++){
				tfCells[i][j].setText(null);
			}
		}
	}

	public void addCheckButtonListener(ActionListener CheckListener){
		checkBut.addActionListener(CheckListener);
	}
	
	public void addEndGameButtonListener(ActionListener EndGameListener){
		endGame.addActionListener(EndGameListener);
	}
	
	public void checkingCellOverWriting(WriteChecking e){
		for (row = 0; row < getGridSize(); row++)
			for(col = 0; col < getGridSize(); col++)
				tfCells[row][col].addKeyListener(e);
	}
	
	public void addMenuEasyButtonListener(ActionListener EasyListener){
		menuEasy.addActionListener(EasyListener);
	}
	
	public void addMenuNormalButtonListener(ActionListener NormalListener){
		menuNormal.addActionListener(NormalListener);
	}
	
	public void addMenuHardButtonListener(ActionListener HardListener){
		menuHard.addActionListener(HardListener);
	}

	public void addPuzzleButtonListener(ActionListener PuzzleButton){
		for(int i = 0; i < 3; i++){
			easyPuzzles[i].addActionListener(PuzzleButton);
			normalPuzzles[i].addActionListener(PuzzleButton);
			hardPuzzles[i].addActionListener(PuzzleButton);
		}
	}
	
	public void addGetBackToMenuListener(ActionListener BackListener){
			getFromEasy.addActionListener(BackListener);
			getFromNormal.addActionListener(BackListener);
			getFromHard.addActionListener(BackListener);
	}


	public JPanel getGameMenu(){
		return gameMenu;
	}

	public JButton getEasyPuzzles(int i){
		return easyPuzzles[i];
	}

	public JButton getNormalPuzzles(int i){
		return normalPuzzles[i];
	}

	public JButton getHardPuzzles(int i){
		return hardPuzzles[i];
	}

	public JPanel getChoiceEasyMenu(){
		return choiceEasyMenu;
	}

	public JPanel getChoiceNormalMenu(){
		return choiceNormalMenu;
	}

	public JPanel getChoiceHardMenu(){
		return choiceHardMenu;
	}

	public JPanel getGameField(){
		return gameField;
	}

	public JPanel getChoice(){
		return choice;
	}


	// setting and updating our time label
	public void setTimeLabel(int min, int sec){
		if(sec<10)
			time.setText(Integer.toString(min) + " : " + "0" + Integer.toString(sec));
		
		else
			time.setText(Integer.toString(min) + " : " +  Integer.toString(sec));
	}

	
	public int getGridSize() {
		return GRID_SIZE;
	}

	public Color getOpenCellTextNo() {
		return OPEN_CELL_TEXT_NO;
	}

	public Color getOpenCellBgcolor() {
		return OPEN_CELL_BGCOLOR;
	}

	public Color getClosedCellBgcolor() {
		return CLOSED_CELL_BGCOLOR;
	}
}