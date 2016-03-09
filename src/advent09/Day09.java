package advent09;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import utilities.InputScanner;

/**
 * 
 * --- Day 9: All in a Single Night ---
 * 
 * Every year, Santa manages to deliver all of his presents in a single night.
 * 
 * This year, however, he has some new locations to visit; his elves have provided 
 * him the distances between every pair of locations. He can start and end at any 
 * two (different) locations he wants, but he must visit each location exactly once. 
 * What is the shortest distance he can travel to achieve this?
 * 
 * For example, given the following distances:
 * 		London to Dublin = 464
 * 		London to Belfast = 518
 * 		Dublin to Belfast = 141
 * 
 * The possible routes are therefore:
 * 		Dublin -> London -> Belfast = 982
 * 		London -> Dublin -> Belfast = 605
 * 		London -> Belfast -> Dublin = 659
 * 		Dublin -> Belfast -> London = 659
 * 		Belfast -> Dublin -> London = 605
 * 		Belfast -> London -> Dublin = 982
 * 
 * The shortest of these is London -> Dublin -> Belfast = 605, 
 * and so the answer is 605 in this example.
 * 
 * What is the distance of the shortest route?
 * @author Hisoka
 *
 */
public class Day09 {

	DirectedGraph<Node> graph = new DirectedGraph<Node>();

	public static void main(String args[]){
		File file = new File("/Users/Hisoka/Documents/workspace/adventofcode/src/advent09/routes.txt");

		Day09 day09 = new Day09();
		
		//day09.graph.addEdge(new Node("Faerun"), new Node("Norrath"), 129d);
		//day09.graph.addEdge(new Node("Faerun"), new Node("TEST"), 129d);
		
		
		try {
			day09.createGraph(InputScanner.readLines(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//System.out.println("" + day09.graph.getEdgesFor(new Node("Faerun")).containsKey(day09.graph.get(new Node("Norrath"))));
		//DijkstrasAlgorithm algorithm = new DijkstrasAlgorithm(day09.graph, new Node("Straylight"));
		DijkstrasAlgorithm algorithm = new DijkstrasAlgorithm(day09.graph, new Node("Faerun"));
		//algorithm.assignTenitiveDistance(new Node("Straylight"));
		algorithm.run();
		//System.out.println(day09.graph.toString());

	}
	
	/**
	 * Create a graph from the input given.
	 * @param edges
	 */
	public void createGraph(List<String> edges) {
		for (String e : edges) {
			// ex. Faerun to Norrath = 129
			String[] parts = e.split(" ");
			graph.addNode(new Node(parts[0]));
			graph.addNode(new Node(parts[2]));
			
			//System.out.println("Adding Edge(" + parts[0] + ", " + parts[2] + ", " + Double.valueOf(parts[4]) + ")");
			
			graph.addEdge(new Node(parts[0]), new Node(parts[2]), Double.valueOf(parts[4]));
		}
	}
}
