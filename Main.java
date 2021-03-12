
import IA.ProbIA5.ProbIA5Board;
import IA.ProbIA5.ProbIA5GoalTest;
import IA.ProbIA5.ProbIA5HeuristicFunction;
import IA.ProbIA5.ProbIA5SuccesorFunction;
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

        ProbRedState pb = new ProbRedState(10, 2, 1234);
        pb.PrintRed();

    }
}
