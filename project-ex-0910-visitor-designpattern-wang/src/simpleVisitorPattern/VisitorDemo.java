package simpleVisitorPattern;

import java.util.Scanner;

import simpleVisitorPattern.visitor.MyAccessVisitor;
import simpleVisitorPattern.visitor.MyFileReadVisitor;
import simpleVisitorPattern.visitor.MyRenameVisitor;
import simpleVisitorPattern.visitor.MyReverseVisitor;
import simpleVisitorPattern.visitor.MyFileSaveVisitor;

public class VisitorDemo {
   private static Scanner scanner;

   public VisitorDemo() {
      scanner = new Scanner(System.in);
   }

   public static void main(String[] args) {
      VisitorDemo demo = new VisitorDemo();

      System.out.println("=============================================");
      System.out.println("Simple Visitor Pattern Example Program");
      System.out.println("=============================================");
      System.out.println("\tMenu Options:");
      System.out.println("\t1. File Read Operation");
      System.out.println("\t2. Print Name Operation");
      System.out.println("\t3. Rename Operation");
      System.out.println("\t4. Reverse Operation");
      System.out.println("\t5. File Save Operation");
      System.out.println("\t6. Exit the program");

      Car car = new Car();
      car.addChild();

      while (true) {
         System.out.println("=============================================");
         System.out.print("Please select an option from 1-6\n");
         
         switch (demo.getOption()) {
         case 1:
            System.out.println("[DBG] Executing MyFileReadVisitor...");
            MyFileReadVisitor fileReadVisitor = new MyFileReadVisitor();
            car.accept(fileReadVisitor);
            break;
         case 2:
            System.out.println("[DBG] Executing MyAccessVisitor...");
            MyAccessVisitor accessVisitor = new MyAccessVisitor();
            car.accept(accessVisitor);
            break;
         case 3:
            System.out.println("[DBG] Executing MyRenameVisitor...");
            MyRenameVisitor renameVisitor = new MyRenameVisitor();
            car.accept(renameVisitor);
            break;
         case 4:
             System.out.println("[DBG] Executing MyReverseVisitor...");
             MyReverseVisitor reverseVisitor = new MyReverseVisitor();
             car.accept(reverseVisitor);
             break;
         case 5:
             System.out.println("[DBG] Executing MyFileSaveVisitor...");
             MyFileSaveVisitor fileSaveVisitor = new MyFileSaveVisitor();
             car.accept(fileSaveVisitor);
             break;
         default:
            break;
         }
      }
   }

   int getOption() {
      int input = scanner.nextInt();
      if (input < 0 || input > 6) {
         System.out.println("You have entered an invalid selection, please try again\n");
      } else if (input == 6) {
         System.out.println("You have quit the program\n");
         System.exit(1);
      } else {
         System.out.println("You have entered " + input + "\n");
         return input;
      }
      return 0;
   }
}