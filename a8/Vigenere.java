package HW.a8;

import tester.Tester;

import java.util.ArrayList;
import java.util.Arrays;

class Vigenere {
  ArrayList<Character> alphabet = new ArrayList<>(Arrays.asList(
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
      'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
      't', 'u', 'v', 'w', 'x', 'y', 'z'));
  ArrayList<ArrayList<Character>> table;

  Vigenere() {
    this.table = initVigenere();
  }

  /* TEMPLATE
   * Fields:
   * ... this.alphabet ... ArrayList<Character>
   * ... this.table ... ArrayList<ArrayList<Character>>
   *
   * Methods:
   * ... this.initVigenere() ... void
   * ... this.decode(String, String) ... String
   * ... this.encode(String, String) ... String
   */

  // Initialize the encoding permutation of the characters
  ArrayList<ArrayList<Character>> initVigenere() {
    ArrayList<ArrayList<Character>> result = new ArrayList<>();
    for (char c : alphabet) {
      ArrayList<Character> row = new ArrayList<>();
      for (int i = 0; i < alphabet.size(); i++) {
        row.add(alphabet.get((alphabet.indexOf(c) + i) % alphabet.size()));
      }
      result.add(row);
    }
    return result;
  }

  // produce an encoded String from the given String and key
  String decode(String key, String message) {
    StringBuilder decoded = new StringBuilder();
    int keyIndex = 0;
    for (int i = 0; i < message.length(); i++) {
      if (keyIndex == key.length()) {
        keyIndex = 0;
      }
      char row_letter = key.charAt(keyIndex);
      char col_letter = message.charAt(i);
      int row = alphabet.indexOf(row_letter);
      int col = table.get(row).indexOf(col_letter);
      decoded.append(alphabet.get(col));
      keyIndex++;
    }
    return decoded.toString();
  }

  // produce a decoded String from the given String and key
  String encode(String key, String message) {
    StringBuilder encoded = new StringBuilder();
    int keyIndex = 0;
    for (int i = 0; i < message.length(); i++) {
      if (keyIndex == key.length()) {
        keyIndex = 0;
      }
      char row_letter = message.charAt(i);
      char col_letter = key.charAt(keyIndex);
      int row = alphabet.indexOf(row_letter);
      int col = alphabet.indexOf(col_letter);
      encoded.append(table.get(row).get(col));
      keyIndex++;
    }
    return encoded.toString();
  }
}

class ExamplesVigenere {

  void testInitVigenere(Tester t) {
    Vigenere v = new Vigenere();
    v.initVigenere();
    StringBuilder s = new StringBuilder();
    for (ArrayList<Character> row : v.table) {
      s.append("|");
      for (char c : row) {
        s.append(c);
      }
    }
    t.checkExpect(s.toString(), "|abcdefghijklmnopqrstuvwxyz|" +
        "bcdefghijklmnopqrstuvwxyza|cdefghijklmnopqrstuvwxyzab|" +
        "defghijklmnopqrstuvwxyzabc|efghijklmnopqrstuvwxyzabcd|" +
        "fghijklmnopqrstuvwxyzabcde|ghijklmnopqrstuvwxyzabcdef|" +
        "hijklmnopqrstuvwxyzabcdefg|ijklmnopqrstuvwxyzabcdefgh|" +
        "jklmnopqrstuvwxyzabcdefghi|klmnopqrstuvwxyzabcdefghij|" +
        "lmnopqrstuvwxyzabcdefghijk|mnopqrstuvwxyzabcdefghijkl|" +
        "nopqrstuvwxyzabcdefghijklm|opqrstuvwxyzabcdefghijklmn|" +
        "pqrstuvwxyzabcdefghijklmno|qrstuvwxyzabcdefghijklmnop|" +
        "rstuvwxyzabcdefghijklmnopq|stuvwxyzabcdefghijklmnopqr|" +
        "tuvwxyzabcdefghijklmnopqrs|uvwxyzabcdefghijklmnopqrst|" +
        "vwxyzabcdefghijklmnopqrstu|wxyzabcdefghijklmnopqrstuv|" +
        "xyzabcdefghijklmnopqrstuvw|yzabcdefghijklmnopqrstuvwx|" +
        "zabcdefghijklmnopqrstuvwxy");
  }

  void testDecode(Tester t) {
    Vigenere v = new Vigenere();
    v.initVigenere();
    t.checkExpect(v.decode("happy", "ahpcizgxkgug"), "thanksgiving");
    t.checkExpect(v.decode("coding", "hiqlvku"), "fundies");
  }

  void testEncode(Tester t) {
    Vigenere v = new Vigenere();
    v.initVigenere();
    t.checkExpect(v.encode("happy", "thanksgiving"), "ahpcizgxkgug");
    t.checkExpect(v.encode("coding", "fundies"), "hiqlvku");

  }
}
