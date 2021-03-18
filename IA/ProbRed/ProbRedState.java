package ProbRed;


import IA.Red.CentrosDatos;
import IA.Red.Sensores;

import java.util.ArrayList;
import java.util.Random;

public class ProbRedState{

    // ATRIBUTES
    static Sensores Sens;
    static CentrosDatos Cent;

    public static String CHANGE = "change";

    //Adjaceny List
    private ArrayList<Integer> DAG; //LAS PRIMERAS POSICIONES SON SENSORES, LAS ULTIMAS SON CENTROS DE DATOS
    private int[] counter;

    // CONSTRUCTORS
    public ProbRedState(int nSens, int nCent, int seed) {

        Sens = new Sensores(nSens, seed);
        Cent = new CentrosDatos(nCent, seed);

        DAG = new ArrayList<Integer>(nSens+nCent);
        counter = new int[nSens + nCent];

        IniDAG(nCent+nSens);
        iniSolution2(nSens,nCent);
    }

    public ProbRedState(ProbRedState original) {
        for (int i = 0; i < original.SensSize(); i++) {
            DAG.set(i, original.DAG.get(i));
            counter[i] = original.counter[i];
        }
    }

    private void IniDAG(int n){
        for(int i = 0; i < n; ++i){
            DAG.add(i,-1);
        }
    }

    public int SensSize(){
        return Sens.size();
    }

    public double getCapacity(int i) { return Sens.get(i).getCapacidad(); }

    public void printInfo(){
        for(int i = 0; i < SensSize(); ++i){
            System.out.println(Sens.get(i).getCapacidad() + " " + Sens.get(i).getCoordX() + " " + Sens.get(i).getCoordY());

        }
    }


    public int CentSize(){
        return Cent.size();
    }

    public int DAGSize() { return DAG.size();}

    public boolean Cicles(int sensID) {

        int[] visited = new int[SensSize()+CentSize()];
        for (int i = 0; i < visited.length; i++) {
            visited[i] = 0;
        }

        visited[sensID] = 1;

        int nxt = DAG.get(sensID);
        while (nxt < Sens.size()) {
            visited[nxt] = 1;
            nxt = DAG.get(nxt);
            if (visited[nxt] == 1) return true;
        }
        return false;
    }

    public int CiclesGeneral() {
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

        boolean CentroEmpty = true;
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
                while (!ConectedTOSensor);
            }
        }
    }

    private double cost(int A, int B){
        double pA = Sens.get(A).getCoordX() - Sens.get(A).getCoordY();
        pA = pA * pA;
        double pB = Sens.get(B).getCoordX() - Sens.get(B).getCoordY();
        pB = pB * pB;
        double d = pA + pB;
        return d *  //DATOS TRANSMITIDOS, AUN POR CALCULAR//Sens.get(A).getCapacidad();
    }

    public void changeConexion(int i, int New){
        DAG.set(i,New);
    }

    public boolean maxCapacity(int pos){
        if(pos < this.SensSize()) return counter[pos] < 3;
        else return counter[pos] < 25;
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
