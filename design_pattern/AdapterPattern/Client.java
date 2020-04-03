
public class Client {
	public static void main(String[] args) {
		Robot robot = new KillerRobot();
		Attacker attacker = new RobotAdapter(robot);
		
		attacker.moveForward();
		attacker.attack();
	}
}
