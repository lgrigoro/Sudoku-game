package controller;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;

import model.SudokuModel;
import view.SudokuView;

public class SudokuController {

	// view part of the project
	private SudokuView theView;
	
	// model part of the project
	private SudokuModel theModel;
	
	//timer variable
	private Timer timeCount;

	public SudokuController(SudokuView theView, SudokuModel theModel){
		this.theView = theView;
		this.theModel = theModel;
		
		timeCount = new Timer(1000, new actionTimer());
		
		this.theView.addMenuEasyButtonListener(new EasyListener());
		this.theView.addMenuNormalButtonListener(new NormalListener());
		this.theView.addMenuHardButtonListener(new HardListener());
		
		this.theView.addGetBackToMenuListener(new BackListener());
		
		this.theView.addPuzzleButtonListener(new PuzzleButton());
		
		this.theView.addCheckButtonListener(new CheckListener());
		this.theView.addEndGameButtonListener(new EndGameListener());
		this.theView.checkingCellOverWriting(new WriteChecking());
	}

	// from main menu to easy menu
	class EasyListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			theView.remove(theView.getGameMenu());
			theView.add(theView.getChoiceEasyMenu(), BorderLayout.CENTER);
			theView.revalidate();
			theView.repaint();
		}
	}

	// from main menu to normal menu
	class NormalListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			theView.remove(theView.getGameMenu());
			theView.add(theView.getChoiceNormalMenu(), BorderLayout.CENTER);
			theView.revalidate();
			theView.repaint();
		}
	}
		
	// from main menu to hard menu
	class HardListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			theView.remove(theView.getGameMenu());
			theView.add(theView.getChoiceHardMenu(), BorderLayout.CENTER);
			theView.revalidate();
			theView.repaint();
		}
	}

	// open the sudoku puzzle
	class PuzzleButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			
			try{
				for(int i = 0; i < 3; i++){
					if(e.getSource() == theView.getEasyPuzzles(i)){
						theView.remove(theView.getChoiceEasyMenu());
						theView.setEmptyEverything();
						setFromFileToCells(fileString("easy", (i+1)));
					}
					
						else if(e.getSource() == theView.getNormalPuzzles(i)){
							theView.remove(theView.getChoiceNormalMenu());
							theView.setEmptyEverything();
							setFromFileToCells(fileString("normal", (i+1)));
						}

							else if(e.getSource() == theView.getHardPuzzles(i)){
								theView.remove(theView.getChoiceHardMenu());
								theView.setEmptyEverything();
								setFromFileToCells(fileString("hard", (i+1)));
							}
				}
				
				theView.add(theView.getGameField(), BorderLayout.WEST);
				theView.add(theView.getChoice(), BorderLayout.EAST);
				timeStart();
				theView.setVisible(true);
				theView.revalidate();
				theView.repaint();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			}
		}
	
	// get back from panel to the previous panel
	class BackListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			
				if(e.getActionCommand()=="Get from easy"){
					theView.remove(theView.getChoiceEasyMenu());
				}
				
				else if(e.getActionCommand()=="Get from normal"){
					theView.remove(theView.getChoiceNormalMenu());
				}
				
				else if(e.getActionCommand()=="Get from hard"){
					theView.remove(theView.getChoiceHardMenu());
				}
				theView.setMainMenu();
				
		}
	}
	
	// checking if cells are correct
	class CheckListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			int i, j;
			boolean endOfGame = true; // if all of the cells are correct it stays true
			boolean[] checkRow, checkCol, checkSquare;
			
			for(i = 0; i < theView.getGridSize(); i++){
				String[] row = new String[9];
				String[] col = new String[9];
				String[] square = new String[9];
				
				for(j = 0; j < theView.getGridSize(); j++){
					row[j] = theView.getTextField(i,j).getText();
					col[j] = theView.getTextField(j,i).getText();
					square[j] = theView.getTextField((i/3)*3 + j/3, i*3%9 + j%3).getText();
				}
				
				checkRow = validate(row);
				checkCol = validate(col);
				checkSquare = validate(square);
				
				// checking all cells
				for(j = 0; j < theView.getGridSize(); j++){
					if(checkRow[j] == false){
						theView.getTextField(i,j).setBackground(theView.getOpenCellTextNo());
						endOfGame = false;
					}

					if(checkCol[j] == false){
						theView.getTextField(j,i).setBackground(theView.getOpenCellTextNo());
						endOfGame = false;
					}
					
					if(checkSquare[j] == false){
						theView.getTextField((i/3)*3 + j/3, i*3%9 + j%3).setBackground(theView.getOpenCellTextNo());
						endOfGame = false;
					}
					
					if(!(theView.getTextField(i,j).isVisible())){
						theView.getTextField(i,j).setBackground(theView.getClosedCellBgcolor());

					}
					
					// if everything is correct then we can end the game
					
					if((endOfGame) && (j == 8) && (i == 8)){
						theView.colorEverything();
						JFrame frame = new JFrame();
					    JOptionPane.showMessageDialog(frame, "The game is over! You ended the puzzle in " + theModel.getTimeMin() + 
					    		" minutes " + theModel.getTimeSec() + " seconds");
					    timeStop();
					    theView.remove(theView.getGameField());
					    theView.remove(theView.getChoice());
					    theView.setMainMenu();
					}
					
					}
					
				}
				
			}
			
		}
	
	// ending the game
	class EndGameListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0){
			int temp;
		    temp = JOptionPane.showConfirmDialog(theView, "Are you sure?");
		    
		    if(temp == 0){
		    	timeStop();
		    	theView.remove(theView.getGameField());
		    	theView.remove(theView.getChoice());
		    	theView.setMainMenu();
		    }
		    
		}
	}
	
	// click the button
	public class WriteChecking extends KeyAdapter {
		public void keyPressed(KeyEvent e){
			int i, j;
			
			Object key = (JTextField) e.getSource();
			int keyCode = e.getKeyCode();
			
			for (i = 0; i < theView.getGridSize(); i++)
				for (j = 0; j < theView.getGridSize(); j++){
			
					if(key == theView.getTextField(i,j)){
						
						if ((theView.getTextField(i,j).getText().length() < 1) &&
								((keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_9) ||
									(keyCode >= KeyEvent.VK_NUMPAD1 && keyCode <= KeyEvent.VK_NUMPAD9) ||
										(keyCode == KeyEvent.VK_UP) || (keyCode == KeyEvent.VK_DOWN) ||
											(keyCode == KeyEvent.VK_RIGHT) || (keyCode == KeyEvent.VK_LEFT) ||
												(keyCode == KeyEvent.VK_TAB) || (keyCode == KeyEvent.VK_BACK_SPACE))){
								theView.getTextField(i,j).setEditable(true);
								theView.getTextField(i,j).setBackground(theView.getOpenCellBgcolor());
						}
						
						
						else {
							theView.getTextField(i,j).setEditable(true);
							theView.getTextField(i,j).setBackground(theView.getOpenCellTextNo());
						}
					}
				}
		}
	}
	
	// checking for the same number in row, column and square
	public boolean[] validate(String[] M){
		boolean[] check = {true, true, true, true, true, true, true, true, true};
		
		for(int i = 0; i < 9; i++)
			for(int j = i+1; j < 9; j++){
				
				if (M[i].equals("0")){
					break;
				}
				
				else if(M[i].equals(M[j])){
					check[i] = false;
					check[j] = false;
				}
			}
		
		return check;
	}

	// reading sudoku puzzle from file
	public String[][] readFrom(String fileName) throws IOException{

		File file = new File(fileName);
		Scanner sc = new Scanner(file);
		String[][] cells = new String[9][9];

		while(sc.hasNext()){

			for(int i = 0; i < 9; i++){
				for(int j = 0; j < 9; j++){
					cells[i][j] = sc.next();
				}
			}
		}
		sc.close();
		return cells;
	}

	// checking sudoku puzzle from file
	public void checkFromFile(String[][] M){
		int i, j;
		boolean[] check1, check2, check3;

		for(i = 0; i < 9; i++){
			String[] row = new String[9];
			String[] col = new String[9];
			String[] square = new String[9];

			for(j = 0; j < 9; j++){
				row[j] = M[i][j];
				col[j] = M[j][i];
				square[j] = M[(i/3)*3 + j/3][i*3%9 + j%3];
			}

			check1 = validate(row);
			check2 = validate(col);
			check3 = validate(square);

			for(j = 0; j < 9; j++){
				if(!(check1[j]) || !(check2[j]) || !(check3[j])){
					i=9;
					JFrame frame = new JFrame();
				    JOptionPane.showMessageDialog(frame, "Your sudoku puzzle is bad :(");
					break;
				}
			}
		}
	}


	//setting sudoku puzzle from file into game cells
	public void setFromFileToCells(String fileName) throws IOException{
		String[][] puzzle = readFrom(fileName);
		
		checkFromFile(puzzle);
		
		for(int i = 0; i < theView.getGridSize(); i++){
			for(int j = 0; j < theView.getGridSize(); j++){
				
				if(puzzle[i][j].equals("0")){
					theView.getTextField(i,j).setBackground(theView.getOpenCellBgcolor());
				}
				
				else {
					theView.getTextField(i,j).setText(puzzle[i][j]);
					theView.getTextField(i,j).setBackground(theView.getClosedCellBgcolor());
					theView.getTextField(i,j).setEnabled(false);
				}
				
			}
		}
	}

	// returning our sudoku puzzle depending on the level of it
	public String fileString(String level, int number){
		String result;
		result = level + "/" + level + Integer.toString(number) + ".txt";
		return result;
	}

	// updating our timer
	public class actionTimer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			theModel.incTime();
			theView.setTimeLabel(theModel.getTimeMin(), theModel.getTimeSec());
		}
	}

	public void timeStart(){
		theModel.setTime(0);
		timeCount.start();
	}

	public void timeStop(){
		timeCount.stop();
		theView.setTimeLabel(0, 0);
	}
}
