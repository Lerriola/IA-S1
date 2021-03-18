
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
        ProbRedState pb = new ProbRedState(400, 2, 1234);
        pb.PrintRed();

    }
}
