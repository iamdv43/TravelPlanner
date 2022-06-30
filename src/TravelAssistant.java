import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * This program calculates the shortest path from start city to destination city
 * according to the preferece of the travellers and based on the vaccination
 * status.
 * 
 * @author Dhruvrajsinh Omkarsinh Vansia, B00891415
 */
public class TravelAssistant {

    LinkedHashMap<String, City> cityInfo = new LinkedHashMap<String, City>();

    public LinkedHashMap<String, City> getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(LinkedHashMap<String, City> cityInfo) {
        this.cityInfo = cityInfo;
    }

    List<List<Node>> adjList = new ArrayList<>();

    public List<List<Node>> getAdjList() {
        return adjList;
    }

    public void setadjList(List<List<Node>> adjList) {
        this.adjList = adjList;
    }

    List<List<Nodeforalgo>> adjList123 = new ArrayList<>();

    public List<List<Nodeforalgo>> getAdjList123() {
        return adjList123;
    }

    public void setadjList123(List<List<Nodeforalgo>> adjList123) {
        this.adjList123 = adjList123;
    }

    List<String> cityListFinal = new ArrayList<>();

    public List<String> getCityListFinal() {
        return cityListFinal;
    }

    public void setCityListFinal(List<String> cityListFinal) {
        this.cityListFinal = cityListFinal;
    }

    List<String> cityListFinal123 = new ArrayList<>();

    public List<String> getCityListFinal123() {
        return cityListFinal123;
    }

    public void setCityListFinal123(List<String> cityListFinal123) {
        this.cityListFinal123 = cityListFinal123;
    }

    /**
     * This method adds city in the program.
     * 
     * @param cityName         name of the city
     * @param testRequired     boolean value that used to ensure, whether covid test
     *                         is required to enter in the city
     * @param timeToTest       numbers of days, to get covid result
     * @param nightlyHotelCost hotel cost in per night in the city
     * @return true if city is added successfully otherwise false
     * @throws IllegalArgumentException
     */

    boolean addCity(String cityName, boolean testRequired, int timeToTest, int nightlyHotelCost)
            throws IllegalArgumentException {

        if (cityName.isEmpty() || cityName == null) {
            throw new IllegalArgumentException("City name is null or empty.");
        }

        if (!cityName.matches("^[a-zA-Z]*$")) {
            throw new IllegalArgumentException("Invalid input.");
        }

        if (nightlyHotelCost < 1) {
            throw new IllegalArgumentException("Hotel cost per night is less than 1.");
        }

        for (String key : cityInfo.keySet()) {
            if (key.equalsIgnoreCase(cityName)) {
                return false;
            }
        }

        City addedCity = new City(cityName, testRequired, timeToTest, nightlyHotelCost);

        cityInfo.put(addedCity.cityName, addedCity);

        return true;
    }

    /**
     * This method adds the flight in the program.
     * 
     * @param startCity       name of the start city
     * @param destinationCity name of the destination city
     * @param flightTime      time taken by the flight to reach destination city
     * @param flightCost      cost of the flight
     * @return true if flight is added successfully otherwise false
     * @throws IllegalArgumentException
     */
    boolean addFlight(String startCity, String destinationCity, int flightTime, int flightCost)
            throws IllegalArgumentException {

        if (startCity == null || startCity.isEmpty() || destinationCity == null || destinationCity.isEmpty())
            throw new IllegalArgumentException("Invalid input");
        if (flightCost < 1 || flightTime < 1)
            throw new IllegalArgumentException("Invalid input.");
        if (startCity.equalsIgnoreCase(destinationCity))
            throw new IllegalArgumentException("Invalid input.");

        // check if flight is already added in the list or not
        List<List<Node>> adjList = this.getAdjList();
        if (!adjList.isEmpty()) {
            for (List<Node> i : adjList) {
                for (Node j : i) {
                    if (j.sCity.equalsIgnoreCase(startCity) && j.dCity.equalsIgnoreCase(destinationCity)
                            && j.mode.equals("f")) {
                        return false;
                    }
                }
            }
        }

        boolean checkStartCity = false;

        // check start city is exist or not
        for (String key : cityInfo.keySet()) {
            if (key.equalsIgnoreCase(startCity)) {
                checkStartCity = true;
                startCity = key;
            }
        }

        boolean checkDestinationCity = false;

        // check destination city is exist or not
        for (String key : cityInfo.keySet()) {
            if (key.equalsIgnoreCase(destinationCity)) {
                checkDestinationCity = true;
                destinationCity = key;
            }

        }

        if (checkStartCity && checkDestinationCity) {

            List<Edge> edges = Arrays.asList(new Edge(startCity, destinationCity, flightTime, flightCost, "f"));

            // Graph graph =
            new Graph(edges, this);

            // graph.printGraph(graph);

            return true;
        } else {
            throw new IllegalArgumentException("Invalid input");
        }

    }

    /**
     * Add train
     * 
     * @param startCity
     * @param destinationCity
     * @param trainTime       time taken by the train
     * @param trainCost       cost of train
     * @return true if train is added successfully otherwise false
     * @throws IllegalArgumentException
     */
    boolean addTrain(String startCity, String destinationCity, int trainTime, int trainCost)
            throws IllegalArgumentException {

        if (startCity == null || startCity.isEmpty() || destinationCity == null || destinationCity.isEmpty())
            throw new IllegalArgumentException("Invalid input.");
        if (trainCost < 1 || trainTime < 1)
            throw new IllegalArgumentException("Invalid input.");
        if (startCity.equalsIgnoreCase(destinationCity))
            throw new IllegalArgumentException("Invalid input.");

        // check if train is already added or not
        List<List<Node>> adjList = this.getAdjList();
        if (!adjList.isEmpty()) {
            for (List<Node> i : adjList) {
                for (Node j : i) {
                    if (j.sCity.equalsIgnoreCase(startCity) && j.dCity.equalsIgnoreCase(destinationCity)
                            && j.mode.equals("t")) {
                        return false;
                    }
                }
            }
        }

        boolean checkStartCity = false;

        for (String key : cityInfo.keySet()) {
            if (key.equalsIgnoreCase(startCity)) {
                checkStartCity = true;
                startCity = key;
            }
        }

        boolean checkDestinationCity = false;

        for (String key : cityInfo.keySet()) {
            if (key.equalsIgnoreCase(destinationCity)) {
                checkDestinationCity = true;
                destinationCity = key;
            }
        }

        if (checkStartCity && checkDestinationCity) {

            List<Edge> edges = Arrays.asList(new Edge(startCity, destinationCity, trainTime, trainCost, "t"));

            // Graph graph = new Graph(edges, this);
            new Graph(edges, this);
            // graph.printGraph(graph);

            return true;
        } else {
            throw new IllegalArgumentException("Invalid input.");
        }

    }

    /**
     * This method finds the shortest possible path based on the priority of the
     * traveller.
     * 
     * @param startCity
     * @param destinationCity
     * @param isVaccinated         true if traveller is vaccinated otherwise false
     * @param costImportance
     * @param travelTimeImportance
     * @param travelHopImportance
     * @return list of string that show shortest path with mode
     * @throws IllegalArgumentException
     */
    List<String> planTrip(String startCity, String destinationCity, boolean isVaccinated, int costImportance,
            int travelTimeImportance, int travelHopImportance) throws IllegalArgumentException {

        List<String> trip = new ArrayList<>();
        if (startCity == null || startCity.isEmpty() || destinationCity == null || destinationCity.isEmpty())
            throw new IllegalArgumentException("Invalid input.");
        if (costImportance < 0 || travelTimeImportance < 0 || travelHopImportance < 0)
            throw new IllegalArgumentException("Invalid input.");

        boolean checkStartCity = false;
        for (String key : cityInfo.keySet()) {
            if (key.equalsIgnoreCase(startCity)) {
                checkStartCity = true;
                startCity = key;
            }
        }

        boolean checkDestinationCity = false;

        for (String key : cityInfo.keySet()) {
            if (key.equalsIgnoreCase(destinationCity)) {
                checkDestinationCity = true;
                destinationCity = key;
            }
        }

        if (startCity.equalsIgnoreCase(destinationCity)) {
            trip.add("start " + startCity);
            return trip;
        }

        if (checkStartCity && checkDestinationCity) {

            List<List<Node>> AdjList = this.getAdjList();

            dijkstraAlgo dAlgo = new dijkstraAlgo();

            trip = dAlgo.algo(AdjList, startCity, destinationCity, isVaccinated, costImportance, travelTimeImportance,
                    travelHopImportance, this);

            return trip;
        } else {
            throw new IllegalArgumentException("Input input.");
        }

    }

}
