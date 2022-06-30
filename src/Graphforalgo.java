import java.util.ArrayList;
import java.util.*;

class Graphforalgo {

    ArrayList<String> listFromHashMap123;
    ArrayList<String> cityList123;
    TravelAssistant ta;

    // Graph Constructor
    Graphforalgo(List<Edgeforalgo> edgesforalgo, TravelAssistant ta) {

        this.ta = ta;

        HashMap<String, City> cityMap = ta.getCityInfo();
        listFromHashMap123 = new ArrayList<String>(cityMap.keySet());

        if (cityList123 == null) {
            cityList123 = listFromHashMap123;

        } else {
            for (int cityIndex = 0; cityIndex < listFromHashMap123.size(); cityIndex++) {
                if (!cityList123.contains(listFromHashMap123.get(cityIndex))) {
                    cityList123.add(listFromHashMap123.get(cityIndex));
                }
            }
        }

        ta.setCityListFinal123(cityList123);

        List<List<Nodeforalgo>> adjList123 = ta.getAdjList123();

        // adjacency list memory allocation
        if (cityList123.size() > adjList123.size()) {
            for (int i = adjList123.size(); i < cityList123.size(); i++) {
                adjList123.add(i, new ArrayList<>());
            }
        }

        for (Edgeforalgo e : edgesforalgo) {

            int forEdge = cityList123.indexOf(e.sCity);

            adjList123.get(forEdge).add(new Nodeforalgo(e.sCity, e.dCity, e.cost, e.mode));

            ta.setadjList123(adjList123);
        }
    }

    // print adjacency list for the graph
    public void printGraphforalgo(Graphforalgo graphforalgo) {

        List<List<Nodeforalgo>> adjList123 = ta.getAdjList123();

        int srcVertex = 0;
        int listSize = adjList123.size();

        System.out.println("Sub-graph: ");
        while (srcVertex < listSize) {
            for (Nodeforalgo edge : adjList123.get(srcVertex)) {

                System.out.print("Start city " + edge.sCity + " => " + edge.dCity + " (" + edge.cost + ")" + " ("
                        + edge.mode + ") \t");
            }

            System.out.println();
            srcVertex++;
        }
    }
}