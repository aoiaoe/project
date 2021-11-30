package geekbang.algo.arraylist;

import java.util.concurrent.TimeUnit;

public class ListNode {

    public Integer data;
    public ListNode next;

    public ListNode(){}
    public ListNode(Integer data){this.data = data;}
    public ListNode(Integer data, ListNode next){this.data = data;this.next = next;}

    public static void display(ListNode head){
       if(head == null) {
           System.out.println();
           return;
       }
       System.out.print(head.data + " -> ");
       try {
           TimeUnit.MILLISECONDS.sleep(100);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       display(head.next);
    }

    public static ListNode createPalindrome(){
        ListNode A = new ListNode(1);
        ListNode B = new ListNode(2);
        ListNode C = new ListNode(3);
        ListNode C1 = new ListNode(3);
        ListNode B1 = new ListNode(2);
        ListNode A1 = new ListNode(1);
//        ListNode D = new ListNode("D");

        A.next = B;
        B.next = C;
//        C.next = D;
        C.next = C1;
        C1.next = B1;
        B1.next = A1;
        return A;
    }

    public static ListNode create(int x){
        ListNode cur = null;
        ListNode head = cur = new ListNode(1);
        for (int i = 1; i < x; i++) {
            cur.next = new ListNode(i + 1);
            cur = cur.next;
        }
        return head;
    }

    public static ListNode createRing(int x){
        ListNode cur = null;
        ListNode head = cur = new ListNode(1);
        for (int i = 1; i < x; i++) {
            cur.next = new ListNode(i + 1);
            cur = cur.next;
        }
        cur.next = head;
        return head;
    }

    public static ListNode createRing2(int x){
        ListNode cur = null;
        ListNode head = cur = new ListNode(1);
        for (int i = 1; i < x; i++) {
            cur.next = new ListNode(i + 1);
            cur = cur.next;
        }
        cur.next = head;
        ListNode n11 = new ListNode(11, head);
        ListNode n12 = new ListNode(12, n11);
        ListNode n13 = new ListNode(13, n12);
        return n13;
    }

    public static ListNode createOdd(int x) {
        ListNode cur = null;
        ListNode head = cur = new ListNode(1);
        for (int i = 1; i < x; i++) {
            if(i % 2 == 0) {
                cur.next = new ListNode(i + 1);
                cur = cur.next;
            }
        }
        return head;
    }

    public static ListNode createEven(int x) {
        ListNode cur = null;
        ListNode head = cur = new ListNode(0);
        for (int i = 1; i < x; i++) {
            if(i % 2 == 1) {
                cur.next = new ListNode(i + 1);
                cur = cur.next;
            }
        }
        return head;
    }
}
