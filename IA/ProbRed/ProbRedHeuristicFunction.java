package IA.ProbRed;

import aima.search.framework.HeuristicFunction;

public class ProbRedHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        ProbRedState board = (ProbRedState) n;


        Double coste = board.totalCost();
        Double totalData = board.totalData();
        Double actualData = board.getDataCenters();

        double lambda = 99.92; // VALOR ALTO IMPLICA PRIORIZAR QUE SE CAPTUREN EL MAXIMO DE DATOS

        Double HeurVal1 =  coste*(100-lambda) -  actualData*lambda;
        Double HeurVal2 = coste / actualData;

        Double l = (1-(actualData/totalData))*100;
        board.updateResults(coste, l);

        return HeurVal1;
    }
}
