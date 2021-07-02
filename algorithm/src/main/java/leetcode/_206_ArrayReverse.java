package leetcode;

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
}
