package niukewang.list;

/**
 * 链表排序
 * 思路:
 *      1、分治 归并排序
 *      2、转化为数组排序
 */
public class _12_SortInList {

    public static void main(String[] args) {
        ListNode head = ListNode.create(new int[]{1,3,2,4,5});
        head.disp();
        head = sortInList(head);
        head.disp();
    }
    public static ListNode sortInList (ListNode head) {
        // write code here
        return split(head);
    }

    public static ListNode split(ListNode head){
        if(head == null || head.next == null){
            return head;
        }
        ListNode fast = head.next, slow = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        if(slow == fast){
            return slow;
        }
        fast = slow.next;
        slow.next = null;
        slow = head;
        return _4_Merge2SortedLists.Merge(split(slow), split(fast));
    }
}
