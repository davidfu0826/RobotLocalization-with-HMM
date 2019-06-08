package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jinjang on 2/20/19.
 */
public class RealRobot {
    private int x;
    private int y;
    private int heading;

    private int rows, cols;
    private Random rand;


    public RealRobot(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        rand = new Random(System.currentTimeMillis());
        x = rand.nextInt(rows);
        y = rand.nextInt(cols);
        heading = 0;
    }

    public int[] trueloc() {
        int[] retval = new int[3];
        retval[0] = x;
        retval[1] = y;
        retval[2] = heading;

        return retval;
    }

    public int[] movebot() {

        boolean validmove = false;
        if(heading == 0 && checkcoords(x-1, y)) {
            validmove = true;
        }
        if(heading == 1 && checkcoords(x, y+1)) {
            validmove = true;
        }
        if(heading == 2 && checkcoords(x+1, y)) {
            validmove = true;
        }
        if(heading == 3 && checkcoords(x, y-1)) {
            validmove = true;
        }

        if(validmove) {
            double randnum = rand.nextDouble();
            if (randnum <= .7) {
                if (heading == 0) {
                    x--;
                    return new int[]{x, y, heading};
                }
                if (heading == 1) {
                    y++;
                    return new int[]{x, y,  heading};
                }
                if (heading == 2) {
                    x++;
                    return new int[]{x, y, heading};
                }
                if (heading == 3) {
                    y--;
                    return new int[]{x, y, heading};
                }
            }

        }

        while (true) {
            int randnum2 = rand.nextInt(4);
            if(randnum2 == heading) continue;

            if (randnum2 == 0 && checkcoords(x-1, y)) {
                heading = randnum2;
                x--;
                return new int[]{x, y, heading};
            }
            if (randnum2 == 1 && checkcoords(x, y+1)) {
                heading = randnum2;
                y++;
                return new int[]{x, y, heading};
            }
            if (randnum2 == 2 && checkcoords(x+1, y)) {
                heading = randnum2;
                x++;
                return new int[]{x, y, heading};
            }
            if (randnum2 == 3 && checkcoords(x, y-1)) {
                heading = randnum2;
                y--;
                return new int[]{x, y, heading};
            }


        }


    }

    public ArrayList<int[]> numbordert1() {
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

    public ArrayList<int[]> numbordert2() {
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

    public int[] sensorRead() {
        double randnum = rand.nextDouble();
        ArrayList<int[]> bordert1 = numbordert1();
        ArrayList<int[]> bordert2 = numbordert2();

        double tier1 = .1;
        double tier2 = bordert1.size() * .05;
        double tier3 = bordert2.size() * .025;

        if(randnum <= tier1) return new int[]{x, y};
        else if(randnum < tier1 + tier2) return bordert1.get(rand.nextInt(bordert1.size()));
        else if(randnum < tier1 + tier2 + tier3) return bordert2.get(rand.nextInt(bordert2.size()));
        else {
            return new int[]{-1,-1};
        }

    }



    public boolean checkcoords(int x, int y) {
        if(x < 0 || y < 0 || x >= rows || y >= cols) {
            return false;
        }

        return true;
    }


}
