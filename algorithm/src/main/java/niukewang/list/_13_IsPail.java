package niukewang.list;

/**
 * 判断一个链表是否是回文
 * 类似:
 *  1->2->3->2->1
 *  1->2->2->1
 */
public class _13_IsPail {

    public static void main(String[] args) {
        ListNode head1 = ListNode.create(new int[]{1,2,3,2,1});
        ListNode head2 = ListNode.create(new int[]{1,2,2,1});
        ListNode head3 = ListNode.create(new int[]{1,2,2,1,1});
        System.out.println(isPail(head1));
        System.out.println(isPail(head2));
        System.out.println(isPail(head3));
    }

    /**
     * 快慢指针 + 反转后半段链表 + 遍历比较
     * @param head
     * @return
     */
    public static boolean isPail (ListNode head) {
        // write code here
        if(head == null){
            return false;
        } else if (head.next == null){
            return  true;
        }
        // 寻找中间节点
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        // 反转后半段节点
        fast = _1_ReverseList.ReverseList(slow.next);
        slow = head;
        // 源节点和反转后的节点，从头开始遍历比较
        while (slow != null && fast != null){
            if (slow.val != fast.val){
                return false;
            }
            fast = fast.next;
            slow = slow.next;
        }
        return true;
    }
}
