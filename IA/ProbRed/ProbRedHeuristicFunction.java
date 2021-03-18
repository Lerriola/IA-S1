package ProbRed;

/**
 * Created by bejar on 17/01/17.
 */

import aima.search.framework.HeuristicFunction;

public class ProbRedHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        ProbRedState board = (ProbRedState)n;
        int data = 0;
        for(int i = 0; i < board.SensSize(); ++i){
            data += board[i].
        }
        //return ((ProbRedState) n).heuristic();
        return 1.0;
    }
}
