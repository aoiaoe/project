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

    /**
     * 声明已给新头结点，定义一个哨兵指向头结点
     * 遍历链表，每遍历一个节点，则从链表上移除节点插入到新链表newHead中,sentinel依次记录链表中剩余节点的头结点
     *
     *  a ----------> b----------> c----------> d----------> e----------> f
     *  ↑
     *  sentinel
     *
     *  null <------- a
     *                ↑
     *               newHead
     * @param head
     * @return
     */
    public static ListNode reverseList_v2(ListNode head){
        if(head == null || head.next == null){
            return head;
        }
        ListNode newHead = null, sentinel = head;
        ListNode temp = null;
        while (sentinel != null){
            temp = sentinel;
            sentinel = sentinel.next;
            temp.next = newHead;
            newHead = temp;
        }
        return newHead;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        ListNode tmp = head;
        for (int i = 1; i < 5; i++) {
            ListNode node = new ListNode(i);
            tmp.next = node;
            tmp = node;
        }
        tmp = head;
        do {
            System.out.print(tmp.val + " -> ");
        }while ((tmp = tmp.next) != null);

        head = reverseList_v2(head);
        System.out.println();
        do {
            System.out.print(head.val + " -> ");
        }while ((head = head.next) != null);
    }
}

