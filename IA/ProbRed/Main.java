
import ProbRed.*;

import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.AStarSearch;
import aima.search.informed.IterativeDeepeningAStarSearch;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("");
        ProbRedState pb = new ProbRedState(10, 2, 10887);
        //ProbRedHeuristicFunction H = new ProbRedHeuristicFunction();
        pb.printInfo();
        pb.PrintData();
        //System.out.println(H.getHeuristicValue(pb));

    }
}
