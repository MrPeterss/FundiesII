import tester.Tester;

// represents a bagel recipe
class BagelRecipe {
  double flour;
  double water;
  double yeast;
  double malt;
  double salt;

  // Integrity Constructor
  BagelRecipe(double flour, double water, double yeast, double salt, double malt) {
    Utils utils = new Utils();
    this.flour = utils.checkIfPositive(flour);
    this.water = utils.checkEquivalence(water, flour,
        "Water and flour must be equal");
    this.yeast = utils.checkIfPositive(yeast);
    this.malt = utils.checkEquivalence(malt, yeast,
        "Yeast and malt must be equal");
    this.salt = utils.checkEquivalence(utils.checkIfPositive(salt) + yeast, flour / 20.0,
        "Salt and yeast must be 1/20th of flour");
  }

  // Convenience Constructor that takes in the volume of flour and yeast
  // and produces a perfect recipe
  BagelRecipe(double flour, double yeast) {
    this(flour, flour, yeast, flour / 20.0 - yeast, yeast);
  }

  // Convenience Constructor that takes in the volume of ingredients in cups and teaspoons
  BagelRecipe(double flour, double yeast, double salt) {
    this(
        flour * 4.25,
        flour * 4.25,
        yeast * 5 / 48.0,
        salt * 10 / 48.0,
        yeast * 5 / 48.0
    );
  }

  /* TEMPLATE
   * Fields:
   * ... this.flour ... - double
   * ... this.water ... - double
   * ... this.yeast ... - double
   * ... this.malt ... - double
   * ... this.salt ... - double
   *
   * Methods:
   * ... this.sameRecipe(BagelRecipe) ... - boolean
   *
   * Methods for Fields:
   * ... NONE ...
   */

  // determines if this recipe is the same as the given recipe within 0.001 ounces
  boolean sameRecipe(BagelRecipe that) {
    return Math.abs(this.flour - that.flour) < 0.001
        && Math.abs(this.water - that.water) < 0.001
        && Math.abs(this.yeast - that.yeast) < 0.001
        && Math.abs(this.malt - that.malt) < 0.001
        && Math.abs(this.salt - that.salt) < 0.001;
  }
}

// Utilities class that is used to build methods throwing exceptions
class Utils {

  //checks if the given original number equivalent to the given other number
  double checkEquivalence(double original, double toCompareTo, String msg) {
    if (checkIfPositive(original) == checkIfPositive(toCompareTo)) {
      return original;
    } else {
      throw new IllegalArgumentException(msg);
    }
  }

  //checks if the given number is positive
  double checkIfPositive(double original) {
    if (original >= 0) {
      return original;
    } else {
      throw new IllegalArgumentException("Ingredient must be non-negative");
    }
  }
}

// Examples class
class ExamplesBagels {

  // empty constructor
  ExamplesBagels() {}

  // examples that should all be the same recipe
  BagelRecipe manualRecipe = new BagelRecipe(40.0, 40.0, 1.0, 1.0, 1.0);
  BagelRecipe perfectRecipe = new BagelRecipe(40.001, 1.001);
  BagelRecipe volumeRecipe = new BagelRecipe(160.0 / 17.0, 48.0 / 5.0, 4.8);

  // examples that are different from the ones above, but similar to each other
  BagelRecipe differentManualRecipe = new BagelRecipe(20.0, 20.0, 0.5, 0.5, 0.5);
  BagelRecipe differentPerfectRecipe = new BagelRecipe(20.0, 0.5);
  BagelRecipe differentVolumeRecipe = new BagelRecipe(160.0 / 34.0, 24.0 / 5.0, 2.4);

  // tests for the bagel recipe class constructor exceptions
  boolean testBagelRecipeConstructor(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Ingredient must be non-negative"),
        "BagelRecipe",
        -40.0, 40.0, 1.0, 1.0, 1.0
    ) && t.checkConstructorException(
        new IllegalArgumentException("Ingredient must be non-negative"),
        "BagelRecipe",
        40.0, -40.0, 1.0, 1.0, 1.0
    ) && t.checkConstructorException(
        new IllegalArgumentException("Ingredient must be non-negative"),
        "BagelRecipe",
        40.0, 40.0, -1.0, 1.0, 1.0
    ) && t.checkConstructorException(
        new IllegalArgumentException("Ingredient must be non-negative"),
        "BagelRecipe",
        40.0, 40.0, 1.0, -1.0, 1.0
    ) && t.checkConstructorException(
        new IllegalArgumentException("Ingredient must be non-negative"),
        "BagelRecipe",
        40.0, 40.0, 1.0, 1.0, -1.0
    ) && t.checkConstructorException(
        new IllegalArgumentException("Water and flour must be equal"),
        "BagelRecipe",
        40.0, 20.0, 1.0, 1.0, 1.0
    ) && t.checkConstructorException(
        new IllegalArgumentException("Yeast and malt must be equal"),
        "BagelRecipe",
        40.0, 40.0, 1.0, 1.0, 2.0
    ) && t.checkConstructorException(
        new IllegalArgumentException("Salt and yeast must be 1/20th of flour"),
        "BagelRecipe",
        40.0, 40.0, 1.0, 2.0, 1.0
    ) && t.checkConstructorException(
        new IllegalArgumentException("Ingredient must be non-negative"),
        "BagelRecipe",
        -40.0, 1.0
    ) && t.checkConstructorException(
        new IllegalArgumentException("Ingredient must be non-negative"),
        "BagelRecipe",
        40.0, -1.0
    ) && t.checkConstructorException(
        new IllegalArgumentException("Salt and yeast must be 1/20th of flour"),
        "BagelRecipe",
        160.0 / 17.0, 48.0 / 5.0, 1.0
    ) && t.checkConstructorException(
        new IllegalArgumentException("Ingredient must be non-negative"),
        "BagelRecipe",
        -160.0 / 17.0, 48.0 / 5.0, 1.0
    ) && t.checkConstructorException(
        new IllegalArgumentException("Ingredient must be non-negative"),
        "BagelRecipe",
        160.0 / 17.0, -48.0 / 5.0, 1.0
    ) && t.checkConstructorException(
        new IllegalArgumentException("Ingredient must be non-negative"),
        "BagelRecipe",
        160.0 / 17.0, 48.0 / 5.0, -1.0
    );
  }

  // tests for the same recipe method
  boolean testSameRecipe(Tester t) {
    return t.checkExpect(this.manualRecipe.sameRecipe(this.perfectRecipe), true)
        && t.checkExpect(this.perfectRecipe.sameRecipe(this.volumeRecipe), true)
        && t.checkExpect(this.volumeRecipe.sameRecipe(this.manualRecipe), true)
        && t.checkExpect(this.differentManualRecipe.sameRecipe(this.differentPerfectRecipe), true)
        && t.checkExpect(this.differentPerfectRecipe.sameRecipe(this.differentVolumeRecipe), true)
        && t.checkExpect(this.differentVolumeRecipe.sameRecipe(this.differentManualRecipe), true)
        && t.checkExpect(this.manualRecipe.sameRecipe(this.differentManualRecipe), false)
        && t.checkExpect(this.manualRecipe.sameRecipe(this.differentPerfectRecipe), false)
        && t.checkExpect(this.manualRecipe.sameRecipe(this.differentVolumeRecipe), false)
        && t.checkExpect(this.perfectRecipe.sameRecipe(this.differentManualRecipe), false)
        && t.checkExpect(this.perfectRecipe.sameRecipe(this.differentPerfectRecipe), false)
        && t.checkExpect(this.perfectRecipe.sameRecipe(this.differentVolumeRecipe), false)
        && t.checkExpect(this.volumeRecipe.sameRecipe(this.differentManualRecipe), false)
        && t.checkExpect(this.volumeRecipe.sameRecipe(this.differentPerfectRecipe), false)
        && t.checkExpect(this.volumeRecipe.sameRecipe(this.differentVolumeRecipe), false);
  }
}
