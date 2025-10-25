public class HCF {

    // Method to compute HCF (GCD) using Euclidean algorithm
    public static int findHCF(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a; // HCF is stored in 'a'
    }

    public static void main(String[] args) {
        int num1 = 36, num2 = 60;
        int hcf = findHCF(num1, num2);
        System.out.println("HCF of " + num1 + " and " + num2 + " is " + hcf);
    }
}

