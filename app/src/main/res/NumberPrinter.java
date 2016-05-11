/**
 * Created by Scott on 2/25/2016.
 */
public class NumberPrinter {

    /**
     * that prints the numbers from 1 to 100.
     * But for multiples of three print
     * “Fizz” instead of the number and for
     * the multiples of five print “Buzz”.
     * For numbers which are multiples of
     * both three and five print “FizzBuzz”
     */
    public void printNumb(){

        //loop through numbers 1-100
        for(int i =1; i < 101; i++){

            //if multiple of 3 and 5 print FizzBuzz
            if(i % 3 == 0 && i % 5 ==0){
                System.out.println("FizzBuzz");
            }

            //multiple of 3 print Fizz
            else if(i % 3 == 0){
                System.our.println("Fizz");
                //multiple of 5 print Buzz
            }else if(i % 5 == 0){
                System.out.println("Buzz");

                //not muluiple of 3 or 5 or both print number
            }else{
                System.out.println(i + "");
            }
        }
    }
}
