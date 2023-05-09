package HW.a1;

interface IAppSet {}

//represents a Folder of apps with a title
class Folder implements IAppSet {
  String title;

  Folder(String title) {
    this.title = title;
  }
  /* TEMPLATE:
     Fields:
     ... this.title ...         -- String
  */
}

//represents an app and the others associated in the same folder
class Apps implements IAppSet {
  String appName;
  IAppSet others;

  public Apps(String appName, IAppSet others) {
    this.appName = appName;
    this.others = others;
  }
  /* TEMPLATE:
     Fields:
     ... this.appName ...         -- String
     ... this.others ...          -- IAppSet
  */
}

class ExamplesSets {
  Folder travel = new Folder("Travel");
  IAppSet uber = new Apps("Uber", travel);
  IAppSet mTickets = new Apps("mTicket", uber);
  IAppSet moovit = new Apps("Moovit", mTickets);
  IAppSet travelApps = new Apps("Orbitz", moovit);

  Folder food = new Folder("Food");
  IAppSet grubhub = new Apps("Grubhub",food);
  IAppSet bgood = new Apps("B. Good", grubhub);
  IAppSet foodApps = new Apps("Gong Cha", bgood);
}