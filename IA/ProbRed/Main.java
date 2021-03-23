
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

import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception {

        Random r = new Random();
        int randomseed = r.nextInt(9999);

        int seed = randomseed;
        //int seed = 123;
        //System.out.println("========= Seed: " + seed);

        ProbRedState pb = new ProbRedState(34, 1, seed);
        //ProbRedHeuristicFunction H = new ProbRedHeuristicFunction();
        //System.out.println("========= Sensors: ");
        pb.printInfo();
        pb.PrintRed();
        //System.out.println("========= Data sent: ");
        pb.PrintData();
        //System.out.println(H.getHeuristicValue(pb));

    }
}
