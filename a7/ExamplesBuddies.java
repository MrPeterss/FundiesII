package HW.a7;

import tester.Tester;

// runs tests for the buddies problem
class ExamplesBuddies {

  Person ann;
  Person bob;
  Person cole;
  Person dan;
  Person ed;
  Person fay;
  Person gabi;
  Person hank;
  Person jan;
  Person kim;
  Person len;

  void initBuddies() {
    ann = new Person("Ann");
    bob = new Person("Bob");
    cole = new Person("Cole");
    dan = new Person("Dan");
    ed = new Person("Ed");
    fay = new Person("Fay");
    gabi = new Person("Gabi");
    hank = new Person("Hank");
    jan = new Person("Jan");
    kim = new Person("Kim");
    len = new Person("Len");
    ann.addBuddy(bob);
    ann.addBuddy(cole);
    bob.addBuddy(ann);
    bob.addBuddy(ed);
    bob.addBuddy(hank);
    cole.addBuddy(dan);
    dan.addBuddy(cole);
    ed.addBuddy(fay);
    fay.addBuddy(ed);
    fay.addBuddy(gabi);
    gabi.addBuddy(ed);
    gabi.addBuddy(fay);
    jan.addBuddy(kim);
    jan.addBuddy(len);
    kim.addBuddy(jan);
    kim.addBuddy(len);
    len.addBuddy(jan);
    len.addBuddy(kim);
  }

  void testAddBuddy(Tester t) {
    this.initBuddies();
    Person joey = new Person("Joey");
    t.checkExpect(joey.buddies, new MTLoBuddy());
    joey.addBuddy(ann);
    t.checkExpect(joey.buddies, new ConsLoBuddy(ann, new MTLoBuddy()));
    t.checkExpect(ann.buddies, new ConsLoBuddy(cole, new ConsLoBuddy(bob, new MTLoBuddy())));
    ann.addBuddy(dan);
    t.checkExpect(ann.buddies, new ConsLoBuddy(dan, new ConsLoBuddy(cole, new ConsLoBuddy(bob,
        new MTLoBuddy()))));
    t.checkExpect(bob.buddies, new ConsLoBuddy(hank, new ConsLoBuddy(ed, new ConsLoBuddy(ann,
        new MTLoBuddy()))));
    bob.addBuddy(fay);
    t.checkExpect(bob.buddies, new ConsLoBuddy(fay, new ConsLoBuddy(hank, new ConsLoBuddy(ed,
        new ConsLoBuddy(ann, new MTLoBuddy())))));
  }

  void testIsSamePerson(Tester t) {
    this.initBuddies();
    t.checkExpect(ann.isSamePerson(ann), true);
    t.checkExpect(ann.isSamePerson(bob), false);
    t.checkExpect(bob.isSamePerson(ann), false);
    t.checkExpect(bob.isSamePerson(bob), true);
  }

  void testHasDirectBuddy(Tester t) {
    this.initBuddies();
    t.checkExpect(ann.hasDirectBuddy(bob), true);
    t.checkExpect(ann.hasDirectBuddy(dan), false);
    t.checkExpect(ed.hasDirectBuddy(gabi), false);
    t.checkExpect(hank.hasDirectBuddy(ann), false);
  }

  void testPartyCount(Tester t) {
    this.initBuddies();
    t.checkExpect(ann.partyCount(), 8);
    t.checkExpect(jan.partyCount(), 3);
    t.checkExpect(hank.partyCount(), 1);
    t.checkExpect(dan.partyCount(), 2);
  }

  void testGetParty(Tester t) {
    this.initBuddies();
    t.checkExpect(ann.getParty(), new ConsLoBuddy(gabi, new ConsLoBuddy(fay, new ConsLoBuddy(ed,
        new ConsLoBuddy(hank, new ConsLoBuddy(bob, new ConsLoBuddy(dan, new ConsLoBuddy(cole,
            new ConsLoBuddy(ann, new MTLoBuddy())))))))));
    t.checkExpect(jan.getParty(), new ConsLoBuddy(kim, new ConsLoBuddy(len, new ConsLoBuddy(jan,
        new MTLoBuddy()))));
    t.checkExpect(hank.getParty(), new ConsLoBuddy(hank, new MTLoBuddy()));
    t.checkExpect(dan.getParty(), new ConsLoBuddy(cole, new ConsLoBuddy(dan, new MTLoBuddy())));
    t.checkExpect(hank.getParty(new ConsLoBuddy(ann, new MTLoBuddy())), new ConsLoBuddy(hank,
        new ConsLoBuddy(ann, new MTLoBuddy())));
    t.checkExpect(ann.getParty(new MTLoBuddy()), new ConsLoBuddy(gabi, new ConsLoBuddy(fay,
        new ConsLoBuddy(ed, new ConsLoBuddy(hank, new ConsLoBuddy(bob, new ConsLoBuddy(dan,
            new ConsLoBuddy(cole, new ConsLoBuddy(ann, new MTLoBuddy())))))))));
  }

  void testCountCommonBuddies(Tester t) {
    this.initBuddies();
    t.checkExpect(ann.countCommonBuddies(bob), 0);
    t.checkExpect(kim.countCommonBuddies(len), 1);
    t.checkExpect(ed.countCommonBuddies(gabi), 1);
  }

  void testHasExtendedBuddy(Tester t) {
    this.initBuddies();
    t.checkExpect(ann.hasExtendedBuddy(bob), true);
    t.checkExpect(ann.hasExtendedBuddy(dan), true);
    t.checkExpect(ann.hasExtendedBuddy(jan), false);
    t.checkExpect(ann.hasExtendedBuddy(bob, new MTLoBuddy()), true);
    t.checkExpect(ann.hasExtendedBuddy(dan, new ConsLoBuddy(ann, new MTLoBuddy())), true);
  }

  void testContains(Tester t) {
    this.initBuddies();
    t.checkExpect(new ConsLoBuddy(ann, new MTLoBuddy()).contains(ann), true);
    t.checkExpect(new ConsLoBuddy(ann, new MTLoBuddy()).contains(bob), false);
    t.checkExpect(new ConsLoBuddy(ann, new ConsLoBuddy(bob, new MTLoBuddy())).contains(ann),
        true);
    t.checkExpect(new ConsLoBuddy(ann, new ConsLoBuddy(bob, new MTLoBuddy())).contains(bob),
        true);
    t.checkExpect(new ConsLoBuddy(ann, new ConsLoBuddy(bob, new MTLoBuddy())).contains(cole),
        false);
  }

  void testCountCommonBuddiesList(Tester t) {
    this.initBuddies();
    t.checkExpect(ann.buddies.countCommonBuddies(new ConsLoBuddy(bob, new MTLoBuddy())),
        1);
    t.checkExpect(ann.buddies.countCommonBuddies(new ConsLoBuddy(bob, new ConsLoBuddy(dan,
        new ConsLoBuddy(cole, new ConsLoBuddy(ann, new MTLoBuddy()))))), 2);
    t.checkExpect(ann.buddies.countCommonBuddies(new ConsLoBuddy(bob, new ConsLoBuddy(dan,
        new ConsLoBuddy(cole, new ConsLoBuddy(ann, new ConsLoBuddy(bob, new MTLoBuddy())))))),
        2);
    t.checkExpect(ann.buddies.countCommonBuddies(new ConsLoBuddy(hank, new MTLoBuddy())), 0);
  }

  void testCountCommonBuddiesHelpList(Tester t) {
    this.initBuddies();
    t.checkExpect(ann.buddies.countCommonBuddiesHelp(new ConsLoBuddy(bob, new MTLoBuddy()),
            0), 1);
    t.checkExpect(ann.buddies.countCommonBuddiesHelp(new ConsLoBuddy(bob, new ConsLoBuddy(dan,
        new ConsLoBuddy(cole, new ConsLoBuddy(ann, new MTLoBuddy())))), 0), 2);
  }

  void testIsConnectedTo(Tester t) {
    this.initBuddies();
    t.checkExpect(ann.buddies.isConnectedTo(bob, new MTLoBuddy()), true);
    t.checkExpect(ann.buddies.isConnectedTo(bob, new ConsLoBuddy(ann, new MTLoBuddy())), true);
    t.checkExpect(ann.buddies.isConnectedTo(bob, new ConsLoBuddy(bob, new MTLoBuddy())), false);
    t.checkExpect(ann.buddies.isConnectedTo(bob, new ConsLoBuddy(dan, new MTLoBuddy())), true);
  }

  void testGetPartyList(Tester t) {
    this.initBuddies();
    t.checkExpect(ann.buddies.getPartyList(new ConsLoBuddy(ann, new MTLoBuddy())),
        new ConsLoBuddy(gabi, new ConsLoBuddy(fay, new ConsLoBuddy(ed,
            new ConsLoBuddy(hank, new ConsLoBuddy(bob, new ConsLoBuddy(dan, new ConsLoBuddy(cole,
                new ConsLoBuddy(ann, new MTLoBuddy())))))))));
    t.checkExpect(hank.buddies.getPartyList(new ConsLoBuddy(kim, new MTLoBuddy())),
        new ConsLoBuddy(kim, new MTLoBuddy()));
    t.checkExpect(cole.buddies.getPartyList(new ConsLoBuddy(ann, new MTLoBuddy())),
        new ConsLoBuddy(cole, new ConsLoBuddy(dan, new ConsLoBuddy(ann, new MTLoBuddy()))));
  }

  void testCount(Tester t) {
    this.initBuddies();
    t.checkExpect(ann.buddies.count(), 2);
    t.checkExpect(hank.buddies.count(), 0);
    t.checkExpect(cole.buddies.count(), 1);
    t.checkExpect(new ConsLoBuddy(ann, new ConsLoBuddy(bob, new ConsLoBuddy(cole,
        new ConsLoBuddy(dan, new MTLoBuddy())))).count(), 4);
    t.checkExpect(new ConsLoBuddy(ann, new ConsLoBuddy(bob, new ConsLoBuddy(cole,
        new ConsLoBuddy(dan, new ConsLoBuddy(ann, new MTLoBuddy()))))).count(), 5);
    t.checkExpect(new MTLoBuddy().count(), 0);
  }

  void testCountHelp(Tester t) {
    this.initBuddies();
    t.checkExpect(ann.buddies.countHelp(4), 6);
    t.checkExpect(hank.buddies.countHelp(0), 0);
    t.checkExpect(cole.buddies.countHelp(0), 1);
    t.checkExpect(new ConsLoBuddy(ann, new ConsLoBuddy(bob, new ConsLoBuddy(cole,
        new ConsLoBuddy(dan, new MTLoBuddy())))).countHelp(3), 7);
  }
}