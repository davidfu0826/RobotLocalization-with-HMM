package model;

import java.util.ArrayList;

/**
 * Created by jinjang on 2/22/19.
 */
public class MatrixThings {
    final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
    int rows, cols;
    double[][] matrix;
    double[][] obsmat;

    static int counter;

    public MatrixThings(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        matrix = new double[rows*cols*4][rows*cols*4];
        obsmat = new double[rows*cols*4][rows*cols*4 + 1];

        counter = 0;
        makeMatrix();
        makeObsmat();
    }

    public void makeMatrix() {
        int currx = 0, curry = 0, currh = 0;
        for(int i = 0; i < rows*cols*4; i++) {
            currh = i % 4;
            currx = (i / 4) / cols;
            curry = (i / 4) % cols;

            if(currx == 0 && curry == 0) {
                if (currh == 0 || currh == 3) {
                    matrix[i][xyhToI(0, 1, 1)] = .5;
                    matrix[i][xyhToI(1, 0, 2)] = .5;
                }
                if(currh == 1) {
                    matrix[i][xyhToI(0, 1, 1)] = .7;
                    matrix[i][xyhToI(1, 0, 2)] = .3;
                }
                if(currh == SOUTH) {
                    matrix[i][xyhToI(0, 1, 1)] = .3;
                    matrix[i][xyhToI(1, 0, 2)] = .7;
                }
            }

            else if(currx == 0 && curry == cols - 1) {
                if(currh == NORTH || currh == EAST) {
                    matrix[i][xyhToI(0, curry - 1, WEST)] = .5;
                    matrix[i][xyhToI(1, curry, SOUTH)] = .5;
                }
                if(currh == SOUTH) {
                    matrix[i][xyhToI(0, curry - 1, WEST)] = .3;
                    matrix[i][xyhToI(1, curry, SOUTH)] = .7;
                }
                if(currh == WEST) {
                    matrix[i][xyhToI(0, curry - 1, WEST)] = .7;
                    matrix[i][xyhToI(1, curry, SOUTH)] = .3;
                }
            }

            else if(currx == rows - 1 && curry == 0) {
                if(currh == SOUTH || currh == WEST) {
                    matrix[i][xyhToI(currx - 1, 0, NORTH)] = .5;
                    matrix[i][xyhToI(currx, 1, EAST)] = .5;
                }
                if(currh == NORTH) {
                    matrix[i][xyhToI(currx - 1, 0, NORTH)] = .7;
                    matrix[i][xyhToI(currx, 1, EAST)] = .3;
                }
                if(currh == EAST) {
                    matrix[i][xyhToI(currx - 1, 0, NORTH)] = .3;
                    matrix[i][xyhToI(currx, 1, EAST)] = .7;
                }
            }

            else if(currx == rows - 1 && curry == cols - 1) {
                if(currh == EAST || currh == SOUTH) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .5;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .5;
                }
                if(currh == WEST) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .3;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .7;
                }
                if(currh == NORTH) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .7;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .3;
                }
            }

            else if(currx == 0) {
                if(currh == NORTH) {
                    matrix[i][xyhToI(currx, curry+1, EAST)] = 1.0/3.0;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = 1.0/3.0;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = 1.0/3.0;
                }
                if(currh == EAST) {
                    matrix[i][xyhToI(currx, curry+1, EAST)] = .7;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = .15;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .15;
                }
                if(currh == SOUTH) {
                    matrix[i][xyhToI(currx, curry+1, EAST)] = .15;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = .70;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .15;
                }
                if(currh == WEST) {
                    matrix[i][xyhToI(currx, curry+1, EAST)] = .15;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = .15;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .7;
                }
            }

            else if(curry == cols - 1) {
                if(currh == EAST) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = 1.0/3.0;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = 1.0/3.0;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = 1.0/3.0;
                }
                if(currh == NORTH) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .7;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = .15;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .15;
                }
                if(currh == SOUTH) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .15;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = .7;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .15;
                }
                if(currh == WEST) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .15;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = .15;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .7;
                }
            }

            else if(currx == rows - 1) {
                if(currh == SOUTH) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = 1.0/3.0;
                    matrix[i][xyhToI(currx, curry+1, EAST)] = 1.0/3.0;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = 1.0/3.0;
                }
                if(currh == NORTH) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .7;
                    matrix[i][xyhToI(currx, curry+1, EAST)] = .15;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .15;
                }
                if(currh == EAST) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .15;
                    matrix[i][xyhToI(currx, curry+1, EAST)] = .7;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .15;
                }
                if(currh == WEST) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .15;
                    matrix[i][xyhToI(currx, curry+1, EAST)] = .15;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .7;
                }
            }

            else if(curry == 0) {
                if(currh == WEST) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = 1.0/3.0;
                    matrix[i][xyhToI(currx, curry+1, EAST)] = 1.0/3.0;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = 1.0/3.0;
                }
                if(currh == NORTH) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .7;
                    matrix[i][xyhToI(currx, curry+1, EAST)] = .15;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = .15;
                }
                if(currh == EAST) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .15;
                    matrix[i][xyhToI(currx, curry+1, EAST)] = .7;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = .15;
                }
                if(currh == SOUTH) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .15;
                    matrix[i][xyhToI(currx, curry+1, EAST)] = .15;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = .7;
                }
            }

            else {
                if(currh == NORTH) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .7;
                    matrix[i][xyhToI(currx, curry+1, EAST)] = .1;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = .1;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .1;
                }
                if(currh == EAST) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .1;
                    matrix[i][xyhToI(currx, curry+1, EAST)] = .7;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = .1;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .1;
                }
                if(currh == SOUTH) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .1;
                    matrix[i][xyhToI(currx, curry+1, EAST)] = .1;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = .7;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .1;
                }
                if(currh == WEST) {
                    matrix[i][xyhToI(currx-1, curry, NORTH)] = .1;
                    matrix[i][xyhToI(currx, curry+1, EAST)] = .1;
                    matrix[i][xyhToI(currx+1, curry, SOUTH)] = .1;
                    matrix[i][xyhToI(currx, curry-1, WEST)] = .7;
                }
            }



        }
    }

    public void makeObsmat() {
        int currx = 0, curry = 0, currh = 0;
        for(int i = 0; i < rows*cols*4; i+=4) {
            currh = i % 4;
            currx = (i / 4) / cols;
            curry = (i / 4) % cols;

            //System.out.println("CURRENT XY!!!: " + currx + "," +curry);


            obsmat[i][xyhToI(currx, curry, NORTH)] = .1;
            obsmat[i][xyhToI(currx, curry, EAST)] = .1;
            obsmat[i][xyhToI(currx, curry, SOUTH)] = .1;
            obsmat[i][xyhToI(currx, curry, WEST)] = .1;

            ArrayList<int[]> borders1 = numbordert1(currx, curry);
            for(int[] xy : borders1) {
                obsmat[i][xyhToI(xy[0], xy[1], NORTH)] = .05;
                obsmat[i][xyhToI(xy[0], xy[1], EAST)] = .05;
                obsmat[i][xyhToI(xy[0], xy[1], SOUTH)] = .05;
                obsmat[i][xyhToI(xy[0], xy[1], WEST)] = .05;
            }
            ArrayList<int[]> borders2 = numbordert2(currx, curry);
            for(int[] xy : borders2) {
                //System.out.println(xy[0] + "," + xy[1]);
                obsmat[i][xyhToI(xy[0], xy[1], NORTH)] = .025;
                obsmat[i][xyhToI(xy[0], xy[1], EAST)] = .025;
                obsmat[i][xyhToI(xy[0], xy[1], SOUTH)] = .025;
                obsmat[i][xyhToI(xy[0], xy[1], WEST)] = .025;
            }

            double nullchance = 1.0 - .1 - (.05*borders1.size()) - (.025*borders2.size());
            obsmat[i][rows*cols*4] = nullchance;
            obsmat[i+1][rows*cols*4] = nullchance;
            obsmat[i+2][rows*cols*4] = nullchance;
            obsmat[i+3][rows*cols*4] = nullchance;


        }
    }

    public int xyhToI(int x, int y, int h) {
        return (x * 4 * cols) + (y * 4) + h;
    }

    public double getTProb(int x, int y, int h, int nX, int nY, int nH) {
        //System.out.print(counter + ": ");
        counter++;
        return matrix[xyhToI(x,y,h)][xyhToI(nX, nY, nH)];
    }

    public double getOProb(int rx, int ry, int x, int y, int h) {
        return obsmat[xyhToI(rx, ry, 0)][xyhToI(x, y, h)];
    }
    public double getOProb(int x, int y, int h) {
        return obsmat[xyhToI(x, y, h)][rows*cols*4];
    }

    public ArrayList<int[]> numbordert1(int x, int y) {
        ArrayList<int[]> output = new ArrayList<int[]>();
        if(checkcoords(x+1, y)) {
            output.add(new int[]{x+1, y});
        }
        if(checkcoords(x-1, y)) {
            output.add(new int[]{x-1, y});
        }
        if(checkcoords(x, y+1)) {
            output.add(new int[]{x, y+1});
        }
        if(checkcoords(x, y-1)) {
            output.add(new int[]{x, y-1});
        }
        if(checkcoords(x+1, y+1)) {
            output.add(new int[]{x+1, y+1});
        }
        if(checkcoords(x+1, y-1)) {
            output.add(new int[]{x+1, y-1});
        }
        if(checkcoords(x-1, y+1)) {
            output.add(new int[]{x-1, y+1});
        }
        if(checkcoords(x-1, y-1)) {
            output.add(new int[]{x-1, y-1});
        }
        return output;
    }

    public ArrayList<int[]> numbordert2(int x, int y) {
        ArrayList<int[]> output = new ArrayList<int[]>();
        for(int i = -2; i <= 2; i++) {
            if (checkcoords(x+i, y+2)) {
                output.add(new int[]{x+i, y+2});
            }
            if (checkcoords(x+i, y-2)) {
                output.add(new int[]{x+i, y-2});
            }
        }
        for(int i = -1; i <= 1; i++) {
            if (checkcoords(x+2, y+i)) {
                output.add(new int[]{x+2, y+i});
            }
            if (checkcoords(x-2, y+i)) {
                output.add(new int[]{x-2, y+i});
            }
        }
        return output;
    }

    public boolean checkcoords(int x, int y) {
        if(x < 0 || y < 0 || x >= rows || y >= cols) {
            return false;
        }

        return true;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public double[][] getObsmat() {
        return obsmat;
    }
}
