

public class Scooter implements Vehicle {

	@Override
	public void incrementSpeed() {
		System.out.println("Speed incremented by: " + Math.ceil(Math.random() * 13));
	}

	@Override
	public void start() {
		System.out.println("Engine started");
	}

	@Override
	public void stop() {
		System.out.println("Engine stopped");
	}

}
