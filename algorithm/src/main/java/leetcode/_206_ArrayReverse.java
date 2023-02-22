package leetcode;

import java.util.Random;

public class _206_ArrayReverse {

    /**
     * 递归反转单链表
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head) {
        if(head==null||head.next==null) {
            return head;
        }
        ListNode node =reverseList(head.next);//返回最后的链表节点
        head.next.next=head;//后一个节点指向自己
        head.next=null;//自己next指向null
        return node;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        ListNode tmp = head;
        for (int i = 1; i < 5; i++) {
            ListNode node = new ListNode(i);
            tmp.next = node;
            tmp = node;
        }
        head = reverseList(head);
        do {
            System.out.println(head.val);
        }while ((head = head.next) != null);
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }


    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                "}";
    }

    public static void disp(ListNode head){
        if(head == null){
            return;
        }
        ListNode dummy = head;
        while (dummy != null){
            System.out.print(dummy.val + " -> ");
            dummy = dummy.next;
        }
        System.out.println();
    }

    public void disp(){
        ListNode dummy = this;
        while (dummy != null){
            System.out.print(dummy.val + " -> ");
            dummy = dummy.next;
        }
        System.out.println();
    }

    public static ListNode create(int n){
        ListNode head = new ListNode(1);
        ListNode dummy = head;
        for (int i = 2; i <= n; i++) {
            ListNode node = new ListNode(i);
            dummy.next = node;
            dummy = node;
        }
        return head;
    }

    public static ListNode create(int[] arr){
        ListNode head = new ListNode(arr[0]);
        ListNode dummy = head;
        for (int i = 1; i <arr.length; i++) {
            ListNode node = new ListNode(arr[i]);
            dummy.next = node;
            dummy = node;
        }
        return head;
    }

    public static ListNode createCycle(int n){
        ListNode head = new ListNode(1);
        ListNode dummy = head;
        int index = new Random().nextInt(n / 2) + 1;
        ListNode cyccleStart = null;
        for (int i = 2; i <= n; i++) {
            ListNode node = new ListNode(i);
            if(i == index){
                cyccleStart = node;
            }
            dummy.next = node;
            dummy = node;
        }
        dummy.next = cyccleStart;
        return head;
    }

    public static ListNode create(int start, int n){
        ListNode head = new ListNode(start);
        ListNode dummy = head;
        for (int i = start + 1; i <= start + n - 1; i++) {
            ListNode node = new ListNode(i);
            dummy.next = node;
            dummy = node;
        }
        return head;
    }
}
