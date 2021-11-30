package geekbang.algo.arraylist;

/**
 * 判断单链表中数据是否是回文
 * 1，快慢指针，慢指针步长1，快指针步长2，当快指针移到最后时，满指针刚好到链表一半，
 * 2，慢指针移动时，将前面的链表反转
 * 3，最后依次比较每个元素
 * 4，将链表恢复原样
 */
public class Palindrome {

    public static void main(String[] args) {
        ListNode head = ListNode.createPalindrome();
        ListNode.display(head);
        System.out.println("是否回文:" + isPalindrome(head));
        ListNode.display(head);
    }

    public static boolean isPalindrome(ListNode head){
        if(head == null || head.data == null){
            return false;
        }
        ListNode pre = null;
        ListNode slow,fast;
        slow = fast = head;

        // 快慢指针移动，并且反转前面一半的链表
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            ListNode next = slow.next;
            slow.next = pre;
            pre = slow;
            slow = next;
        }

        ListNode cur1 = pre;
        ListNode cur2 = slow;
        // 如果元素为偶数个时，需要将慢指针再移动一步
        if(fast != null){
            slow = slow.next;
        }

        // 将前面反转过的链表与剩下的链表元素挨个比较
        while (slow != null){
            if (!slow.data.equals(pre.data)){
                return false;
            }
            slow = slow.next;
            pre = pre.next;
        }

        // 将链表恢复原样
        do {
            ListNode tmp = cur1.next;
            cur1.next = cur2;
            cur2 = cur1;
            cur1 = tmp;
        }while (cur1 != null);
        head = cur2;

        return true;
    }
}
