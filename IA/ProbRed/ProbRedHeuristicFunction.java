package IA.ProbRed;

import aima.search.framework.HeuristicFunction;

public class ProbRedHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        double Lmda = 0.9;

        ProbRedState board = (ProbRedState) n;


        Double coste = board.totalCost();
        Double totalData = board.totalData();
        Double actualData = board.getDataCenters();
        Double lostData = totalData-actualData;

        Double HeurVal1 =  coste -  actualData;

        Double HeurVal2 = coste / actualData;
        //System.out.println( " Coste: " + coste /*+ " - Heur: " + HeurVal2*/);
       // System.out.println("Data: " + actualData + "/" + totalData);
        //board.PrintDAGData();
        //System.out.println("Porcentaje de datos recogidos: " + (actualData/totalData)*100.);

        
        return HeurVal2;
    }
}
