package HW.a3;

import tester.Tester;
import java.awt.*;

interface IMobile {
  // Returns the total weight of this mobile
  int totalWeight();

  // Returns the height of this mobile
  int totalHeight();
}

class Simple implements IMobile {
  int length;
  int weight;
  Color color;

  Simple(int length, int weight, Color color) {
    this.length = length;
    this.weight = weight;
    this.color = color;
  }

  public int totalWeight() {
    return this.weight;
  }

  public int totalHeight() {
    return this.length;
  }
}

class Complex implements IMobile {
  int length;
  int leftside;
  int rightside;
  IMobile left;
  IMobile right;

  Complex(int length, int leftside, int rightside, IMobile left, IMobile right) {
    this.length = length;
    this.leftside = leftside;
    this.rightside = rightside;
    this.left = left;
    this.right = right;
  }

  public int totalWeight() {
    return this.left.totalWeight() + this.right.totalWeight();
  }

  public int totalHeight() {
    if (this.left.totalHeight() > this.right.totalHeight()) {
      return this.left.totalHeight() + this.length;
    }
    else {
      return this.right.totalHeight() + this.length;
    }
  }
}

class ExamplesMobiles {
  
  ExamplesMobiles() {}

  IMobile simple1 = new Simple(2, 20, Color.BLUE);

  IMobile component1 = new Simple(1, 36, Color.BLUE);
  IMobile component2 = new Simple(1, 12, Color.RED);
  IMobile component3 = new Simple(2, 36, Color.RED);
  IMobile component4 = new Simple(1, 60, Color.GREEN);

  IMobile complex1 = new Complex(2, 5, 3, component3, component4);
  IMobile complex2 = new Complex(2, 8, 1, component2, complex1);
  IMobile complex3 = new Complex(1, 9, 3, component1, complex2);


  boolean testTotalWeight(Tester t) {
    return t.checkExpect(complex3.totalWeight(), 144) &&
           t.checkExpect(simple1.totalWeight(), 20);
  }
}