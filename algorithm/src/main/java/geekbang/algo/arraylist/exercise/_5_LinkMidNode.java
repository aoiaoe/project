package geekbang.algo.arraylist.exercise;

import geekbang.algo.arraylist.ListNode;

/**
 * 获取链表的中间节点
 */
public class _5_LinkMidNode {
    public static void main(String[] args) {
        ListNode head = ListNode.create(1);
        ListNode midNode = getMidNode(head);
        ListNode.display(head);
        System.out.println("中间节点：" + midNode.data);
    }

    private static ListNode getMidNode(ListNode head) {
        if(head == null){
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
