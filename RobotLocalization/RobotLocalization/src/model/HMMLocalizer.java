package model;

import control.EstimatorInterface;

/**
 * Created by jinjang on 2/20/19.
 */
public class HMMLocalizer implements EstimatorInterface {

    private int rows, cols, head;
    private RealRobot rob;
    private int[] sensorxy;
    private MatrixThings matr;

    private double[][] currprobs;
    private double[][] transmatrix;
    private double[][] obsermatrix;

    public HMMLocalizer(int rows, int cols, int head) {
        this.rows = rows;
        this.cols = cols;
        this.head = head;
        rob = new RealRobot(rows, cols);
        matr = new MatrixThings(rows, cols);
        currprobs = new double[rows*cols*4][1];
        for(int i = 0; i < rows*cols*4; i++) {
            currprobs[i][0] = 1.0/((double) (rows*cols));
        }

        transmatrix = matr.getMatrix();
        obsermatrix = matr.getObsmat();
    }

    public HMMLocalizer() {
        this.rows = 5;
        this.cols = 5;
        this.head = 4;
        rob = new RealRobot(5, 5);
        matr = new MatrixThings(rows, cols);
        currprobs = new double[rows*cols*4][1];
        for(int i = 0; i < rows*cols*4; i++) {
            currprobs[i][0] = 1.0/((double) (rows*cols));
        }
    }

    @Override
    public int getNumRows() {
        return this.rows;
    }

    @Override
    public int getNumCols() {
        return this.cols;
    }

    @Override
    public int getNumHead() {
        return this.head;
    }

    @Override
    public void update() {
        int[] temp = rob.movebot();
        //System.out.println(temp[0] + ", " + temp[1] + ", " + temp[2]);
        sensorxy = rob.sensorRead();
        //System.out.println("sensor reading: " + sensorxy[0] + ", " + sensorxy[1]);
        matrixFun(sensorxy[0], sensorxy[1]);

        double max = 0.0;
        int maxindex = -1;
        for(int i = 0; i < rows*cols*4; i+=4) {
            if(currprobs[i][0] > max) {
                max = currprobs[i][0];
                maxindex = i;
            }
        }

        int currx = (maxindex / 4) / cols;
        int curry = (maxindex / 4) % cols;

        int manhat = Math.abs(currx - getCurrentTrueState()[0]) + Math.abs(curry - getCurrentTrueState()[1]);

        System.out.println("You are " + manhat + " squares away!");

    }

    @Override
    public int[] getCurrentTrueState() {
        int[] temp = rob.trueloc();
        //System.out.println("trueloc: " + temp[0] + ", " + temp[1] + ", " + temp[2]);
        return temp;
    }

    @Override
    public int[] getCurrentReading() {
        if(sensorxy[0] == -1) return null;
        return sensorxy;
    }

    @Override
    public double getCurrentProb(int x, int y) {
        return currprobs[matr.xyhToI(x, y, 0)][0];
    }

    @Override
    public double getOrXY(int rX, int rY, int x, int y, int h) {
        if(rX < 0 || rY < 0) {
            return matr.getOProb(x,y,h);
        }
        return matr.getOProb(rX, rY, x, y, h);
    }

    @Override
    public double getTProb(int x, int y, int h, int nX, int nY, int nH) {
        //System.out.print(x + "," + y + "," + h + ":" + nX + "," + nY + "," + nH + "||");
        double output = matr.getTProb(x, y, h, nX, nY, nH);
        //System.out.println(output);
        return output;
    }

    public void matrixFun(int x, int y) {
        //System.out.println("MATRIX IS FUN !!!");

        double[][] newObMatrix = new double[4*rows*cols][4*rows*cols];

        /*
        for(int i = 0; i < 4*rows*cols; i++) {

            //System.out.println(i + ": " + x + "," + y);
            if(x < 0 || y < 0) {
                //newObMatrix[i][i] = obsermatrix[i][4*rows*cols];
                newObMatrix[i][i] = matr.getOProb(x, y, 0) //obsermatrix[i][rows*cols*4];
            }
            else {
                //newObMatrix[i][i] = obsermatrix[i][matr.xyhToI(x, y, 0)];
                newObMatrix[i][i] = obsermatrix[matr.xyhToI(x, y, 0)][i];
            }
        }
        */

        int count = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                for(int k = 0; k < 4; k++) {
                    if(x < 0) {
                        newObMatrix[count][count] = matr.getOProb(i, j, 0);
                    }
                    else {
                        newObMatrix[count][count] = matr.getOProb(x, y, i, j, 0);
                    }
                    count++;
                }
            }
        }

        //double[][] newTrMatrix = MatrixOperations.transposeMatrix(transmatrix);
        //double[][] OTMatrix = MatrixOperations.multiplicar(newObMatrix, newTrMatrix);
        double[][] OTMatrix = MatrixOperations.multiplicar(newObMatrix, transmatrix);

        currprobs = MatrixOperations.multiplicar(OTMatrix, currprobs);
        normalize();

    }

    public void normalize() {
        double total = 0.0;
        for(int i = 0; i < rows*cols*4; i+=4) {
            total+=currprobs[i][0];
        }

        for(int i = 0; i < rows*cols*4; i++) {
            currprobs[i][0] = (currprobs[i][0] / total);
        }
    }
}
