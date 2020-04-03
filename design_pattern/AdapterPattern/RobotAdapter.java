

public class RobotAdapter implements Attacker {

	Robot robot;

	public RobotAdapter(Robot robot) {
		this.robot = robot;
	}

	@Override
	public void moveForward() {
		robot.jump();
	}

	@Override
	public void attack() {
		robot.takeCharge();
	}

}
