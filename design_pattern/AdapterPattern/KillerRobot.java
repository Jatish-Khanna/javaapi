
public class KillerRobot implements Robot {

	@Override
	public void jump() {
		System.out.println("Jumped multiple steps");
	}

	@Override
	public void takeCharge() {
		System.out.println("Attacked");
	}

}
