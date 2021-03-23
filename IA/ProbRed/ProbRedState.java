package ProbRed;


import IA.Red.CentrosDatos;
import IA.Red.Sensores;

import java.util.*;

public class ProbRedState{

    // ATRIBUTES
    static Sensores Sens;
    static CentrosDatos Cent;

    public static String CHANGE = "change";

    //Adjaceny List
    private ArrayList<Integer> DAG; //LAS PRIMERAS POSICIONES SON SENSORES, LAS ULTIMAS SON CENTROS DE DATOS
    private int[] counter;
    private ArrayList<Double> DAGData; // GUARDA PARA CADA SENSOR LOS DATOS QUE TIENE ALMACENADOS, INCLUYENDO LOS QUE RECUBE DE SUS CONEXIONES

    // CONSTRUCTORS
    public ProbRedState(int nSens, int nCent, int seed) {

        Sens = new Sensores(nSens, seed);
        Cent = new CentrosDatos(nCent, seed);

        DAG = new ArrayList<Integer>(nSens+nCent);
        counter = new int[nSens + nCent];
        DAGData = new ArrayList<Double>(nSens+nCent);

        IniDAG(nCent+nSens);
        IniDAGData(nCent+nSens);
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

    private void IniDAGData(int n){
        for(int i = 0; i < n; ++i) {
            DAGData.add(i,0.0);
        }
    }

    public int SensSize(){
        return Sens.size();
    }

    public double getCapacity(int i) { return Sens.get(i).getCapacidad(); }

    public int CentSize(){
        return Cent.size();
    }

    public int DAGSize() { return DAG.size();}

    public int DAGDataSize(){ return DAGData.size();}

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
            DAG.set(i, aux1);
            ++aux1;
            double capacidadI = Sens.get(i).getCapacidad();
            DAGData.set(i, capacidadI);
            DAGData.set(nSens+i, capacidadI);
        }

        for(int j = nCent; j < DAG.size() - nCent; ++j){
            if (j+1 == nSens) { // last sensor
                DAG.set(j,0);
                double capacity = Sens.get(j).getCapacidad();
                double dataInput = DAGData.get(j-1);
                DAGData.set(j, Double.min(3*capacity, capacity+dataInput));
            }
            else {
                double capacity = Sens.get(j).getCapacidad();
                double dataInput = 0;
                if (j != nCent) dataInput = DAGData.get(j-1);
                DAGData.set(j, Double.min(3*capacity, capacity+dataInput));
                DAG.set(j, j+1);
            }

        }
        double capacity = Sens.get(0).getCapacidad();
        double dataInput = DAGData.get(nSens-1);
        DAGData.set(0, Double.min(3*capacity, capacity+dataInput));
        DAGData.set(nSens, Double.min(150, DAGData.get(0)));
    }

    private Queue<Integer> NodeParents(int id) {
        Queue<Integer> daddy = new LinkedList<Integer>();
        for (int i = 0; i < DAG.size(); ++i) {
            if (DAG.get(i) == id) daddy.add(i);
        }
        return daddy;
    }

    private double dataCalculation(int i) {
        Queue<Integer> parents = NodeParents(i);
        if(parents.isEmpty() && i < SensSize()) {
            DAGData.set(i, Sens.get(i).getCapacidad());
            return DAGData.get(i);
        }
        else{
            if(i < SensSize())DAGData.set(i,Sens.get(i).getCapacidad());
            while(!parents.isEmpty()){
                Integer k = parents.peek();
                parents.poll();
                double value = DAGData.get(i) + dataCalculation(k);
                DAGData.set(i, value);
                if(i < SensSize()){
                    double max = Sens.get(i).getCapacidad();
                    if(value > 3*max) DAGData.set(i,3*max);
                }
                else{
                    if(DAGData.get(i) > 150.) DAGData.set(i,150.);
                }
            }
            return DAGData.get(i);
        }

    }


    private void iniSolution2(int nSens, int nCent){

        DAG = new ArrayList<Integer>(nSens + nCent);
        IniDAG(nSens+nCent);

        DAGData = new ArrayList<Double>(nSens + nCent);
        IniDAGData(nSens+nCent);


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
        for(int m = DAGSize()-nCent; m < DAGSize(); ++m){
            double aux = dataCalculation(m);
        }
    }
/*
    private double cost(int A, int B){
        double pA = Sens.get(A).getCoordX() - Sens.get(A).getCoordY();
        pA = pA * pA;
        double pB = Sens.get(B).getCoordX() - Sens.get(B).getCoordY();
        pB = pB * pB;
        double d = pA + pB;
        return d *  //DATOS TRANSMITIDOS, AUN POR CALCULAR//Sens.get(A).getCapacidad();
    }
*/
    public void changeConexion(int i, int New){
        updateOldPath(i);
        updateNewPath(i, New);
        DAG.set(i,New);
    }

    public void swapConexion(int S1, int S2, int S3, int S4 ){
        updateOldPath(S1);
        updateOldPath(S3);
        updateNewPath(S1,S4);
        updateNewPath(S3,S2);
        DAG.set(S1,S4);
        DAG.set(S3,S2);
    }

    private void updateOldPath(int i) {
        // Update old
        int OldSensor = DAG.get(i);
        DAGData.set(OldSensor, DAGData.get(OldSensor) - DAGData.get(i));

        double datalast = 0;
        int k = DAG.get(OldSensor); // current sensor
        if (k != -1) {
            while (k < Sens.size()) {
                if (DAG.get(k) >= Sens.size()) datalast = DAGData.get(k);
                double capacity = Sens.get(k).getCapacidad();
                double dataInput = DAGData.get(OldSensor);
                DAGData.set(k, Double.min(3 * capacity, capacity + dataInput));
                OldSensor = k;
                k = DAG.get(k);
            }

            // Update old center
            double diff = DAGData.get(OldSensor) - datalast;
            DAGData.set(k, DAGData.get(k) + diff);
        }
    }

    private void updateNewPath(int i, int New){
        // Update new
        double datalast = 0;
        while (New < Sens.size()) {
            if (DAG.get(New) >= Sens.size()) datalast = DAGData.get(New);
            double capacity = Sens.get(New).getCapacidad();
            double dataInput = DAGData.get(i);
            DAGData.set(New, Double.min(3*capacity, DAGData.get(New)+dataInput));
            i = New;
            New = DAG.get(New);
        }
        // Update new center
        double diff2 = DAGData.get(i) - datalast; // 0 or positive
        DAGData.set(New, Double.min(150,DAGData.get(New)+diff2));
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

    public void PrintData(){
        for(int i = 0; i < DAGData.size(); ++i) {
            if (i >= Sens.size()) System.out.println("========= Data received: (Centers) ");
            if (i == Cent.size()) System.out.println("-  -  -  -");
            System.out.println(i + " -------> " + DAGData.get(i));
        }
    }

    public void printInfo(){
        for(int i = 0; i < SensSize(); ++i) {
            System.out.print("Sens:" + i + "   C:" + Sens.get(i).getCapacidad());
            System.out.print("   X:" + Sens.get(i).getCoordX());
            System.out.print("   Y:" + Sens.get(i).getCoordY());
            System.out.println();
        }
    }
}
