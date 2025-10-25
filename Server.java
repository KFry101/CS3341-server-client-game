import java.util.*;


public class Server {
    private static final int[] valid_numbers = {50, 51, 52, 54, 55, 56, 57, 58, 60, 62, 63, 64, 65, 66, 68, 69, 
                            70, 72, 74, 75, 76, 77, 78, 80, 81, 82, 84, 85, 86, 87, 88, 90,
                            91, 92, 93, 94, 95, 96, 98, 99};

    public static boolean isValidNumber(int input){
        for (int n : valid_numbers) {
            if (n == input) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){
        
    }


}

   
