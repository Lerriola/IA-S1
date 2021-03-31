package IA.ProbRed;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;

public class ProbRedSuccesorFunctionH implements SuccessorFunction {

    public List getSuccessors(Object state){

        ArrayList retval = new ArrayList();
        ProbRedState Estado = (ProbRedState) state;

        for(int i = 0; i < Estado.SensSize(); i++){
            for (int j = 0; j < Estado.DAGSize(); ++j){
                ProbRedState new_state = new ProbRedState(Estado);
                if(i != j && new_state.maxCapacity(j)) {
                    boolean tecicles = new_state.changeConexion(i,j);
                    if(!tecicles) {
                        //new_state.PrintDAGData();
                        retval.add(new Successor(new_state.CHANGE + " " + i + " " + j, new_state));
                        //System.out.println(new_state.CHANGE + " " + i + " " + j);
                    }
                }
            }
        }
        return retval;
    }
}
