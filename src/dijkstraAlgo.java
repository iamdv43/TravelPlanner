import java.util.*;

class dijkstraAlgo {

    /**
     * This method finds the shortest path between start city and destination city.
     * First, if there are two paths available between the same city then select the
     * path which has minimum cost and travel time. Using this, it creates new
     * adjacency list. After that, the adjacency matrix is built from the adjacency
     * list to ease the process of the finding the shorted path. Then, through
     * Dijkstra algorithm it finds the shortest path. Finally, this method adds mode
     * of travel in the list of the path.
     * 
     * 
     * @param adjList              adjacency list
     * @param sCity                Name of start city
     * @param destiCity            name of destination city
     * @param isVaccinated         vaccination status
     * @param costImportance       co
     * @param travelTimeImportance
     * @param travelHopImportance
     * @param ta                   object of the travelassistant class
     * @return list of path
     */
    List<String> algo(List<List<Node>> adjList, String sCity, String destiCity, boolean isVaccinated,
            int costImportance, int travelTimeImportance, int travelHopImportance, TravelAssistant ta) {

        if (costImportance == 0 && travelTimeImportance == 0) {
            travelHopImportance = 999999 * travelHopImportance;
            costImportance = 1;
            travelTimeImportance = 1;
        }

        // total number of cities
        int nOfCities = adjList.size();

        List<List<Nodeforalgo>> adjList123 = new ArrayList<>();

        ta.setadjList123(adjList123);

        // find a route between two cities
        for (int edgeIndex = 0; edgeIndex < adjList.size(); edgeIndex++) {
            ArrayList<String> cityCount = new ArrayList<>();
            ArrayList<Integer> costOfTrans = new ArrayList<>();
            ArrayList<String> modeOftrans = new ArrayList<>();
            // Graphforalgo graphforalgo = null;
            String scityAlgo = "";
            for (Node edge : adjList.get(edgeIndex)) {
                if (!cityCount.stream().anyMatch(edge.dCity::equalsIgnoreCase)) {
                    cityCount.add(edge.dCity);
                    costOfTrans.add(costImportance * edge.cost + travelTimeImportance * edge.time);
                    modeOftrans.add(edge.mode);
                } else {
                    int preCost = costOfTrans.get(cityCount.indexOf(edge.dCity));
                    int newCost = costImportance * edge.cost + travelTimeImportance * edge.time;

                    if (newCost < preCost) {
                        costOfTrans.set(cityCount.indexOf(edge.dCity), newCost);
                        modeOftrans.set(cityCount.indexOf(edge.dCity), edge.mode);
                    }
                }
                scityAlgo = edge.sCity;
            }

            for (int i = 0; i < cityCount.size(); i++) {

                List<Edgeforalgo> edgeforalgo = Arrays
                        .asList(new Edgeforalgo(scityAlgo, cityCount.get(i), costOfTrans.get(i), modeOftrans.get(i)));

                // graphforalgo =
                new Graphforalgo(edgeforalgo, ta);

            }
            // if (graphforalgo != null) {
            // graphforalgo.printGraphforalgo(graphforalgo);
            // }

        }

        int[] cost = new int[nOfCities];

        boolean[] checkingCondition = new boolean[nOfCities];

        for (int i = 0; i < nOfCities; i++) {
            cost[i] = Integer.MAX_VALUE;
            checkingCondition[i] = false;
        }

        List<String> cityListFinal123 = ta.getCityListFinal123();

        int indexOfCity = cityListFinal123.indexOf(sCity);

        cost[indexOfCity] = 0;

        adjList123 = ta.getAdjList123();

        int[][] finalGraph = new int[adjList123.size()][adjList123.size()];

        for (int i = 0; i < adjList123.size(); i++) {
            if (adjList123.get(i).size() != 0) {

                List<Nodeforalgo> node = adjList123.get(i);
                ArrayList<Integer> index123 = new ArrayList<>();
                ArrayList<Integer> weight12 = new ArrayList<>();
                for (Nodeforalgo node1 : node) {
                    index123.add(cityListFinal123.indexOf(node1.dCity));

                    weight12.add(node1.cost);
                }

                // System.out.println(index123 + " " + weight12);
                for (int j1 = 0; j1 < adjList123.size(); j1++) {
                    if (index123.contains(j1)) {
                        finalGraph[i][j1] = weight12.get(index123.indexOf(j1));

                    }
                }

            }
        }

        // System.out.println("--------------------\n" +
        // Arrays.deepToString(finalGraph));

        ArrayList<Integer> pathCheck = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();

        HashMap<String, City> cityInfo = ta.getCityInfo();
        HashMap<Integer, Boolean> vaccineInfo = new HashMap<>();
        for (int ii = 0; ii < nOfCities; ii++) {
            vaccineInfo.put(ii, false);
        }

        if (cityInfo.get(sCity).timeToTest > 0) {
            isVaccinated = true;
        }

        // find the shortest path and also checks for vaccination status
        for (int iterate = 0; iterate < nOfCities; iterate++) {

            int x = minCost(cost, checkingCondition, nOfCities);

            checkingCondition[x] = true;

            for (int y = 0; y < nOfCities; y++) {
                if (!checkingCondition[y] && finalGraph[x][y] != 0 && cost[x] != Integer.MAX_VALUE
                        && cost[x] + finalGraph[x][y] + travelHopImportance * 1 < cost[y]) {
                    if (isVaccinated || vaccineInfo.get(x) || !cityInfo.get(cityListFinal123.get(y)).testRequired) {

                        if (cost[y] < Integer.MAX_VALUE) {
                            cost[y] = cost[x] + finalGraph[x][y] + travelHopImportance * 1;
                        } else {
                            cost[y] = cost[x] + finalGraph[x][y];
                        }

                    } else if (cityInfo.get(cityListFinal123.get(y)).timeToTest > 0) {
                        if (cost[y] < Integer.MAX_VALUE) {
                            cost[y] = cost[x] + finalGraph[x][y] + travelHopImportance * 1
                                    + cityInfo.get(cityListFinal123.get(y)).timeToTest
                                            * cityInfo.get(cityListFinal123.get(y)).nightlyHotelCost;
                        } else {
                            cost[y] = cost[x] + finalGraph[x][y] + cityInfo.get(cityListFinal123.get(y)).timeToTest
                                    * cityInfo.get(cityListFinal123.get(y)).nightlyHotelCost;
                        }
                        vaccineInfo.put(y, true);
                    }
                    boolean flag1 = true;
                    if (path.size() > 0) {
                        for (int i0 = 1; i0 < path.size(); i0 = i0 + 3) {
                            if (path.get(i0) == y && path.get(i0 + 1) > cost[y]) {
                                path.remove(i0 - 1);
                                path.remove(i0 - 1);
                                path.remove(i0 - 1);
                            } else if (path.get(i0) == y && path.get(i0 + 1) < cost[y]) {
                                flag1 = false;
                            }
                        }
                    }
                    if (flag1 && cost[y] < Integer.MAX_VALUE) {
                        path.add(x);
                        path.add(y);
                        path.add(cost[y]);
                    }
                    // System.out.println(path.toString() + " =====");

                }

            }

            boolean flag2 = true;
            for (int i = 0; i < cost.length; i++) {
                if (cost[i] < Integer.MAX_VALUE && checkingCondition[i] == false) {
                    flag2 = false;
                }
            }

            if (flag2) {
                iterate = nOfCities;
            }
        }

        for (int o1 = 0; o1 < path.size() - 1; o1 = o1 + 3) {
            pathCheck.add(path.get(o1));
            pathCheck.add(path.get(o1 + 1));
        }

        // System.out.println("path: " + path.toString());
        // System.out.println("pathCheck: " + pathCheck.toString());

        // printSolution(cost, nOfCities);

        int indexOfdcity = cityListFinal123.indexOf(destiCity);

        ArrayList<Integer> pathfinalfinal = new ArrayList<>();

        for (int r1 = 1; r1 < pathCheck.size(); r1 = r1 + 2) {
            if (pathCheck.get(r1) == indexOfdcity) {
                if (pathCheck.get(r1 - 1) == cityListFinal123.indexOf(sCity)) {
                    pathfinalfinal.add(pathCheck.get(r1 - 1));
                    // System.out.println(pathfinalfinal + " ****");
                }
                while (pathCheck.get(r1 - 1) != cityListFinal123.indexOf(sCity)) {

                    if (pathfinalfinal.isEmpty())
                        pathfinalfinal.add(pathCheck.get(r1 - 1));
                    else if (pathCheck.get(r1) == pathfinalfinal.get(pathfinalfinal.size() - 1))
                        pathfinalfinal.add(pathCheck.get(r1 - 1));

                    r1 = r1 - 2;

                    // System.out.println(pathfinalfinal + " ****///");
                }

                Collections.reverse(pathfinalfinal);
                if (pathfinalfinal.size() > 0) {
                    if (pathfinalfinal.get(0) != cityListFinal123.indexOf(sCity))
                        pathfinalfinal.add(0, cityListFinal123.indexOf(sCity));
                    pathfinalfinal.add(indexOfdcity);
                }

                break;
            }
        }
        // if (pathfinalfinal.size() > 0) {
        // System.out.println(pathfinalfinal.toString() + " lets go!");
        // } else {
        // System.out.println("Bad luck! :) ");
        // }
        List<String> finalPath = new ArrayList<>();
        if (pathfinalfinal.size() > 0) {
            for (int jj = 0; jj < pathfinalfinal.size() - 1; jj++) {
                List<Nodeforalgo> findMode = adjList123.get(pathfinalfinal.get(jj));
                for (Nodeforalgo n1 : findMode) {
                    String a = "";
                    if (n1.sCity.equalsIgnoreCase(cityListFinal123.get(pathfinalfinal.get(jj)))
                            && n1.dCity.equalsIgnoreCase(cityListFinal123.get(pathfinalfinal.get(jj + 1)))) {

                        if (n1.mode == "f")
                            a = "fly";
                        else
                            a = "train";

                        a = a + " " + n1.dCity;
                        finalPath.add(a);
                        // System.out.println(finalPath + " /*/*");
                    }
                }
            }
        }
        if (finalPath.size() > 0) {
            finalPath.add(0, "start " + sCity);
        }
        // System.out.println(finalPath.toString() + " finally");

        if (finalPath.isEmpty()) {
            return null;
        }

        return finalPath;
    }

    /**
     * This method finds the index of the next possible vertex that has minimum cost
     * using two array and total number of cities.
     * 
     * @param cost              array of cost
     * @param checkingCondition marking array
     * @param nOfCities         total cities
     * @return index of the min cost
     */
    int minCost(int[] cost, boolean[] checkingCondition, int nOfCities) {

        int minCost = Integer.MAX_VALUE;
        int indexOfminCost = -1;

        for (int v = 0; v < nOfCities; v++)
            if (checkingCondition[v] == false && cost[v] <= minCost) {
                minCost = cost[v];
                indexOfminCost = v;
            }

        return indexOfminCost;
    }
}