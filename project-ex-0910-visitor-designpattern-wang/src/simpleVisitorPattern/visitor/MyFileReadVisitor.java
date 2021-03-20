package simpleVisitorPattern.visitor;

import java.io.File;
import java.util.List;

import simpleVisitorPattern.part.Body;
import simpleVisitorPattern.part.Engine;
import simpleVisitorPattern.part.Wheel;
import simpleVisitorPattern.part.Break;
import util.UtilFile;

public class MyFileReadVisitor extends CartPartVisitor {

   private String filePath = System.getProperty("user.dir");
   private List<String> contents;

   public MyFileReadVisitor() {	     
	  System.out.println("user.dir: " + filePath);
      contents = UtilFile.readFile(filePath + File.separator + "inputdata.txt");
   }

   @Override
   public boolean visit(Wheel part) {
      final int LINE_NUM_WHEEL = 0;
      String[] tokens = contents.get(LINE_NUM_WHEEL).split(",");
      part.setName(tokens[0].trim());
      part.setModelNumberWheel(tokens[1].trim());
      part.setModelYearWheel(tokens[2].trim());
      return super.visit(part);
   }

   @Override
   public boolean visit(Engine part) {
      final int LINE_NUM_ENGINE = 1;
      String[] tokens = contents.get(LINE_NUM_ENGINE).split(",");
      part.setName(tokens[0].trim());
      part.setModelNumberEngine(tokens[1].trim());
      part.setModelYearEngine(tokens[2].trim());
      return super.visit(part);
   }

   @Override
   public boolean visit(Body part) {
      final int LINE_NUM_BODY = 2;
      String[] tokens = contents.get(LINE_NUM_BODY).split(",");
      part.setName(tokens[0].trim());
      part.setModelNumberBody(tokens[1].trim());
      part.setModelYearBody(tokens[2].trim());
      return super.visit(part);
   }
   
   @Override
   public boolean visit(Break part) {
      final int LINE_NUM_BREAK = 3;
      String[] tokens = contents.get(LINE_NUM_BREAK).split(",");
      part.setName(tokens[0].trim());
      part.setModelNumberBreak(tokens[1].trim());
      part.setModelYearBreak(tokens[2].trim());
      return super.visit(part);
   }
}
