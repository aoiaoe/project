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
        ListNode head1 = ListNode.create(new int[]{2,4,3});
        ListNode head2 = ListNode.create(new int[]{5,6,4});
        addTwoNumbers(head1, head2).disp();
    }

    public static ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        int over = 0;
        ListNode head = l1;
        while(l1 != null && l2 != null){
            over = over + l1.val + l2.val;
            l1.val = over % 10;
            over = over / 10;
            l1 = l1.next;
            l2 = l2.next;
        }
        l1 = l1 != null ? l1 : l2;
        if(l1.next == null && over > 0){
            l1.next = new ListNode(over);
            return head;
        }
        l1 = l1.next;
        while(over > 0){
            over += l1.val;
            l1.val = over % 10;
            over /= 10;
            if(l1.next == null && over > 0){
                l1.next = new ListNode(over);
                over = 0;
            }
            l1 = l1.next;
        }
        return head;
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