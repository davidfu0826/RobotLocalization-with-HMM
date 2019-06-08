package control;

import model.DummyLocalizer;
import model.HMMLocalizer;
import model.MatrixThings;
import view.RobotLocalizationViewer;

public class Main {
	/*
	 * build your own if you like, this is just an example of how to start the viewer
	 * ...
	 */
	
	public static void main( String[] args) {

		/*MatrixThings matr = new MatrixThings(4,4);
		for(int i = 0; i < 16*4; i++) {
			for(int j = 0; j < 16*4; j++) {
				System.out.print((matr.matrix[i][j]==0.0?"X":matr.matrix[i][j]) + "|");
			}
			System.out.println();
			System.out.println("NEW LINE");
		}
		*/

		
		/*
		 * generate you own localiser / estimator wrapper here to plug it into the 
		 * graphics class.
		 */
		EstimatorInterface l = new HMMLocalizer( 8, 8, 4);

		RobotLocalizationViewer viewer = new RobotLocalizationViewer( l);

		/*
		 * this thread controls the continuous update. If it is not started, 
		 * you can only click through your localisation stepwise
		 */
		new LocalizationDriver( 500, viewer).start();

	}
}	