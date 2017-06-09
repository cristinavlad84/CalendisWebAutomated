package ro.evozon;

public class Outsie {
	public static String fox;
	public static String dog;
	
	
	
	public static void modify(String color, String blu){
		System.out.println("inside");
		System.out.println(fox);
		System.out.println(dog);
		fox= color;
		dog=blu;
	}
	public static void main(String[]args){
		System.out.println("cicii");
		Outsie.dog="Pearl";
		Outsie.fox="Jammie";
		Outsie.modify("KKK", "JJJJ");
		System.out.println(fox);
		System.out.println(dog);
		
	}

}
