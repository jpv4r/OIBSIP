import java.util.Scanner;
import java.util.Random;

public class NumberGuessing{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        Random ran=new Random();
        int marks=0;
        int trys=1;
        boolean repeat=true;
        while(repeat){
            int min=1;
            int max=20;
            int random=ran.nextInt(max-min+1)+min;
            int guess;
            int attempts=0;
            int maxattempt=5;

            System.out.println("Round"+trys+":-");
            System.out.println("A Number is choosen between "+min+" and "+max+". Try guessing it!");
            while (true) {
                System.out.print("Enter your guess: ");
                guess=sc.nextInt();
                attempts++;
                if(guess==random){
                    System.out.println("Congratulations! You've guessed the correct number in "+attempts+" attempts.");
                        marks+=(maxattempt-attempts+1);  
                        break;
                }
                else if(guess<random){
                        System.out.println("The number is higher. Try again.");
                }
                else {
                        System.out.println("The number is lower. Try again.");
                }
                if(attempts>=maxattempt){
                    System.out.println("Sorry! You've used all your attempts. The correct number was "+random);
                    break;
                }
            }
                System.out.println("Your score:"+marks);
                System.out.print("Do you want to play another round? (yes/no): ");
                String response = sc.next();
                 if (!response.equalsIgnoreCase("yes")) {
                    repeat=false;
                }
                trys++;
        }
        System.out.println("Thank you for playing! Your final score is: "+marks);
        sc.close();
        
    }
}