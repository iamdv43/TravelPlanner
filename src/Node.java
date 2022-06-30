class Node {
    String sCity, dCity, mode;
    int cost, time;

    Node(String sCity, String dCity, int time, int cost, String mode) {
        this.sCity = sCity;
        this.dCity = dCity;
        this.cost = cost;
        this.time = time;
        this.mode = mode;
    }
}