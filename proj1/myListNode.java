/* SListNode.java */

/**
 *  SListNode is a class used internally by the SList class.  An SList object
 *  is a singly-linked list, and an SListNode is a node of a singly-linked
 *  list.  Each SListNode has two references:  one to an object, and one to
 *  the next node in the list.
 *
 *  @author Kathy Yelick and Jonathan Shewchuk
 */

class myListNode {
    int type;
    int amt;
    int starveTime;
    myListNode next;

  /**
   *  SListNode() (with one parameter) constructs a list node referencing the
   *  item "obj".
   */

    myListNode(int type, int amt, int sT) {
	this.type = type;
	this.amt = amt;
	this.starveTime = sT;
	next = null;
  }

  /**
   *  SListNode() (with two parameters) constructs a list node referencing the
   *  item "obj", whose next list node is to be "next".
   */

    myListNode(int type, int amt, int sT, myListNode next) {
	this.type = type;
	this.amt = amt;
	this.starveTime = sT;
	this.next = next;
  }
}

