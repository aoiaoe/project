package niukewang.list;

import java.util.Random;

class ListNode {
    int val;
    ListNode next = null;
    ListNode(int val) {
        this.val = val;
    }

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