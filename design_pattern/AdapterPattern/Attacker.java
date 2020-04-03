
// For Interface segregation principle 
// Movable interface should declare moveForward method not Attacker
public interface Attacker {

	public abstract void moveForward();
	public abstract void attack();
}
