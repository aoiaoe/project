package niukewang.list;

import java.math.BigDecimal;

/**
 * 链表相加
 * 9 -> 3 -> 7
 * +    6 -> 3
 * --------------
 * 1 -> 0 -> 0 -> 0
 */
public class _11_AddInList {

    public static void main(String[] args) {
//        ListNode head1 = ListNode.create(3);
//        ListNode head2 = ListNode.create(4);
        int[] arr1 = new int[]{5, 9, 2, 3, 7, 4, 9, 9, 0, 2, 6, 6, 1, 3, 8, 3, 2, 1, 9, 8, 4, 3, 1, 3, 3, 7, 5, 3, 9, 3, 1, 3, 1};
        int[] arr2 = new int[]{4, 2, 8, 3, 5, 1, 0, 5, 7, 4, 5, 0, 2, 5, 0, 3, 9, 7, 3, 6, 8, 4, 4, 9, 7, 1};
        ListNode head1 = ListNode.create(arr1);
        ListNode head2 = ListNode.create(arr2);
        head1.disp();
        head2.disp();
        addInList(head1, head2).disp();
    }

    public static ListNode addInList(ListNode head1, ListNode head2) {
        if (head1 == null) {
            return head2;
        } else if (head2 == null) {
            return head1;
        }
        head1 = _1_ReverseList.ReverseList(head1);
        head2 = _1_ReverseList.ReverseList(head2);
        ListNode head = new ListNode(-1);
        ListNode sentinel = head;
        int over = 0;
        while (head1 != null && head2 != null) {
            over = over + (head1 != null ? head1.val : 0);
            over = over + (head2 != null ? head2.val : 0);
            sentinel.next = new ListNode(over % 11);
            over = over / 10;
            sentinel = sentinel.next;
            head1 = head1.next;
            head2 = head2.next;
        }
        if (head1 != null) {
            sentinel.next = head1;
        } else if (head2 != null) {
            sentinel.next = head2;
        }
        while (over > 0) {
            sentinel = sentinel.next;
            over = sentinel.val + over;
            sentinel.val = over % 10;
            over = over / 10;
        }
        head = head.next;
        return _1_ReverseList.ReverseList(head);
    }

    /**
     * 将链表中的数字转换成int型,适合长度小于10的链表
     * 将链表中的数字转换成long型,适合长度小于20左右的链表
     *
     * @param head1
     * @param head2
     * @return
     */
    public static ListNode addInListByChange2Num(ListNode head1, ListNode head2) {
        if (head1 == null) {
            return head2;
        } else if (head2 == null) {
            return head1;
        }
        // write code here
        int x = 0, y = 0;
        ListNode cur = head1;
        ListNode cur2 = head2;
        while (cur != null || cur2 != null) {
            if (cur != null) {
                x = x * 10 + cur.val;
                cur = cur.next;
            }
            if (cur2 != null) {
                y = y * 10 + cur2.val;
                cur2 = cur2.next;
            }
        }
        cur = null;
        int res = x + y;
        while (res > 0) {
            cur2 = new ListNode(res % 10);
            cur2.next = cur;
            cur = cur2;
            res /= 10;
        }
        return cur;
    }
}
