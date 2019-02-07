package np_complete;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class TravelingSalesmanHeuristicSolution {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		Point[] point = readData();
		List<Integer> tour = new ArrayList<Integer>();
		List<Double> tour_distance = new ArrayList<Double>();
		tour.add(1); // first visit the point "1"
		point[0].visited = true;
		tour_distance.add(0.0);
		double min = Double.MAX_VALUE;
		int min_point = 0;
		while(tour.size() < point.length) {
			int last_in_previous_tour = tour.get(tour.size()-1);
			for(int j = 0; j < point.length; j++) {
//				if (!point[j].visited && min > (tour_distance.get(tour_distance.size()-1) + 
//						point[last_in_previous_tour-1].distanceTo(point[j]))) {
//					min = tour_distance.get(tour_distance.size()-1) + 
//							point[last_in_previous_tour-1].distanceTo(point[j]);
//					min_point = j+1;
//				}
				if (!point[j].visited && min > point[last_in_previous_tour-1].distanceTo(point[j])) {
					min = point[last_in_previous_tour-1].distanceTo(point[j]);
					min_point = j+1;
				}
			}
			tour.add(min_point);
			point[min_point-1].visited = true;
			tour_distance.add(min);
			min = Double.MAX_VALUE;
			// 1199794 not right: use min = previous total + current edge cost
			// 1203406 right: use min = current edge cost. and calculate the sum of all numbers in tour_distance
			// 1010383 not right: because of the initial value of min, not big enough initially
			// 990.968052709151
		}
		long end = System.currentTimeMillis();
		long time = (end-start) / 1000;
		System.out.println("The algorithm cost: " + time + " seconds in total.");
		int last_point = tour.size()-1;
		min = 0.0;
		for(int i = 0; i < tour_distance.size(); i++) {
			min += tour_distance.get(i);
		}
		min += point[tour.get(last_point)-1].distanceTo(point[0]);
//		min = tour_distance.get(tour_distance.size()-1) + point[tour.get(last_point)-1].distanceTo(point[0]);
		
		System.out.println(min);
	}
	
	public static Point[] readData() throws IOException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/np_complete/nn_test.txt");
//		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/np_complete/nn.txt");
		BufferedReader br = new BufferedReader(fr);
//		Point[] point = new Point[33708];
		Point[] point = new Point[100];
		String line = "";
		int i = 0;
		while((line = br.readLine()) != null) {						
			point[i] = new Point(Double.parseDouble(line.split(" ")[1]),
					Double.parseDouble(line.split(" ")[2]));
			i++;
		}
		br.close();
		return point;
	}

}
