package HW.a2;

import tester.Tester;

// represents an XML Document
interface IXML {
  // computes the content length of an XML document
  int contentLength();

  // determines if this piece of xml contains a tag with the given name
  boolean hasTag(String name);

  // determines if this piece of xml contains an attribute with the given name
  boolean hasAttribute(String name);

  // renders this piece of xml as a string
  String renderAsString();

}

// represents plain text in an XML element
class Plaintext implements IXML {
  String txt;

  Plaintext(String txt) {
    this.txt = txt;
  }

  /* TEMPLATE
   FIELDS:
   ... this.text ...                  -- String
   METHODS
   ... this.contentLength() ...       -- int
   ... this.hasTag(String) ...        -- boolean
   ... this.hasAttribute(String) ...  -- boolean
   ... this.renderAsString() ...      -- String
  */

  // computes the content length of the plain text
  public int contentLength() {
    return this.txt.length();
  }

  // determines if this piece of xml has the given tag (it doesn't)
  public boolean hasTag(String name) {
    return false;
  }

  // determines if this piece of xml has the given attribute (it doesn't)
  public boolean hasAttribute(String name) {
    return false;
  }

  // renders this piece of xml as a string
  public String renderAsString() {
    return this.txt;
  }
}

// represents an untagged XML element
class Untagged implements IXML {
  ILoXML content;

  Untagged(ILoXML content) {
    this.content = content;
  }

  /* TEMPLATE
   FIELDS:
    ... this.content ...                        -- ILoXML
   METHODS
    ... this.contentLength() ...                -- int
    ... this.hasTag(String) ...                 -- boolean
    ... this.hasAttribute(String) ...           -- boolean
    ... this.renderAsString() ...               -- String
   METHODS FOR FIELDS
    ... this.content.contentLength() ...       -- int
    ... this.content.hasTag(String) ...        -- boolean
    ... this.content.hasAttribute(String) ...  -- boolean
    ... this.content.renderAsString() ...      -- String
  */


  // computes the content length of the untagged XML elements
  public int contentLength() {
    return this.content.contentLength();
  }

  // determines if this piece of xml has the given tag
  public boolean hasTag(String name) {
    return this.content.hasTag(name);
  }

  // determines if this piece of xml has the given attribute
  public boolean hasAttribute(String name) {
    return this.content.hasAttribute(name);
  }

  // renders this piece of xml as a string
  public String renderAsString() {
    return this.content.renderAsString();
  }
}

// represents a tagged XML element
class Tagged implements IXML {
  Tag tag;
  ILoXML content;

  Tagged(Tag tag, ILoXML content) {
    this.tag = tag;
    this.content = content;
  }

  /* TEMPLATE
   FIELDS:
    ... this.tag ...                            -- Tag
    ... this.content ...                       -- ILoXML
   METHODS
    ... this.contentLength() ...                -- int
    ... this.hasTag(String) ...                 -- boolean
    ... this.hasAttribute(String) ...           -- boolean
    ... this.renderAsString() ...               -- String
   METHODS FOR FIELDS
    ... this.tag.hasAttribute(String) ...       -- boolean
    ... this.tag.isTag(String) ...              -- boolean
    ... this.content.contentLength() ...       -- int
    ... this.content.hasTag(String) ...        -- boolean
    ... this.content.hasAttribute(String) ...  -- boolean
    ... this.content.renderAsString() ...      -- String
  */

  // determines if this piece of xml has the given tag
  public boolean hasTag(String name) {
    return this.tag.isTag(name) || this.content.hasTag(name);
  }

  // determines if this piece of xml has the given attribute
  public boolean hasAttribute(String name) {
    return this.tag.hasAttribute(name) || this.content.hasAttribute(name);
  }

  // renders this piece of xml as a string
  public String renderAsString() {
    return this.content.renderAsString();
  }

  // computes the content length of the tagged XML elements
  public int contentLength() {
    return this.content.contentLength();
  }
}

// represents a list of XML elements
interface ILoXML {
  // computes the content length of the list of XML elements
  int contentLength();

  // determines if the list of XML elements has the given tag
  boolean hasTag(String name);

  // determines if the list of XML elements has the given attribute
  boolean hasAttribute(String name);

  // renders the list of XML elements as a string
  String renderAsString();
}

// represents an empty list of XML elements
class MtLoXML implements ILoXML {

  MtLoXML() {}

  /* TEMPLATE
   FIELDS:
    NONE
   METHODS
    ... this.contentLength() ...                -- int
    ... this.hasTag(String) ...                 -- boolean
    ... this.hasAttribute(String) ...           -- boolean
    ... this.renderAsString() ...               -- String
   METHODS FOR FIELDS
    NONE
  */

  // determines if the empty list of XML elements has the given tag (it doesn't)
  public boolean hasTag(String name) {
    return false;
  }

  // determines if the empty list of XML elements has the given attribute (it doesn't)
  public boolean hasAttribute(String name) {
    return false;
  }

  // renders the empty list of XML elements as a string
  public String renderAsString() {
    return "";
  }

  // computes the content length of the empty list of XML elements
  public int contentLength() {
    return 0;
  }
}

// represents a non-empty list of XML elements
class ConsLoXML implements ILoXML {
  IXML first;
  ILoXML rest;

  ConsLoXML(IXML first, ILoXML rest) {
    this.first = first;
    this.rest = rest;
  }

  /* TEMPLATE
   FIELDS:
    ... this.first ...                          -- IXML
    ... this.rest ...                           -- ILoXML
   METHODS
    ... this.contentLength() ...                -- int
    ... this.hasTag(String) ...                 -- boolean
    ... this.hasAttribute(String) ...           -- boolean
    ... this.renderAsString() ...               -- String
   METHODS FOR FIELDS
    ... this.first.contentLength() ...          -- int
    ... this.first.hasTag(String) ...           -- boolean
    ... this.first.hasAttribute(String) ...     -- boolean
    ... this.first.renderAsString() ...         -- String
    ... this.rest.contentLength() ...           -- int
    ... this.rest.hasTag(String) ...            -- boolean
    ... this.rest.hasAttribute(String) ...      -- boolean
    ... this.rest.renderAsString() ...          -- String
  */

  // determines if the non-empty list of XML elements has the given tag
  public boolean hasTag(String name) {
    return this.first.hasTag(name) || this.rest.hasTag(name);
  }

  // determines if the non-empty list of XML elements has the given attribute
  public boolean hasAttribute(String name) {
    return this.first.hasAttribute(name) || this.rest.hasAttribute(name);
  }

  // renders the non-empty list of XML elements as a string
  public String renderAsString() {
    return this.first.renderAsString() + this.rest.renderAsString();
  }

  // computes the content length of the non-empty list of XML elements
  public int contentLength() {
    return this.first.contentLength() + this.rest.contentLength();
  }
}

// represents a tag
class Tag {
  String name;
  ILoAtt atts;

  Tag(String name, ILoAtt atts) {
    this.name = name;
    this.atts = atts;
  }

  /* TEMPLATE
   FIELDS:
    ... this.name ...                           -- String
    ... this.atts ...                           -- ILoAtt
   METHODS
    ... this.isTag(String) ...                  -- boolean
    ... this.hasAttribute(String) ...           -- boolean
   METHODS FOR FIELDS
    ... this.atts.hasAttribute(String) ...      -- boolean
  */

  // determines if this tag is the given tag name
  boolean isTag(String name) {
    return this.name.equals(name);
  }

  // determines if this tag has the given attribute
  boolean hasAttribute(String name) {
    return this.atts.hasAttribute(name);
  }

}

// represents a list of attributes
interface ILoAtt {
  // determines if the list of attributes has the given attribute
  boolean hasAttribute(String name);
}

// represents an empty list of attributes
class MtLoAtt implements ILoAtt {
  MtLoAtt() {}

  /* TEMPLATE
   FIELDS:
    NONE
   METHODS
    ... this.hasAttribute(String) ...           -- boolean
   METHODS FOR FIELDS
    NONE
  */

  // determines if the empty list of attributes has the given attribute (it doesn't)
  public boolean hasAttribute(String name) {
    return false;
  }
}

// represents a non-empty list of attributes
class ConsLoAtt implements ILoAtt {
  Att first;
  ILoAtt rest;

  ConsLoAtt(Att first, ILoAtt rest) {
    this.first = first;
    this.rest = rest;
  }

  /* TEMPLATE
   FIELDS:
    ... this.first ...                          -- Att
    ... this.rest ...                           -- ILoAtt
   METHODS
    ... this.hasAttribute(String) ...           -- boolean
   METHODS FOR FIELDS
    ... this.first.isAttribute(String) ...      -- boolean
    ... this.rest.hasAttribute(String) ...      -- boolean
  */

  // determines if the non-empty list of attributes has the given attribute
  public boolean hasAttribute(String name) {
    return this.first.isAttribute(name) || this.rest.hasAttribute(name);
  }
}

// represents an attribute
class Att {
  String name;
  String value;

  Att(String name, String value) {
    this.name = name;
    this.value = value;
  }

  /* TEMPLATE
   FIELDS:
    ... this.name ...                           -- String
    ... this.value ...                          -- String
   METHODS
    ... this.isAttribute(String) ...            -- boolean
   METHODS FOR FIELDS
    NONE
  */

  // determines if this attribute is the given attribute name
  boolean isAttribute(String name) {
    return this.name.equals(name);
  }
}

class ExamplesXML {
  ExamplesXML() {}

  // represents "I am XML!"
  IXML xml1 = new Plaintext("I am XML!");

  // represents "I am <yell>XML</yell>!"
  IXML xml2 = new Untagged(new ConsLoXML(new Plaintext("I am "),
      new ConsLoXML(new Tagged(new Tag("yell", new MtLoAtt()),
          new ConsLoXML(new Plaintext("XML"), new MtLoXML())),
          new ConsLoXML(new Plaintext("!"), new MtLoXML()))));

  // represents "I am <yell><italic>X</italic>ML</yell>!"
  IXML xml3 = new Untagged(
      new ConsLoXML(
          new Plaintext("I am "),
          new ConsLoXML(
              new Tagged(new Tag("yell", new MtLoAtt()),
                  new ConsLoXML(
                      new Tagged(
                          new Tag("italic", new MtLoAtt()),
                          new ConsLoXML(new Plaintext("X"), new MtLoXML())),
                      new ConsLoXML(new Plaintext("ML"), new MtLoXML()))),
              new ConsLoXML(new Plaintext("!"), new MtLoXML()))));

  // represents "I am <yell volume="30db"><italic>X</italic>ML</yell>!"
  IXML xml4 = new Untagged(
      new ConsLoXML(
          new Plaintext("I am "),
          new ConsLoXML(
              new Tagged(
                  new Tag("yell", new ConsLoAtt(new Att("volume", "30db"), new MtLoAtt())),
                  new ConsLoXML(
                      new Tagged(
                          new Tag("italic", new MtLoAtt()),
                          new ConsLoXML(new Plaintext("X"), new MtLoXML())),
                      new ConsLoXML(new Plaintext("ML"), new MtLoXML()))),
              new ConsLoXML(new Plaintext("!"), new MtLoXML()))));

  // represents "I am <yell volume="30db" duration="5sec"><italic>X</italic>ML</yell>!"
  IXML xml5 = new Untagged(new ConsLoXML(
      new Plaintext("I am "),
      new ConsLoXML(
          new Tagged(
              new Tag(
                  "yell",
                  new ConsLoAtt(
                      new Att("volume", "30db"),
                      new ConsLoAtt(
                          new Att("duration", "5sec"),
                          new MtLoAtt()))),
              new ConsLoXML(
                  new Tagged(
                      new Tag("italic", new MtLoAtt()),
                      new ConsLoXML(new Plaintext("X"), new MtLoXML())),
                  new ConsLoXML(new Plaintext("ML"), new MtLoXML()))),
          new ConsLoXML(new Plaintext("!"), new MtLoXML()))));

  // other examples:

  // represent a empty list of xml
  ILoXML mtLoXML = new MtLoXML();
  // represent a list of xml with "I am XML!" in it
  ILoXML consLoXML1 = new ConsLoXML(xml1, mtLoXML);
  // represent a list of xml with "I am XML!" in it untagged
  IXML untagged1 = new Untagged(consLoXML1);
  // represents an arbitrary size attribute
  Att att1 = new Att("size", "40px");
  // represents an empty list of attributes
  ILoAtt mtLoAtt = new MtLoAtt();
  // represents a list of attributes with "size" in it
  ILoAtt consLoAtt1 = new ConsLoAtt(att1, mtLoAtt);
  // represents a tag named "box" and "size" attribute in it
  Tag tag1 = new Tag("box", consLoAtt1);
  // represents a tagged xml with "box" tag and "I am XML!" in it
  IXML tagged1 = new Tagged(tag1, consLoXML1);
  // represents a list of xml with "I am XML!" in it tagged
  ILoXML consLoXML2 = new ConsLoXML(tagged1, mtLoXML);


  // tests for contentLength in IXML
  boolean testIXMLContentLength(Tester t) {
    return t.checkExpect(this.xml1.contentLength(), 9)
        && t.checkExpect(this.xml2.contentLength(), 9)
        && t.checkExpect(this.xml3.contentLength(), 9)
        && t.checkExpect(this.xml4.contentLength(), 9)
        && t.checkExpect(this.xml5.contentLength(), 9);
  }

  // tests for contentLength helper in ILoXML
  boolean testILoXMLContentLength(Tester t) {
    return t.checkExpect(this.mtLoXML.contentLength(), 0)
        && t.checkExpect(this.consLoXML1.contentLength(), 9);
  }

  // tests for hasTag in IXML
  boolean testIXMLHasTag(Tester t) {
    return t.checkExpect(this.xml1.hasTag("yell"), false)
        && t.checkExpect(this.xml2.hasTag("yell"), true)
        && t.checkExpect(this.xml3.hasTag("yell"), true)
        && t.checkExpect(this.xml4.hasTag("yell"), true)
        && t.checkExpect(this.xml5.hasTag("yell"), true)
        && t.checkExpect(this.xml1.hasTag("italic"), false)
        && t.checkExpect(this.xml2.hasTag("italic"), false)
        && t.checkExpect(this.xml3.hasTag("italic"), true)
        && t.checkExpect(this.xml4.hasTag("italic"), true)
        && t.checkExpect(this.xml5.hasTag("italic"), true);
  }

  // tests for hasTag helper in ILoXML
  boolean testILoXMLHasTag(Tester t) {
    return t.checkExpect(this.consLoXML2.hasTag("box"), true)
        && t.checkExpect(this.consLoXML2.hasTag("yell"), false)
        && t.checkExpect(this.consLoXML1.hasTag("box"), false);
  }

  // tests for hasAttribute in IXML
  boolean testIXMLHasAttribute(Tester t) {
    return t.checkExpect(this.xml1.hasAttribute("volume"), false)
      && t.checkExpect(this.xml2.hasAttribute("volume"), false)
      && t.checkExpect(this.xml3.hasAttribute("volume"), false)
      && t.checkExpect(this.xml4.hasAttribute("volume"), true)
      && t.checkExpect(this.xml5.hasAttribute("volume"), true)
      && t.checkExpect(this.xml1.hasAttribute("duration"), false)
      && t.checkExpect(this.xml2.hasAttribute("duration"), false)
      && t.checkExpect(this.xml3.hasAttribute("duration"), false)
      && t.checkExpect(this.xml4.hasAttribute("duration"), false)
      && t.checkExpect(this.xml5.hasAttribute("duration"), true);
  }

  // tests for hasAttribute helper in ILoXML
  boolean testILoXMLHasAttribute(Tester t) {
    return t.checkExpect(this.consLoXML2.hasAttribute("size"), true)
        && t.checkExpect(this.consLoXML2.hasAttribute("volume"), false)
        && t.checkExpect(this.consLoXML1.hasAttribute("volume"), false);
  }

  // tests for hasAttribute helper in ILoAtt
  boolean testILoAttHasAttribute(Tester t) {
    return t.checkExpect(this.mtLoAtt.hasAttribute("size"), false)
        && t.checkExpect(this.consLoAtt1.hasAttribute("size"), true);
  }

  // tests for isAttribute helper in Att
  boolean testAttIsAttribute(Tester t) {
    return t.checkExpect(att1.isAttribute("volume"), false)
        && t.checkExpect(att1.isAttribute("size"), true);
  }

  // tests for renderAsString in IXML
  boolean testIXMLRenderAsString(Tester t) {
    return t.checkExpect(this.xml1.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml2.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml3.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml4.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml5.renderAsString(), "I am XML!");
  }

  // tests for renderAsString helper in ILoXML
  boolean testILoXMLRenderAsString(Tester t) {
    return t.checkExpect(this.mtLoXML.renderAsString(), "")
        && t.checkExpect(this.consLoXML1.renderAsString(), "I am XML!");
  }
}
