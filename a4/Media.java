package HW.a4;

import tester.Tester;

// a piece of media
interface IMedia {
  
  // is this media old?
  boolean isReallyOld();
  
  // are captions available in this language?
  boolean isCaptionAvailable(String language);
  
  // a string showing the proper display of the media
  String format();
}

// class that represents a media type
abstract class AMedia implements IMedia {
  String title;
  ILoString captionOptions;

  // constructor
  AMedia(String title, ILoString captionOptions) {
    this.title = title;
    this.captionOptions = captionOptions;
  }

  /* TEMPLATE
   * Fields:
   * ... this.title ... - String
   * ... this.captionOptions ... - ILoString
   *
   * Methods:
   * ... this.isReallyOld() ... - boolean
   * ... this.isCaptionAvailable(String) ... - boolean
   * ... this.format() ... - String
   *
   * Methods for Fields:
   * ... this.captionOptions.stringInList(String) ... - boolean
   */

  // is this media old?
  public boolean isReallyOld() {
    return false;
  }

  // are captions available in the given language for this media?
  public boolean isCaptionAvailable(String language) {
    return this.captionOptions.stringInList(language);
  }

  // produces a string to represent the media
  public abstract String format();
}

// represents a movie
class Movie extends AMedia {
  int year;

  // constructor
  Movie(String title, int year, ILoString captionOptions) {
    super(title, captionOptions);
    this.year = year;
  }

  /* TEMPLATE
   * Fields:
   * ... this.title ... - String
   * ... this.captionOptions ... - ILoString
   * ... this.year ... - int
   *
   * Methods:
   * ... this.isReallyOld() ... - boolean
   * ... this.isCaptionAvailable(String) ... - boolean
   * ... this.format() ... - String
   *
   * Methods for Fields:
   * ... this.captionOptions.stringInList(String) ... - boolean
   */

  // is this movie old?
  public boolean isReallyOld() {
    return this.year < 1930;
  }

  // produces a string to represent the movie
  public String format() {
    return this.title + " (" + this.year + ")";
  }
}

// represents a TV episode
class TVEpisode extends AMedia {
  String showName;
  int seasonNumber;
  int episodeOfSeason;

  // constructor
  TVEpisode(String title, String showName, int seasonNumber, int episodeOfSeason,
      ILoString captionOptions) {
    super(title, captionOptions);
    this.showName = showName;
    this.seasonNumber = seasonNumber;
    this.episodeOfSeason = episodeOfSeason;
  }

  /* TEMPLATE
   * Fields:
   * ... this.title ... - String
   * ... this.captionOptions ... - ILoString
   * ... this.showName ... - String
   * ... this.seasonNumber ... - int
   * ... this.episodeOfSeason ... - int
   *
   * Methods:
   * ... this.isReallyOld() ... - boolean
   * ... this.isCaptionAvailable(String) ... - boolean
   * ... this.format() ... - String
   *
   * Methods for Fields:
   * ... this.captionOptions.stringInList(String) ... - boolean
   */

  // produces a string to represent the TV episode
  public String format() {
    return this.showName + " " +
        this.seasonNumber + "." +
        this.episodeOfSeason + " - " +
        this.title;
  }
}

// represents a YouTube video
class YTVideo extends AMedia {

  String channelName;

  // constructor
  public YTVideo(String title, String channelName, ILoString captionOptions) {
    super(title, captionOptions);
    this.channelName = channelName;
  }

  /* TEMPLATE
   * Fields:
   * ... this.title ... - String
   * ... this.captionOptions ... - ILoString
   * ... this.channelName ... - String
   *
   * Methods:
   * ... this.isReallyOld() ... - boolean
   * ... this.isCaptionAvailable(String) ... - boolean
   * ... this.format() ... - String
   *
   * Methods for Fields:
   * ... this.captionOptions.stringInList(String) ... - boolean
   */

  // produces a string to represent the YouTube video
  public String format() {
    return this.title + " by " + this.channelName;
  }
}

// lists of strings
interface ILoString {
  // is this string in this list?
  boolean stringInList(String s);
}

// an empty list of strings
class MtLoString implements ILoString {

  // constructor (empty)
  MtLoString() {}

  /* TEMPLATE
   * Fields:
   * ... NONE ...
   *
   * Methods:
   * ... this.stringInList(String) ... - boolean
   *
   * Methods for Fields:
   * ... NONE ...
   */

  // is this string in this empty list?
  public boolean stringInList(String s) {
    return false;
  }
}

// a non-empty list of strings
class ConsLoString implements ILoString {

  String first;
  ILoString rest;

  // constructor
  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  /* TEMPLATE
   * Fields:
   * ... this.first ... - String
   * ... this.rest ... - ILoString
   *
   * Methods:
   * ... this.stringInList(String) ... - boolean
   *
   * Methods for Fields:
   * ... this.rest.stringInList(String) ... - boolean
   */

  // is this string in this non-empty list?
  public boolean stringInList(String s) {
    return this.first.equalsIgnoreCase(s) || this.rest.stringInList(s);
  }
}

class ExamplesMedia {

  // constructor (empty)
  ExamplesMedia() {}

  // Examples of media
  IMedia matrix = new Movie("The Matrix", 1999,
      new ConsLoString("English",
          new ConsLoString("French", new MtLoString())));

  IMedia shrek = new Movie("Shrek", 2001,
      new ConsLoString("English",
          new ConsLoString("Albanian",
              new ConsLoString("Spanish",
                  new MtLoString()))));

  IMedia metropolis = new Movie("Metropolis", 1927,
      new ConsLoString("English", new MtLoString()));

  IMedia friendsSeason1Ep1 = new TVEpisode("The One with the Sonogram at the End", "Friends", 1, 1,
      new ConsLoString("English",
          new ConsLoString("Spanish",
              new MtLoString())));

  IMedia friendsSeason1Ep2 = new TVEpisode("The One with the Thumb", "Friends", 1, 2,
      new ConsLoString("English",
          new ConsLoString("Spanish",
              new MtLoString())));

  IMedia mrBeastVideo = new YTVideo(
      "1,000 Blind People See For The First Time",
      "MrBeast",
      new ConsLoString("English",
          new ConsLoString("French",
              new ConsLoString("Spanish",
                  new MtLoString()))));

  IMedia mrPeterssVideo = new YTVideo(
      "How I won $10,000 by killing a pig (MrBeast Challenge)",
      "MrPeterss",
      new ConsLoString("English", new MtLoString()));

  ILoString empty = new MtLoString();
  ILoString eng = new ConsLoString("English", new MtLoString());
  ILoString engFre = new ConsLoString("English",
      new ConsLoString("French",
          new MtLoString()));
  ILoString engFreSpa = new ConsLoString("English",
      new ConsLoString("French",
          new ConsLoString("Spanish",
              new MtLoString())));
  ILoString engSpa = new ConsLoString("English",
      new ConsLoString("Spanish",
          new MtLoString()));
  ILoString fre = new ConsLoString("French", new MtLoString());


  // Tests for isReallyOld
  boolean testIsReallyOld(Tester t) {
    return t.checkExpect(this.matrix.isReallyOld(), false) &&
        t.checkExpect(this.metropolis.isReallyOld(), true) &&
        t.checkExpect(this.friendsSeason1Ep1.isReallyOld(), false) &&
        t.checkExpect(this.friendsSeason1Ep2.isReallyOld(), false) &&
        t.checkExpect(this.mrBeastVideo.isReallyOld(), false) &&
        t.checkExpect(this.mrPeterssVideo.isReallyOld(), false);
  }

  // Tests for isCaptionAvailable
  boolean testIsCaptionAvailable(Tester t) {
    return t.checkExpect(this.matrix.isCaptionAvailable("French"), true) &&
        t.checkExpect(this.matrix.isCaptionAvailable("Spanish"), false) &&
        t.checkExpect(this.friendsSeason1Ep2.isCaptionAvailable("English"), true) &&
        t.checkExpect(this.friendsSeason1Ep2.isCaptionAvailable("French"), false) &&
        t.checkExpect(this.mrPeterssVideo.isCaptionAvailable("English"), true) &&
        t.checkExpect(this.mrPeterssVideo.isCaptionAvailable("French"), false);
  }

  // Tests for format
  boolean testFormat(Tester t) {
    return t.checkExpect(this.matrix.format(), "The Matrix (1999)") &&
        t.checkExpect(this.metropolis.format(), "Metropolis (1927)") &&
        t.checkExpect(this.friendsSeason1Ep1.format(),
            "Friends 1.1 - The One with the Sonogram at the End") &&
        t.checkExpect(this.friendsSeason1Ep2.format(),
            "Friends 1.2 - The One with the Thumb") &&
        t.checkExpect(this.mrBeastVideo.format(),
            "1,000 Blind People See For The First Time by MrBeast") &&
        t.checkExpect(this.mrPeterssVideo.format(),
            "How I won $10,000 by killing a pig (MrBeast Challenge) by MrPeterss");
  }

  // Tests for stringInList
  boolean testStringInList(Tester t) {
    return t.checkExpect(this.eng.stringInList("English"), true) &&
        t.checkExpect(this.eng.stringInList("French"), false) &&
        t.checkExpect(this.engFre.stringInList("English"), true) &&
        t.checkExpect(this.engFre.stringInList("French"), true) &&
        t.checkExpect(this.engFre.stringInList("Spanish"), false) &&
        t.checkExpect(this.engFreSpa.stringInList("English"), true) &&
        t.checkExpect(this.engFreSpa.stringInList("French"), true) &&
        t.checkExpect(this.engFreSpa.stringInList("Spanish"), true) &&
        t.checkExpect(this.engSpa.stringInList("English"), true) &&
        t.checkExpect(this.engSpa.stringInList("French"), false) &&
        t.checkExpect(this.engSpa.stringInList("Spanish"), true) &&
        t.checkExpect(this.fre.stringInList("English"), false) &&
        t.checkExpect(this.fre.stringInList("French"), true) &&
        t.checkExpect(this.fre.stringInList("Spanish"), false);
  }
}
