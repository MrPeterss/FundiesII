import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.*;
import tester.Tester;

import java.awt.*;
import java.util.Random;

// interface to store constants
interface IConstants {
  // The size of the scene
  int SCENE_SIZE = 500;
  // represents no button found in the position over button method
  Button NO_BUTTON = new Button(Color.WHITE, 404, 404);
}

// Represents a game of Simon Says
class SimonWorld extends World implements IConstants {
  // The Space in between the buttons
  ILoButton buttons;
  // The sequence of buttons to be shown
  ILoButton sequence;
  // The sequence of buttons that still need to be shown (used in show mode)
  ILoButton tempSequence;
  // Buttons that the player has clicked on
  ILoButton playerSequence;
  // the button that is darkened (used in show mode)
  WorldImage darkenedButton;
  // Is the game in show mode?
  boolean isInShowMode;
  // The random number generator
  Random random;
  // should the game show the darkened button this tick?
  boolean shouldShow;
  // text to display during the game
  String text;

  // constructor
  SimonWorld(ILoButton buttons, ILoButton sequence, ILoButton tempSequence,
             ILoButton playerSequence, WorldImage darkenedButton, boolean isInShowMode,
             Random random, boolean shouldShow, String text) {
    this.buttons = buttons;
    this.sequence = sequence;
    this.tempSequence = tempSequence;
    this.playerSequence = playerSequence;
    this.darkenedButton = darkenedButton;
    this.isInShowMode = isInShowMode;
    this.random = random;
    this.shouldShow = shouldShow;
    this.text = text;
  }

  // convenience constructor to start game
  SimonWorld(ILoButton buttons, Random random, ILoButton sequence) {
    this(buttons, sequence, sequence, new MtLoButton(), sequence.drawDarkened(), true,
         random, true, "Wait");
  }

  // convenience constructor to update showing during show mode
  SimonWorld(SimonWorld that, boolean shouldShow, WorldImage darkenedButton,
             ILoButton nextSequence) {
    this(that.buttons, that.sequence, nextSequence, that.playerSequence, darkenedButton, true,
         that.random, shouldShow, that.text);
  }

  // convenience constructor for getting out of show mode
  SimonWorld(SimonWorld that, boolean isInShowMode, boolean shouldShow,
             WorldImage darkenedButton) {
    this(that.buttons, that.sequence, that.tempSequence, that.playerSequence, darkenedButton,
        isInShowMode, that.random, shouldShow, "Repeat");
  }

  // convenience constructor for when the player has passed the previous round
  SimonWorld(SimonWorld that, ILoButton newSequence, WorldImage drawDarkened) {
    this(that.buttons, newSequence, newSequence, new MtLoButton(), drawDarkened, true,
        that.random, true, "Wait");
  }

  // convenience constructor for when the player updates their sequence
  SimonWorld(SimonWorld that, ILoButton playerSequence,
             WorldImage drawDarkened, boolean updatePlayer) {
    this(that.buttons, that.sequence, that.sequence, playerSequence, drawDarkened,
        that.isInShowMode, that.random, that.shouldShow, that.text);
  }

  // convenience constructor for when the player lifts their mouse
  SimonWorld(SimonWorld that, WorldImage drawDarkened) {
    this(that.buttons, that.sequence, that.tempSequence, that.playerSequence,
        drawDarkened, that.isInShowMode, that.random, that.shouldShow, that.text);
  }

  /* TEMPLATE
  Fields:
  ... this.buttons ... - ILoButton
  ... this.sequence ... - ILoButton
  ... this.tempSequence ... - ILoButton
  ... this.playerSequence ... - ILoButton
  ... this.darkenedButton ... - WorldImage
  ... this.isInShowMode ... - boolean
  ... this.random ... - Random
  ... this.shouldShow ... - boolean
  ... this.text ... - String
  Methods:
  ... this.onKeyEvent(String) ... - World
  ... this.onMouseClicked(Posn) ... - World
  ... this.onMouseReleased(Posn) ... - World
  ... this.makeScene() ... - WorldScene
  ... this.onTick() ... - World
  Methods for Fields:
  ... this.buttons.drawButtons()... - WorldImage
  ... this.buttons.drawDarkened()... - WorldImage
  ... this.buttons.getNextSequence()... - ILoButton
  ... this.buttons.isDone()... - boolean
  ... this.buttons.isOverButton(Posn)... - Button
  ... this.buttons.startsWith(ILoButton)... - boolean
  ... this.buttons.chop(ILoButton)... - ILoButton
  ... this.buttons.chopHelp(Button, ILoButton)... - ILoButton
  ... this.buttons.isSameAs(ILoButton)... - boolean
  ... this.buttons.isSameAsHelper(Button, ILoButton)... - boolean
  ... this.buttons.addInner(Button)... - ILoButton
  ... this.buttons.addToSequence(Random)... - ILoButton

  ... this.sequence.drawButtons()... - WorldImage
  ... this.sequence.drawDarkened()... - WorldImage
  ... this.sequence.getNextSequence()... - ILoButton
  ... this.sequence.isDone()... - boolean
  ... this.sequence.isOverButton(Posn)... - Button
  ... this.sequence.startsWith(ILoButton)... - boolean
  ... this.sequence.chop(ILoButton)... - ILoButton
  ... this.sequence.chopHelp(Button, ILoButton)... - ILoButton
  ... this.sequence.isSameAs(ILoButton)... - boolean
  ... this.sequence.isSameAsHelper(Button, ILoButton)... - boolean
  ... this.sequence.addInner(Button)... - ILoButton
  ... this.sequence.addToSequence(Random)... - ILoButton

  ... this.tempSequence.drawButtons()... - WorldImage
  ... this.tempSequence.drawDarkened()... - WorldImage
  ... this.tempSequence.getNextSequence()... - ILoButton
  ... this.tempSequence.isDone()... - boolean
  ... this.tempSequence.isOverButton(Posn)... - Button
  ... this.tempSequence.startsWith(ILoButton)... - boolean
  ... this.tempSequence.chop(ILoButton)... - ILoButton
  ... this.tempSequence.chopHelp(Button, ILoButton)... - ILoButton
  ... this.tempSequence.isSameAs(ILoButton)... - boolean
  ... this.tempSequence.isSameAsHelper(Button, ILoButton)... - boolean
  ... this.tempSequence.addInner(Button)... - ILoButton
  ... this.tempSequence.addToSequence(Random)... - ILoButton

  ... this.playerSequence.drawButtons()... - WorldImage
  ... this.playerSequence.drawDarkened()... - WorldImage
  ... this.playerSequence.getNextSequence()... - ILoButton
  ... this.playerSequence.isDone()... - boolean
  ... this.playerSequence.isOverButton(Posn)... - Button
  ... this.playerSequence.startsWith(ILoButton)... - boolean
  ... this.playerSequence.chop(ILoButton)... - ILoButton
  ... this.playerSequence.chopHelp(Button, ILoButton)... - ILoButton
  ... this.playerSequence.isSameAs(ILoButton)... - boolean
  ... this.playerSequence.isSameAsHelper(Button, ILoButton)... - boolean
  ... this.playerSequence.addInner(Button)... - ILoButton
  ... this.playerSequence.addToSequence(Random)... - ILoButton
   */

  // Draw the current state of the world
  public WorldScene makeScene() {
    WorldScene background = new WorldScene(SCENE_SIZE, SCENE_SIZE);
    background = background.placeImageXY(new TextImage("Simon Says", 20, Color.BLACK),
        SCENE_SIZE / 2, 20);
    background = background.placeImageXY(new TextImage(this.text, 20, Color.RED),
        SCENE_SIZE / 2, 50);
    background = background.placeImageXY(this.buttons.drawButtons(),
        SCENE_SIZE / 2, SCENE_SIZE / 2);
    background = background.placeImageXY(this.darkenedButton,
        SCENE_SIZE / 2, SCENE_SIZE / 2);
    return background;
  }

  // handles ticking of the clock and updating the world if needed
  public World onTick() {
    // if we are in show mode
    if (this.isInShowMode) {
      // if this tick we should show the next button in the sequence
      if (this.shouldShow) {
        // update should show to false
        // update darkened button to be the no button
        // update temp sequence to be the next sequence
        return new SimonWorld(this, false, new EmptyImage(), this.tempSequence.getNextSequence());
      } else {
        // we are not currently showing, we should if it's not empty
        if (this.tempSequence.isDone()) {
          // update show mode to false
          // update should show to false
          // update darkened button to be the no button
          return new SimonWorld(this, false, false, new EmptyImage());
        } else {
          // update should show to true
          // update darkened button to be the next button in the sequence
          // keep the temp sequence the same
          return new SimonWorld(this, true, this.tempSequence.drawDarkened(), this.tempSequence);
        }
      }
    }
    return this;
  }

  // Returns the final scene with the given message displayed
  public WorldScene lastScene(String msg) {
    WorldScene background = new WorldScene(SCENE_SIZE, SCENE_SIZE);
    background = background.placeImageXY(new TextImage(msg, 20, Color.BLACK),
        SCENE_SIZE / 2, SCENE_SIZE / 2);
    return background;
  }

  // handles mouse down events
  public SimonWorld onMousePressed(Posn pos) {
    // make sure we are not in show mode
    if (!this.isInShowMode) {
      // get the place where the user pressed
      Button pressed = this.buttons.isOverButton(pos);
      // if the user pressed a button
      if (!pressed.isSameButtonAs(NO_BUTTON)) {
        // add the button to the player sequence
        ILoButton newPlayerSequence = this.playerSequence.addInner(pressed);
        // if the sequence starts with the player sequence
        if (this.sequence.startsWith(newPlayerSequence)) {
          // if the player sequence is the same as the sequence
          if (newPlayerSequence.isSameAs(this.sequence)) {
            // go to the next round
            // update the sequence
            // update the temp sequence to the same as the sequence -
            // update the player sequence to mt -
            // update the text -
            // update the show mode -
            // update should show -
            // update the darkened button
            ILoButton newSequence = this.sequence.addToSequence(this.random);
            return new SimonWorld(this, newSequence, newSequence.drawDarkened());
          } else {
            // update the player sequence
            // update the darkened button
            return new SimonWorld(this, newPlayerSequence, pressed.drawDark(), true);
          }
        } else {
          // this is where we lose
          this.endOfWorld("You Lose!");
        }
      }
    }
    return this;
  }

  // handles mouse up events
  public SimonWorld onMouseReleased(Posn pos) {
    // is the game showing us the sequence?
    if (!this.isInShowMode) {
      return new SimonWorld(this, new EmptyImage());
    }
    return this;
  }
} 

// Represents a list of buttons
interface ILoButton extends IConstants {
  // Draw the buttons in the list
  WorldImage drawButtons();

  // Draws the first button (if it is there) darkened
  WorldImage drawDarkened();

  // Gets the next sequence of buttons
  ILoButton getNextSequence();

  // Is the sequence done?
  boolean isDone();

  // Is the position given over a button?
  Button isOverButton(Posn pos);

  // Does this sequence start with the given sequence?
  boolean startsWith(ILoButton sequence);

  // Helper for startsWith
  ILoButton chop(ILoButton sequence);

  // helper for chop
  ILoButton chopHelp(Button button, ILoButton lob);

  // Is this sequence the same as the given sequence?
  boolean isSameAs(ILoButton sequence);

  // Helper for isSameAs
  boolean isSameAsHelper(Button button, ILoButton sequence);

  // add the value to the innermost part of the list
  ILoButton addInner(Button b);

  // add a random button to the sequence
  ILoButton addToSequence(Random random);
} 

// Represents an empty list of buttons
class MtLoButton implements ILoButton {

  // empty constructor
  MtLoButton() {}

  /* TEMPLATE
   * Fields:
    * ... NONE ...
    *
   * Methods:
    * ... this.drawButtons() ... -- WorldImage
    * ... this.drawDarkened() ... -- WorldImage
    * ... this.getNextSequence() ... -- ILoButton
    * ... this.isDone() ... -- boolean
    * ... this.isOverButton(Posn pos) ... -- Button
    * ... this.startsWith(ILoButton sequence) ... -- boolean
    * ... this.chop(ILoButton sequence) ... -- ILoButton
    * ... this.chopHelp(Button button, ILoButton lob) ... -- ILoButton
    * ... this.isSameAs(ILoButton sequence) ... -- boolean
    * ... this.isSameAsHelper(Button button, ILoButton sequence) ... -- boolean
    * ... this.addInner(Button b) ... -- ILoButton
    * ... this.addToSequence(Random random) ... -- ILoButton
    *
   * Methods for Fields:
    * ... NONE ...
  */

  // Draw the buttons in the empty list
  public WorldImage drawButtons() {
    return new EmptyImage();
  }

  // Draws the first button (if it is there) darkened;
  // in this case, there is no button, so return an empty image
  public WorldImage drawDarkened() {
    return new EmptyImage();
  }

  // Gets the next sequence of buttons; in this case, there is no next sequence,
  // so return an empty list
  public ILoButton getNextSequence() {
    return new MtLoButton();
  }

  // Is the sequence done? In this case, the sequence is done
  public boolean isDone() {
    return true;
  }

  // Is the position given over a button? In this case,
  // there are no buttons, so return NO_BUTTON
  public Button isOverButton(Posn pos) {
    return NO_BUTTON;
  }

  // Does this sequence start with the given sequence?
  public boolean startsWith(ILoButton sequence) {
    return sequence.isSameAs(this);
  }

  // chop the list to the length of the given list
  public ILoButton chop(ILoButton sequence) {
    return new MtLoButton();
  }

  // helper for chop
  public ILoButton chopHelp(Button button, ILoButton lob) {
    return new MtLoButton();
  }

  // Is this sequence the same as the given sequence?
  public boolean isSameAs(ILoButton sequence) {
    return sequence.isSameAsHelper(NO_BUTTON, this);
  }

  // Helper for isSameAs
  public boolean isSameAsHelper(Button button, ILoButton sequence) {
    return button.isSameButtonAs(NO_BUTTON);
  }

  // add the value to the innermost part of the list
  public ILoButton addInner(Button b) {
    return new ConsLoButton(b, new MtLoButton());
  }

  // add a random button to the sequence
  public ILoButton addToSequence(Random random) {
    int randInt = random.nextInt(4);
    if (randInt == 0) {
      return new ConsLoButton(new Button(Color.RED, 60, 60), new MtLoButton());
    }
    else if (randInt == 1) {
      return new ConsLoButton(new Button(Color.GREEN, 60, -60), new MtLoButton());
    }
    else if (randInt == 2) {
      return new ConsLoButton(new Button(Color.BLUE, -60, 60), new MtLoButton());
    }
    else {
      return new ConsLoButton(new Button(Color.YELLOW, -60, -60), new MtLoButton());
    }
  }
}

// Represents a non-empty list of buttons
class ConsLoButton implements ILoButton {
  Button first;
  ILoButton rest;

  // constructor
  ConsLoButton(Button first, ILoButton rest) {
    this.first = first;
    this.rest = rest;
  }

  /* TEMPLATE
   * Fields:
    * ... this.first ... - Button
    * ... this.rest ... - ILoButton
    *
   * Methods:
    * ... this.drawButtons() ... -- WorldImage
    * ... this.drawDarkened() ... -- WorldImage
    * ... this.getNextSequence() ... -- ILoButton
    * ... this.isDone() ... -- boolean
    * ... this.isOverButton(Posn) ... -- Button
    * ... this.startsWith(ILoButton) ... -- boolean
    * ... this.chop(ILoButton) ... -- ILoButton
    * ... this.chopHelp(Button, ILoButton) ... -- ILoButton
    * ... this.isSameAs(ILoButton) ... -- boolean
    * ... this.isSameAsHelper(Button, ILoButton) ... -- boolean
    * ... this.addInner(Button) ... -- ILoButton
    * ... this.addToSequence(Random random) ... -- ILoButton
    *
   * Methods for fields:
    * ... this.first.drawLit() ... - WorldImage
    * ... this.first.drawDark() ... - WorldImage
    * ... this.first.drawButton(Color) ... - WorldImage
    * ... this.first.isOverButton(Posn) ... - boolean
    * ... this.first.isSameButtonAs(Button) ... - boolean
    *
    * ... this.rest.drawButtons() ... - WorldImage
    * ... this.rest.drawDarkened() ... - WorldImage
    * ... this.rest.getNextSequence() ... - ILoButton
    * ... this.rest.isDone() ... - boolean
    * ... this.rest.isOverButton(Posn) ... - Button
    * ... this.rest.startsWith(ILoButton) ... - boolean
    * ... this.rest.chop(ILoButton) ... - ILoButton
    * ... this.rest.chopHelp(Button, ILoButton) ... - ILoButton
    * ... this.rest.isSameAs(ILoButton) ... - boolean
    * ... this.rest.isSameAsHelper(Button, ILoButton) ... - boolean
    * ... this.rest.addInner(Button) ... - ILoButton
    * ... this.rest.addToSequence(Random) ... - ILoButton
   */

  // Draw the buttons in the list
  public WorldImage drawButtons() {
    return new OverlayOffsetAlign(
        AlignModeX.PINHOLE,
        AlignModeY.PINHOLE,
        this.first.drawLit(),
        0,
        0,
        this.rest.drawButtons());
  }

  // Draws the first button darkened
  public WorldImage drawDarkened() {
    return this.first.drawDark();
  }

  // Gets the next sequence of buttons
  public ILoButton getNextSequence() {
    return this.rest;
  }

  // Is the sequence done?
  public boolean isDone() {
    return false;
  }

  // Is the position given over a button?
  public Button isOverButton(Posn pos) {
    if (this.first.isOverButton(pos)) {
      return this.first;
    }
    else {
      return this.rest.isOverButton(pos);
    }
  }

  // Does this sequence start with the given sequence?
  public boolean startsWith(ILoButton sequence) {
    return this.chop(sequence).isSameAs(sequence);
  }

  // chop off the buttons after the given sequence length
  public ILoButton chop(ILoButton sequence) {
    return sequence.chopHelp(this.first, this.rest);
  }

  // helper for chop
  public ILoButton chopHelp(Button button, ILoButton lob) {
    return new ConsLoButton(button,lob.chop(this.rest));
  }

  // Is this sequence the same as the given sequence?
  public boolean isSameAs(ILoButton sequence) {
    return sequence.isSameAsHelper(this.first, this.rest);
  }

  // Helper for isSameAs
  public boolean isSameAsHelper(Button button, ILoButton sequence) {
    if (button.isSameButtonAs(NO_BUTTON)) {
      return false;
    } else {
      return this.first.isSameButtonAs(button) && this.rest.isSameAs(sequence);
    }
  }

  // add the value to the innermost part of the list
  public ILoButton addInner(Button b) {
    return new ConsLoButton(this.first, this.rest.addInner(b));
  }

  // add a random button to the sequence
  public ILoButton addToSequence(Random random) {
    int randInt = random.nextInt(4);
    if (randInt == 0) {
      return this.addInner(new Button(Color.RED, 60, 60));
    }
    else if (randInt == 1) {
      return this.addInner(new Button(Color.GREEN, 60, -60));
    }
    else if (randInt == 2) {
      return this.addInner(new Button(Color.BLUE, -60, 60));
    }
    else {
      return this.addInner(new Button(Color.YELLOW, -60, -60));
    }
  }
}

// Represents one of the four buttons you can click
class Button {
  Color color;
  int x;
  int y;

  // Constructor
  Button(Color color, int x, int y) {
    this.color = color;
    this.x = x;
    this.y = y;
  }

  /* TEMPLATE
   * Fields:
    * ... this.color ... - Color
    * ... this.x ... - int
    * ... this.y ... - int
   * Methods:
    * ... this.drawDark() ... - WorldImage
    * ... this.drawLit() ... - WorldImage
    * ... this.drawButton(Color) ... - WorldImage
    * ... this.isOverButton(Posn) ... - boolean
    * ... this.isSameButtonAs(Button) ... - boolean
   * Methods for fields:
    * ... NONE ...
   */

  // Draw this button dark
  WorldImage drawDark() {
    return this.drawButton(this.color.darker().darker());
  }

  // Draw this button lit
  WorldImage drawLit() {
    return this.drawButton(this.color.brighter().brighter());
  }

  // Draw this button with the given color
  WorldImage drawButton(Color color) {
    return new RectangleImage(100, 100, "solid", color).movePinhole(this.x, this.y);
  }

  // Is the position given over this button?
  boolean isOverButton(Posn pos) {
    return -(pos.x - SimonWorld.SCENE_SIZE / 2) > this.x - 50
        && -(pos.x - SimonWorld.SCENE_SIZE / 2) < this.x + 50
        && -(pos.y - SimonWorld.SCENE_SIZE / 2) > this.y - 50
        && -(pos.y - SimonWorld.SCENE_SIZE / 2) < this.y + 50;
  }

  // Is this button the same as the given button?
  boolean isSameButtonAs(Button button) {
    return this.color.equals(button.color);
  }
}

// Examples
class ExamplesSimon implements IConstants {
  //put all of your examples and tests here

  // Examples for Buttons
  Button noButton = new Button(Color.WHITE, 404, 404);
  Button redButton = new Button(Color.RED, 100, 100);
  Button greenButton = new Button(Color.GREEN, 400, 100);
  Button blueButton = new Button(Color.BLUE, 100, 400);
  Button yellowButton = new Button(Color.YELLOW, 400, 400);
  Button yellowButton1 = new Button(Color.YELLOW, 400, 400);
  Button yellowButton2 = new Button(Color.YELLOW, -60, -60);

  // Examples for ILoButton
  ILoButton mtLoButton = new MtLoButton();
  ILoButton consLoButton = new ConsLoButton(redButton,
      new ConsLoButton(greenButton,
          new ConsLoButton(blueButton,
              new ConsLoButton(yellowButton,
                  new MtLoButton()))));

  ILoButton consLoButtonMinus1 = new ConsLoButton(greenButton,
      new ConsLoButton(blueButton,
          new ConsLoButton(yellowButton,
              new MtLoButton())));

  ILoButton partialLoButton = new ConsLoButton(redButton,
      new ConsLoButton(greenButton,
          new MtLoButton()));

  ILoButton partialLoButton2 = new ConsLoButton(redButton,
      new ConsLoButton(greenButton,
          new ConsLoButton(blueButton,
              new MtLoButton())));

  Button gameButtonRed = new Button(Color.RED, 60, 60);
  Button gameButtonGreen = new Button(Color.GREEN, 60, -60);
  Button gameButtonBlue = new Button(Color.BLUE, -60, 60);
  Button gameButtonYellow = new Button(Color.YELLOW, -60, -60);

  ILoButton buttonsForGame = new ConsLoButton(gameButtonRed,
      new ConsLoButton(gameButtonGreen,
          new ConsLoButton(gameButtonBlue,
              new ConsLoButton(gameButtonYellow,
                  new MtLoButton()))));

  ILoButton weirdOrderButtons = new ConsLoButton(gameButtonBlue,
      new ConsLoButton(gameButtonYellow,
          new MtLoButton()));



  // Tests for Button methods:

  // Tests for drawDark
  boolean testDrawDark(Tester t) {
    return t.checkExpect(redButton.drawDark(), new RectangleImage(100, 100, "solid",
        Color.RED.darker().darker()).movePinhole(100, 100))
        && t.checkExpect(greenButton.drawDark(), new RectangleImage(100, 100, "solid",
        Color.GREEN.darker().darker()).movePinhole(400, 100))
        && t.checkExpect(blueButton.drawDark(), new RectangleImage(100, 100, "solid",
        Color.BLUE.darker().darker()).movePinhole(100, 400))
        && t.checkExpect(yellowButton.drawDark(), new RectangleImage(100, 100, "solid",
        Color.YELLOW.darker().darker()).movePinhole(400, 400));
  }

  // Tests for drawLit
  boolean testDrawLit(Tester t) {
    return t.checkExpect(redButton.drawLit(), new RectangleImage(100, 100, "solid",
        Color.RED.brighter().brighter()).movePinhole(100, 100))
        && t.checkExpect(greenButton.drawLit(), new RectangleImage(100, 100, "solid",
        Color.GREEN.brighter().brighter()).movePinhole(400, 100))
        && t.checkExpect(blueButton.drawLit(), new RectangleImage(100, 100, "solid",
        Color.BLUE.brighter().brighter()).movePinhole(100, 400))
        && t.checkExpect(yellowButton.drawLit(), new RectangleImage(100, 100, "solid",
        Color.YELLOW.brighter().brighter()).movePinhole(400, 400));
  }

  // Tests for drawButton
  boolean testDrawButton(Tester t) {
    return t.checkExpect(redButton.drawButton(Color.RED),
        new RectangleImage(100, 100, "solid",
        Color.RED).movePinhole(100, 100))
        && t.checkExpect(greenButton.drawButton(Color.GREEN),
          new RectangleImage(100, 100, "solid",
          Color.GREEN).movePinhole(400, 100))
        && t.checkExpect(blueButton.drawButton(Color.BLUE),
          new RectangleImage(100, 100, "solid",
          Color.BLUE).movePinhole(100, 400))
        && t.checkExpect(yellowButton.drawButton(Color.YELLOW),
          new RectangleImage(100, 100, "solid",
          Color.YELLOW).movePinhole(400, 400));
  }

  // Tests for isOverButton
  boolean testIsOverButton(Tester t) {
    return t.checkExpect(yellowButton2.isOverButton(new Posn(324, 336)), true)
        && t.checkExpect(yellowButton2.isOverButton(new Posn(600, 600)), false);
  }

  // Tests for isSameButtonAs
  boolean testIsSameButtonAs(Tester t) {
    return t.checkExpect(noButton.isSameButtonAs(new Button(Color.RED, 60, 60)), false)
        && t.checkExpect(noButton.isSameButtonAs(new Button(Color.WHITE, 60, 60)), true)
        && t.checkExpect(redButton.isSameButtonAs(new Button(Color.RED, 60, 60)), true)
        && t.checkExpect(redButton.isSameButtonAs(new Button(Color.RED, 100, 100)), true)
        && t.checkExpect(redButton.isSameButtonAs(new Button(Color.GREEN, 60, 60)), false)
        && t.checkExpect(redButton.isSameButtonAs(new Button(Color.BLUE, 60, 60)), false)
        && t.checkExpect(redButton.isSameButtonAs(new Button(Color.YELLOW, 60, 60)), false)
        && t.checkExpect(yellowButton.isSameButtonAs(yellowButton1), true);
  }



  // Tests for ILoButton methods:

  // Tests for drawButtons
  boolean testDrawButtons(Tester t) {
    return t.checkExpect(mtLoButton.drawButtons(), new EmptyImage())
        && t.checkExpect(buttonsForGame.drawButtons(),
        new OverlayOffsetAlign(
          AlignModeX.PINHOLE,
          AlignModeY.PINHOLE,
          new RectangleImage(100, 100, "solid", Color.RED).movePinhole(60, 60),
          0, 0,
          new OverlayOffsetAlign(
            AlignModeX.PINHOLE,
            AlignModeY.PINHOLE,
            new RectangleImage(100, 100, "solid", Color.GREEN).movePinhole(60, -60),
            0, 0,
            new OverlayOffsetAlign(
              AlignModeX.PINHOLE,
              AlignModeY.PINHOLE,
              new RectangleImage(100, 100, "solid", Color.BLUE).movePinhole(-60, 60),
              0, 0,
              new OverlayOffsetAlign(
                AlignModeX.PINHOLE,
                AlignModeY.PINHOLE,
                new RectangleImage(100, 100, "solid", Color.YELLOW).movePinhole(-60, -60),
                0, 0,
                new EmptyImage())))));
  }

  // Tests for drawDarkened
  boolean testDrawDarkened(Tester t) {
    return t.checkExpect(mtLoButton.drawDarkened(), new EmptyImage())
        && t.checkExpect(partialLoButton.drawDarkened(), new RectangleImage(100, 100, "solid",
            Color.RED.darker().darker()).movePinhole(100, 100))
        && t.checkExpect(buttonsForGame.drawDarkened(), new RectangleImage(100, 100, "solid",
            Color.RED.darker().darker()).movePinhole(60, 60));
  }

  // Tests for getNextSequence
  boolean testGetNextSequence(Tester t) {
    return t.checkExpect(mtLoButton.getNextSequence(), new MtLoButton())
        && t.checkExpect(consLoButton.getNextSequence(), new ConsLoButton(new Button(Color.GREEN,
        400, 100), new ConsLoButton(new Button(Color.BLUE, 100, 400), new ConsLoButton(new Button(
        Color.YELLOW, 400, 400), new MtLoButton()))))
        && t.checkExpect(partialLoButton.getNextSequence(), new ConsLoButton(
            new Button(Color.GREEN, 400, 100), new MtLoButton()));
  }

  // Tests for isDone
  boolean testIsDone(Tester t) {
    return t.checkExpect(mtLoButton.isDone(), true)
        && t.checkExpect(consLoButton.isDone(), false)
        && t.checkExpect(partialLoButton.isDone(), false);
  }

  // Tests for isOverButton
  boolean testIsOverButton2(Tester t) {
    return t.checkExpect(buttonsForGame.isOverButton(new Posn(324, 336)), yellowButton2)
        && t.checkExpect(buttonsForGame.isOverButton(new Posn(600, 600)), NO_BUTTON);
  }

  // Tests for startsWith
  boolean testStartsWith(Tester t) {
    return t.checkExpect(consLoButton.startsWith(partialLoButton), true)
        && t.checkExpect(consLoButton.startsWith(partialLoButton2), true)
        && t.checkExpect(consLoButton.startsWith(consLoButton), true)
        && t.checkExpect(consLoButton.startsWith(mtLoButton), true)
        && t.checkExpect(buttonsForGame.startsWith(partialLoButton), true)
        && t.checkExpect(consLoButton.startsWith(weirdOrderButtons), false);
  }

  // Tests for chop
  boolean testChop(Tester t) {
    return t.checkExpect(consLoButton.chop(partialLoButton), partialLoButton)
        && t.checkExpect(consLoButton.chop(weirdOrderButtons), partialLoButton)
        && t.checkExpect(partialLoButton.chop(consLoButton), partialLoButton);
  }

  // Tests for chopHelp
  boolean testChopHelp(Tester t) {
    return t.checkExpect(mtLoButton.chopHelp(redButton, mtLoButton), new MtLoButton())
        && t.checkExpect(consLoButton.chopHelp(redButton, partialLoButton),
                        new ConsLoButton(new Button(Color.RED, 100, 100),
                            this.partialLoButton))
        && t.checkExpect(partialLoButton.chopHelp(redButton, consLoButton),
            new ConsLoButton(new Button(Color.RED, 100, 100),
                            new ConsLoButton(redButton, new MtLoButton())));
  }

  // Tests for isSameAs
  boolean testIsSameAs(Tester t) {
    return t.checkExpect(mtLoButton.isSameAs(mtLoButton), true)
        && t.checkExpect(mtLoButton.isSameAs(consLoButton), false)
        && t.checkExpect(consLoButton.isSameAs(mtLoButton), false)
        && t.checkExpect(consLoButton.isSameAs(consLoButton), true)
        && t.checkExpect(consLoButton.isSameAs(partialLoButton), false)
        && t.checkExpect(partialLoButton.isSameAs(consLoButton), false)
        && t.checkExpect(consLoButton.isSameAs(buttonsForGame), true);
  }

  // Tests for isSameAsHelper
  boolean testIsSameAsHelper(Tester t) {
    return t.checkExpect(mtLoButton.isSameAsHelper(IConstants.NO_BUTTON, mtLoButton), true)
        && t.checkExpect(mtLoButton.isSameAsHelper(redButton, consLoButton), false)
        && t.checkExpect(consLoButton.isSameAsHelper(redButton, consLoButtonMinus1), true);
  }

  // Tests for addInner
  boolean testAddInner(Tester t) {
    return t.checkExpect(partialLoButton.addInner(blueButton), partialLoButton2)
        && t.checkExpect(mtLoButton.addInner(blueButton), new ConsLoButton(blueButton, mtLoButton))
        && t.checkExpect(consLoButton.addInner(blueButton), new ConsLoButton(redButton,
            new ConsLoButton(greenButton, new ConsLoButton(blueButton,
                new ConsLoButton(yellowButton,
                    new ConsLoButton(blueButton, mtLoButton))))));
  }

  // Tests for addToSequence
  boolean testAddToSequence(Tester t) {
    return t.checkExpect(mtLoButton.addToSequence(new Random(696969696)),
        new ConsLoButton(new Button(Color.GREEN, 60, -60), mtLoButton))
        && t.checkExpect(consLoButton.addToSequence(new Random(696969696)),
            new ConsLoButton(new Button(Color.RED, 100, 100),
                new ConsLoButton(new Button(Color.GREEN, 400, 100),
                    new ConsLoButton(new Button(Color.BLUE, 100, 400),
                        new ConsLoButton(new Button(Color.YELLOW, 400, 400),
                            new ConsLoButton(new Button(Color.GREEN, 60, -60), mtLoButton))))));
  }


  // WorldScenes for draw testing
  WorldScene background1 = new WorldScene(SCENE_SIZE, SCENE_SIZE).placeImageXY(
      new TextImage("Simon Says", 20, Color.BLACK), SCENE_SIZE / 2, 20).placeImageXY(
          new TextImage("Wait", 20, Color.RED), SCENE_SIZE / 2, 50).placeImageXY(
              this.buttonsForGame.drawButtons(), SCENE_SIZE / 2, SCENE_SIZE / 2).placeImageXY(
                  this.weirdOrderButtons.drawDarkened(), SCENE_SIZE / 2, SCENE_SIZE / 2);
  WorldScene background2 = new WorldScene(SCENE_SIZE, SCENE_SIZE).placeImageXY(
      new TextImage("Simon Says", 20, Color.BLACK), SCENE_SIZE / 2, 20).placeImageXY(
          new TextImage("Wait", 20, Color.RED), SCENE_SIZE / 2, 50).placeImageXY(
              this.buttonsForGame.drawButtons(), SCENE_SIZE / 2, SCENE_SIZE / 2).placeImageXY(
                  new EmptyImage(), SCENE_SIZE / 2, SCENE_SIZE / 2);
  WorldScene background3 = new WorldScene(SCENE_SIZE, SCENE_SIZE).placeImageXY(
      new TextImage("Simon Says", 20, Color.BLACK), SCENE_SIZE / 2, 20).placeImageXY(
          new TextImage("Repeat", 20, Color.RED), SCENE_SIZE / 2, 50).placeImageXY(
              this.buttonsForGame.drawButtons(), SCENE_SIZE / 2, SCENE_SIZE / 2).placeImageXY(
                  new EmptyImage(), SCENE_SIZE / 2, SCENE_SIZE / 2);

  WorldScene youLose = new WorldScene(SCENE_SIZE, SCENE_SIZE).placeImageXY(
      new TextImage("You Lose!", 20, Color.BLACK), SCENE_SIZE / 2, SCENE_SIZE / 2);

  WorldScene arbLossMsg = new WorldScene(SCENE_SIZE, SCENE_SIZE).placeImageXY(
      new TextImage("arbitrary loss message!", 20, Color.BLACK), SCENE_SIZE / 2, SCENE_SIZE / 2);

  // Examples for simon world
  SimonWorld exSimonWorld1 = new SimonWorld(buttonsForGame,
      new Random(696969696), weirdOrderButtons);
  SimonWorld exSimonWorld2 = new SimonWorld(exSimonWorld1, false,
      new EmptyImage(), weirdOrderButtons.getNextSequence());
  SimonWorld exSimonWorld3 = new SimonWorld(exSimonWorld2, true,
      exSimonWorld2.tempSequence.drawDarkened(), weirdOrderButtons.getNextSequence());
  SimonWorld exSimonWorld4 = new SimonWorld(exSimonWorld3, false,
      new EmptyImage(), weirdOrderButtons.getNextSequence().getNextSequence());
  SimonWorld exSimonWorld5 = new SimonWorld(exSimonWorld4, false,
      false, new EmptyImage());

  SimonWorld exSimonWorld6 = new SimonWorld(exSimonWorld5,
      new MtLoButton().addInner(gameButtonBlue),
      gameButtonBlue.drawDark(), true);
  SimonWorld exSimonWorld7 = new SimonWorld(exSimonWorld6,
      weirdOrderButtons.addInner(gameButtonGreen),
      weirdOrderButtons.addInner(gameButtonGreen).drawDarkened());

  SimonWorld exSimonWorld8 = new SimonWorld(exSimonWorld6, new EmptyImage());

  // Tests for SimonWorld methods

  // Tests for makeScene
  boolean testMakeScene(Tester t) {
    return t.checkExpect(exSimonWorld1.makeScene(), background1)
        && t.checkExpect(exSimonWorld2.makeScene(), background2)
        && t.checkExpect(exSimonWorld5.makeScene(), background3);
  }

  // Tests for onTick
  boolean testOnTick(Tester t) {
    return t.checkExpect(exSimonWorld1.onTick(), exSimonWorld2)
        && t.checkExpect(exSimonWorld2.onTick(), exSimonWorld3)
        && t.checkExpect(exSimonWorld3.onTick(), exSimonWorld4)
        && t.checkExpect(exSimonWorld4.onTick(), exSimonWorld5)
        && t.checkExpect(exSimonWorld5.onTick(), exSimonWorld5);
  }

  // Test for lastScene
  boolean testLastScene(Tester t) {
    return t.checkExpect(exSimonWorld5.lastScene("You Lose!"), youLose)
        && t.checkExpect(exSimonWorld2.lastScene("arbitrary loss message!"), arbLossMsg);
  }

  // Tests for onMousePressed
  boolean testOnMousePressed(Tester t) {
    return t.checkExpect(exSimonWorld5.onMousePressed(new Posn(25, 25)), exSimonWorld5)
        && t.checkExpect(exSimonWorld5.onMousePressed(new Posn(300, 175)), exSimonWorld6)
        && t.checkExpect(exSimonWorld6.onMousePressed(new Posn(300, 300)), exSimonWorld7)
        && t.checkExpect(exSimonWorld7.onMousePressed(new Posn(300, 300)), exSimonWorld7)
        && t.checkExpect(exSimonWorld7.onMousePressed(new Posn(100, 100)), exSimonWorld7);
  }

  // Tests for onMouseReleased
  boolean testOnMouseReleased(Tester t) {
    return t.checkExpect(exSimonWorld5.onMouseReleased(new Posn(25, 25)), exSimonWorld5)
        && t.checkExpect(exSimonWorld5.onMouseReleased(new Posn(300, 175)), exSimonWorld5)
        && t.checkExpect(exSimonWorld6.onMouseReleased(new Posn(300, 300)), exSimonWorld8)
        && t.checkExpect(exSimonWorld6.onMouseReleased(new Posn(100, 100)), exSimonWorld8)
        && t.checkExpect(exSimonWorld7.onMouseReleased(new Posn(300, 300)), exSimonWorld7);
  }

  //runs the game by creating a world and calling bigBang
  boolean testSimonSays(Tester t) {
    Random random = new Random(696969696);
    SimonWorld starterWorld = new SimonWorld(buttonsForGame, random,
        new MtLoButton().addToSequence(random));
    int sceneSize = SimonWorld.SCENE_SIZE;
    return starterWorld.bigBang(sceneSize, sceneSize, 1);
  }
}