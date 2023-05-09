package HW.a8;

import tester.Tester;

import java.util.function.Predicate;

// Represents a Deque
class Deque<T> {
  Sentinel<T> header;

  Deque() {
    this.header = new Sentinel<T>();
  }

  Deque(Sentinel<T> header) {
    this.header = header;
  }

  /* TEMPLATE
    * Fields:
    * ... this.header ... -- Sentinel<T>
    *
    * Methods:
    * ... this.size() ... -- int
    * ... this.addAtHead(T data) ... -- void
    * ... this.addAtTail(T data) ... -- void
    * ... this.removeFromHead() ... -- void
    * ... this.removeFromTail() ... -- void
    * ... this.find(Predicate<T> pred) ... -- ANode<T>
    *
    * Methods for Fields:
    * ... this.header.size() ... -- int
    * ... this.header.next.sizeHelp() ... -- int
    * ... this.header.addAtHead(T data) ... -- void
    * ... this.header.addAtTail(T data) ... -- void
    * ... this.header.removeFromHead() ... -- void
    * ... this.header.removeFromTail() ... -- void
    * ... this.header.removeThis() ... -- void
    * ... this.header.find(Predicate<T> pred) ... -- ANode<T>
    * ... this.header.next.findHelp(Predicate<T> pred) ... -- ANode<T>
   */

  // returns the total size of this deque
  int size() {
    return this.header.size();
  }

  // EFFECT: adds the given value to the front of the list
  void addAtHead(T data) {
    this.header.addAtHead(data);
  }

  // EFFECT: adds the given value to the end of the list
  void addAtTail(T data) {
    this.header.addAtTail(data);
  }

  // EFFECT: removes the first value of the list
  T removeFromHead() {
    return this.header.removeFromHead();
  }

  // EFFECT: removes the last value from the list
  T removeFromTail() {
    return this.header.removeFromTail();
  }

  // Finds and returns the first value that fulfils the predicate in this list
  ANode<T> find(Predicate<T> pred) {
    return this.header.find(pred);
  }
}

// Represents a node in a Deque
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  ANode(ANode<T> next, ANode<T> prev) {
    this.next = next;
    this.prev = prev;
  }

  /* TEMPLATE
    * Fields:
    * ... this.next ... -- ANode<T>
    * ... this.prev ... -- ANode<T>
    *
    * Methods:
    * ... this.size() ... -- int
    * ... this.sizeHelp() ... -- int
    * ... this.addAtHead(T data) ... -- void
    * ... this.addAtTail(T data) ... -- void
    * ... this.removeFromHead() ... -- void
    * ... this.removeFromTail() ... -- void
    * ... this.removeThis() ... -- void
    * ... this.find(Predicate<T> pred) ... -- ANode<T>
    * ... this.findHelp(Predicate<T> pred) ... -- ANode<T>
    *
    * Methods for Fields:
    * ... this.next.size() ... -- int
    * ... this.next.sizeHelp() ... -- int
    * ... this.next.addAtHead(T data) ... -- void
    * ... this.next.addAtTail(T data) ... -- void
    * ... this.next.removeFromHead() ... -- void
    * ... this.next.removeFromTail() ... -- void
    * ... this.next.removeThis() ... -- void
    * ... this.next.find(Predicate<T> pred) ... -- ANode<T>
    * ... this.next.findHelp(Predicate<T> pred) ... -- ANode<T>
    *
    * ... this.prev.size() ... -- int
    * ... this.prev.sizeHelp() ... -- int
    * ... this.prev.addAtHead(T data) ... -- void
    * ... this.prev.addAtTail(T data) ... -- void
    * ... this.prev.removeFromHead() ... -- void
    * ... this.prev.removeFromTail() ... -- void
    * ... this.prev.removeThis() ... -- void
    * ... this.prev.find(Predicate<T> pred) ... -- ANode<T>
    * ... this.prev.findHelp(Predicate<T> pred) ... -- ANode<T>
   */

  // the helper for the size method
  abstract int sizeHelp();

  // removes this from the list by updating the previous' and next's next and previous
  abstract T removeThis();

  // the helper for the find method
  abstract ANode<T> findHelp(Predicate<T> pred);
}

// represents the sentinel of a Deque
class Sentinel<T> extends ANode<T> {
  Sentinel() {
    super(null, null);
    this.next = this;
    this.prev = this;
  }

  /* TEMPLATE
    * Fields:
    * ... this.next ... -- ANode<T>
    * ... this.prev ... -- ANode<T>
    *
    * Methods:
    * ... this.size() ... -- int
    * ... this.sizeHelp() ... -- int
    * ... this.addAtHead(T data) ... -- void
    * ... this.addAtTail(T data) ... -- void
    * ... this.removeFromHead() ... -- void
    * ... this.removeFromTail() ... -- void
    * ... this.removeThis() ... -- void
    * ... this.find(Predicate<T> pred) ... -- ANode<T>
    * ... this.findHelp(Predicate<T> pred) ... -- ANode<T>
    *
    * Methods for Fields:
    * ... this.next.size() ... -- int
    * ... this.next.sizeHelp() ... -- int
    * ... this.next.addAtHead(T data) ... -- void
    * ... this.next.addAtTail(T data) ... -- void
    * ... this.next.removeFromHead() ... -- void
    * ... this.next.removeFromTail() ... -- void
    * ... this.next.removeThis() ... -- void
    * ... this.next.find(Predicate<T> pred) ... -- ANode<T>
    * ... this.next.findHelp(Predicate<T> pred) ... -- ANode<T>
    *
    * ... this.prev.size() ... -- int
    * ... this.prev.sizeHelp() ... -- int
    * ... this.prev.addAtHead(T data) ... -- void
    * ... this.prev.addAtTail(T data) ... -- void
    * ... this.prev.removeFromHead() ... -- void
    * ... this.prev.removeFromTail() ... -- void
    * ... this.prev.removeThis() ... -- void
    * ... this.prev.find(Predicate<T> pred) ... -- ANode<T>
    * ... this.prev.findHelp(Predicate<T> pred) ... -- ANode<T>
   */

  // returns the size of this list
  int size() {
    return this.next.sizeHelp();
  }

  // helper for the size method
  int sizeHelp() {
    return 0;
  }

  // add the given data to the head of the list
  void addAtHead(T data) {
    this.next = new Node<T>(data, this.next, this);
  }

  // add the given data to the tail of the list
  void addAtTail(T data) {
    this.prev = new Node<T>(data, this, this.prev);
  }

  // removes the first value of the list
  T removeFromHead() {
    return next.removeThis();
  }

  // removes this value from the list
  T removeThis() {
    throw new RuntimeException("Cannot remove from an empty list");
  }

  // removes the last value from the list
  T removeFromTail() {
    return prev.removeThis();
  }

  // finds and returns the first value that fulfils the predicate in this list
  ANode<T> find(Predicate<T> pred) {
    return this.next.findHelp(pred);
  }

  // the helper for the find method
  ANode<T> findHelp(Predicate<T> pred) {
    return this;
  }
}

// represents a node in a Deque
class Node<T> extends ANode<T> {
  T data;

  Node(T data, ANode<T> next, ANode<T> prev) {
    super(next, prev);
    if (next == null || prev == null) {
      throw new IllegalArgumentException("next and prev cannot be null");
    }
    this.data = data;
    next.prev = this;
    prev.next = this;
  }

  Node(T data) {
    super(null, null);
    this.data = data;
  }

  /* TEMPLATE
    * Fields:
    * ... this.data ... -- T
    * ... this.next ... -- ANode<T>
    * ... this.prev ... -- ANode<T>
    *
    * Methods:
    * ... this.sizeHelp() ... -- int
    * ... this.removeThis() ... -- void
    * ... this.findHelp(Predicate<T> pred) ... -- ANode<T>
    *
    * Methods for Fields:
    * ... this.next.size() ... -- int
    * ... this.next.sizeHelp() ... -- int
    * ... this.next.addAtHead(T data) ... -- void
    * ... this.next.addAtTail(T data) ... -- void
    * ... this.next.removeFromHead() ... -- void
    * ... this.next.removeFromTail() ... -- void
    * ... this.next.removeThis() ... -- void
    * ... this.next.find(Predicate<T> pred) ... -- ANode<T>
    * ... this.next.findHelp(Predicate<T> pred) ... -- ANode<T>
    *
    * ... this.prev.size() ... -- int
    * ... this.prev.sizeHelp() ... -- int
    * ... this.prev.addAtHead(T data) ... -- void
    * ... this.prev.addAtTail(T data) ... -- void
    * ... this.prev.removeFromHead() ... -- void
    * ... this.prev.removeFromTail() ... -- void
    * ... this.prev.removeThis() ... -- void
    * ... this.prev.find(Predicate<T> pred) ... -- ANode<T>
    * ... this.prev.findHelp(Predicate<T> pred) ... -- ANode<T>
   */

  // returns the size of this list
  int sizeHelp() {
    return 1 + this.next.sizeHelp();
  }

  // removes this from the list by updating the previous' and next's next and previous
  T removeThis() {
    this.next.prev = this.prev;
    this.prev.next = this.next;
    return this.data;
  }

  // finds and returns the first value that fulfils the predicate in this list
  ANode<T> findHelp(Predicate<T> pred) {
    if (pred.test(this.data)) {
      return this;
    }
    else {
      return this.next.findHelp(pred);
    }
  }
}

class ExamplesDeque {
  // empty list of strings
  Deque<String> deque1 = new Deque<String>();

  // list of "abc", "bcd", "cde", and "def"
  Sentinel<String> header = new Sentinel<String>();
  Node<String> node1 = new Node<String>("abc", header, header);
  Node<String> node2 = new Node<String>("bcd", header, node1);
  Node<String> node3 = new Node<String>("cde", header, node2);
  Node<String> node4 = new Node<String>("def", header, node3);
  Deque<String> deque2 = new Deque<String>(header);

  // list of 10, 5, 17, 3, 6, 8
  Sentinel<Integer> header2 = new Sentinel<Integer>();
  Node<Integer> node5 = new Node<Integer>(10, header2, header2);
  Node<Integer> node6 = new Node<Integer>(5, header2, node5);
  Node<Integer> node7 = new Node<Integer>(17, header2, node6);
  Node<Integer> node8 = new Node<Integer>(3, header2, node7);
  Node<Integer> node9 = new Node<Integer>(6, header2, node8);
  Node<Integer> node10 = new Node<Integer>(8, header2, node9);
  Deque<Integer> deque3 = new Deque<Integer>(header2);

  void initDeque() {
    deque1 = new Deque<String>();

    header = new Sentinel<String>();
    node1 = new Node<String>("abc", header, header);
    node2 = new Node<String>("bcd", header, node1);
    node3 = new Node<String>("cde", header, node2);
    node4 = new Node<String>("def", header, node3);
    deque2 = new Deque<String>(header);

    header2 = new Sentinel<Integer>();
    node5 = new Node<Integer>(10, header2, header2);
    node6 = new Node<Integer>(5, header2, node5);
    node7 = new Node<Integer>(17, header2, node6);
    node8 = new Node<Integer>(3, header2, node7);
    node9 = new Node<Integer>(6, header2, node8);
    node10 = new Node<Integer>(8, header2, node9);
    deque3 = new Deque<Integer>(header2);
  }

  void testSize(Tester t) {
    initDeque();

    t.checkExpect(deque1.size(), 0);
    t.checkExpect(deque2.size(), 4);
    t.checkExpect(deque3.size(), 6);
  }

  void testAddAtHead(Tester t) {
    initDeque();

    deque1.addAtHead("abc");
    t.checkExpect(deque1.size(), 1);
    t.checkExpect(deque1.header.next, new Node<>("abc", deque1.header, deque1.header));
    t.checkExpect(deque1.header.next.next, deque1.header);

    deque2.addAtHead("xyz");
    t.checkExpect(deque2.size(), 5);
    t.checkExpect(deque2.header.next, new Node<>("xyz", node1, deque2.header));
    t.checkExpect(deque2.header.next.next, node1);
    t.checkExpect(deque2.header.next.next.next, node2);
    t.checkExpect(deque2.header.next.next.next.next, node3);
    t.checkExpect(deque2.header.next.next.next.next.next, node4);
    t.checkExpect(deque2.header.next.next.next.next.next.next, deque2.header);

    deque3.addAtHead(1);
    t.checkExpect(deque3.size(), 7);
    t.checkExpect(deque3.header.next, new Node<>(1, node5, deque3.header));
    t.checkExpect(deque3.header.next.next, node5);
    t.checkExpect(deque3.header.next.next.next, node6);
    t.checkExpect(deque3.header.next.next.next.next, node7);
    t.checkExpect(deque3.header.next.next.next.next.next, node8);
    t.checkExpect(deque3.header.next.next.next.next.next.next, node9);
    t.checkExpect(deque3.header.next.next.next.next.next.next.next, node10);
    t.checkExpect(deque3.header.next.next.next.next.next.next.next.next, deque3.header);
  }

  void testAddAtTail(Tester t) {
    initDeque();

    deque1.addAtTail("abc");
    t.checkExpect(deque1.size(), 1);
    t.checkExpect(deque1.header.prev, new Node<>("abc", deque1.header, deque1.header));
    t.checkExpect(deque1.header.prev.prev, deque1.header);

    deque2.addAtTail("xyz");
    t.checkExpect(deque2.size(), 5);
    t.checkExpect(deque2.header.prev, new Node<>("xyz", deque2.header, node4));
    t.checkExpect(deque2.header.prev.prev, node4);
    t.checkExpect(deque2.header.prev.prev.prev, node3);
    t.checkExpect(deque2.header.prev.prev.prev.prev, node2);
    t.checkExpect(deque2.header.prev.prev.prev.prev.prev, node1);
    t.checkExpect(deque2.header.prev.prev.prev.prev.prev.prev, deque2.header);

    deque3.addAtTail(1);
    t.checkExpect(deque3.size(), 7);
    t.checkExpect(deque3.header.prev, new Node<>(1, deque3.header, node10));
    t.checkExpect(deque3.header.prev.prev, node10);
    t.checkExpect(deque3.header.prev.prev.prev, node9);
    t.checkExpect(deque3.header.prev.prev.prev.prev, node8);
    t.checkExpect(deque3.header.prev.prev.prev.prev.prev, node7);
    t.checkExpect(deque3.header.prev.prev.prev.prev.prev.prev, node6);
    t.checkExpect(deque3.header.prev.prev.prev.prev.prev.prev.prev, node5);
    t.checkExpect(deque3.header.prev.prev.prev.prev.prev.prev.prev.prev, deque3.header);
  }

  void testRemoveFromHead(Tester t) {
    initDeque();

    t.checkException(new RuntimeException("Cannot remove from an empty list"), deque1,
        "removeFromHead");

    t.checkExpect(deque2.removeFromHead(), "abc");
    t.checkExpect(deque2.size(), 3);
    t.checkExpect(deque2.header.next, node2);
    t.checkExpect(deque2.header.next.next, node3);
    t.checkExpect(deque2.header.next.next.next, node4);
    t.checkExpect(deque2.header.next.next.next.next, deque2.header);

    t.checkExpect(deque3.removeFromHead(), 10);
    t.checkExpect(deque3.size(), 5);
    t.checkExpect(deque3.header.next, node6);
    t.checkExpect(deque3.header.next.next, node7);
    t.checkExpect(deque3.header.next.next.next, node8);
    t.checkExpect(deque3.header.next.next.next.next, node9);
    t.checkExpect(deque3.header.next.next.next.next.next, node10);
    t.checkExpect(deque3.header.next.next.next.next.next.next, deque3.header);
  }

  void testRemoveFromTail(Tester t) {
    initDeque();

    t.checkException(new RuntimeException("Cannot remove from an empty list"), deque1,
        "removeFromTail");

    t.checkExpect(deque2.removeFromTail(), "def");
    t.checkExpect(deque2.size(), 3);
    t.checkExpect(deque2.header.next, node1);
    t.checkExpect(deque2.header.next.next, node2);
    t.checkExpect(deque2.header.next.next.next, node3);
    t.checkExpect(deque2.header.next.next.next.next, deque2.header);

    t.checkExpect(deque3.removeFromTail(), 8);
    t.checkExpect(deque3.size(), 5);
    t.checkExpect(deque3.header.next, node5);
    t.checkExpect(deque3.header.next.next, node6);
    t.checkExpect(deque3.header.next.next.next, node7);
    t.checkExpect(deque3.header.next.next.next.next, node8);
    t.checkExpect(deque3.header.next.next.next.next.next, node9);
    t.checkExpect(deque3.header.next.next.next.next.next.next, deque3.header);
  }

  void testFind(Tester t) {
    initDeque();

    t.checkExpect(deque1.find((x -> x.equals("abc"))), deque1.header);
    t.checkExpect(deque2.find((x -> x.equals("abc"))), node1);
    t.checkExpect(deque2.find((x -> x.equals("def"))), node4);
    t.checkExpect(deque2.find((x -> x.equals("xyz"))), header);
    t.checkExpect(deque3.find((x -> x == 6)), node9);
    t.checkExpect(deque3.find((x -> x == 10)), node5);
    t.checkExpect(deque3.find((x -> x == 1)), header2);
  }

  void testFindHelp(Tester t) {
    initDeque();
    t.checkExpect(deque1.header.findHelp((x -> x.equals("abc"))), deque1.header);
    t.checkExpect(deque2.header.next.findHelp((x -> x.equals("abc"))), node1);
    t.checkExpect(deque2.header.next.findHelp((x -> x.equals("xyz"))), header);
  }

  void testSizeHelp(Tester t) {
    initDeque();
    t.checkExpect(deque1.header.sizeHelp(), 0);
    t.checkExpect(deque2.header.next.sizeHelp(), 4);
    t.checkExpect(deque3.header.next.sizeHelp(), 6);
    t.checkExpect(deque2.header.prev.sizeHelp(), 1);
  }

  void testRemoveThis(Tester t) {
    initDeque();
    t.checkException(new RuntimeException("Cannot remove from an empty list"), deque1.header,
        "removeThis");
    t.checkExpect(deque2.header.next, node1);
    t.checkExpect(deque2.header.next.removeThis(), "abc");
    t.checkExpect(deque2.header.next, node2);
    t.checkExpect(deque2.header.next.next, node3);
    t.checkExpect(deque2.header.next.next.next, node4);
  }

  void testAddAtHeadSentinal(Tester t) {
    initDeque();
    t.checkExpect(deque2.header.next, node1);
    deque2.header.addAtHead("xyz");
    t.checkExpect(deque2.header.next, new Node<>("xyz", node1, deque2.header));
  }

  void testAddAtTailSentinal(Tester t) {
    initDeque();
    t.checkExpect(deque2.header.prev, node4);
    deque2.header.addAtTail("xyz");
    t.checkExpect(deque2.header.prev, new Node<>("xyz", deque2.header, node4));
  }

  void testRemoveFromHeadSentinal(Tester t) {
    initDeque();
    t.checkException(new RuntimeException("Cannot remove from an empty list"), deque1.header,
        "removeFromHead");
    t.checkExpect(deque2.header.next, node1);
    t.checkExpect(deque2.header.removeFromHead(), "abc");
    t.checkExpect(deque2.header.next, node2);
  }

  void testRemoveFromTailSentinal(Tester t) {
    initDeque();
    t.checkException(new RuntimeException("Cannot remove from an empty list"), deque1.header,
        "removeFromTail");
    t.checkExpect(deque2.header.prev, node4);
    t.checkExpect(deque2.header.removeFromTail(), "def");
    t.checkExpect(deque2.header.prev, node3);
  }
}
