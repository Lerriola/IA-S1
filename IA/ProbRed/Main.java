package IA.ProbRed;

import IA.ProbRed.*;

import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.AStarSearch;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.IterativeDeepeningAStarSearch;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception {

        //Random r = new Random();
        //int randomseed = r.nextInt(9999);
        ProbRedState pb = new ProbRedState(100, 4, 1234, 4321);

        // Create the Problem object
        Problem p = new  Problem(pb,
                new ProbRedSuccesorFunctionH(),
                new ProbRedGoalTest(),
                new ProbRedHeuristicFunction());

        Search alg = new HillClimbingSearch();
        long start = System.currentTimeMillis();
        SearchAgent agent = new SearchAgent(p, alg);
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        System.out.println();
        // We print the results of the search
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());
        // You can access also to the goal state using the
        // method getGoalState of class Search
        System.out.println();
        System.out.println("Elapsed time " + elapsedTime);
        System.out.println();
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
    }

    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}
