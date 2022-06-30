import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String addCityCommand = "city";
        String addFlightCommand = "flight";
        String addtTrainCommand = "train";
        String planTripCommand = "trip";

        String userCommand = "";

        System.out.println("Commands available");
        System.out.println(" " + addCityCommand
                + " <city name> <test required to enter in city> <time (in days) to get covid test results> <nightly hotel stay cost>");
        System.out.println(" " + addFlightCommand + " <start city> <destination city> <flight time> <fight cost>");
        System.out.println(" " + addtTrainCommand + " <start city> <destination city> <train time> <train cost>");
        System.out.println(" " + planTripCommand
                + " <start city> <destination city> <isVaccinated> <costImportance> <travelTimeImportance> <travelHopImportance>");
        System.out.println(" " + "quit");

        TravelAssistant assistant = new TravelAssistant();
        Scanner userInput = new Scanner(System.in);

        do {

            userCommand = userInput.next();

            if (userCommand.equalsIgnoreCase(addCityCommand)) {

                // try {
                String cityName = userInput.next();
                boolean testRequired = userInput.nextBoolean();
                int timeToTest = userInput.nextInt();
                int nightlyHotelCost = userInput.nextInt();

                boolean check = assistant.addCity(cityName, testRequired, timeToTest, nightlyHotelCost);

                if (check) {
                    System.out.println("City is added successfully.");
                } else {
                    System.out.println("Fail to add.");
                }
                // } catch (Exception e) {
                // System.out.println("Input mismatched.");
                // continue;
                // }

            } else if (userCommand.equalsIgnoreCase(addFlightCommand)) {

                try {
                    String startCity = userInput.next();
                    String destinationCity = userInput.next();
                    int fTime = userInput.nextInt();
                    int fCost = userInput.nextInt();

                    boolean check1 = assistant.addFlight(startCity, destinationCity, fTime, fCost);

                    if (check1) {
                        System.out.println("Flight is added successfully.");
                    } else {
                        System.out.println("Fail to add.");
                    }
                } catch (Exception e) {
                    System.out.println("Input mismatched.");
                    continue;
                }
            } else if (userCommand.equalsIgnoreCase(addtTrainCommand)) {

                try {
                    String startCity = userInput.next();
                    String destinationCity = userInput.next();
                    int tTime = userInput.nextInt();
                    int tCost = userInput.nextInt();

                    boolean check2 = assistant.addTrain(startCity, destinationCity, tTime, tCost);

                    if (check2) {
                        System.out.println("Train is added successfully.");
                    } else {
                        System.out.println("Fail to add.");
                    }
                } catch (Exception e) {
                    System.out.println("Input mismatched.");
                    continue;
                }
            } else if (userCommand.equalsIgnoreCase(planTripCommand)) {
                try {
                    String startCity = userInput.next();
                    String destinationCity = userInput.next();
                    boolean isVaccinated = userInput.nextBoolean();
                    int costImportance = userInput.nextInt();
                    int travelTimeImportance = userInput.nextInt();
                    int travelHopImportance = userInput.nextInt();

                    List<String> route = assistant.planTrip(startCity, destinationCity, isVaccinated, costImportance,
                            travelTimeImportance, travelHopImportance);

                    System.out.println(route);

                } catch (Exception e) {
                    System.out.println("Input mismatch");
                    continue;
                }
            }

        } while (!userCommand.equalsIgnoreCase("quit"));

        userInput.close();

    }
}
