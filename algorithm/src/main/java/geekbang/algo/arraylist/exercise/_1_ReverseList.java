package geekbang.algo.arraylist.exercise;

import geekbang.algo.arraylist.ListNode;

/**
 * 反转链表
 */
public class _1_ReverseList {

    public static void main(String[] args) {
        ListNode head = ListNode.create(10);
        ListNode.display(head);
        head = reverse(head);
        ListNode.display(head);
    }
    public static ListNode reverse(ListNode head){
        if(head == null || head.next == null)
            return head;
        ListNode cur = head;
        ListNode temp = null;
        while (cur != null){
            // 头节点等于当前节点
            head = cur;
            // 当前节点指向当前的下一个节点
            cur = cur.next;
            // 头节点的下一个节点指向反转后的链表 形成新的反转过的链表
            head.next = temp;
            // 将新反转的链表复制给临时变量
            temp = head;
        }
        return head;
    }
}
