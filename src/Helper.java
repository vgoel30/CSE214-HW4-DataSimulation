import java.util.*;

//Helper class to try stuff out for the simulation class
public class Helper {

	public static void main(String[] args) {
		
		double probability = Math.random();
		
		System.out.println(probability);
		
		for(int i = 1; i <= 3; i++){
			if(probability > Math.random())
				System.out.println(true);
			else
				System.out.println(false);
		}

	}

}
