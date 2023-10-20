package leetcode;

/**
 * 删除链表倒数的第N个节点
 */
public class _19_DeleteNthFromEnd {

    public static void main(String[] args) {
        int arr[] = {1,2};
        ListNode listNode = ListNode.create(arr);
        listNode.disp();
        ListNode listNode1 = removeNthFromEnd(listNode, 1);
        listNode1.disp();
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if(head == null){
            return head;
        }
        ListNode fast = head, slow = head;
        int count = 0;
        while (slow != null && fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            count++;
        }
        int length = count * 2 + (fast != null ? 1 : 0);
        if(n > length){
            return head;
        } else if(n == length){
            fast = head.next;
            head.next = null;
            return fast;
        }
        ListNode dummy = new ListNode();
        dummy.next = head;
        fast = dummy;
        for(int i = 0; i < length - n; i++){
            fast = fast.next;
        }
        slow = fast.next;
        fast.next = slow.next;
        slow.next = null;
        dummy.next = null;
        return head;
    }
}
