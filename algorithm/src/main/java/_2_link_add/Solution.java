package _2_link_add;

public class Solution {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(2,new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5,new ListNode(6, new ListNode(4)));
        ListNode l3 = addTwoNumbers(l1, l2);
        System.out.println(l3.val);
        while((l3 = l3.next) != null){
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
        while (l1 != null || l2 != null || up != 0){
            x = l1 == null ? 0 : l1.val;
            y = l2 == null ? 0 : l2.val;
            mod = x + y + up;
            up = 0;
            if(mod >= 10){
                mod = mod % 10;
                up = 1;
            }
            if(res == null){
                res = new ListNode(mod);
                cur = res;
            } else {
                ListNode node = new ListNode(mod);
                cur.next = node;
                cur = node;
            }
            if(l1 != null) {
                l1 = l1.next;
            }
            if(l2 != null) {
                l2 = l2.next;
            }
            mod = 0;
            x = 0;
            y = 0;
        }
        return res;
    }

}
