package HW.a7;

// represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {

  Person first;
  ILoBuddy rest;

  ConsLoBuddy(Person first, ILoBuddy rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * Template:
   * Methods:
   * ... contains(Person that) ... -- boolean
   * ... countCommonBuddies(ILoBuddy that) ... -- int
   * ... countCommonBuddiesHelp(ILoBuddy that, int acc) ... -- int
   * ... isConnectedTo(Person that, ILoBuddy seen) ... -- boolean
   * ... getParty(ILoBuddy invited) ... -- ILoBuddy
   * ... count() ... -- int
   * ... countHelp(int acc) ... -- int
   */

  // does this list contain that person?
  public boolean contains(Person that) {

    /*
     * Template for that:
     * Methods of that:
     * ... that.addBuddy(Person that) ... -- void
     * ... that.isSamePerson(Person that) ... -- boolean
     * ... that.hasDirectBuddy(Person that) ... -- boolean
     * ... that.partyCount() ... -- int
     * ... that.getParty() ... -- ILoBuddy
     * ... that.getParty(ILoBuddy invited) ... -- ILoBuddy
     * ... that.countCommonBuddies(Person that) ... -- int
     * ... that.hasExtendedBuddy(Person that) ... -- boolean
     * ... that.hasExtendedBuddy(Person that, ILoBuddy seen) ... -- boolean
     */

    return that.isSamePerson(this.first) || this.rest.contains(that);
  }

  // count the number of common direct buddies between this and that list
  public int countCommonBuddies(ILoBuddy that) {

    /*
     * Template for that:
     * Methods of that:
     * ... that.contains(Person that) ... -- boolean
     * ... that.countCommonBuddies(ILoBuddy that) ... -- int
     * ... that.countCommonBuddiesHelp(ILoBuddy that, int acc) ... -- int
     * ... that.isConnectedTo(Person that, ILoBuddy seen) ... -- boolean
     * ... that.getParty(ILoBuddy invited) ... -- ILoBuddy
     * ... that.count() ... -- int
     * ... that.countHelp(int acc) ... -- int
     */

    return this.countCommonBuddiesHelp(that, 0);
  }

  // helper for countCommonBuddies
  // Accumulator: the integer for the accumulator
  public int countCommonBuddiesHelp(ILoBuddy that, int acc) {

    /*
     * Template for that:
     * Methods of that:
     * ... that.contains(Person that) ... -- boolean
     * ... that.countCommonBuddies(ILoBuddy that) ... -- int
     * ... that.countCommonBuddiesHelp(ILoBuddy that, int acc) ... -- int
     * ... that.isConnectedTo(Person that, ILoBuddy seen) ... -- boolean
     * ... that.getParty(ILoBuddy invited) ... -- ILoBuddy
     * ... that.count() ... -- int
     * ... that.countHelp(int acc) ... -- int
     */

    if (that.contains(this.first)) {
      return this.rest.countCommonBuddiesHelp(that, acc + 1);
    } else {
      return this.rest.countCommonBuddiesHelp(that, acc);
    }
  }

  // is this person connected to the list of buddies?
  // Accumulator: the people seen so far
  public boolean isConnectedTo(Person that, ILoBuddy seen) {

    /*
     * Template for that:
     * Methods of that:
     * ... that.addBuddy(Person that) ... -- void
     * ... that.isSamePerson(Person that) ... -- boolean
     * ... that.hasDirectBuddy(Person that) ... -- boolean
     * ... that.partyCount() ... -- int
     * ... that.getParty() ... -- ILoBuddy
     * ... that.getParty(ILoBuddy invited) ... -- ILoBuddy
     * ... that.countCommonBuddies(Person that) ... -- int
     * ... that.hasExtendedBuddy(Person that) ... -- boolean
     * ... that.hasExtendedBuddy(Person that, ILoBuddy seen) ... -- boolean
     *
     * Template for seen:
     * Methods of seen:
     * ... seen.contains(Person that) ... -- boolean
     * ... seen.countCommonBuddies(ILoBuddy that) ... -- int
     * ... seen.countCommonBuddiesHelp(ILoBuddy that, int acc) ... -- int
     * ... seen.isConnectedTo(Person that, ILoBuddy seen) ... -- boolean
     * ... seen.getParty(ILoBuddy invited) ... -- ILoBuddy
     * ... seen.count() ... -- int
     * ... seen.countHelp(int acc) ... -- int
     */

    if (seen.contains(this.first)) {
      return this.rest.isConnectedTo(that, seen);
    } else {
      return this.rest.isConnectedTo(that, new ConsLoBuddy(this.first, seen))
          || this.first.isSamePerson(that)
          || this.first.hasExtendedBuddy(that, new ConsLoBuddy(this.first, seen));
    }
  }

  // get the party given the people already invited
  // Accummulator: the people invited so far
  public ILoBuddy getPartyList(ILoBuddy invited) {

    /*
     * Template for invited:
     * Methods of that:
     * ... invited.contains(Person that) ... -- boolean
     * ... invited.countCommonBuddies(ILoBuddy that) ... -- int
     * ... invited.countCommonBuddiesHelp(ILoBuddy that, int acc) ... -- int
     * ... invited.isConnectedTo(Person that, ILoBuddy seen) ... -- boolean
     * ... invited.getParty(ILoBuddy invited) ... -- ILoBuddy
     * ... invited.count() ... -- int
     * ... invited.countHelp(int acc) ... -- int
     */

    if (invited.contains(this.first)) {
      return this.rest.getPartyList(invited);
    } else {
      return this.rest.getPartyList(this.first.getParty(invited));
    }
  }

  // count how many people are in this list
  public int count() {
    return this.countHelp(0);
  }

  // helper for count
  // Accumulator: the number of people counted so far
  public int countHelp(int acc) {
    return this.rest.countHelp(acc + 1);
  }
}
