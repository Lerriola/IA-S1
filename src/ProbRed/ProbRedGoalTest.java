package ProbRed;

import aima.search.framework.GoalTest;

/**
 * Created by bejar on 17/01/17.
 */
public class ProbRedGoalTest implements GoalTest {

    public boolean isGoalState(Object state){
        //return((ProbRedState) state).is_goal();
        return true;
    }
}
