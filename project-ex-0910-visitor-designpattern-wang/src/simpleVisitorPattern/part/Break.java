package simpleVisitorPattern.part;

import simpleVisitorPattern.visitor.CartPartVisitor;

public class Break implements ICarElement {
   String name;
   String modelNumberBreak;
   String modelYearBreak;

   public Break(String n) {
      this.name = n;
   }

   public void accept(CartPartVisitor visitor) {
      visitor.visit(this);
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getModelNumberBreak() {
      return modelNumberBreak;
   }

   public void setModelNumberBreak(String modelNumberBreak) {
      this.modelNumberBreak = modelNumberBreak;
   }

   public String getModelYearBreak() {
      return modelYearBreak;
   }

   public void setModelYearBreak(String modelYearBreak) {
      this.modelYearBreak = modelYearBreak;
   }
}