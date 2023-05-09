interface IMode {}

//represents a study mode
class Study implements IMode {
  String subject;
  boolean examReview;

  // the constructor
  Study(String subject, boolean examReview) {
    this.subject = subject;
    this.examReview = examReview;
  }
  /* TEMPLATE:
     Fields:
     ... this.subject ...              -- String
     ... this.examReview ...           -- boolean
  */
}

//represents a socialize mode
class Socialize implements IMode {
  String description;
  int friends;

  // the constructor
  Socialize(String description, int friends) {
    this.description = description;
    this.friends = friends;
  }
  /* TEMPLATE:
     Fields:
     ... this.description ...       -- String
     ... this.friends ...           -- int
  */
}

//represents an exercise mode
class Exercise implements IMode {
  String name;
  boolean aerobic;

  // the constructor
  public Exercise(String name, boolean aerobic) {
    this.name = name;
    this.aerobic = aerobic;
  }
  /* TEMPLATE:
     Fields:
     ... this.name ...              -- String
     ... this.aerobic ...           -- boolean
  */
}

interface IPlace {}

//represents a classroom place location
class Classroom implements IPlace {
  String name;
  int roomCapacity;
  int occupantCount;

  // the constructor
  Classroom(String name, int roomCapacity, int occupantCount) {
    this.name = name;
    this.roomCapacity = roomCapacity;
    this.occupantCount = occupantCount;
  }
  /* TEMPLATE:
     Fields:
     ... this.name ...              -- String
     ... this.roomCapacity ...      -- int
     ... this.occupantCount ...     -- int
  */
}

//represents a gym place location
class Gym implements IPlace {
  String name;
  int exerciseMachines;
  int patrons;
  boolean open;

  // the constructor
  Gym(String name, int exerciseMachines, int patrons, boolean open) {
    this.name = name;
    this.exerciseMachines = exerciseMachines;
    this.patrons = patrons;
    this.open = open;
  }
  /* TEMPLATE:
     Fields:
     ... this.name ...              -- String
     ... this.exerciseMachines ...  -- int
     ... this.patrons ...           -- int
     ... this.open ...              -- boolean
  */
}

//represents a student center place location
class StudentCenter implements IPlace {
  String name;
  boolean open;

  // the constructor
  StudentCenter(String name, boolean open) {
    this.name = name;
    this.open = open;
  }
  /* TEMPLATE:
     Fields:
     ... this.name ...              -- String
     ... this.open ...              -- boolean
  */
}


//represents a student
class SimStudent {
  String name;
  IMode mode;
  IPlace location;
  double gpa;
  String major;

  // the constructor
  public SimStudent(String name, IMode mode, IPlace location, double gpa, String major) {
    this.name = name;
    this.mode = mode;
    this.location = location;
    this.gpa = gpa;
    this.major = major;
  }
  /* TEMPLATE:
     Fields:
     ... this.name ...              -- String
     ... this.mode ...              -- IMode
     ... this.location ...          -- IPlace
     ... this.gpa ...               -- double
     ... this.major ...             -- String
  */
}

// examples of places, activities, and students
class ExamplesSims {
  ExamplesSims() {}

  // examples of places
  IPlace shillman105 = new Classroom("Shillman 105", 115, 86);
  IPlace marino = new Gym("Marino Recreation Center", 78, 47, true);
  IPlace curry = new StudentCenter("Curry Student Center", false);
  IPlace khoury210b = new Classroom("West Village H 210B", 45, 32);
  IPlace squashBusters = new Gym("SquashBusters",10,30, true);
  IPlace hastings110 = new Classroom("Hastings 110", 30, 24);
  //examples of activities
  IMode biologyHomework = new Study("Biology", false);
  IMode chestPress = new Exercise("Chest Press", false);
  IMode popeyesMeetup = new Socialize("spending time at popeyes with friends", 5);
  IMode party = new Socialize("throwing a party in curry student center", 50);
  IMode running = new Exercise("Running", true);
  IMode csStudy = new Study("Computer Science", true);

  // examples of students
  SimStudent student1 = new SimStudent(
      "Peter Bidoshi",
      biologyHomework,
      khoury210b,
      4.0,
      "Computer Science and Biology"
  );
  SimStudent student2 = new SimStudent(
      "Rodrigo Aldrey",
      chestPress,
      marino,
      4.0,
      "Computer Science and Business"
  );
  SimStudent student3 = new SimStudent(
      "Kevin Xu",
      popeyesMeetup,
      curry,
      3.1,
      "Computer Science"
  );
  SimStudent student4 = new SimStudent(
      "Kaamil Thobani",
      party,
      curry,
      3.9,
      "Computer Science"
  );
  SimStudent student5 = new SimStudent(
      "Josh Kung",
      running,
      squashBusters,
      3.9,
      "Computer Science and Business"
  );
  SimStudent student6 = new SimStudent(
      "Troy Caron",
      csStudy,
      shillman105,
      2.0,
      "Computer Science"
  );
}
