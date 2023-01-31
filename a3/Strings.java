package HW.a3;
// CS 2510, Assignment 3

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
    boolean goesAfter(String s);
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
    MtLoString(){}

    /*
     TEMPLATE
     FIELDS:
     ... NONE ...

     METHODS
     ... this.combine() ...     -- String
     ... this.sort() ...        -- ILoString
     ... this.insert(String) ...-- ILoString

     METHODS FOR FIELDS
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

    // checks if the given string comes before the given list
    public boolean goesAfter(String s) {
        return true;
    }
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
    String first;
    ILoString rest;
    
    ConsLoString(String first, ILoString rest){
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
     
     METHODS FOR FIELDS
     ... this.first.concat(String) ...        -- String
     ... this.first.compareTo(String) ...     -- int
     ... this.rest.combine() ...              -- String
     */
    
    // combine all Strings in this list into one
    public String combine(){
        return this.first.concat(this.rest.combine());
    }

    // sort this list of strings
    public ILoString sort() {
        return this.rest.sort().insert(this.first);
    }

    // insert the given string into this list of strings
    public ILoString insert(String s) {
        if (s.toLowerCase().compareTo(this.first.toLowerCase()) <= 0)
            return new ConsLoString(s, this);
        else
            return new ConsLoString(this.first, this.rest.insert(s));
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
        if (that.goesAfter(this.first)) {
            return new ConsLoString(this.first, this.rest.merge(that));
        } else {
            return that.merge(this);
        }
    }

    // checks if the given string comes before the given list
    public boolean goesAfter(String s) {
        return s.toLowerCase().compareTo(this.first.toLowerCase()) >= 0;
    }
}

// to represent examples for lists of strings
class ExamplesStrings{

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
    
    // test the method combine for the lists of Strings
    boolean testCombine(Tester t){
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
                                                new MtLoString())))))))));
    }
}