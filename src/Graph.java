import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Graph {

    Map<String, City> cityMap;
    TravelAssistant ta;

    ArrayList<String> listFromHashMap;
    ArrayList<String> cityList;

    // Graph Constructor
    Graph(List<Edge> edges, TravelAssistant ta) {

        this.cityMap = ta.getCityInfo();
        this.ta = ta;
        listFromHashMap = new ArrayList<String>(cityMap.keySet());
        if (cityList == null) {
            cityList = listFromHashMap;

        } else {
            for (int cityIndex = 0; cityIndex < listFromHashMap.size(); cityIndex++) {
                if (!cityList.contains(listFromHashMap.get(cityIndex))) {
                    cityList.add(listFromHashMap.get(cityIndex));
                }
            }
        }

        ta.setCityListFinal(cityList);

        List<List<Node>> adjList = ta.getAdjList();

        // adjacency list memory allocation
        if (cityList.size() > adjList.size()) {
            for (int i = adjList.size(); i < cityList.size(); i++) {
                adjList.add(i, new ArrayList<>());
            }
        }

        for (Edge e : edges) {

            int forEdge = cityList.indexOf(e.sCity);

            adjList.get(forEdge).add(new Node(e.sCity, e.dCity, e.time, e.cost, e.mode));

            ta.setadjList(adjList);
        }
    }

    // print adjacency list for the graph
    public void printGraph(Graph graph) {

        List<List<Node>> adjList = ta.getAdjList();

        int v = 0;
        int listSize = adjList.size();

        System.out.println("Graph: ");
        while (v < listSize) {
            for (Node edge : adjList.get(v)) {
                System.out.print("Start city " + edge.sCity + " => " + edge.dCity + " (" + edge.time + ")" + " ("
                        + edge.cost + ")" + " (" + edge.mode + ") \t");
            }
            System.out.println();
            v++;
        }
    }
}