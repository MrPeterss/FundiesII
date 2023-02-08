package HW.a4.copy;

import tester.Tester;

// a piece of media
interface IMedia {
  
  // is this media really old?
  boolean isReallyOld();
  
  // are captions available in this language?
  boolean isCaptionAvailable(String language);
  
  // a string showing the proper display of the media
  String format();
}

// represents a movie
class Movie implements IMedia {
  String title;
  int year;
  ILoString captionOptions; // available captions
  
  Movie(String title, int year, ILoString captionOptions) {
    this.title = title;
    this.year = year;
    this.captionOptions = captionOptions;
  }
  
  public boolean isReallyOld() {
    if (this.year < 1930) {
      return true;
    }
    else {
      return false;
    }
  }
  
  public boolean isCaptionAvailable(String language) {
    return this.captionOptions.stringInList(language);
  }
  
  public String format() {
    return this.title + " (" + this.year + ")";
  }
}

// represents a TV episode
class TVEpisode implements IMedia {
  String title;
  String showName;
  int seasonNumber;
  int episodeOfSeason;
  ILoString captionOptions; // available captions

  TVEpisode(String title, String showName, int seasonNumber, int episodeOfSeason,
      ILoString captionOptions) {
    this.title = title;
    this.showName = showName;
    this.seasonNumber = seasonNumber;
    this.episodeOfSeason = episodeOfSeason;
    this.captionOptions = captionOptions;
  }

  public boolean isReallyOld() {
    return false;
  }

  public boolean isCaptionAvailable(String language) {
    return this.captionOptions.stringInList(language);
  }

  public String format() {
    return this.showName + " " +
        this.seasonNumber + "." +
        this.episodeOfSeason + " - " +
        this.title;
  }
}

// represents a YouTube video
class YTVideo implements IMedia {
  String title;
  String channelName;
  ILoString captionOptions; // available captions
  
  public YTVideo(String title, String channelName, ILoString captionOptions) {
    this.title = title;
    this.channelName = channelName;
    this.captionOptions = captionOptions;
  }
  
  public boolean isReallyOld() {
    return false;
  }
  
  public boolean isCaptionAvailable(String language) {
    return this.captionOptions.stringInList(language);
  }
  
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
  public boolean stringInList(String s) {
    return false;
  }
}

// a non-empty list of strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;
  
  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  public boolean stringInList(String s) {
    return this.first.equalsIgnoreCase(s) || this.rest.stringInList(s);
  }
}

class ExamplesMedia {
  Movie m1 = new Movie("The Matrix", 1999, new ConsLoString("English",
      new ConsLoString("French", new MtLoString())));
  Movie m2 = new Movie("Shrek", 2001,
      new ConsLoString("English", new MtLoString()));
  Movie mOld = new Movie("Metropolis", 1927,
      new ConsLoString("English", new MtLoString()));

  TVEpisode t1 = new TVEpisode("The One with the Sonogram at the End", "Friends", 1, 1,
      new ConsLoString("English", new MtLoString()));
  TVEpisode t2 = new TVEpisode("The One with the Thumb", "Friends", 1, 2,
      new ConsLoString("English", new MtLoString()));

  YTVideo y1 = new YTVideo(
      "1,000 Blind People See For The First Time",
      "MrBeast",
      new ConsLoString("English", new MtLoString()));
  YTVideo y2 = new YTVideo(
      "How I won $10,000 by killing a pig (MrBeast Challenge)",
      "MrPeterss",
      new ConsLoString("English", new MtLoString()));

  boolean testIsReallyOld(Tester t) {
    return t.checkExpect(this.m1.isReallyOld(), false) &&
        t.checkExpect(this.mOld.isReallyOld(), true) &&
        t.checkExpect(this.t1.isReallyOld(), false) &&
        t.checkExpect(this.y1.isReallyOld(), false);
  }

  boolean testIsCaptionAvailable(Tester t) {
    return t.checkExpect(this.m1.isCaptionAvailable("English"), true) &&
        t.checkExpect(this.m1.isCaptionAvailable("French"), true) &&
        t.checkExpect(this.m1.isCaptionAvailable("Spanish"), false) &&
        t.checkExpect(this.t1.isCaptionAvailable("English"), true) &&
        t.checkExpect(this.t1.isCaptionAvailable("French"), false) &&
        t.checkExpect(this.y1.isCaptionAvailable("English"), true) &&
        t.checkExpect(this.y1.isCaptionAvailable("French"), false);
  }

  boolean testFormat(Tester t) {
    return t.checkExpect(this.m1.format(), "The Matrix (1999)") &&
        t.checkExpect(this.t1.format(), "Friends 1.1 - The One with the Sonogram at the End") &&
        t.checkExpect(this.y1.format(), "1,000 Blind People See For The First Time by MrBeast");
  }
}
