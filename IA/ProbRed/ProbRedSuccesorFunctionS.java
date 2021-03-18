package ProbRed;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProbRedSuccesorFunctionS implements SuccessorFunction {

    public List getSuccessors(Object state){

        ArrayList retval = new ArrayList();
        ProbRedState Estado = (ProbRedState) state;
        ProbRedState new_state = new ProbRedState(Estado);

        Random rand = new Random();
        int randSens = rand.nextInt(new_state.SensSize());
        int randConnexion = rand.nextInt(new_state.DAGSize());
        boolean continua = true;

        while (continua) {
            new_state = new ProbRedState(Estado);
            if(randSens != randConnexion && !new_state.maxCapacity(randConnexion)){
                new_state.changeConexion(randSens,randConnexion);
                if(!new_state.Cicles(randSens)) {
                    retval.add(new Successor("change " + randSens + " " + randConnexion, new_state));
                    continua = false;
                }
            }
            if (continua) randConnexion = rand.nextInt(Estado.DAGSize());
        };
        return retval;
    }
}
