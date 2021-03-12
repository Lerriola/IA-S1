package ProbRed;


import IA.Red.CentrosDatos;
import IA.Red.Sensores;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by bejar on 17/01/17.
 */
public class ProbRedState{

    // ATRIBUTES
    static Sensores Sens;
    static CentrosDatos Cent;

    //Adjaceny List
    private ArrayList<Integer> DAG; //LAS PRIMERAS POSICIONES SON SENSORES, LAS ULTIMAS SON CENTROS DE DATOS

    // CONSTRUCTORS
    public ProbRedState(int nSens, int nCent, int seed) {
        Sens = new Sensores(nSens, seed);
        Cent = new CentrosDatos(nCent, seed);

        iniSolution(nSens, nCent);
    }

    public int SensSize(){
        return Sens.size();
    }

    public int  CentSize(){
        return Cent.size();
    }

    private int countCONNEX(int id) {
        int counter = 0;
        for (int i = 0; i < DAG.size(); ++i) {
            if (DAG.get(i) == id) counter++;
        }
        return counter;
    }

    private double countDATA(int id){
        double counter = 0;
        for (int j = 0; j < SensSize(); ++j) {
            if(j != id){
                if(DAG.get(j) == id){
                    counter += Sens.get(j).getCapacidad();
                }
            }
        }
        return counter;
    }

    private void iniSolution(int nSens, int nCent){

        DAG = new ArrayList<Integer>(nSens + nCent);

        boolean menor3 = true;
        boolean menor25 = true;

        Random rand = new Random();


        for(int i = 0; i < SensSize(); ++i) {
            menor3 = true;
            menor25 = true;
            // Generamos un int random
            int RI = rand.nextInt(SensSize() + CentSize());

            if (i != RI) {
                if(RI < SensSize()){
                    while(menor3) {
                        if (countCONNEX(RI) < 3) { // Comprobamos que el sensor i no tenga más de 3 connex.
                            DAG.set(i, RI);
                            menor3 = false;
                        } else RI = rand.nextInt(SensSize() + CentSize());
                    }
                }
                else{
                    while(menor25) {
                        if (countCONNEX(RI) < 25) { // Comprobamos que el sensor i no tenga más de 3 connex.
                            DAG.set(i, RI);
                            menor25 = false;
                        }
                        else RI =  rand.nextInt(SensSize() + CentSize());
                    }
                }
            }
        }
    }

    public void PrintRed(){
        for(int i = 0; i < DAG.size(); ++i){
            System.out.println(i + " apunta a " + DAG.get(i) + "//" );
        }
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
