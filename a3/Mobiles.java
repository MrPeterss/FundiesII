import javalib.worldimages.*;
import tester.Tester;

import java.awt.*;

interface IMobile {

  // the scale of the mobile (drawing purposes)
  int SCALE = 15;

  // the line color for the mobile (drawing purposes)
  Color LINE_COLOR = Color.BLACK;

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

  /*
   TEMPLATE
   FIELDS:
    ... this.length ... - int
    ... this.weight ... - int
    ... this.color ... - Color
   METHODS:
    ... this.totalWeight() ... - int
    ... this.totalHeight() ... - int
    ... this.isBalanced() ... - boolean
    ... this.buildMobile(IMobile that, int string, int strut) ... - IMobile
    ... this.curWidth() ... - int
    ... this.curWidthHelpLeft() ... - int
    ... this.curWidthHelpRight() ... - int
    ... this.drawMobile() ... - WorldImage
   METHODS FOR FIELDS:
    ... NONE ...
   */

  // Returns the total weight of this mobile
  public int totalWeight() {
    return this.weight;
  }

  // Returns the height of this mobile
  public int totalHeight() {
    return this.length + this.weight / 10;
  }

  // Determines if this mobile is balanced
  public boolean isBalanced() {
    return true;
  }

  // Build a mobile given another balanced mobile that is also balanced
  public IMobile buildMobile(IMobile that, int string, int strut) {
    return new Complex(
        string,
        ((int) (strut * ((double) that.totalWeight()) /
            (that.totalWeight() + this.totalWeight()))),
        ((int) (strut * ((double) this.totalWeight()) /
            (that.totalWeight() + this.totalWeight()))),
        this,
        that);
  }

  // Determines the total width of this mobile
  public int curWidth() {
    return this.curWidthHelpLeft() + this.curWidthHelpRight();
  }

  // helper for the left side for curWidth
  public int curWidthHelpLeft() {
    if (this.totalWeight() % 10 == 0) {
      return this.totalWeight() / 10 / 2;
    } else {
      return ((int) (this.totalWeight() / 10.0) + 1) / 2;
    }
  }

  // helper for the right side for curWidth
  public int curWidthHelpRight() {
    if (this.totalWeight() % 10 == 0) {
      return this.totalWeight() / 10 / 2;
    } else {
      return ((int) (this.totalWeight() / 10.0) + 1) / 2;
    }
  }

  // draws this mobile
  public WorldImage drawMobile() {
    WorldImage drawing = new AboveImage(
        new LineImage(new Posn(0, this.length * IMobile.SCALE), IMobile.LINE_COLOR),
        new RectangleImage(
            this.curWidth() * IMobile.SCALE,
            this.curWidth() * IMobile.SCALE, "solid", this.color
        )
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

  /*
   TEMPLATE
   FIELDS:
    ... this.length ... - int
    ... this.leftside ... - int
    ... this.rightside ... - int
    ... this.left ... - IMobile
    ... this.right ... - IMobile
   METHODS:
    ... this.totalWeight() ... - int
    ... this.totalHeight() ... - int
    ... this.isBalanced() ... - boolean
    ... this.buildMobile(IMobile that, int string, int strut) ... - IMobile
    ... this.curWidth() ... - int
    ... this.curWidthHelpLeft() ... - int
    ... this.curWidthHelpRight() ... - int
    ... this.drawMobile() ... - WorldImage
   METHODS FOR FIELDS:
    ... this.left.totalWeight() ... - int
    ... this.left.totalHeight() ... - int
    ... this.left.isBalanced() ... - boolean
    ... this.left.buildMobile(IMobile that, int string, int strut) ... - IMobile
    ... this.left.curWidth() ... - int
    ... this.left.curWidthHelpLeft() ... - int
    ... this.left.curWidthHelpRight() ... - int
    ... this.left.drawMobile() ... - WorldImage
    ... this.right.totalWeight() ... - int
    ... this.right.totalHeight() ... - int
    ... this.right.isBalanced() ... - boolean
    ... this.right.buildMobile(IMobile that, int string, int strut) ... - IMobile
    ... this.right.curWidth() ... - int
    ... this.right.curWidthHelpLeft() ... - int
    ... this.right.curWidthHelpRight() ... - int
    ... this.right.drawMobile() ... - WorldImage
   */

  // Returns the total weight of this mobile
  public int totalWeight() {
    return this.left.totalWeight() + this.right.totalWeight();
  }

  // Returns the height of this mobile
  public int totalHeight() {
    return this.length + Math.max(this.left.totalHeight(), this.right.totalHeight());
  }

  // Determines if this mobile is balanced
  public boolean isBalanced() {
    return this.leftside * this.left.totalWeight() == this.rightside * this.right.totalWeight()
        && this.left.isBalanced() && this.right.isBalanced();
  }

  // Build a mobile given another balanced mobile that is also balanced
  public IMobile buildMobile(IMobile that, int string, int strut) {
    return new Complex(
        string,
        ((int) (strut * ((double)that.totalWeight()) /
            (that.totalWeight() + this.totalWeight()))),
        ((int) (strut * ((double)this.totalWeight()) /
            (that.totalWeight() + this.totalWeight()))),
        this,
        that);
  }

  // Determines the total width of this mobile
  public int curWidth() {
    return this.curWidthHelpLeft() + this.curWidthHelpRight();
  }

  // helper for the left side for curWidth
  public int curWidthHelpLeft() {
    return Math.max(
      this.right.curWidthHelpLeft() - this.rightside,
      this.left.curWidthHelpLeft() + this.leftside
    );
  }

  // helper for the right side for curWidth
  public int curWidthHelpRight() {
    return Math.max(
      this.left.curWidthHelpRight() - this.leftside,
      this.right.curWidthHelpRight() + this.rightside
    );
  }

  // draws this mobile
  public WorldImage drawMobile() {
    // represents the string
    WorldImage string =
        new LineImage(new Posn(0, IMobile.SCALE * this.length), IMobile.LINE_COLOR);
    WorldImage adjustedString = string.movePinhole(0, this.length / 2.0 * IMobile.SCALE);

    // represents the left strut
    WorldImage leftStrut =
        new LineImage(new Posn(IMobile.SCALE * this.leftside, 0), IMobile.LINE_COLOR);
    WorldImage adjustedLeftStrut = leftStrut.movePinhole(this.leftside / 2.0 * IMobile.SCALE, 0);

    // represents both the string and the left strut
    // don't need to adjust because pinhole should be in the center
    WorldImage stringAndLeftStrut = new OverlayOffsetAlign(
        AlignModeX.PINHOLE,
        AlignModeY.PINHOLE,
        adjustedString,
        0,
        0,
        adjustedLeftStrut
    );

    // represents the right strut
    WorldImage rightStrut =
        new LineImage(new Posn(IMobile.SCALE * this.rightside, 0), IMobile.LINE_COLOR);
    WorldImage adjustedRightStrut = rightStrut.movePinhole(
        -this.rightside / 2.0 * IMobile.SCALE, 0
    );

    // represents the strings and the struts together
    // pinhole should be in the center
    WorldImage allStringStruts = new OverlayOffsetAlign(
        AlignModeX.PINHOLE,
        AlignModeY.PINHOLE,
        stringAndLeftStrut,
        0,
        0,
        adjustedRightStrut
    );

    // adjust the pinhole above to attatch the left
    WorldImage toAttatchLeftAllStringStruts = allStringStruts.movePinhole(
        - this.leftside * IMobile.SCALE,
        0
    );

    // pinholes should already be in the correct positions (top center)
    // left child mobile
    WorldImage leftMobile = this.left.drawMobile();
    // right child mobile
    WorldImage rightMobile = this.right.drawMobile();

    // attatch left to the left
    WorldImage leftAttatched = new OverlayOffsetAlign(
        AlignModeX.PINHOLE,
        AlignModeY.PINHOLE,
        toAttatchLeftAllStringStruts,
        0,
        0,
        leftMobile
    );

    // adjust that pinhole all the way to the right
    WorldImage adjustedLeftAttatched = leftAttatched.movePinhole(
        (this.leftside + this.rightside) * IMobile.SCALE, 0
    );

    // attatch right to the right
    WorldImage allAttatched = new OverlayOffsetAlign(
        AlignModeX.PINHOLE,
        AlignModeY.PINHOLE,
        adjustedLeftAttatched,
        0,
        0,
        rightMobile
    );

    // adjust the pinhole to the center top
    WorldImage finalMobile = allAttatched.movePinhole(
        - this.rightside * IMobile.SCALE,
        - this.length * IMobile.SCALE
    );
    return finalMobile;
  }
}

class ExamplesMobiles {
  
  ExamplesMobiles() {}

  IMobile exampleSimple = new Simple(2, 20, Color.BLUE);

  IMobile component1 = new Simple(1, 36, Color.BLUE);
  IMobile component2 = new Simple(1, 12, Color.RED);
  IMobile component3 = new Simple(2, 36, Color.RED);
  IMobile component4 = new Simple(1, 60, Color.GREEN);

  IMobile complex1 = new Complex(2, 5, 3, this.component3, this.component4);
  IMobile complex2 = new Complex(2, 8, 1, this.component2, this.complex1);
  IMobile exampleComplex = new Complex(1, 9, 3, this.component1, this.complex2);

  IMobile complex4 = new Complex(1, 5, 3, this.component1, this.complex2);

  IMobile suspiciousComplex1 = new Complex(4, 15, 3, this.component3, this.complex1);
  IMobile suspiciousComplex2 = new Complex(2, 8, 1, this.component2, this.suspiciousComplex1);
  IMobile example3 = new Complex(1, 9, 3, this.component1, this.suspiciousComplex2);

  // tests for the totalWeight method
  boolean testTotalWeight(Tester t) {
    return t.checkExpect(this.exampleComplex.totalWeight(), 144)
        && t.checkExpect(this.exampleSimple.totalWeight(), 20);
  }

  // tests for the totalHeight method
  boolean testTotalHeight(Tester t) {
    return t.checkExpect(this.exampleComplex.totalHeight(), 12)
        && t.checkExpect(this.exampleSimple.totalHeight(), 4);
  }

  // tests for the isBalanced method
  boolean testIsBalance(Tester t) {
    return t.checkExpect(this.exampleComplex.isBalanced(), true)
        && t.checkExpect(this.exampleSimple.isBalanced(), true)
        && t.checkExpect(this.complex4.isBalanced(), false);
  }

  // tests for the buildMobile method
  boolean testBuildMobile(Tester t) {
    return t.checkExpect(this.component1.buildMobile(this.complex2, 1, 12), this.exampleComplex);
  }

  // tests for the curWidth method
  boolean testCurWidth(Tester t) {
    return t.checkExpect(this.exampleComplex.curWidth(), 21)
        && t.checkExpect(this.exampleSimple.curWidth(), 2)
        && t.checkExpect(this.example3.curWidth(), 26);
  }

  // tests for the curWidthHelpLeft method
  boolean testCurWidthHelpLeft(Tester t) {
    return t.checkExpect(this.exampleComplex.curWidthHelpLeft(), 11)
        && t.checkExpect(this.exampleSimple.curWidthHelpLeft(), 1)
        && t.checkExpect(this.example3.curWidthHelpLeft(), 13);
  }

  // tests for the curWidthHelpRight method
  boolean testCurWidthHelpRight(Tester t) {
    return t.checkExpect(this.exampleComplex.curWidthHelpRight(), 10)
        && t.checkExpect(this.exampleSimple.curWidthHelpRight(), 1)
        && t.checkExpect(this.example3.curWidthHelpRight(), 13);
  }

  // tests for the curWidthHelp method
  boolean testDrawMobile(Tester t) {
    return t.checkExpect(this.exampleSimple.drawMobile(), new AboveImage(
      new LineImage(new Posn(0, 30), Color.BLACK),
      new RectangleImage(30, 30, "solid", Color.BLUE)
    ).movePinhole(0, -30))
      && t.checkExpect(this.complex1.drawMobile(),
        new OverlayOffsetAlign(
          AlignModeX.PINHOLE,
          AlignModeY.PINHOLE,
          new OverlayOffsetAlign(
            AlignModeX.PINHOLE,
            AlignModeY.PINHOLE,
            new OverlayOffsetAlign(
              AlignModeX.PINHOLE,
              AlignModeY.PINHOLE,
                new OverlayOffsetAlign(
                  AlignModeX.PINHOLE,
                  AlignModeY.PINHOLE,
                  new LineImage(
                    new Posn(0, IMobile.SCALE * 2),
                    IMobile.LINE_COLOR
                  ).movePinhole(-30, 0),
                  0,
                  0,
                  new LineImage(
                    new Posn(IMobile.SCALE * 5, 0),
                    IMobile.LINE_COLOR
                  ).movePinhole(0, 30)
                ),
              0,
              0,
              new LineImage(
                new Posn(IMobile.SCALE * 3, 0),
                IMobile.LINE_COLOR
              ).movePinhole(0, 15)
            ).movePinhole(
              0,
              0
            ),
            0,
            0,
            new AboveImage(
                new LineImage(new Posn(0, 2 * IMobile.SCALE), IMobile.LINE_COLOR),
                new RectangleImage(2 * IMobile.SCALE, 2 * IMobile.SCALE, "solid", Color.RED)
            ).movePinhole(15, 0)
          ).movePinhole(0, 45),
          0,
          0,
          new AboveImage(
              new LineImage(new Posn(0, 1 * IMobile.SCALE), IMobile.LINE_COLOR),
              new RectangleImage(3 * IMobile.SCALE, 3 * IMobile.SCALE, "solid", Color.GREEN)
          ).movePinhole(0, 15)
        ).movePinhole(0, 15)
    );
  }
}