package ProbRed;

import aima.search.framework.HeuristicFunction;

public class ProbRedHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        ProbRedState board = (ProbRedState) n;
        //Double Datos = board.getDataCenters();
        //Double coste = board.getTotalCost();
        //Double HeurVal = (1-Lmda) * coste - Lmda * Datos;

        return 0.0;
    }
}
