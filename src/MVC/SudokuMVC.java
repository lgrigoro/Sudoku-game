package MVC;

import controller.SudokuController;
import model.SudokuModel;
import view.SudokuView;

//
//  This class has method, where we call every part of our MVC project and then we run them
//
public class SudokuMVC {
	public static void main (String[] args){
		SudokuView theView = new SudokuView();
		SudokuModel theModel = new SudokuModel();
		new SudokuController(theView, theModel);
	}
}