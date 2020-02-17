

public class GenericsAssignable {
 
    public static void main(String[] args) {
        new GenericsAssignable().create();
    }
 
    public void create() {
 
        List<Object> objectsBad = new ArrayList<Pet>(); //1. COMPILE ERROR
        List<Pet> petsBad = new ArrayList<Dog>();    //2. COMPILE ERROR
 
        //===== "?" and "? extends" - read only =====
        List<?> petsOk = new ArrayList<Pet>(); //read only
        List<? extends Pet> petsOk2 = new ArrayList<Dog>(); //read only
        List<? extends Pet> petsOk3 = new ArrayList<Pet>(); //read only
        List<? extends Dog> petsOk4 =  new ArrayList<Dog>(); //read only
        List<? extends Dog> petsOk5 =  new ArrayList<SpanielDog>(); //read only
 
        List<? extends Dog> petsBad2 =  new ArrayList<Pet>(); //3. COMPILE ERROR - read only
 
        //====  "? super" - can add objects to collection  ====
        List<? super Dog> petsOk6 = new ArrayList<Dog>();
        List<? super Dog> petsOk7 = new ArrayList<Pet>();
        List<? super Dog> petsOk8 = new ArrayList<Object>();
 
        //can add Dog or any subclass of Dog
        petsOk6.add(new Dog());
        petsOk6.add(new SpanielDog());//polymorphic
 
        petsOk6.add(new Pet()); //4. COMPILE ERROR
 
        List<? super Dog> petsBad3 = new ArrayList<SpanielDog>(); //5. COMPILE ERROR
 
    }
}
