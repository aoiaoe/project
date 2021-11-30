package geekbang.algo.arraylist.exercise;

import geekbang.algo.arraylist.ListNode;

/**
 * 两个有序链表合并
 */
public class _3_MergeSortedList {

    public static void main(String[] args) {
        ListNode oddList = ListNode.createOdd(10);
        ListNode evenList = ListNode.createEven(10);

        System.out.print("odd -> ");
        ListNode.display(oddList);
        System.out.print("even -> ");
        ListNode.display(evenList);
        ListNode head = merge(oddList, evenList);
        System.out.print("merged -> ");
        ListNode.display(head);
    }

    public static ListNode merge(ListNode l1, ListNode l2){
        ListNode head = null;
        ListNode cur = null;
        if(l1 == null && l2 == null){
            return head;
        } else if(l1 == null && l2 != null){
            return l2;
        } else if(l1 != null && l2 == null){
            return l1;
        }
        if (l1.data.compareTo(l2.data) < 0){
            head = l1;
            l1 = l1.next;
        } else {
            head = l2;
            l2 = l2.next;
        }
        head.next = null;
        cur = head;
        ListNode tmp = null;
        while (l1 != null || l2 != null){
            if(l1 != null && l2 == null){
                tmp = l1;
                l1 = l1.next;
            } else if(l1 == null && l2 != null){
                tmp = l2;
                l2 = l2.next;
            } else {
                // string的比较,从第一位起开始逐位比较,所以10<9
                if (l1.data.compareTo(l2.data) < 0) {
                    tmp = l1;
                    l1 = l1.next;
                } else {
                    tmp = l2;
                    l2 = l2.next;
                }
            }
            tmp.next = null;
            cur.next = tmp;
            cur = tmp;
        }
        return head;
    }
}
