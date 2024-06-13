package leetcode;

public class _24_SwapPairListNode {

    public static void main(String[] args) {
        ListNode listNode = ListNode.create(3);
        listNode.disp();
        ListNode listNode1 = swapPairs(listNode);
        listNode1.disp();
    }

    public static ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode dummy = new ListNode();
        ListNode sentinel = head;
        ListNode tail = dummy;
        while (sentinel != null && sentinel.next != null){
            tail.next = sentinel.next;
            head = sentinel;
            sentinel = sentinel.next.next;
            tail.next.next = head;
            head.next = null;
            tail = head;
        }
        tail.next = sentinel;

        return dummy.next;
    }
}
