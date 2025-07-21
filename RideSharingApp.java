import java.util.*;

class RideRequest {
    String passengerName;
    String pickupLocation;
    String dropLocation;

    public RideRequest(String passengerName, String pickupLocation, String dropLocation) {
        this.passengerName = passengerName;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
    }

    @Override
    public String toString() {
        return "Passenger: " + passengerName + ", From: " + pickupLocation + ", To: " + dropLocation;
    }
}

class Driver {
    String name;
    boolean isAvailable;

    public Driver(String name) {
        this.name = name;
        this.isAvailable = true;
    }

    public boolean respondToRide(RideRequest request, Scanner scanner) {
        System.out.println("\nDriver " + name + ", you have a new ride request:");
        System.out.println(request);
        System.out.print("Do you want to accept the ride? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
            isAvailable = false;
            System.out.println(name + " accepted the ride!");
            return true;
        } else {
            System.out.println(name + " rejected the ride.");
            return false;
        }
    }

    public void startRide(RideRequest request) {
        System.out.println("\n Ride started by " + name + " with " + request.passengerName);
        System.out.println("From: " + request.pickupLocation + " -> To: " + request.dropLocation);

        Thread rideThread = new Thread(() -> {
            try {
                int rideTimeInSeconds = 5; // simulate 5 seconds of ride = 5 km
                for (int i = 1; i <= rideTimeInSeconds; i++) {
                    System.out.println("... driving " + i + " km");
                    Thread.sleep(1000); // 1 second per km
                }
                double totalFare = 5.0 + (15 * 2.0); 

                System.out.println("\nRide completed.");
                System.out.println("Total fare: rupee" + totalFare);

            } catch (InterruptedException e) {
                System.out.println("Ride was interrupted.");
            } finally {
                isAvailable = true;
            }
        });

        rideThread.start();

        try {
            rideThread.join(); // Wait for ride to complete before exiting
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class RideSharingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver("Ravi"));
        drivers.add(new Driver("Priya"));
        drivers.add(new Driver("Aman"));

        System.out.println("----- Welcome to RideShare App -----");
        System.out.print("Enter passenger name: ");
        String passengerName = scanner.nextLine();
        System.out.print("Enter pickup location: ");
        String pickup = scanner.nextLine();
        System.out.print("Enter drop location: ");
        String drop = scanner.nextLine();

        RideRequest request = new RideRequest(passengerName, pickup, drop);

        boolean rideAccepted = false;
        for (Driver driver : drivers) {
            if (driver.isAvailable) {
                rideAccepted = driver.respondToRide(request, scanner);
                if (rideAccepted) {
                    driver.startRide(request);
                    break;
                }
            }
        }

        if (!rideAccepted) {
            System.out.println(" Sorry, no drivers accepted your ride at the moment.");
        }

        scanner.close();
    }
}
