package IA.ProbRed;

import aima.search.framework.HeuristicFunction;

public class ProbRedHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        double Lmda = 0.4;

        ProbRedState board = (ProbRedState) n;


        Double coste = board.totalCost();
        Double totalData = board.totalData();
        Double actualData = board.getDataCenters();
        Double lostData = totalData-actualData;

        Double HeurVal1 = (1-Lmda) * coste + Lmda * lostData;

        Double HeurVal2 = coste / totalData;
        //System.out.println("Datos: " + Datos + " Coste: " + coste + " - Heur: " + HeurVal);
        //System.out.println("Data: " + actualData + "/" + totalData);
        //board.PrintDAGData();
        
        return HeurVal2;
    }
}
