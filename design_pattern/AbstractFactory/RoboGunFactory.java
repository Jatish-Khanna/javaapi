

public class RoboGunFactory implements CharacterFactory{

	private CharacterBody characterBody;
	private Weapon weapon;
	
	public RoboGunFactory() {
		characterBody = new RobotBody();
		weapon = new MaverickGun();
	}
	
	@Override
	public void makeCharacter() {
		characterBody.buildBody();
		weapon.buildWeapon();
	}

	public CharacterBody getCharacterBody() {
		return characterBody;
	}

	public Weapon getWeapon() {
		return weapon;
	}
}
