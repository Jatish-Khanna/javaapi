
public class SimpleVehicleFactory implements VehicleFactory {

	private Vehicle vehicle;

	public SimpleVehicleFactory() {
	}

	@Override
	public Vehicle makeVehicle(VehicleType vehicleType) {
		if (vehicleType == VehicleType.SCOOTER) {
			vehicle = new Scooter();
		} else if (vehicleType == VehicleType.CAR) {
			vehicle = new Car();
		}
		return vehicle;
	}

	@Override
	public Vehicle getVehicle() {
		return vehicle;
	}

}
