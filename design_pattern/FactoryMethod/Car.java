
public class Car implements Vehicle {

	@Override
	public void incrementSpeed() {
		System.out.println("Speed incremented by: " + Math.ceil(Math.random() * 30));
	}

	@Override
	public void start() {
		System.out.println("Engine turned on");
	}

	@Override
	public void stop() {
		System.out.println("Engine turned off");
	}

}
