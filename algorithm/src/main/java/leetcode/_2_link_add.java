package leetcode;

/**
 * 给你两个非空 的链表，表示两个非负的整数。它们每位数字都是按照逆序的方式存储的，并且每个节点只能存储一位数字。
 *
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 *
 * 你可以假设除了数字 0 之外，这两个数都不会以 0开头。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _2_link_add {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        ListNode l3 = addTwoNumbers(l1, l2);
        System.out.println(l3.val);
        while ((l3 = l3.next) != null) {
            System.out.println(l3.val);
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res = null;
        ListNode cur = null;
        int up = 0;
        int mod = 0;
        int x = 0;
        int y = 0;
        while (l1 != null || l2 != null || up != 0) {
            x = l1 == null ? 0 : l1.val;
            y = l2 == null ? 0 : l2.val;
            mod = x + y + up;
            up = 0;
            if (mod >= 10) {
                mod = mod % 10;
                up = 1;
            }
            if (res == null) {
                res = new ListNode(mod);
                cur = res;
            } else {
                ListNode node = new ListNode(mod);
                cur.next = node;
                cur = node;
            }
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
            mod = 0;
            x = 0;
            y = 0;
        }
        return res;
    }

}