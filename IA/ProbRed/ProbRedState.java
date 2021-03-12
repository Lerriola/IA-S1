package ProbRed;


import IA.Red.CentrosDatos;
import IA.Red.Sensores;

import java.util.ArrayList;

/**
 * Created by bejar on 17/01/17.
 */
public class ProbRedState{

    // ATRIBUTES
    static Sensores Sens;
    static CentrosDatos Cent;

    //Adjaçeny List
    private ArrayList<Integer> DAG;

    // CONSTRUCTORS
    public ProbRedState(int nSens, int nCent, int seed) {
        Sens = new Sensores(nSens, seed);
        Cent = new CentrosDatos(nCent, seed);

    }

    /* Class independent from AIMA classes
       - It has to implement the state of the problem and its operators
    */

    /* State data structure
        vector with the parity of the coins (we can assume 0 = heads, 1 = tails
    */

    /* auxiliary functions */

    // Some functions will be needed for creating a copy of the state

    /* ^^^^^ TO COMPLETE ^^^^^ */

}
