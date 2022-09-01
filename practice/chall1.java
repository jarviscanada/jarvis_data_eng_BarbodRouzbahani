import java.util.Scanner;

/**
 * ticket: https://www.notion.so/jarviscanada/Check-if-a-number-is-even-or-odd-4cbdd9acd9504c04b9b4ef0213f8084d
 */
public class chall1 {
    public static void main(String args[]) {
        System.out.print("\nPlease enter a number: ");
        Scanner input = new Scanner(System.in);
        int inputInt = input.nextInt();

        if (inputInt % 2 == 0) {
            System.out.println("Even");
        } else {
            System.out.println("Odd");
        }
    }
}