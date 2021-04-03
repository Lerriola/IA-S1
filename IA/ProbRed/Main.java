package IA.ProbRed;

import IA.ProbRed.*;

import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.AStarSearch;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.IterativeDeepeningAStarSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);

        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("--Artificial Intelligence laboratory assignment on Local Search Algorithms--" );
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("                       .,,uod8B8bou,,.\n" +
                "              ..,uod8BBBBBBBBBBBBBBBBRPFT?l!i:.\n" +
                "         ,=m8BBBBBBBBBBBBBBBRPFT?!||||||||||||||\n" +
                "         !...:!TVBBBRPFT||||||||||!!^^\"\"'   ||||\n" +
                "         !.......:!?|||||!!^^\"\"'            ||||\n" +
                "         !.........||||                     ||||\n" +
                "         !.........||||  ##                 ||||\n" +
                "         !.........||||                     ||||\n" +
                "         !.........||||                     ||||\n" +
                "         !.........||||                     ||||\n" +
                "         !.........||||                     ||||\n" +
                "         `.........||||                    ,||||\n" +
                "          .;.......||||               _.-!!|||||\n" +
                "   .,uodWBBBBb.....||||       _.-!!|||||||||!:'\n" +
                "!YBBBBBBBBBBBBBBb..!|||:..-!!|||||||!iof68BBBBBb....\n" +
                "!..YBBBBBBBBBBBBBBb!!||||||||!iof68BBBBBBRPFT?!::   `.\n" +
                "!....YBBBBBBBBBBBBBBbaaitf68BBBBBBRPFT?!:::::::::     `.\n" +
                "!......YBBBBBBBBBBBBBBBBBBBRPFT?!::::::;:!^\"`;:::       `.\n" +
                "!........YBBBBBBBBBBRPFT?!::::::::::^''...::::::;         iBBbo.\n" +
                "`..........YBRPFT?!::::::::::::::::::::::::;iof68bo.      WBBBBbo.\n" +
                "  `..........:::::::::::::::::::::::;iof688888888888b.     `YBBBP^'\n" +
                "    `........::::::::::::::::;iof688888888888888888888b.     `\n" +
                "      `......:::::::::;iof688888888888888888888888888888b.\n" +
                "        `....:::;iof688888888888888888888888888888888899fT!\n" +
                "          `..::!8888888888888888888888888888888899fT|!^\"'\n" +
                "            `' !!988888888888888888888888899fT|!^\"'\n" +
                "                `!!8888888888888888899fT|!^\"'\n" +
                "                  `!988888888899fT|!^\"'\n" +
                "                    `!9899fT|!^\"'\n" +
                "                      `!^\"'");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("------------------Zixuan Sun, Cesar Mellado & Leo Arriola-------------------");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println();
        System.out.println();


        System.out.println("---------------------------------");
        System.out.println("Introduce the number of sensors :" );
        System.out.println("---------------------------------");

        int Sens;
        try {
            Sens = Integer.parseInt(in.nextLine());
        }catch (Exception e){
            System.out.println("Something went wrong. Pleas try again.");
            return;
        }

        System.out.println("--------------------------------------");
        System.out.println("Introduce the number of data centers :");
        System.out.println("--------------------------------------");

        int Cent;
        try {
            Cent = Integer.parseInt(in.nextLine());
        }catch (Exception e){
            System.out.println("Something went wrong. Pleas try again.");
            return;
        }

        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("Would you like to manually introduce the sensor and data center seeds, Answer Y/N :");
        System.out.println("-----------------------------------------------------------------------------------");

        int SensSeed;
        int CentSeed;

        String Seed = in.nextLine();
        if(Seed.equals("Y")) {

            System.out.println("-------------------------------------");
            System.out.println("Please type the desired sensor seed :");
            System.out.println("-------------------------------------");

            try {
                SensSeed = Integer.parseInt(in.nextLine());
            }catch (Exception e){
                System.out.println("Something went wrong. Pleas try again.");
                return;
            }

            System.out.println("-------------------------------------------------");
            System.out.println("Please type the desired sensor data center seed :");
            System.out.println("-------------------------------------------------");

            try {
                CentSeed = Integer.parseInt(in.nextLine());
            }catch (Exception e){
                System.out.println("Something went wrong. Pleas try again.");
                return;
            }
        }
        else {
            Random r = new Random();
            SensSeed = r.nextInt(999999);
            CentSeed = r.nextInt(999999);
        }

        ProbRedState pb = new ProbRedState(Sens, Cent, SensSeed, CentSeed);

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Type H to choose HillClimbing or S to choose Simulated Annealing as the seach algorithm :");
        System.out.println("-----------------------------------------------------------------------------------------");
        String ALG = in.nextLine();

        long start = System.currentTimeMillis();
        long end = 0;
        if (ALG.equals("H")) {
            // Create the Problem object
            Problem p = new Problem(pb,
                    new ProbRedSuccesorFunctionH(),
                    new ProbRedGoalTest(),
                    new ProbRedHeuristicFunction());

            Search alg = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(p, alg);

            end = System.currentTimeMillis();

            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        }
        else if (ALG.equals("S")) {
            Problem p = new Problem(pb,
                    new ProbRedSuccesorFunctionS(),
                    new ProbRedGoalTest(),
                    new ProbRedHeuristicFunction());

            Search alg = new SimulatedAnnealingSearch(10000, 1000, 5, 0.001);
            SearchAgent agent = new SearchAgent(p, alg);

            end = System.currentTimeMillis();
            printInstrumentation(agent.getInstrumentation());

        }
        else System.out.println("Try Again");
        long elapsedTime = end - start;

        // We print the results of the search

        // You can access also to the goal state using the
        // method getGoalState of class Search

        System.out.println();
        System.out.println("Execution time " + elapsedTime);
        System.out.println("Final cost " + pb.getcosteFinal() + "   Lost data " + pb.getlossDataPerc() + " %");
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
