package HW.a8;

import tester.Tester;

import java.util.*;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class PermutationCode {
  // The original list of characters to be encoded
  ArrayList<Character> alphabet =
      new ArrayList<Character>(Arrays.asList(
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);

  // A random number generator
  Random rand = new Random(1);

  // Create a new instance of the encoder/decoder with a new permutation code
  PermutationCode() {
    this.code = this.initEncoder();
  }

  // Create a new instance of the encoder/decoder with the given code
  PermutationCode(ArrayList<Character> code) {
    this.code = code;
  }

  /* TEMPLATE
    * Fields:
    * ... this.alphabet ... ArrayList<Character>
    * ... this.code ... ArrayList<Character>
    * ... this.rand ... Random
   */

  // Initialize the encoding permutation of the characters
  ArrayList<Character> initEncoder() {
    ArrayList<Character> encoder = new ArrayList<Character>();
    ArrayList<Character> temp = new ArrayList<Character>(this.alphabet);
    while (temp.size() > 0) {
      int index = this.rand.nextInt(temp.size());
      encoder.add(temp.get(index));
      temp.remove(index);
    }
    return encoder;
  }

  // produce an encoded String from the given String
  String encode(String source) {
    String result = "";
    for (char c: source.toCharArray()) {
      int index = this.alphabet.indexOf(c);
      result += this.code.get(index);
    }
    return result;
  }

  // produce a decoded String from the given String
  String decode(String code) {
    String result = "";
    for (char c: code.toCharArray()) {
      int index = this.code.indexOf(c);
      result += this.alphabet.get(index);
    }
    return result;
  }
}

class ExamplePermutations {

  PermutationCode code1 = new PermutationCode();
  PermutationCode code2 = new PermutationCode(
      new ArrayList<Character>(Arrays.asList(
      'z', 'y', 'x', 'w', 'v', 'u', 't', 's', 'r',
      'q', 'p', 'o', 'n', 'm', 'l', 'k', 'j', 'i',
      'h', 'g', 'f', 'e', 'd', 'c', 'b', 'a')));

  void testInitEncoder(Tester t) {
    t.checkExpect(this.code1.code,
        new ArrayList<Character>(Arrays.asList(
        'r', 'n', 'h', 'o', 'y', 'q', 't', 'u', 'l',
        'v', 'a', 'x', 'g', 'i', 'k', 'c', 'e', 'j',
        'z', 's', 'f', 'm', 'd', 'w', 'b', 'p')));
    t.checkExpect(this.code2.code,
        new ArrayList<Character>(Arrays.asList(
        'z', 'y', 'x', 'w', 'v', 'u', 't', 's', 'r',
        'q', 'p', 'o', 'n', 'm', 'l', 'k', 'j', 'i',
        'h', 'g', 'f', 'e', 'd', 'c', 'b', 'a')));
  }

  void testEncode(Tester t) {
    t.checkExpect(this.code1.encode("hello"), "uyxxk");
    t.checkExpect(this.code2.encode("hello"), "svool");
    t.checkExpect(this.code1.encode("zyx"), "pbw");
  }

  void testDecode(Tester t) {
    t.checkExpect(this.code1.decode("uyxxk"), "hello");
    t.checkExpect(this.code2.decode("svool"), "hello");
    t.checkExpect(this.code1.decode("rnhoy"), "abcde");
  }
}
