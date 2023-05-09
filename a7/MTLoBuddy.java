package HW.a7;

// represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {
  MTLoBuddy() {
  }

  /*
   * TEMPLATE:
   * Fields:
   * ... NONE ...
   *
   * Methods:
   * ... contains(Person that) ... -- boolean
   * ... countCommonBuddies(ILoBuddy that) ... -- int
   * ... countCommonBuddiesHelp(ILoBuddy that, int acc) ... -- int
   * ... isConnectedTo(Person that, ILoBuddy seen) ... -- boolean
   * ... getParty(ILoBuddy invited) ... -- ILoBuddy
   * ... count() ... -- int
   * ... countHelp(int acc) ... -- int
   *
   * Methods for Fields:
   * ... NONE ...
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

    return false;
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

    return 0;
  }

  // helper for countCommonBuddies
  // Accumulator: the number of common buddies counted so far
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

    return acc;
  }

  // is this person connected to the list of buddies?
  // Accumulator: the list of people already seen
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

    return false;
  }

  // get the party given the people already invited
  // Accumulator: the list of people already invited
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

    return invited;
  }

  // count how many people are in this list
  public int count() {
    return this.countHelp(0);
  }

  // helper for count
  // Accumulator: the number of people counted so far
  public int countHelp(int acc) {
    return acc;
  }
}
