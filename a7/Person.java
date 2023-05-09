package HW.a7;

// represents a Person with a username and a list of buddies
class Person {

  String username;
  ILoBuddy buddies;

  Person(String username) {
    this.username = username;
    this.buddies = new MTLoBuddy();
  }

  /*
    * Template:
    * Fields:
    * ... this.username ... -- String
    * ... this.buddies ... -- ILoBuddy
    *
    * Methods:
    * ... addBuddy(Person that) ... -- void
    * ... isSamePerson(Person that) ... -- boolean
    * ... hasDirectBuddy(Person that) ... -- boolean
    * ... partyCount() ... -- int
    * ... getParty() ... -- ILoBuddy
    * ... getParty(ILoBuddy invited) ... -- ILoBuddy
    * ... countCommonBuddies(Person that) ... -- int
    * ... hasExtendedBuddy(Person that) ... -- boolean
    * ... hasExtendedBuddy(Person that, ILoBuddy seen) ... -- boolean
    *
    * Methods for Fields:
    * ... this.buddies.contains(Person that) ... -- boolean
    * ... this.buddies.countCommonBuddies(Person that) ... -- int
    * ... this.buddies.countCommonBuddiesHelp(Person that, int acc) ... -- int
    * ... this.buddies.isConnectedTo(Person that, ILoBuddy seen) ... -- boolean
    * ... this.buddies.getParty(ILoBuddy invited) ... -- ILoBuddy
    * ... this.buddies.count() ... -- int
    * ... this.buddies.countHelp(int acc) ... -- int
   */

  // EFFECT: Change this person's buddy list so that it includes the given person
  void addBuddy(Person that) {

    /*
     * Template for that:
     * Fields of that:
     * ... that.username ... -- String
     * ... that.buddies ... -- ILoBuddy
     *
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
     * Methods for Fields of that:
     * ... that.buddies.contains(Person that) ... -- boolean
     * ... that.buddies.countCommonBuddies(Person that) ... -- int
     * ... that.buddies.countCommonBuddiesHelp(Person that, int acc) ... -- int
     * ... that.buddies.isConnectedTo(Person that, ILoBuddy seen) ... -- boolean
     * ... that.buddies.getParty(ILoBuddy invited) ... -- ILoBuddy
     * ... that.buddies.count() ... -- int
     * ... that.buddies.countHelp(int acc) ... -- int
     */

    this.buddies = new ConsLoBuddy(that, this.buddies);
  }

  // is this person the same as that person?
  public boolean isSamePerson(Person that) {

    /*
     * Template for that:
     * Fields of that:
     * ... that.username ... -- String
     * ... that.buddies ... -- ILoBuddy
     *
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
     * Methods for Fields:
     * ... that.buddies.contains(Person that) ... -- boolean
     * ... that.buddies.countCommonBuddies(Person that) ... -- int
     * ... that.buddies.countCommonBuddiesHelp(Person that, int acc) ... -- int
     * ... that.buddies.isConnectedTo(Person that, ILoBuddy seen) ... -- boolean
     * ... that.buddies.getParty(ILoBuddy invited) ... -- ILoBuddy
     * ... that.buddies.count() ... -- int
     * ... that.buddies.countHelp(int acc) ... -- int
     */

    return this.username.equals(that.username);
  }

  // returns true if this Person has that as a direct buddy
  boolean hasDirectBuddy(Person that) {

    /*
     * Template for that:
     * Fields of that:
     * ... that.username ... -- String
     * ... that.buddies ... -- ILoBuddy
     *
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
     * Methods for Fields:
     * ... that.buddies.contains(Person that) ... -- boolean
     * ... that.buddies.countCommonBuddies(Person that) ... -- int
     * ... that.buddies.countCommonBuddiesHelp(Person that, int acc) ... -- int
     * ... that.buddies.isConnectedTo(Person that, ILoBuddy seen) ... -- boolean
     * ... that.buddies.getParty(ILoBuddy invited) ... -- ILoBuddy
     * ... that.buddies.count() ... -- int
     * ... that.buddies.countHelp(int acc) ... -- int
     */

    return this.buddies.contains(that);
  }

  // returns the number of people who will show up at the party
  // given by this person
  int partyCount() {
    return this.getParty().count();
  }

  // returns a list of all the people who will show up at the party
  ILoBuddy getParty() {
    return this.buddies.getPartyList(new ConsLoBuddy(this, new MTLoBuddy()));
  }

  // returns a list of all the people who will show up at the party
  // given a list of ppl who have already been invited
  ILoBuddy getParty(ILoBuddy invited) {

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

    System.out.println("Inviting " + this.username);
    return this.buddies.getPartyList(new ConsLoBuddy(this, invited));
  }

  // returns the number of people that are direct buddies
  // of both this and that person
  int countCommonBuddies(Person that) {

    /*
     * Template for that:
     * Fields of that:
     * ... that.username ... -- String
     * ... that.buddies ... -- ILoBuddy
     *
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
     * Methods for Fields:
     * ... that.buddies.contains(Person that) ... -- boolean
     * ... that.buddies.countCommonBuddies(Person that) ... -- int
     * ... that.buddies.countCommonBuddiesHelp(Person that, int acc) ... -- int
     * ... that.buddies.isConnectedTo(Person that, ILoBuddy seen) ... -- boolean
     * ... that.buddies.getParty(ILoBuddy invited) ... -- ILoBuddy
     * ... that.buddies.count() ... -- int
     * ... that.buddies.countHelp(int acc) ... -- int
     */

    return this.buddies.countCommonBuddies(that.buddies);
  }

  // will the given person be invited to a party
  // organized by this person?
  boolean hasExtendedBuddy(Person that) {

    /*
     * Template for that:
     * Fields of that:
     * ... that.username ... -- String
     * ... that.buddies ... -- ILoBuddy
     *
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
     * Methods for Fields:
     * ... that.buddies.contains(Person that) ... -- boolean
     * ... that.buddies.countCommonBuddies(Person that) ... -- int
     * ... that.buddies.countCommonBuddiesHelp(Person that, int acc) ... -- int
     * ... that.buddies.isConnectedTo(Person that, ILoBuddy seen) ... -- boolean
     * ... that.buddies.getParty(ILoBuddy invited) ... -- ILoBuddy
     * ... that.buddies.count() ... -- int
     * ... that.buddies.countHelp(int acc) ... -- int
     */

    return this.buddies.isConnectedTo(that, new MTLoBuddy());
  }

  // will the given person be invited to a party
  // organized by this person? given a list of ppl we have already checked
  boolean hasExtendedBuddy(Person that, ILoBuddy seen) {

    /*
     * Template for that:
     * Fields of that:
     * ... that.username ... -- String
     * ... that.buddies ... -- ILoBuddy
     *
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
     * Methods for Fields:
     * ... that.buddies.contains(Person that) ... -- boolean
     * ... that.buddies.countCommonBuddies(Person that) ... -- int
     * ... that.buddies.countCommonBuddiesHelp(Person that, int acc) ... -- int
     * ... that.buddies.isConnectedTo(Person that, ILoBuddy seen) ... -- boolean
     * ... that.buddies.getParty(ILoBuddy invited) ... -- ILoBuddy
     * ... that.buddies.count() ... -- int
     * ... that.buddies.countHelp(int acc) ... -- int
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

    return this.buddies.isConnectedTo(that, seen);
  }

}
