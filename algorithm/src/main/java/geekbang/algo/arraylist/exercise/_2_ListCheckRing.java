package geekbang.algo.arraylist.exercise;

import geekbang.algo.arraylist.ListNode;

/**
 * 检测链表中的环
 * 快慢指针判断： 如存在环，快慢指针总会相遇退出，
 * 如不存在环，则遇链尾即可退出
 */
public class _2_ListCheckRing {

    public static void main(String[] args) {
        ListNode head = ListNode.createRing2(10);
//        ListNode.display(head);
        System.out.println("链表是否是环:" + checkRing(head));

    }

    private static boolean checkRing(ListNode head) {
        if(head == null || head.next == null){
            return false;
        }

        ListNode slow = head;
        ListNode fast = head.next;


        while(fast != null && fast.next != null){
           if(slow == fast){
               return true;
           }
           slow = slow.next;
           fast = fast.next.next;
        }
        return false;
    }
}
