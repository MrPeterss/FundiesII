package HW.a7;

// represents a list of Person's buddies
interface ILoBuddy {
  // does the given person appear in this list?
  boolean contains(Person that);

  // count the number of common direct buddies between this and that list
  int countCommonBuddies(ILoBuddy that);

  // helper for countCommonBuddies
  int countCommonBuddiesHelp(ILoBuddy that, int acc);

  // is this person connected to the list of buddies?
  boolean isConnectedTo(Person that, ILoBuddy seen);

  // get the party given the people already invited
  ILoBuddy getPartyList(ILoBuddy invited);

  // count how many people are in this list
  int count();

  // helper for count
  // Accumulator: the number of people counted so far
  int countHelp(int acc);
}
