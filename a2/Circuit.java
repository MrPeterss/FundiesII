package HW.a2;

import tester.Tester;

// represents a circuit
interface ICircuit {
  // counts the number of components in a circuit
  int countComponents();

  // returns the total voltage of a circuit
  double totalVoltage();

  // returns the total current of a circuit
  double totalCurrent();

  // returns the total resistance of a circuit
  double totalResistance();

  // returns a new circuit with the opposite polarity
  ICircuit reversePolarity();
}

// represents a battery in a circuit
class Battery implements ICircuit {

  String name;
  double voltage;
  double nominalResistance;

  Battery(String name, double voltage, double nominalResistance) {
    this.name = name;
    this.voltage = voltage;
    this.nominalResistance = nominalResistance;
  }

  /* TEMPLATE
     FIELDS:
     ... this.name ...                -- String
     ... this.voltage ...             -- double
     ... this.nominalResistance ...   -- double
     METHODS
     ... this.countComponents() ...   -- int
     ... this.totalVoltage() ...      -- double
     ... this.totalCurrent() ...      -- double
     ... this.totalResistance() ...   -- double
     ... this.reversePolarity() ...   -- HW.a2.ICircuit
  */

  // because the battery is 1 component, it always returns 1
  public int countComponents() {
    return 1;
  }

  // returns the voltage of the battery
  public double totalVoltage() {
    return this.voltage;
  }

  // returns the current of the battery
  public double totalCurrent() {
    return this.totalVoltage() / this.totalResistance();
  }

  // returns the total resistance of the battery
  public double totalResistance() {
    return this.nominalResistance;
  }

  // returns a new battery with the opposite polarity
  public ICircuit reversePolarity() {
    return new Battery(this.name, -this.voltage, this.nominalResistance);
  }
}

// represents a resistor in a circuit
class Resistor implements ICircuit {

  String name;
  double resistance;

  Resistor(String name, double resistance) {
    this.name = name;
    this.resistance = resistance;
  }

  /* TEMPLATE
     FIELDS:
     ... this.name ...                -- String
     ... this.resistance ...          -- double
     METHODS
     ... this.countComponents() ...   -- int
     ... this.totalVoltage() ...      -- double
     ... this.totalCurrent() ...      -- double
     ... this.totalResistance() ...   -- double
     ... this.reversePolarity() ...   -- HW.a2.ICircuit
  */

  // because the resistor is 1 component, it always returns 1
  public int countComponents() {
    return 1;
  }

  // returns the voltage (0) of the resistor
  public double totalVoltage() {
    return 0;
  }

  // returns the current of the resistor
  public double totalCurrent() {
    return this.totalVoltage() / this.totalResistance();
  }

  // returns the resistance of the resistor
  public double totalResistance() {
    return this.resistance;
  }

  // returns a new resistor with the opposite polarity
  public ICircuit reversePolarity() {
    return this;
  }
}

// represents a series circuit
class Series implements ICircuit {

  ICircuit first;
  ICircuit second;

  Series(ICircuit first, ICircuit second) {
    this.first = first;
    this.second = second;
  }

  /* TEMPLATE
     FIELDS:
     ... this.first ...                     -- HW.a2.ICircuit
     ... this.second ...                    -- HW.a2.ICircuit
     METHODS:
     ... this.countComponents() ...         -- int
     ... this.totalVoltage() ...            -- double
     ... this.totalCurrent() ...            -- double
     ... this.totalResistance() ...         -- double
     ... this.reversePolarity() ...         -- HW.a2.ICircuit
     METHODS FOR FIELDS:
     ... this.first.countComponents() ...   -- int
     ... this.first.totalVoltage() ...      -- double
     ... this.first.totalCurrent() ...      -- double
     ... this.first.totalResistance() ...   -- double
     ... this.first.reversePolarity() ...   -- HW.a2.ICircuit

     ... this.second.countComponents() ...  -- int
     ... this.second.totalVoltage() ...     -- double
     ... this.second.totalCurrent() ...     -- double
     ... this.second.totalResistance() ...  -- double
     ... this.second.reversePolarity() ...  -- HW.a2.ICircuit
  */

  // counts all the components in a series circuit
  public int countComponents() {
    return this.first.countComponents() + this.second.countComponents();
  }

  // returns the total voltage of a series circuit
  public double totalVoltage() {
    return this.first.totalVoltage() + this.second.totalVoltage();
  }

  // returns the total current of a series circuit
  public double totalCurrent() {
    return this.totalVoltage() / this.totalResistance();
  }

  // returns the total resistance of a series circuit
  public double totalResistance() {
    return this.first.totalResistance() + this.second.totalResistance();
  }

  // returns a new series circuit with the opposite polarity
  public ICircuit reversePolarity() {
    return new Series(this.first.reversePolarity(), this.second.reversePolarity());
  }
}

// represents a parallel circuit
class Parallel implements ICircuit {

  ICircuit first;
  ICircuit second;

  Parallel(ICircuit first, ICircuit second) {
    this.first = first;
    this.second = second;
  }


  /* TEMPLATE
     FIELDS:
     ... this.first ...                     -- HW.a2.ICircuit
     ... this.second ...                    -- HW.a2.ICircuit
     METHODS:
     ... this.countComponents() ...         -- int
     ... this.totalVoltage() ...            -- double
     ... this.totalCurrent() ...            -- double
     ... this.totalResistance() ...         -- double
     ... this.reversePolarity() ...         -- HW.a2.ICircuit
     METHODS FOR FIELDS:
     ... this.first.countComponents() ...   -- int
     ... this.first.totalVoltage() ...      -- double
     ... this.first.totalCurrent() ...      -- double
     ... this.first.totalResistance() ...   -- double
     ... this.first.reversePolarity() ...   -- HW.a2.ICircuit

     ... this.second.countComponents() ...  -- int
     ... this.second.totalVoltage() ...     -- double
     ... this.second.totalCurrent() ...     -- double
     ... this.second.totalResistance() ...  -- double
     ... this.second.reversePolarity() ...  -- HW.a2.ICircuit
  */

  // counts all the components in a parallel circuit
  public int countComponents() {
    return this.first.countComponents() + this.second.countComponents();
  }

  // determines the voltage of the circuit
  public double totalVoltage() {
    return Math.max(this.first.totalVoltage(), this.second.totalVoltage());
  }

  // returns the total current of a parallel circuit
  public double totalCurrent() {
    return this.totalVoltage() / this.totalResistance();
  }

  // returns the total resistance of a parallel circuit
  public double totalResistance() {
    return 1 / (1 / this.first.totalResistance() + 1 / this.second.totalResistance());
  }

  // returns a new parallel circuit with the opposite polarity
  public ICircuit reversePolarity() {
    return new Parallel(this.first.reversePolarity(), this.second.reversePolarity());
  }
}

// Examples
class ExamplesCircuits {

  ExamplesCircuits() {}

  ICircuit batteryOne = new Battery("B 1", 10.0, 25);
  ICircuit resistorOne = new Resistor("R 1", 100.0);
  ICircuit simpleSeries = new Series(this.batteryOne, this.resistorOne);

  // components of the complex circuit
  ICircuit bt1 = new Battery("BT 1", 10.0, 0.0);
  ICircuit bt2 = new Battery("BT 2", 20.0, 0.0);
  ICircuit R4 = new Resistor("R 4", 200.0);
  ICircuit R5 = new Resistor("R 5", 50.0);
  ICircuit R1 = new Resistor("R 1", 100.0);
  ICircuit R2 = new Resistor("R 2", 250.0);
  ICircuit R3 = new Resistor("R 3", 500.0);

  // connections in the complex circuit
  ICircuit connectionOne = new Series(this.bt1, this.bt2);
  ICircuit connectionTwo = new Series(this.R4, this.R5);
  ICircuit connectionThree = new Parallel(this.connectionTwo, this.R1);
  ICircuit connectionFour = new Parallel(this.connectionThree, this.R2);
  ICircuit connectionFive = new Parallel(this.connectionFour, this.R3);

  ICircuit complexCircuit = new Series(connectionOne, connectionFive);

  // tests for the countComponents method
  boolean testCountComponents(Tester t) {
    return t.checkExpect(this.batteryOne.countComponents(), 1)
        && t.checkExpect(this.simpleSeries.countComponents(), 2)
        && t.checkExpect(this.complexCircuit.countComponents(), 7);
  }

  // tests for the totalVoltage method
  boolean testTotalVoltage(Tester t) {
    return t.checkInexact(this.batteryOne.totalVoltage(), 10.0, 0.001)
        && t.checkInexact(this.complexCircuit.totalVoltage(), 30.0, 0.001);
  }

  // tests for the totalCurrent method
  boolean testTotalCurrent(Tester t) {
    return t.checkInexact(this.simpleSeries.totalCurrent(), 0.08, 0.001)
        && t.checkInexact(this.complexCircuit.totalCurrent(), 0.6, 0.001);
  }

  // tests for the totalResistance method
  boolean testTotalResistance(Tester t) {
    return t.checkInexact(this.simpleSeries.totalResistance(), 125.0, 0.001)
        && t.checkInexact(this.complexCircuit.totalResistance(), 50.0, 0.001);
  }

  // tests for the reversePolarity method
  boolean testReversePolarity(Tester t) {
    return t.checkExpect(
      this.batteryOne.reversePolarity(),
      new Battery("B 1", -10.0, 25))
      && t.checkExpect(
        this.simpleSeries.reversePolarity(),
        new Series(new Battery("B 1", -10.0, 25), resistorOne))
      && t.checkExpect(
        this.complexCircuit.reversePolarity(),
        new Series(
          new Series(
            new Battery("BT 1", -10.0, 0.0),
            new Battery("BT 2", -20.0, 0.0)),
          this.connectionFive));
  }
}
