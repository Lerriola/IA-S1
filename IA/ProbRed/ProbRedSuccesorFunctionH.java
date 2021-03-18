package ProbRed;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;

public class ProbRedSuccesorFunctionH implements SuccessorFunction {

    public List getSuccessors(Object state){

        ArrayList retval = new ArrayList();
        ProbRedState Estado = (ProbRedState) state;

        for(int i = 0; i < Estado.SensSize(); i++){
            ProbRedState new_state = new ProbRedState(Estado);
            for (int j = 0; j < Estado.DAGSize(); ++j){
                if(i != j && !new_state.maxCapacity(j)) {
                    new_state.changeConexion(i,j);
                    if(!new_state.Cicles(i))
                        retval.add(new Successor("change "+ i + " " + j, new_state));
                }
            }
        }
        return retval;
    }
}
