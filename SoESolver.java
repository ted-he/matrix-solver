/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soesolver;

/**
 * Author: Ted He Class: ICS4U
 *
 * Input: Processing: Output:
 */
public class SoESolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /*
        final double[][] oCoeffs = {
            {2, 4, -3},
            {-6, 2, 7},
            {4, -3, 2}
        };
        
        final double[] oConsts = {-3, 39, 9};
        
        final double[][] coeffs = {
            {2, 4, -3},
            {-6, 2, 7},
            {4, -3, 2}
        };
        
        final double[] consts = {-3, 39, 9};
        */
        
        double[][] oCoeffs = {
            {2, 2, -1, 1},
            {4, 0, -1, 2},
            {8, 5, -3, 4},
            {3, 3, -2, 2}
        };
        
        double[] oConsts = {4, 6, 12, 6};
        
        double[][] coeffs = {
            {2, 2, -1, 1},
            {4, 0, -1, 2},
            {8, 5, -3, 4},
            {3, 3, -2, 2}
        };
        
        double[] consts = {4, 6, 12, 6};
        
        
        System.out.println("TBS:");
        display(coeffs, consts);

        double[][][] REFMtx = toREF(coeffs, consts);

        System.out.println("REF:");
        display(REFMtx[0], REFMtx[1][0]);

        double[] solution = solve(REFMtx[0], consts);
        
        if(checkSol(oCoeffs, oConsts, solution)) {
            System.out.print("\nVALID SOLUTION FOUND: ");
            display(solution);
        } else {
            System.out.println("\nWARNING: NO SOLUTION");
        }

    }

    public static void display(double[][] coeffs, double[] consts) {

        for (int row = 0; row < coeffs.length; row++) {

            for (int col = 0; col < coeffs[0].length; col++) {

                System.out.print(coeffs[row][col] + "\t");

            }

            System.out.println("│  " + consts[row]);

        }

        System.out.println("");

    }
    
    public static void display(double[][] mtx) {
        for(double[] row : mtx) {
            for(double col : row) {
                System.out.print(col + "\t");
            }
            System.out.println("");
        }
    }
    
    public static void display(double[] array) {
        System.out.print("[");
        for(double element : array) {
            System.out.print(element + " ");
        }
        System.out.println("\b]\n");
    }

    public static double[][][] toREF(double[][] coeffs, double[] consts) {

        for (int refRow = 0; refRow < coeffs.length - 1; refRow++) { // Loop through "reference" rows

            double mult1 = coeffs[refRow][refRow]; // Get first nonzero coefficient from "reference" row

            for (int modRow = refRow + 1; modRow < coeffs.length; modRow++) { // Loop through "modify" rows

                double mult2 = coeffs[modRow][refRow]; // Get first nonzero coefficient from "modify" row

                for (int col = refRow; col < coeffs.length; col++) { // Loop through columns

                    coeffs[modRow][col] = (mult2 * coeffs[refRow][col]) - (mult1 * coeffs[modRow][col]);

                }

                consts[modRow] = (mult2 * consts[refRow]) - (mult1 * consts[modRow]);

            }

        }

        double[][][] output = {coeffs, {consts}};

        return output;

    }

    public static double[] solve(double[][] REFMtx, double[] consts) {

        double[] sol = new double[REFMtx[0].length]; // Solution array

        for (int row = REFMtx.length - 1; row >= 0; row--) { // Loops through each row in REF array backwards

            double divisor = REFMtx[row][row]; // Gets the coefficient of the target unknown of the current row

            for (int col = REFMtx[row].length - 1; col > row; col--) { // Loops through each column backwards

                consts[row] -= REFMtx[row][col] * sol[col];

            }

            sol[row] = consts[row] / divisor;

        }

        return sol;

    }
    
    public static boolean checkSol(double[][] coeffs, double[] consts, double[] solution) {
        
        System.out.println("Solution found. Verifying...");
        System.out.println("System Matrix: ");
        display(coeffs, consts);
        System.out.print("Solution: ");
        display(solution);
        
        for(int row = 0; row < coeffs.length; row++) {
            
            double curSum = 0;
            double[] curRow = coeffs[row];
            
            System.out.print("Checking equation " + (row + 1) + ": ");
            
            for(int col = 0; col < coeffs.length; col++) {
                curSum += (curRow[col] * solution[col]);
                System.out.print(curRow[col] + "(" + solution[col] + ") + ");
            }
            
            System.out.println("\b\b= " + consts[row]);
             
            if (consts[row] != curSum) return false;
            
        }
        
        return true;
    }

}
