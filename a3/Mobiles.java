import javalib.worldimages.*;
import tester.Tester;

import java.awt.*;

interface IMobile {

  int SCALE = 10;

  Color COLOR = Color.BLACK;

  // Returns the total weight of this mobile
  int totalWeight();

  // Returns the height of this mobile
  int totalHeight();

  // Determines if this mobile is balanced
  boolean isBalanced();

  // Build a mobile given another balanced mobile that is also balanced
  IMobile buildMobile(IMobile that, int string, int strut);

  // Determines the total width of this mobile
  int curWidth();

  // helper for the left side for curWidth
  int curWidthHelpLeft();

  // helper for the right side for curWidth
  int curWidthHelpRight();

  // draws this mobile
  WorldImage drawMobile();
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
    return this.length + this.weight / 10;
  }

  public boolean isBalanced() {
    return true;
  }

  public IMobile buildMobile(IMobile that, int string, int strut) {
    return new Complex(
        string,
        ((int) (strut * (((double)that.totalWeight())/(that.totalWeight()+this.totalWeight())))),
        ((int) (strut * ((double)this.totalWeight())/(that.totalWeight()+this.totalWeight()))),
        this,
        that);
  }

  public int curWidth() {
    return this.curWidthHelpLeft() + this.curWidthHelpRight();
  }

  public int curWidthHelpLeft() {
    if (this.totalWeight() % 10 == 0)
      return this.totalWeight() / 10 / 2;
    else
      return ((int) (this.totalWeight() / 10.0) + 1) / 2;
  }

  public int curWidthHelpRight() {
    if (this.totalWeight() % 10 == 0)
      return this.totalWeight() / 10 / 2;
    else
      return ((int) (this.totalWeight() / 10.0) + 1) / 2;
  }

  public WorldImage drawMobile() {

    WorldImage drawing = new AboveImage(
        new LineImage(new Posn(0, this.length * SCALE), this.COLOR).movePinhole(0, 1),
        new RectangleImage(this.curWidth() * SCALE, this.curWidth() * SCALE, "solid", this.color)
    );

    return drawing.movePinhole(0, - drawing.getHeight() / 2.0);
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
    return this.length + Math.max(this.left.totalHeight(), this.right.totalHeight());
  }

  public boolean isBalanced() {
    return this.leftside * this.left.totalWeight() == this.rightside * this.right.totalWeight()
        && this.left.isBalanced() && this.right.isBalanced();
  }

  public IMobile buildMobile(IMobile that, int string, int strut) {
    return new Complex(
        string,
        ((int) (strut * (((double)that.totalWeight())/(that.totalWeight()+this.totalWeight())))),
        ((int) (strut * (((double)this.totalWeight())/(that.totalWeight()+this.totalWeight())))),
        this,
        that);
  }

  public int curWidth() {
    return this.curWidthHelpLeft() + this.curWidthHelpRight();
  }

  // gets the length of the left side of this mobile
  public int curWidthHelpLeft() {
    return Math.max(this.right.curWidthHelpLeft() - this.rightside, this.left.curWidthHelpLeft() + this.leftside);
  }

  public int curWidthHelpRight() {
    return Math.max(this.left.curWidthHelpRight() - this.leftside, this.right.curWidthHelpRight() + this.rightside);
  }

  public WorldImage drawMobile() {
    WorldImage leftStrut = new LineImage(new Posn(this.leftside * this.SCALE, 0), this.COLOR);
    WorldImage string = new LineImage(new Posn(0, this.length * this.SCALE), this.COLOR);

    WorldImage rightStrut = new LineImage(new Posn(this.rightside * this.SCALE, 0), this.COLOR);


    WorldImage leftStrutString = new AboveAlignImage(
        AlignModeX.RIGHT,
        string,
        leftStrut
    );

    double offsetCalcA = (this.leftside + this.rightside) * this.SCALE / 2.0;
    double pinOffX = this.leftside * this.SCALE - offsetCalcA;
    double pinOffY = this.length * this.SCALE / 2.0;

    WorldImage wires =
        new BesideAlignImage(
            AlignModeY.BOTTOM,
            leftStrutString,
            rightStrut
        ).movePinhole((int) pinOffX, pinOffY - 10);

    WorldImage wiresAdjustLeft = wires.movePinhole(-this.leftside * this.SCALE, this.length * this.SCALE / 2.0);

    // draw the left and right
    WorldImage left = this.left.drawMobile();
    WorldImage right = this.right.drawMobile();

    WorldImage wiresLeft = new OverlayOffsetAlign(
        AlignModeX.PINHOLE,
        AlignModeY.PINHOLE,
        wiresAdjustLeft,
        0,
        0,
        left
    );

    WorldImage wiresLeftToAddRight = new VisiblePinholeImage(wiresLeft.movePinhole(
        0,0)
    );

    WorldImage finalImage = new OverlayOffsetAlign(
        AlignModeX.PINHOLE,
        AlignModeY.PINHOLE,
        wiresLeftToAddRight,
        0,
        0,
        right
    );
    return new VisiblePinholeImage(finalImage.movePinhole( - this.rightside * this.SCALE, - this.length * this.SCALE));
  }
}

class ExamplesMobiles {
  
  ExamplesMobiles() {}

  IMobile exampleSimple = new Simple(2, 20, Color.BLUE);

  IMobile component1 = new Simple(1, 36, Color.BLUE);
  IMobile component2 = new Simple(1, 12, Color.RED);
  IMobile component3 = new Simple(2, 36, Color.RED);
  IMobile component4 = new Simple(1, 60, Color.GREEN);

  IMobile complex1 = new Complex(2, 5, 3, component3, component4);
  IMobile complex2 = new Complex(2, 8, 1, component2, complex1);
  IMobile exampleComplex = new Complex(1, 9, 3, component1, complex2);

  IMobile complex4 = new Complex(1, 5, 3, component1, complex2);

  IMobile suspiciousComplex1 = new Complex(2, 15, 3, component3, component4);
  IMobile suspiciousComplex2 = new Complex(2, 8, 1, component2, suspiciousComplex1);
  IMobile example3 = new Complex(1, 9, 3, component1, suspiciousComplex2);

  boolean testTotalWeight(Tester t) {
    return t.checkExpect(exampleComplex.totalWeight(), 144)
        && t.checkExpect(exampleSimple.totalWeight(), 20);
  }

  boolean testTotalHeight(Tester t) {
    return t.checkExpect(exampleComplex.totalHeight(), 12)
        && t.checkExpect(exampleSimple.totalHeight(), 4);
  }

  boolean testIsBalance(Tester t) {
    return t.checkExpect(exampleComplex.isBalanced(), true)
        && t.checkExpect(exampleSimple.isBalanced(), true)
        && t.checkExpect(complex4.isBalanced(), false);
  }

  boolean testBuildMobile(Tester t) {
    return t.checkExpect(component1.buildMobile(complex2, 1, 12), exampleComplex);
  }

  boolean testCurWidth(Tester t) {
    return t.checkExpect(exampleComplex.curWidth(), 21)
        && t.checkExpect(exampleSimple.curWidth(), 2)
        && t.checkExpect(example3.curWidth(), 23);
  }

  boolean testDrawMobile(Tester t) {
    return t.checkExpect(exampleSimple.drawMobile(), new OverlayOffsetImage(
      new LineImage(new Posn(0, 40), Color.BLACK),
      10,
      0,
      new RectangleImage(21, 12, "solid", Color.RED)
    ).movePinhole(0, -12));
  }
}