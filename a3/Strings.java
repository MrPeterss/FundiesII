import tester.*;

// to represent a list of Strings
interface ILoString {
  // combine all Strings in this list into one
  String combine();

  // sort this list of strings
  ILoString sort();

  // insert the given string into this list of strings
  ILoString insert(String s);

  // is the List of Strings sorted?
  boolean isSorted();

  // helper for the isSorted method
  boolean isSortedHelp(String s);

  // interleave this list of strings with another list of strings
  ILoString interleave(ILoString that);

  // merge the given sorted list with another sorted list but keep it sorted
  ILoString merge(ILoString that);

  // checks if the given string comes before the given list
  ILoString mergeHelp(ILoString acc);

  // reverses the list of strings
  ILoString reverse();

  // helper for the reverse method
  ILoString reverseHelp(ILoString acc);

  // determines if the given list is a "doubled" list
  boolean isDoubledList();

  // helper for the isDoubledList method
  boolean isDoubledListHelp(String s);

  // determines if the list is a palindrome list
  boolean isPalindromeList();

  // determines if the list is the same as the given list
  boolean sameList(ILoString that);

  // helper for the sameList method
  boolean sameListHelp(String s, ILoString that);
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
  MtLoString() {
  }

  /*
   TEMPLATE
   FIELDS:
    ... NONE ...
   METHODS
    ... this.combine() ...                        -- String
    ... this.sort() ...                           -- ILoString
    ... this.insert(String) ...                   -- ILoString
    ... this.isSorted() ...                       -- boolean
    ... this.isSortedHelp(String) ...             -- boolean
    ... this.interleave(ILoString) ...            -- ILoString
    ... this.merge(ILoString) ...                 -- ILoString
    ... this.mergeHelp(ILoString) ...             -- ILoString
    ... this.reverse() ...                        -- ILoString
    ... this.reverseHelp(ILoString) ...           -- ILoString
    ... this.isDoubledList() ...                  -- boolean
    ... this.isDoubledListHelp(String) ...        -- boolean
    ... this.isPalindromeList() ...               -- boolean
    ... this.sameList(ILoString) ...              -- boolean
    ... this.sameListHelp(String, ILoString) ...  -- boolean
   METHODS FOR FIELDS:
    ... NONE ...
   */

  // combine all Strings in this list into one
  public String combine() {
    return "";
  }

  // sort this list of strings
  public ILoString sort() {
    return this;
  }

  // insert the given string into this list of strings
  public ILoString insert(String s) {
    return new ConsLoString(s, this);
  }

  // is the empty List of Strings sorted? (ye it is)
  public boolean isSorted() {
    return true;
  }

  // helper for the isSorted method
  public boolean isSortedHelp(String s) {
    return true;
  }

  // interleave this list of strings with another list of strings
  public ILoString interleave(ILoString that) {
    return that;
  }

  // merge the given sorted list with another sorted list but keep it sorted
  public ILoString merge(ILoString that) {
    return that;
  }

  // returns the accumulator when the list is empty
  public ILoString mergeHelp(ILoString acc) {
    return acc;
  }

  // reverses the list of strings (returns the empty list)
  public ILoString reverse() {
    return this;
  }

  // returns the accumulator when the list is empty
  public ILoString reverseHelp(ILoString acc) {
    return acc;
  }

  // determines if the given list is a "doubled" list
  public boolean isDoubledList() {
    return true;
  }

  // helper for the isDoubledList method
  public boolean isDoubledListHelp(String s) {
    return false;
  }

  // determines if the list is a palindrome list
  public boolean isPalindromeList() {
    return true;
  }

  // determines if the list is the same as the given list
  public boolean sameList(ILoString that) {
    return that.sameListHelp(null, this);
  }

  // helper for the sameList method
  public boolean sameListHelp(String s, ILoString that) {
    return s == null;
  }
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }
    
  /*
   TEMPLATE
   FIELDS:
    ... this.first ...         -- String
    ... this.rest ...          -- ILoString
   METHODS
    ... this.combine() ...     -- String
    ... this.sort() ...        -- ILoString
    ... this.insert(String) ...-- ILoString
    ... this.isSorted() ...    -- boolean
    ... this.isSortedHelp(String) ... -- boolean
    ... this.interleave(ILoString) ... -- ILoString
    ... this.merge(ILoString) ... -- ILoString
    ... this.mergeHelp(ILoString) ... -- ILoString
    ... this.reverse() ...     -- ILoString
    ... this.reverseHelp(ILoString) ... -- ILoString
    ... this.isDoubledList() ... -- boolean
    ... this.isDoubledListHelp(String) ... -- boolean
    ... this.isPalindromeList() ... -- boolean
    ... this.sameList(ILoString) ... -- boolean
    ... this.sameListHelp(String, ILoString) ... -- boolean
   METHODS FOR FIELDS:
    ... this.rest.combine() ... -- String
    ... this.rest.sort() ... -- ILoString
    ... this.rest.insert(String) ... -- ILoString
    ... this.rest.isSorted() ... -- boolean
    ... this.rest.isSortedHelp(String) ... -- boolean
    ... this.rest.interleave(ILoString) ... -- ILoString
    ... this.rest.merge(ILoString) ... -- ILoString
    ... this.rest.mergeHelp(ILoString) ... -- ILoString
    ... this.rest.reverse() ... -- ILoString
    ... this.rest.reverseHelp(ILoString) ... -- ILoString
    ... this.rest.isDoubledList() ... -- boolean
    ... this.rest.isDoubledListHelp(String) ... -- boolean
    ... this.rest.isPalindromeList() ... -- boolean
    ... this.rest.sameList(ILoString) ... -- boolean
    ... this.rest.sameListHelp(String, ILoString) ... -- boolean
   */

  // combine all Strings in this list into one
  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  // sort this list of strings
  public ILoString sort() {
    return this.rest.sort().insert(this.first);
  }

  // insert the given string into this list of strings
  public ILoString insert(String s) {
    if (s.toLowerCase().compareTo(this.first.toLowerCase()) <= 0) {
      return new ConsLoString(s, this);
    } else {
      return new ConsLoString(this.first, this.rest.insert(s));
    }
  }

  // is the List of Strings sorted?
  public boolean isSorted() {
    return this.rest.isSortedHelp(this.first);
  }

  // helper for the isSorted method
  public boolean isSortedHelp(String s) {
    return this.first.toLowerCase().compareTo(s.toLowerCase()) >= 0
        && this.rest.isSortedHelp(this.first);
  }

  // interleave this list of strings with another list of strings
  public ILoString interleave(ILoString that) {
    return new ConsLoString(this.first, that.interleave(this.rest));
  }

  // merge the given sorted list with another sorted list but keep it sorted
  public ILoString merge(ILoString that) {
    return this.mergeHelp(that);
  }

  // checks if the given string comes before the given list
  public ILoString mergeHelp(ILoString acc) {
    return this.rest.mergeHelp(acc.insert(this.first));
  }

  // reverses the list of strings
  public ILoString reverse() {
    return this.reverseHelp(new MtLoString());
  }

  // helper for the reverse method
  public ILoString reverseHelp(ILoString acc) {
    return rest.reverseHelp(new ConsLoString(this.first, acc));
  }

  // determines if the given list is a "doubled" list
  public boolean isDoubledList() {
    return this.rest.isDoubledListHelp(this.first);
  }

  // helper for the isDoubledList method
  public boolean isDoubledListHelp(String s) {
    return s.equals(this.first) && this.rest.isDoubledList();
  }

  // determines if the given list is a palindrome list
  public boolean isPalindromeList() {
    return this.reverse().sameList(this);
  }

  // determines if the given list is the same as the given list
  public boolean sameList(ILoString that) {
    return that.sameListHelp(this.first, this.rest);
  }

  // helper for the sameList method
  public boolean sameListHelp(String s, ILoString that) {
    return s.equals(this.first) && that.sameList(this.rest);
  }
}

// to represent examples for lists of strings
class ExamplesStrings {

  ILoString mary = new ConsLoString("Mary ",
      new ConsLoString("had ",
          new ConsLoString("a ",
              new ConsLoString("little ",
                  new ConsLoString("lamb.", new MtLoString())))));

  ILoString interesting = new ConsLoString("interesting ",
      new ConsLoString("INTERESTING ",
          new ConsLoString("interesting ", new MtLoString())));

  ILoString sorted = new ConsLoString("a ",
      new ConsLoString("had ",
          new ConsLoString("lamb.",
              new ConsLoString("little ",
                  new ConsLoString("Mary ", new MtLoString())))));

  ILoString partOfDoubledList = new ConsLoString("a ",
      new ConsLoString("b ",
          new ConsLoString("b ",
              new ConsLoString("c ",
                  new ConsLoString("c ", new MtLoString())))));
  ILoString doubledList = new ConsLoString("a ", partOfDoubledList);

  ILoString palin = new ConsLoString("a ",
      new ConsLoString("b ",
          new ConsLoString("c ",
              new ConsLoString("c ",
                  new ConsLoString("b ",
                      new ConsLoString("a ", new MtLoString()))))));

  // test the method combine for the lists of Strings
  boolean testCombine(Tester t) {
    return
        t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
  }

  // test the method for sorting
  boolean testSort(Tester t) {
    return
        t.checkExpect(this.mary.sort(), this.sorted) &&
            t.checkExpect(this.interesting.sort(),
                new ConsLoString("interesting ",
                    new ConsLoString("INTERESTING ",
                        new ConsLoString("interesting ", new MtLoString()))));
  }

  // test the method for inserting
  boolean testInsert(Tester t) {
    return
        t.checkExpect(this.mary.insert("a "), new ConsLoString("a ", this.mary)) &&
            t.checkExpect(this.interesting.insert("interesting "),
                new ConsLoString("interesting ",
                    new ConsLoString("interesting ",
                        new ConsLoString("INTERESTING ",
                            new ConsLoString("interesting ", new MtLoString())))));
  }

  // test the method for isSorted
  boolean testIsSorted(Tester t) {
    return
        t.checkExpect(this.mary.isSorted(), false) &&
            t.checkExpect(this.interesting.isSorted(), true) &&
            t.checkExpect(this.sorted.isSorted(), true);
  }

  // test the method for isSortedHelp
  boolean testIsSortedHelp(Tester t) {
    return
        t.checkExpect(this.mary.isSortedHelp("a"), false) &&
            t.checkExpect(this.interesting.isSortedHelp("interesting"), true) &&
            t.checkExpect(this.sorted.isSortedHelp("a"), true);
  }

  // test the method for interleave
  boolean testInterleave(Tester t) {
    return
        t.checkExpect(this.mary.interleave(this.interesting),
            new ConsLoString("Mary ",
                new ConsLoString("interesting ",
                    new ConsLoString("had ",
                        new ConsLoString("INTERESTING ",
                            new ConsLoString("a ",
                                new ConsLoString("interesting ",
                                    new ConsLoString("little ",
                                        new ConsLoString("lamb.",
                                            new MtLoString())))))))));
  }

  // test the method for merge
  boolean testMerge(Tester t) {
    return
        t.checkExpect(this.mary.merge(this.interesting),
            new ConsLoString("a ",
                new ConsLoString("had ",
                    new ConsLoString("interesting ",
                        new ConsLoString("INTERESTING ",
                            new ConsLoString("interesting ",
                                new ConsLoString("lamb.",
                                    new ConsLoString("little ",
                                        new ConsLoString("Mary ",
                                            new MtLoString()))))))))) &&
            t.checkExpect(this.mary.merge(new MtLoString()), sorted);
  }

  // test the method for mergeHelp
  boolean testMergeHelp(Tester t) {
    return
        t.checkExpect(this.mary.mergeHelp(this.interesting),
            new ConsLoString("a ",
                new ConsLoString("had ",
                    new ConsLoString("interesting ",
                        new ConsLoString("INTERESTING ",
                            new ConsLoString("interesting ",
                                new ConsLoString("lamb.",
                                    new ConsLoString("little ",
                                        new ConsLoString("Mary ",
                                            new MtLoString()))))))))) &&
            t.checkExpect(this.mary.mergeHelp(new MtLoString()), sorted);
  }

  // test the method for reverse
  boolean testReverse(Tester t) {
    return
        t.checkExpect(this.mary.reverse(),
            new ConsLoString("lamb.",
                new ConsLoString("little ",
                    new ConsLoString("a ",
                        new ConsLoString("had ",
                            new ConsLoString("Mary ", new MtLoString())))))) &&
            t.checkExpect(this.interesting.reverse(),
                new ConsLoString("interesting ",
                    new ConsLoString("INTERESTING ",
                        new ConsLoString("interesting ", new MtLoString())))) &&
            t.checkExpect(this.palin.reverse(), this.palin);
  }

  // test the method for reverseHelp
  boolean testReverseHelp(Tester t) {
    return
        t.checkExpect(this.mary.reverseHelp(new MtLoString()),
            new ConsLoString("lamb.",
                new ConsLoString("little ",
                    new ConsLoString("a ",
                        new ConsLoString("had ",
                            new ConsLoString("Mary ", new MtLoString())))))) &&
            t.checkExpect(this.interesting.reverseHelp(new MtLoString()),
                new ConsLoString("interesting ",
                    new ConsLoString("INTERESTING ",
                        new ConsLoString("interesting ", new MtLoString())))) &&
            t.checkExpect(this.palin.reverseHelp(new MtLoString()), this.palin);
  }

  // test the method for isDoubledList
  boolean testIsDoubledList(Tester t) {
    return
        t.checkExpect(this.mary.isDoubledList(), false) &&
            t.checkExpect(doubledList.isDoubledList(), true) &&
            t.checkExpect(this.interesting.isDoubledList(), false);
  }

  // test the method for isDoubledListHelp
  boolean testIsDoubledListHelp(Tester t) {
    return
        t.checkExpect(this.mary.isDoubledListHelp("a "), false) &&
            t.checkExpect(partOfDoubledList.isDoubledListHelp("a "), true);
  }

  // test the method for isPalindromeList
  boolean testIsPalindromeList(Tester t) {
    return
        t.checkExpect(this.mary.isPalindromeList(), false) &&
            t.checkExpect(this.palin.isPalindromeList(), true);
  }

  // test sameList
  boolean testSameList(Tester t) {
    return
        t.checkExpect(this.mary.sameList(this.mary), true) &&
            t.checkExpect(this.mary.sameList(this.interesting), false) &&
            t.checkExpect(this.mary.sameList(this.sorted), false);
  }

  // test sameListHelp
  boolean testSameListHelp(Tester t) {
    return
        t.checkExpect(this.doubledList.sameListHelp("a ", this.partOfDoubledList), true) &&
            t.checkExpect(this.mary.sameListHelp("a ", this.interesting), false);
  }


}