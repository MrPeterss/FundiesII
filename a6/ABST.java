package HW.a6;

import tester.Tester;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

// represents a binary search tree
abstract class ABST<T> {
  Comparator<T> order;

  public ABST(Comparator<T> order) {
    this.order = order;
  }

  // inserts the given data into this binary search tree
  abstract ABST<T> insert(T t);

  // is the given data in this binary search tree?
  abstract boolean present(T t);

  // get the leftmost item contained in this binary search tree
  abstract T getLeftmost();

  // helper for the getLeftmost method
  abstract T getLeftmostHelp(T t);

  // get the all but the leftmost item contained in this binary search tree
  abstract ABST<T> getRight();

  // is this tree the same as the given tree?
  abstract boolean sameTree(ABST<T> that);

  // is this tree the same as the given node?
  abstract boolean sameNode(Node<T> that);

  // is this tree the same as the given leaf?
  abstract boolean sameLeaf(Leaf<T> that);

  // does this tree have the same data as the given tree?
  abstract boolean sameData(ABST<T> that);

  // does this tree have the same data as the given node?
  abstract boolean sameNodeData(Node<T> that);

  // does this tree have the same data as the given leaf?
  abstract boolean sameLeafData(Leaf<T> that);

  // make a list of the data in this tree
  abstract IList<T> buildList();
}

// represents a leaf on a binary search tree
class Leaf<T> extends ABST<T> {

  /* TEMPLATE
    * FIELDS:
    * ... this.order ... -- Comparator<T>
    * METHODS:
    * ... this.insert(T t) ... -- ABST<T>
    * ... this.present(T t) ... -- boolean
    * ... this.getLeftmost() ... -- T
    * ... this.getLeftmostHelp(T t) ... -- T
    * ... this.getRight() ... -- ABST<T>
    * ... this.sameTree(ABST<T> that) ... -- boolean
    * ... this.sameNode(Node<T> that) ... -- boolean
    * ... this.sameLeaf(Leaf<T> that) ... -- boolean
    * ... this.sameData(ABST<T> that) ... -- boolean
    * ... this.sameNodeData(Node<T> that) ... -- boolean
    * ... this.sameLeafData(Leaf<T> that) ... -- boolean
    * ... this.buildList() ... -- IList<T>
    * METHODS FOR FIELDS:
    * ... this.order.compare(T t1, T t2) ... -- int
   */

  public Leaf(Comparator<T> order) {
    super(order);
  }

  // inserts the given data into this binary search tree
  ABST<T> insert(T t) {
    return new Node<T>(this.order, t, new Leaf<T>(this.order), new Leaf<T>(this.order));
  }

  // is the given data in this binary search tree?
  boolean present(T t) {
    return false;
  }

  // get the leftmost item contained in this binary search tree
  T getLeftmost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  // helper for the getLeftmost method
  T getLeftmostHelp(T t) {
    return t;
  }

  // get the all but the leftmost item contained in this binary search tree
  ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }

  // is this tree the same as the given tree?
  boolean sameTree(ABST<T> that) {

    /* TEMPLATE for that
     * METHODS:
     * ... that.insert(T t) ... -- ABST<T>
     * ... that.present(T t) ... -- boolean
     * ... that.getLeftmost() ... -- T
     * ... that.getLeftmostHelp(T t) ... -- T
     * ... that.getRight() ... -- ABST<T>
     * ... that.sameTree(ABST<T> that) ... -- boolean
     * ... that.sameNode(Node<T> that) ... -- boolean
     * ... that.sameLeaf(Leaf<T> that) ... -- boolean
     * ... that.sameData(ABST<T> that) ... -- boolean
     * ... that.sameNodeData(Node<T> that) ... -- boolean
     * ... that.sameLeafData(Leaf<T> that) ... -- boolean
     * ... that.buildList() ... -- IList<T>
     */

    return that.sameLeaf(this);
  }

  // is this tree the same as the given node?
  boolean sameNode(Node<T> that) {

    /* TEMPLATE for that
     * METHODS:
     * ... that.insert(T t) ... -- ABST<T>
     * ... that.present(T t) ... -- boolean
     * ... that.getLeftmost() ... -- T
     * ... that.getLeftmostHelp(T t) ... -- T
     * ... that.getRight() ... -- ABST<T>
     * ... that.sameTree(ABST<T> that) ... -- boolean
     * ... that.sameNode(Node<T> that) ... -- boolean
     * ... that.sameLeaf(Leaf<T> that) ... -- boolean
     * ... that.sameData(ABST<T> that) ... -- boolean
     * ... that.sameNodeData(Node<T> that) ... -- boolean
     * ... that.sameLeafData(Leaf<T> that) ... -- boolean
     * ... that.buildList() ... -- IList<T>
     */

    return false;
  }

  // is this tree the same as the given leaf?
  boolean sameLeaf(Leaf<T> that) {

    /* TEMPLATE
      * FIELDS:
      * ... that.order ... -- Comparator<T>
      *
      * METHODS:
      * ... that.insert(T t) ... -- ABST<T>
      * ... that.present(T t) ... -- boolean
      * ... that.getLeftmost() ... -- T
      * ... that.getLeftmostHelp(T t) ... -- T
      * ... that.getRight() ... -- ABST<T>
      * ... that.sameTree(ABST<T> that) ... -- boolean
      * ... that.sameNode(Node<T> that) ... -- boolean
      * ... that.sameLeaf(Leaf<T> that) ... -- boolean
      * ... that.sameData(ABST<T> that) ... -- boolean
      * ... that.sameNodeData(Node<T> that) ... -- boolean
      * ... that.sameLeafData(Leaf<T> that) ... -- boolean
      * ... that.buildList() ... -- IList<T>
      *
      * METHODS FOR FIELDS:
      * ... that.order.compare(T t1, T t2) ... -- int
     */

    return true;
  }

  // does this tree have the same data as the given tree?
  boolean sameData(ABST<T> that) {

    /* TEMPLATE for that
     * METHODS:
     * ... that.insert(T t) ... -- ABST<T>
     * ... that.present(T t) ... -- boolean
     * ... that.getLeftmost() ... -- T
     * ... that.getLeftmostHelp(T t) ... -- T
     * ... that.getRight() ... -- ABST<T>
     * ... that.sameTree(ABST<T> that) ... -- boolean
     * ... that.sameNode(Node<T> that) ... -- boolean
     * ... that.sameLeaf(Leaf<T> that) ... -- boolean
     * ... that.sameData(ABST<T> that) ... -- boolean
     * ... that.sameNodeData(Node<T> that) ... -- boolean
     * ... that.sameLeafData(Leaf<T> that) ... -- boolean
     * ... that.buildList() ... -- IList<T>
     */

    return that.sameLeafData(this);
  }

  // does this tree have the same data as the given node?
  boolean sameNodeData(Node<T> that) {

    /* TEMPLATE for that
     * METHODS:
     * ... that.insert(T t) ... -- ABST<T>
     * ... that.present(T t) ... -- boolean
     * ... that.getLeftmost() ... -- T
     * ... that.getLeftmostHelp(T t) ... -- T
     * ... that.getRight() ... -- ABST<T>
     * ... that.sameTree(ABST<T> that) ... -- boolean
     * ... that.sameNode(Node<T> that) ... -- boolean
     * ... that.sameLeaf(Leaf<T> that) ... -- boolean
     * ... that.sameData(ABST<T> that) ... -- boolean
     * ... that.sameNodeData(Node<T> that) ... -- boolean
     * ... that.sameLeafData(Leaf<T> that) ... -- boolean
     * ... that.buildList() ... -- IList<T>
     */

    return false;
  }

  // does this tree have the same data as the given leaf?
  boolean sameLeafData(Leaf<T> that) {

    /* TEMPLATE for that
     * METHODS:
     * ... that.insert(T t) ... -- ABST<T>
     * ... that.present(T t) ... -- boolean
     * ... that.getLeftmost() ... -- T
     * ... that.getLeftmostHelp(T t) ... -- T
     * ... that.getRight() ... -- ABST<T>
     * ... that.sameTree(ABST<T> that) ... -- boolean
     * ... that.sameNode(Node<T> that) ... -- boolean
     * ... that.sameLeaf(Leaf<T> that) ... -- boolean
     * ... that.sameData(ABST<T> that) ... -- boolean
     * ... that.sameNodeData(Node<T> that) ... -- boolean
     * ... that.sameLeafData(Leaf<T> that) ... -- boolean
     * ... that.buildList() ... -- IList<T>
     */

    return true;
  }

  // builds a list of the data in this tree
  IList<T> buildList() {
    return new MtList<T>();
  }
}

// represents a node on a binary search tree
class Node<T> extends ABST<T> {
  T data;
  ABST<T> left;
  ABST<T> right;

  Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
  }

  /*
    * TEMPLATE
    * FIELDS:
    * ... this.order ... -- Comparator<T>
    * ... this.data ... -- T
    * ... this.left ... -- ABST<T>
    * ... this.right ... -- ABST<T>
    * METHODS:
    * ... this.insert(T t) ... -- ABST<T>
    * ... this.present(T t) ... -- boolean
    * ... this.getLeftmost() ... -- T
    * ... this.getLeftmostHelp(T t) ... -- T
    * ... this.getRight() ... -- ABST<T>
    * ... this.sameTree(ABST<T> that) ... -- boolean
    * ... this.sameNode(Node<T> that) ... -- boolean
    * ... this.sameLeaf(Leaf<T> that) ... -- boolean
    * ... this.sameData(ABST<T> that) ... -- boolean
    * ... this.sameNodeData(Node<T> that) ... -- boolean
    * ... this.sameLeafData(Leaf<T> that) ... -- boolean
    * ... this.buildList() ... -- IList<T>
    * METHODS FOR FIELDS:
    * ... this.order.compare(T t1, T t2) ... -- int
    *
    * ... this.left.insert(T t) ... -- ABST<T>
    * ... this.left.present(T t) ... -- boolean
    * ... this.left.getLeftmost() ... -- T
    * ... this.left.getLeftmostHelp(T t) ... -- T
    * ... this.left.getRight() ... -- ABST<T>
    * ... this.left.sameTree(ABST<T> that) ... -- boolean
    * ... this.left.sameNode(Node<T> that) ... -- boolean
    * ... this.left.sameLeaf(Leaf<T> that) ... -- boolean
    * ... this.left.sameData(ABST<T> that) ... -- boolean
    * ... this.left.sameNodeData(Node<T> that) ... -- boolean
    * ... this.left.sameLeafData(Leaf<T> that) ... -- boolean
    * ... this.left.buildList() ... -- IList<T>
    *
    * ... this.right.insert(T t) ... -- ABST<T>
    * ... this.right.present(T t) ... -- boolean
    * ... this.right.getLeftmost() ... -- T
    * ... this.right.getLeftmostHelp(T t) ... -- T
    * ... this.right.getRight() ... -- ABST<T>
    * ... this.right.sameTree(ABST<T> that) ... -- boolean
    * ... this.right.sameNode(Node<T> that) ... -- boolean
    * ... this.right.sameLeaf(Leaf<T> that) ... -- boolean
    * ... this.right.sameData(ABST<T> that) ... -- boolean
    * ... this.right.sameNodeData(Node<T> that) ... -- boolean
    * ... this.right.sameLeafData(Leaf<T> that) ... -- boolean
    * ... this.right.buildList() ... -- IList<T>
   */

  // inserts the given data into this binary search tree
  ABST<T> insert(T t) {
    int comparison = this.order.compare(this.data, t);
    if (comparison > 0) {
      return new Node<T>(this.order, this.data, this.left.insert(t), this.right);
    } else if (comparison < 0) {
      return new Node<T>(this.order, this.data, this.left, this.right.insert(t));
    } else {
      return this;
    }
  }

  // is the given data in this binary search tree?
  boolean present(T t) {
    return this.order.compare(this.data, t) == 0
        || this.left.present(t)
        || this.right.present(t);
  }

  // get the leftmost item contained in this binary search tree
  T getLeftmost() {
    return this.left.getLeftmostHelp(this.data);
  }

  // helper for the getLeftmost method
  T getLeftmostHelp(T t) {
    return this.left.getLeftmostHelp(this.data);
  }

  // get the all but the leftmost item contained in this binary search tree
  ABST<T> getRight() {
    if (this.order.compare(this.data, this.getLeftmost()) == 0) {
      return this.right;
    }
    else {
      return new Node<>(this.order, this.data, this.left.getRight(), this.right);
    }
  }

  // is this tree the same as the given tree?
  boolean sameTree(ABST<T> that) {

    /* TEMPLATE for that
      * METHODS:
      * ... that.insert(T t) ... -- ABST<T>
      * ... that.present(T t) ... -- boolean
      * ... that.getLeftmost() ... -- T
      * ... that.getLeftmostHelp(T t) ... -- T
      * ... that.getRight() ... -- ABST<T>
      * ... that.sameTree(ABST<T> that) ... -- boolean
      * ... that.sameNode(Node<T> that) ... -- boolean
      * ... that.sameLeaf(Leaf<T> that) ... -- boolean
      * ... that.sameData(ABST<T> that) ... -- boolean
      * ... that.sameNodeData(Node<T> that) ... -- boolean
      * ... that.sameLeafData(Leaf<T> that) ... -- boolean
      * ... that.buildList() ... -- IList<T>
     */

    return that.sameNode(this);
  }

  // is this tree the same as the given node?
  boolean sameNode(Node<T> that) {

    /* TEMPLATE for that
      * FIELDS:
      * ... that.data ... -- T
      * ... that.left ... -- ABST<T>
      * ... that.right ... -- ABST<T>
      * ... that.order ... -- Comparator<T>
      * METHODS:
      * ... that.insert(T t) ... -- ABST<T>
      * ... that.present(T t) ... -- boolean
      * ... that.getLeftmost() ... -- T
      * ... that.getLeftmostHelp(T t) ... -- T
      * ... that.getRight() ... -- ABST<T>
      * ... that.sameTree(ABST<T> that) ... -- boolean
      * ... that.sameNode(Node<T> that) ... -- boolean
      * ... that.sameLeaf(Leaf<T> that) ... -- boolean
      * ... that.sameData(ABST<T> that) ... -- boolean
      * ... that.sameNodeData(Node<T> that) ... -- boolean
      * ... that.sameLeafData(Leaf<T> that) ... -- boolean
      * ... that.buildList() ... -- IList<T>
      * METHODS FOR FIELDS:
      * ... that.left.insert(T t) ... -- ABST<T>
      * ... that.left.present(T t) ... -- boolean
      * ... that.left.getLeftmost() ... -- T
      * ... that.left.getLeftmostHelp(T t) ... -- T
      * ... that.left.getRight() ... -- ABST<T>
      * ... that.left.sameTree(ABST<T> that) ... -- boolean
      * ... that.left.sameNode(Node<T> that) ... -- boolean
      * ... that.left.sameLeaf(Leaf<T> that) ... -- boolean
      * ... that.left.sameData(ABST<T> that) ... -- boolean
      * ... that.left.sameNodeData(Node<T> that) ... -- boolean
      * ... that.left.sameLeafData(Leaf<T> that) ... -- boolean
      * ... that.left.buildList() ... -- IList<T>
      *
      * ... that.right.insert(T t) ... -- ABST<T>
      * ... that.right.present(T t) ... -- boolean
      * ... that.right.getLeftmost() ... -- T
      * ... that.right.getLeftmostHelp(T t) ... -- T
      * ... that.right.getRight() ... -- ABST<T>
      * ... that.right.sameTree(ABST<T> that) ... -- boolean
      * ... that.right.sameNode(Node<T> that) ... -- boolean
      * ... that.right.sameLeaf(Leaf<T> that) ... -- boolean
      * ... that.right.sameData(ABST<T> that) ... -- boolean
      * ... that.right.sameNodeData(Node<T> that) ... -- boolean
      * ... that.right.sameLeafData(Leaf<T> that) ... -- boolean
      * ... that.right.buildList() ... -- IList<T>
      *
      * ... that.order.compare(T t1, T t2) ... -- int
     */

    return this.order.compare(this.data, that.data) == 0
        && this.left.sameTree(that.left)
        && this.right.sameTree(that.right);
  }

  // is this tree the same as the given leaf?
  boolean sameLeaf(Leaf<T> that) {

    /* TEMPLATE for that
     * METHODS:
     * ... that.insert(T t) ... -- ABST<T>
     * ... that.present(T t) ... -- boolean
     * ... that.getLeftmost() ... -- T
     * ... that.getLeftmostHelp(T t) ... -- T
     * ... that.getRight() ... -- ABST<T>
     * ... that.sameTree(ABST<T> that) ... -- boolean
     * ... that.sameNode(Node<T> that) ... -- boolean
     * ... that.sameLeaf(Leaf<T> that) ... -- boolean
     * ... that.sameData(ABST<T> that) ... -- boolean
     * ... that.sameNodeData(Node<T> that) ... -- boolean
     * ... that.sameLeafData(Leaf<T> that) ... -- boolean
     * ... that.buildList() ... -- IList<T>
     */

    return false;
  }

  // does this tree have the same data as the given tree?
  boolean sameData(ABST<T> that) {

    /* TEMPLATE for that
     * METHODS:
     * ... that.insert(T t) ... -- ABST<T>
     * ... that.present(T t) ... -- boolean
     * ... that.getLeftmost() ... -- T
     * ... that.getLeftmostHelp(T t) ... -- T
     * ... that.getRight() ... -- ABST<T>
     * ... that.sameTree(ABST<T> that) ... -- boolean
     * ... that.sameNode(Node<T> that) ... -- boolean
     * ... that.sameLeaf(Leaf<T> that) ... -- boolean
     * ... that.sameData(ABST<T> that) ... -- boolean
     * ... that.sameNodeData(Node<T> that) ... -- boolean
     * ... that.sameLeafData(Leaf<T> that) ... -- boolean
     * ... that.buildList() ... -- IList<T>
     */

    return that.sameNodeData(this);
  }

  // does this tree have the same data as the given node?
  boolean sameNodeData(Node<T> that) {

    /* TEMPLATE for that
     * FIELDS:
     * ... that.data ... -- T
     * ... that.left ... -- ABST<T>
     * ... that.right ... -- ABST<T>
     * ... that.order ... -- Comparator<T>
     * METHODS:
     * ... that.insert(T t) ... -- ABST<T>
     * ... that.present(T t) ... -- boolean
     * ... that.getLeftmost() ... -- T
     * ... that.getLeftmostHelp(T t) ... -- T
     * ... that.getRight() ... -- ABST<T>
     * ... that.sameTree(ABST<T> that) ... -- boolean
     * ... that.sameNode(Node<T> that) ... -- boolean
     * ... that.sameLeaf(Leaf<T> that) ... -- boolean
     * ... that.sameData(ABST<T> that) ... -- boolean
     * ... that.sameNodeData(Node<T> that) ... -- boolean
     * ... that.sameLeafData(Leaf<T> that) ... -- boolean
     * ... that.buildList() ... -- IList<T>
     * METHODS FOR FIELDS:
     * ... that.left.insert(T t) ... -- ABST<T>
     * ... that.left.present(T t) ... -- boolean
     * ... that.left.getLeftmost() ... -- T
     * ... that.left.getLeftmostHelp(T t) ... -- T
     * ... that.left.getRight() ... -- ABST<T>
     * ... that.left.sameTree(ABST<T> that) ... -- boolean
     * ... that.left.sameNode(Node<T> that) ... -- boolean
     * ... that.left.sameLeaf(Leaf<T> that) ... -- boolean
     * ... that.left.sameData(ABST<T> that) ... -- boolean
     * ... that.left.sameNodeData(Node<T> that) ... -- boolean
     * ... that.left.sameLeafData(Leaf<T> that) ... -- boolean
     * ... that.left.buildList() ... -- IList<T>
     *
     * ... that.right.insert(T t) ... -- ABST<T>
     * ... that.right.present(T t) ... -- boolean
     * ... that.right.getLeftmost() ... -- T
     * ... that.right.getLeftmostHelp(T t) ... -- T
     * ... that.right.getRight() ... -- ABST<T>
     * ... that.right.sameTree(ABST<T> that) ... -- boolean
     * ... that.right.sameNode(Node<T> that) ... -- boolean
     * ... that.right.sameLeaf(Leaf<T> that) ... -- boolean
     * ... that.right.sameData(ABST<T> that) ... -- boolean
     * ... that.right.sameNodeData(Node<T> that) ... -- boolean
     * ... that.right.sameLeafData(Leaf<T> that) ... -- boolean
     * ... that.right.buildList() ... -- IList<T>
     *
     * ... that.order.compare(T t1, T t2) ... -- int
     */

    return this.order.compare(this.getLeftmost(), that.getLeftmost()) == 0
        && this.getRight().sameData(that.getRight());
  }

  // does this tree have the same data as the given leaf?
  boolean sameLeafData(Leaf<T> that) {

    /* TEMPLATE for that
     * METHODS:
     * ... that.insert(T t) ... -- ABST<T>
     * ... that.present(T t) ... -- boolean
     * ... that.getLeftmost() ... -- T
     * ... that.getLeftmostHelp(T t) ... -- T
     * ... that.getRight() ... -- ABST<T>
     * ... that.sameTree(ABST<T> that) ... -- boolean
     * ... that.sameNode(Node<T> that) ... -- boolean
     * ... that.sameLeaf(Leaf<T> that) ... -- boolean
     * ... that.sameData(ABST<T> that) ... -- boolean
     * ... that.sameNodeData(Node<T> that) ... -- boolean
     * ... that.sameLeafData(Leaf<T> that) ... -- boolean
     * ... that.buildList() ... -- IList<T>
     */

    return false;
  }

  // builds a list of the data in this tree
  IList<T> buildList() {
    return new ConsList<>(this.getLeftmost(), this.getRight().buildList());
  }
}

// represents a book
class Book {
  String title;
  String author;
  int price;

  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }
}

// represents a comparator for sorting books by title
class BooksByTitle implements Comparator<Book> {
  public int compare(Book b1, Book b2) {
    return b1.title.compareTo(b2.title);
  }
}

// represents a comparator for sorting books by Author
class BooksByAuthor implements Comparator<Book> {
  public int compare(Book b1, Book b2) {
    return b1.author.compareTo(b2.author);
  }
}

// represents a comparator for sorting books by price
class BooksByPrice implements Comparator<Book> {
  public int compare(Book b1, Book b2) {
    return b1.price > b2.price ? 1 : b2.price < b2.price ? -1 : 0;
  }
}

// represents a comparator for sorting integers by value
class IntegersByValue implements Comparator<Integer> {
  public int compare(Integer i1, Integer i2) {
    return i1 - i2;
  }
}

// represents a list of type T
interface IList<T> {
  // returns a list of all the elements of this list that satisfy the given predicate
  IList<T> filter(Predicate<T> pred);

  // returns a list of all the elements of this list converted by the given function
  <U> IList<U> map(Function<T,U> converter);

  // returns the result of folding this list with the given function and initial value
  <U> U fold(BiFunction<T,U,U> converter,U initial);
}

// represents an empty list of type T
class MtList<T> implements IList<T> {

  MtList() {}

  /*
   * TEMPLATE
   * FIELDS:
   * ... NONE ...
   * METHODS:
   * ... this.filter(Predicate<T> pred) ... -- IList<T>
   * ... this.map(Function<T,U> converter) ... -- IList<U>
   * ... this.fold(BiFunction<T,U,U> converter,U initial) ... -- U
   * METHODS FOR FIELDS:
   * ... NONE ...
   */

  // filters the empty list
  public IList<T> filter(Predicate<T> pred) {
    return new MtList<T>();
  }

  // maps the empty list
  public <U> IList<U> map(Function<T, U> converter) {
    return new MtList<U>();
  }

  // folds the empty list
  public <U> U fold(BiFunction<T, U, U> converter, U initial) {
    return initial;
  }
}

// represents a non-empty list of type T
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first,IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * TEMPLATE
   * FIELDS:
   * ... this.first ... -- T
   * ... this.rest ... -- IList<T>
   * METHODS:
   * ... this.filter(Predicate<T> pred) ... -- IList<T>
   * ... this.map(Function<T,U> converter) ... -- IList<U>
   * ... this.fold(BiFunction<T,U,U> converter,U initial) ... -- U
   * METHODS FOR FIELDS:
   * ... this.first.filter(Predicate<T> pred) ... -- IList<T>
   * ... this.first.map(Function<T,U> converter) ... -- IList<U>
   * ... this.first.fold(BiFunction<T,U,U> converter,U initial) ... -- U
   * ... this.rest.filter(Predicate<T> pred) ... -- IList<T>
   * ... this.rest.map(Function<T,U> converter) ... -- IList<U>
   * ... this.rest.fold(BiFunction<T,U,U> converter,U initial) ... -- U
   */

  // filters the non-empty list
  public IList<T> filter(Predicate<T> pred) {
    /* TEMPLATE FOR Predicate<T> pred
     * FIELDS:
     * ... NONE ...
     * METHODS:
     * ... pred.test(T t) ... -- boolean
     * METHODS FOR FIELDS:
     * ... NONE ...
     */

    if (pred.test(this.first)) {
      return new ConsList<T>(this.first,this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  // maps the non-empty list
  public <U> IList<U> map(Function<T, U> converter) {
    /* TEMPLATE FOR Function<T,U> converter
     * FIELDS:
     * ... NONE ...
     * METHODS:
     * ... converter.apply(T t) ... -- U
     * METHODS FOR FIELDS:
     * ... NONE ...
     */
    return new ConsList<U>(converter.apply(this.first),this.rest.map(converter));
  }

  // folds the non-empty list
  public <U> U fold(BiFunction<T, U, U> converter, U initial) {
    /* TEMPLATE FOR BiFunction<T,U,U> converter
     * FIELDS:
     * ... NONE ...
     * METHODS:
     * ... converter.apply(T t,U u) ... -- U
     * METHODS FOR FIELDS:
     * ... NONE ...
     */
    return converter.apply(this.first, this.rest.fold(converter, initial));
  }
}

class ExamplesBTS {

  // examples for books

  Book b1 = new Book("The Hobbit", "J.R.R. Tolkien", 8);
  Book b2 = new Book("Percy Jackson", "Rick Riordan", 10);
  Book b3 = new Book("Heroes Of Olympus", "Rick Riordan", 15);
  Book b4 = new Book("To Kill a Mockingbird", "Harper Lee", 20);
  Book b5 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 25);

  BooksByPrice byPrice = new BooksByPrice();
  BooksByAuthor byAuthor = new BooksByAuthor();
  BooksByTitle byTitle = new BooksByTitle();

  ABST<Book> leafBook = new Leaf<Book>(this.byPrice);
  ABST<Book> bst1 = new Node<Book>(this.byPrice, b2,
      leafBook, leafBook);
  ABST<Book> bst1a = new Node<Book>(this.byPrice, b2,
      new Node<Book>(this.byPrice, b1, leafBook, leafBook), leafBook);
  ABST<Book> bst2 = new Node<Book>(this.byPrice, b4,
      leafBook, leafBook);
  ABST<Book> bst3 = new Node<Book>(this.byPrice, b3,
      bst1, bst2);

  /*                    bst3 representation

                               b3
                              /  \
                             b2  b4
                           /  \  /  \
                           L  L  L  L
   */

  // examples for integers

  IntegersByValue byIntValue = new IntegersByValue();

  ABST<Integer> leafInt = new Leaf<Integer>(byIntValue);
  ABST<Integer> intBst1 = new Node<Integer>(this.byIntValue, 50,
      leafInt, leafInt);
  ABST<Integer> intBst1a = new Node<Integer>(this.byIntValue, 50,
      new Node<Integer>(this.byIntValue, 10, leafInt, leafInt), leafInt);
  ABST<Integer> intBst2 = new Node<Integer>(this.byIntValue, 100, leafInt, leafInt);
  ABST<Integer> intBst3 = new Node<Integer>(this.byIntValue, 75,
      intBst1, intBst2);

  /*                    intBst3 representation

                                75
                             /      \
                            50      100
                          /   \     /  \
                        leaf leaf leaf leaf
   */

  ABST<Integer> intBst4 = new Node<Integer>(this.byIntValue, 75,
      intBst1a, intBst2);

  /*                    intBst4 representation

                                75
                             /      \
                            50      100
                          /   \     /  \
                        10   leaf leaf leaf
                       /  \
                     leaf leaf
   */

  ABST<Integer> intBst5 = new Node<Integer>(this.byIntValue, 75,
      leafInt, intBst2);

  /*                    intBst5 representation

                                75
                             /      \
                           leaf      100
                                     /  \
                                    leaf leaf
   */

  ABST<Book> bstA = new Node<Book>(this.byPrice, b3,
      new Node<Book>(this.byPrice, b2,
          new Node<Book>(this.byPrice, b1, leafBook, leafBook), leafBook),
      new Node<Book>(this.byPrice, b4, leafBook, leafBook));

  ABST<Book> bstB = new Node<Book>(this.byPrice, b3,
      new Node<Book>(this.byPrice, b2,
          new Node<Book>(this.byPrice, b1, leafBook, leafBook), leafBook),
      new Node<Book>(this.byPrice, b4, leafBook, leafBook));

  ABST<Book> bstC = new Node<Book>(this.byPrice, b2,
      new Node<Book>(this.byPrice, b1, leafBook, leafBook),
      new Node<Book>(this.byPrice, b4,
          new Node<Book>(this.byPrice, b3, leafBook, leafBook), leafBook));

  ABST<Book> bstD = new Node<Book>(this.byPrice, b3,
      new Node<Book>(this.byPrice, b1, leafBook, leafBook),
      new Node<Book>(this.byPrice, b4, leafBook,
          new Node<Book>(this.byPrice, b5, leafBook, leafBook)));

  /*
              bstA:       bstB:       bstC:       bstD:
               b3          b3          b2          b3
              /  \        /  \        /  \        /  \
             b2  b4      b2  b4      b1   b4     b1   b4
            /           /                /             \
          b1           b1               b3              b5
   */

  // examples for list of books

  IList<Book> mtListBook = new MtList<Book>();
  IList<Book> listBook1 = new ConsList<Book>(b4, mtListBook);
  IList<Book> listBook2 = new ConsList<Book>(b3, listBook1);
  IList<Book> listBook3 = new ConsList<Book>(b2, listBook2);
  IList<Book> listBook4 = new ConsList<Book>(b1, listBook3);


  // tests for insert
  void testInsert(Tester t) {
    t.checkExpect(this.leafBook.insert(b1), new Node<Book>(this.byPrice, b1, leafBook, leafBook));
    t.checkExpect(this.bst3.insert(b1), new Node<Book>(this.byPrice, b3, bst1a, bst2));
    t.checkExpect(this.leafInt.insert(75),
        new Node<Integer>(this.byIntValue, 75, leafInt, leafInt));
    t.checkExpect(this.intBst3.insert(10),
        new Node<Integer>(this.byIntValue, 75, intBst1a, intBst2));
  }

  // tests for present
  void testPresent(Tester t) {
    t.checkExpect(this.leafBook.present(b1), false);
    t.checkExpect(this.bst3.present(b2), true);
    t.checkExpect(this.bst3.present(b3), true);
    t.checkExpect(this.bst3.present(b1), false);
    t.checkExpect(this.leafInt.present(75), false);
    t.checkExpect(this.intBst3.present(100), true);
    t.checkExpect(this.intBst3.present(50), true);
    t.checkExpect(this.intBst3.present(12), false);
  }

  // tests for getLeftmost
  void testGetLeftmost(Tester t) {
    t.checkException(new RuntimeException("No leftmost item of an empty tree"), this.leafBook,
        "getLeftmost");
    t.checkExpect(this.bst3.getLeftmost(), b2);
    t.checkException(new RuntimeException("No leftmost item of an empty tree"), this.leafInt,
        "getLeftmost");
    t.checkExpect(this.intBst3.getLeftmost(), 50);
    t.checkExpect(this.intBst4.getLeftmost(), 10);
  }

  // tests for getLeftmostHelp
  void testGetLeftmostHelp(Tester t) {
    t.checkExpect(this.leafBook.getLeftmostHelp(b1), b1);
    t.checkExpect(this.bst3.getLeftmostHelp(b3), b2);
    t.checkExpect(this.leafInt.getLeftmostHelp(75), 75);
    t.checkExpect(this.intBst3.getLeftmostHelp(75), 50);
    t.checkExpect(this.intBst4.getLeftmostHelp(75), 10);
  }

  // tests for getRight
  void testGetRight(Tester t) {
    t.checkException(new RuntimeException("No right of an empty tree"), this.leafBook, "getRight");
    t.checkException(new RuntimeException("No right of an empty tree"), this.leafInt, "getRight");
    t.checkExpect(this.intBst4.getRight(), intBst3);
    t.checkExpect(this.intBst5.getRight(), intBst2);
  }

  // tests for sameTree
  void testSameTree(Tester t) {
    t.checkExpect(this.leafBook.sameTree(this.leafBook), true);
    t.checkExpect(this.leafBook.sameTree(this.bst3), false);
    t.checkExpect(this.bst3.sameTree(this.bst3), true);
    t.checkExpect(this.bst3.sameTree(this.bst1), false);
    t.checkExpect(this.leafInt.sameTree(this.leafInt), true);
    t.checkExpect(this.leafInt.sameTree(this.intBst3), false);
    t.checkExpect(this.intBst3.sameTree(this.intBst3), true);
    t.checkExpect(this.intBst3.sameTree(this.intBst1), false);
    t.checkExpect(this.bstA.sameTree(this.bstB), true);
    t.checkExpect(this.bstA.sameTree(this.bstC), false);
    t.checkExpect(this.bstA.sameTree(this.bstD), false);
  }

  // tests for sameLeaf
  void testSameLeaf(Tester t) {
    t.checkExpect(this.leafBook.sameLeaf(new Leaf<Book>(this.byPrice)), true);
    t.checkExpect(this.leafInt.sameLeaf(new Leaf<Integer>(this.byIntValue)), true);
    t.checkExpect(this.intBst3.sameLeaf(new Leaf<Integer>(this.byIntValue)), false);
  }

  // tests for sameNode
  void testSameNode(Tester t) {
    t.checkExpect(this.leafBook.sameNode(new Node<Book>(this.byPrice, b1, leafBook, leafBook)),
        false);
    t.checkExpect(this.bst3.sameNode(new Node<Book>(this.byPrice, b3, bst1, bst2)),
        true);
    t.checkExpect(this.bst3.sameNode(new Node<Book>(this.byPrice, b3, bst1a, bst2)),
        false);
    t.checkExpect(this.leafInt.sameNode(
        new Node<Integer>(this.byIntValue, 75, leafInt, leafInt)), false);
    t.checkExpect(this.intBst3.sameNode(
        new Node<Integer>(this.byIntValue, 75, intBst1, intBst2)), true);
    t.checkExpect(this.intBst3.sameNode(
        new Node<Integer>(this.byIntValue, 75, intBst1a, intBst2)), false);
  }

  // tests for sameData
  void testSameData(Tester t) {
    t.checkExpect(this.bstA.sameData(this.bstB), true);
    t.checkExpect(this.bstA.sameData(this.bstC), true);
    t.checkExpect(this.bstA.sameData(this.bstD), false);
  }

  // tests for sameNodeData
  void testSameNodeData(Tester t) {
    t.checkExpect(this.bstA.sameLeafData(new Leaf<Book>(this.byPrice)), false);
    t.checkExpect(this.bstA.sameNodeData(new Node<Book>(this.byPrice, b2,
        new Node<Book>(this.byPrice, b1, leafBook, leafBook),
        new Node<Book>(this.byPrice, b4,
            new Node<Book>(this.byPrice, b3, leafBook, leafBook), leafBook))
    ), true);
    t.checkExpect(this.bstA.sameNodeData(new Node<Book>(this.byPrice, b3,
        new Node<Book>(this.byPrice, b1, leafBook, leafBook),
        new Node<Book>(this.byPrice, b4, leafBook,
            new Node<Book>(this.byPrice, b5, leafBook, leafBook)))), false);
    t.checkExpect(this.leafBook.sameNodeData(new Node<Book>(this.byPrice, b5, leafBook, leafBook)),
        false);
  }

  // tests for sameLeafData
  void testSameLeafData(Tester t) {
    t.checkExpect(this.bstA.sameLeafData(new Leaf<Book>(this.byPrice)), false);
    t.checkExpect(this.leafBook.sameLeafData(new Leaf<Book>(this.byPrice)),
        true);
  }

  // tests for buildList
  void testBuildList(Tester t) {
    t.checkExpect(this.leafBook.buildList(), new MtList<Book>());
    t.checkExpect(this.bstA.buildList(), listBook4);
    t.checkExpect(this.bstB.buildList(), listBook4);
    t.checkExpect(this.bstC.buildList(), listBook4);
  }
}
