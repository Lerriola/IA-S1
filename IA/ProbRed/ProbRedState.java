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

    public static String CHANGE = "change";

    //Adjaceny List
    private ArrayList<Integer> DAG; //LAS PRIMERAS POSICIONES SON SENSORES, LAS ULTIMAS SON CENTROS DE DATOS

    // CONSTRUCTORS
    public ProbRedState(int nSens, int nCent, int seed) {
        Sens = new Sensores(nSens, seed);
        Cent = new CentrosDatos(nCent, seed);
        DAG = new ArrayList<Integer>(nSens+nCent);
        IniDAG(nSens+nCent);
        System.out.println("Size antes de iniSolution: " + DAGSize());
        iniSolution1(nSens, nCent);
        System.out.println("Size después de iniSolution: " + DAGSize());
    }

    private void IniDAG(int n){
        for(int i = 0; i < n; ++i){
            DAG.add(i,-1);
        }
    }

    public int SensSize(){
        return Sens.size();
    }

    public int  CentSize(){
        return Cent.size();
    }

    public int DAGSize() { return DAG.size();}

    private boolean Cicles(int sensID) {
        int nxt = DAG.get(sensID);
        while (nxt < Sens.size()) {
            nxt = DAG.get(nxt);
            if (nxt == sensID) return true;
        }
        return false;
    }

    private int CiclesGeneral() {
        for (int i = 0; i < Sens.size(); ++i) {
            if (Cicles(i)) return i;
        }
        return -1;
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

    private void iniSolution1(int nSens, int nCent){

        int aux1 = nSens;
        for(int i = 0; i < nCent; ++i){
            DAG.set(i,aux1);
            ++aux1;
        }
        for(int j = nCent; j < DAG.size() - nCent; ++j){
            if(j + 1 == nSens )DAG.set(j,0);
            else  DAG.set(j,j+1);
        }
    }

    private void iniSolution2(int nSens, int nCent){

        DAG = new ArrayList<Integer>(nSens + nCent);
        IniDAG(nSens+nCent);

        int[] counter = new int[nSens + nCent];
        boolean CentroEmpty = true;
        int ConectToSensors = 0;
        for (int i = 0; i < nSens; ++i) {
            if (CentroEmpty){
                int j = 0;
                boolean conti;
                do {
                    if (counter[nSens + j] < 25) {
                        DAG.set(i, nSens + j);
                        ++counter[nSens + j];
                        conti = true;
                    } else {
                        conti = false;
                        ++j;
                    }
                }
                while (!conti && j < nCent);
                if (j == nCent && !conti) {
                    CentroEmpty = false;
                    DAG.set(i,0);
                    ++counter[0];
                    ConectToSensors = i-1;
                }
            }
            else {
                int j2 = 0;
                boolean ConectedTOSensor = false;
                do {
                    if(counter[j2] < 3){
                        DAG.set(i,j2);
                        ++counter[j2];
                        ConectedTOSensor = true;
                    }
                    else ++j2;
                }
                while (!ConectedTOSensor && j2 < ConectToSensors);
            }
        }
    }

    private void changeConexion(int i, int New){
        DAG.set(i,New);
    }


    public void PrintRed(){
        for(int i = 0; i < DAG.size(); ++i){
            System.out.println(i + " -----> " + DAG.get(i));
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
