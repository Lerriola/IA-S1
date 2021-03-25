package ProbRed;

import aima.search.framework.HeuristicFunction;

public class ProbRedHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        double Lmda = 0.4;

        ProbRedState board = (ProbRedState) n;
        Double Datos = board.getDataCenters();
        Double coste = board.totalCost();
        Double HeurVal = (1-Lmda) * coste - Lmda * Datos;
        //System.out.println("Datos: " + Datos + " Coste: " + coste + " - Heur: " + HeurVal);

        return HeurVal;
    }
}
