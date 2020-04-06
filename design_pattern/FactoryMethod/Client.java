
public class Client {
	public static void main(String[] args) {
		VehicleFactory factory = new SimpleVehicleFactory();
		Vehicle vehicle = factory.makeVehicle(VehicleType.SCOOTER);
		processVehicle(vehicle);
		vehicle = factory.makeVehicle(VehicleType.CAR);
		processVehicle(vehicle);
	}

	private static void processVehicle(Vehicle vehicle) {
		vehicle.start();
		vehicle.incrementSpeed();
		vehicle.stop();
	}
}
