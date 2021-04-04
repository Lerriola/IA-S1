package IA.ProbRed;


import IA.Red.CentrosDatos;
import IA.Red.Sensores;

import java.util.*;

public class ProbRedState{

    // ATRIBUTES
    static Sensores Sens;
    static CentrosDatos Cent;

    public static String CHANGE = "change";
    public static String SWAP = "swap";

    //Adjaceny List
    private ArrayList<Integer> DAG; //LAS PRIMERAS POSICIONES SON SENSORES, LAS ULTIMAS SON CENTROS DE DATOS
    private int[] counter;
    private ArrayList<Double> DAGData; // GUARDA PARA CADA SENSOR LOS DATOS QUE TIENE ALMACENADOS, INCLUYENDO LOS QUE RECUBE DE SUS CONEXIONES

    static Double costeFinal = -1.0;
    static Double lossDataPerc = -1.0;
    
    // CONSTRUCTORS
    public ProbRedState(int nSens, int nCent, int SenSeed, int CentSeed) {

        Sens = new Sensores(nSens,SenSeed);
        Cent = new CentrosDatos(nCent, CentSeed);

        DAG = new ArrayList<Integer>(nSens+nCent);
        counter = new int[nSens + nCent];
        DAGData = new ArrayList<Double>(nSens+nCent);

        IniDAG(nCent+nSens);
        IniDAGData(nCent+nSens);
        iniSolution2(nSens,nCent);
    }

    public ProbRedState(ProbRedState original) {

        int CopySize = original.DAGSize();

        DAG = new ArrayList<Integer>(CopySize);
        counter = new int[CopySize];
        DAGData = new ArrayList<Double>(CopySize);

        IniDAG(CopySize);
        IniDAGData(CopySize);

        for (int i = 0; i < CopySize; i++) {
            if(i < original.SensSize()) DAG.set(i, original.DAG.get(i));
            counter[i] = original.counter[i];
            DAGData.set(i, original.DAGData.get(i));
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
                if(i < SensSize()) {
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

    public double totalCost(){
        double cost = 0.;
        for(int i = 0; i < DAGSize() - CentSize(); ++i){
            int x1 = Sens.get(i).getCoordX();
            int y1 = Sens.get(i).getCoordY();
            int x2, y2;
            if(DAG.get(i) >= SensSize()){
                x2 = Cent.get(DAG.get(i) - SensSize()).getCoordX();
                y2 = Cent.get(DAG.get(i) - SensSize()).getCoordY();
            }
            else{
                x2 = Sens.get(DAG.get(i)).getCoordX();
                y2 = Sens.get(DAG.get(i)).getCoordY();
            }
            double dist = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1);
            cost += (dist*DAGData.get(i));
        }
        return cost;
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

    public boolean changeConexion(int i, int New) {
        updateOldPath(i);
        DAG.set(i,New);
        boolean tecicles = Cicles(i);
        if (tecicles) return true;
        updateNewPath(i, New);
        return false;
    }

    public boolean swapConexion(int s1,int s2){
        updateOldPath(s1);
        updateOldPath(s2);
        int aux1 = DAG.get(s2);
        int aux2 = DAG.get(s1);
        DAG.set(s1,aux1);
        boolean tecicles = Cicles(s1);
        if(tecicles) return true;
        DAG.set(s2,aux2);
        boolean tecicles2 = Cicles(s2);
        if(tecicles2)return true;
        updateNewPath(s1,aux1);
        updateNewPath(s2,aux2);
        return false;
    }
    public void UpdateALL() {
        double aux = 0;
        for (int m = SensSize(); m < DAGSize(); ++m)
            aux = dataCalculation(m);
    }

    public double totalData() {
        double counter = 0;
        for (int j = 0; j < SensSize(); ++j)
            counter += Sens.get(j).getCapacidad();
        return counter;
    }

    private void updateOldPath(int i) {
        // Update old
        int OldSensor = DAG.get(i);
        --counter[OldSensor];

        double datalast = 0;
        int k = DAG.get(OldSensor); // current sensor
        if (k != -1) {
            Queue<Integer> OldParents = NodeParents(OldSensor);
            double oldData = 0;
            while(!OldParents.isEmpty()){
                Integer L = OldParents.peek();
                OldParents.poll();
                oldData += DAGData.get(L);
            }
            oldData -= DAGData.get(i);
            double cap = Sens.get(OldSensor).getCapacidad();
            DAGData.set(OldSensor, Double.min(3 * cap, oldData + cap));

            while (k < Sens.size()) {
                if (DAG.get(k) >= Sens.size()) datalast = DAGData.get(k); //ULTIMO SENSOR
                Queue<Integer>Parents = NodeParents(k);
                double data = Sens.get(k).getCapacidad();
                while (!Parents.isEmpty()) {
                    Integer q = Parents.peek();
                    Parents.poll();
                    data += DAGData.get(q);
                }
                double capacity = Sens.get(k).getCapacidad();
                DAGData.set(k, Double.min(3 * capacity, data));
                OldSensor = k;
                k = DAG.get(k);

            }
            Queue<Integer> pp = NodeParents(k);
            double dTot = 0.;
            while(!pp.isEmpty()){
                Integer o = pp.peek();
                pp.poll();
                dTot += DAGData.get(o);
            }
            DAGData.set(k, dTot);
        }
        else DAGData.set(OldSensor, Double.min(150,DAGData.get(OldSensor)-DAGData.get(i)));
    }



    private void updateNewPath(int i, int New){
        // Update new
        double datalast = 0;
        ++counter[New];
        // No apunta a centro
        int aux = New;
        int antaux = i;
        while (aux < Sens.size()) {
            if (DAG.get(aux) >= Sens.size()) datalast = DAGData.get(aux);
            Queue<Integer> Parents = NodeParents(aux);
            double data = Sens.get(aux).getCapacidad();
            while(!Parents.isEmpty()) {
                Integer q = Parents.peek();
                Parents.poll();
                data += DAGData.get(q);
            }
            double capacity = Sens.get(aux).getCapacidad();
            DAGData.set(aux, Double.min(3*capacity, data));
            antaux = aux;
            aux = DAG.get(aux);

        }
        // Update new center
        Queue<Integer> pp = NodeParents(aux);
        double dTot = 0.;
        while(!pp.isEmpty()){
            Integer o = pp.peek();
            pp.poll();
            dTot += DAGData.get(o);
        }
        DAGData.set(aux, dTot);
    }


    public boolean maxCapacity(int pos){
        if(pos < this.SensSize()) return counter[pos] < 3;
        else return counter[pos] < 25;
    }

    public Double getDataCenters() {
        double total = 0;
        for (int i = Sens.size(); i < DAGData.size(); i++) {
            total += DAGData.get(i);
        }
        return total;
    }

    public void total() {
        System.out.println(getDataCenters());
    }


    public void PrintRed() {
        for(int i = 0; i < DAG.size(); ++i){
            System.out.println(i + " -----> " + DAG.get(i));
        }
    }

    public void PrintData() {
        for(int i = 0; i < DAGData.size(); ++i) {
            if (i >= Sens.size()) System.out.println("========= Data received: (Centers) ");
            if (i == Cent.size()) System.out.println("-  -  -  -");
            System.out.println(i + " -------> " + DAGData.get(i));
        }
    }

    public void PrintDAGData() {
        System.out.print("DAGData: [");
        for (int i = SensSize(); i < DAGData.size(); i++) {
            String s = i == 0 ? "" + DAGData.get(i) : ", " + DAGData.get(i);
            System.out.print(s);
        }
        System.out.print("]");
        System.out.println();
    }

    public void printInfo() {
        for(int i = 0; i < SensSize(); ++i) {
            System.out.print("Sens:" + i + "   C:" + Sens.get(i).getCapacidad());
            System.out.print("   X:" + Sens.get(i).getCoordX());
            System.out.print("   Y:" + Sens.get(i).getCoordY());
            System.out.println();
        }
        System.out.println("== Centers: ==");
        for(int i = SensSize(); i < DAG.size(); ++i) {
            System.out.print("Cent:" + (i-SensSize()));
            System.out.print("   X:" + Cent.get(i-SensSize()).getCoordX());
            System.out.print("   Y:" + Cent.get(i-SensSize()).getCoordY());
            System.out.println();
        }
    }

    public void updateResults(Double c, Double l) {
        costeFinal = c;
        lossDataPerc = l;
    }

    public Double getcosteFinal() {
        return costeFinal;
    }

    public Double getlossDataPerc() {
        return lossDataPerc;
    }
}
