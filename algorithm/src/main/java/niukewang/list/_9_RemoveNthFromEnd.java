package niukewang.list;

/**
 * 移除倒数第n个节点
 *
 * 思路1: 双指针
 * 思路2: 遍历寻找k节点，再次遍历删除
 */
public class _9_RemoveNthFromEnd {

    public static void main(String[] args) {
        ListNode head = ListNode.create(2);
        removeNthFromEnd(head, 2).disp();
    }

    /**
     * 双指针
     * 思路:
     *      1、处理掉特殊节点, 既；链表中就1个节点，删除倒数1个节点
     *      2、先虚拟一个头结点，然后利用双指针，找到倒数第k+1个节点，既第k个节点的前序节点
     *      3、让k的前序节点的next指向k的后续节点，实现删除
     *      4、返回虚拟头结点的下一个节点，既头结点
     * @param head
     * @param n
     * @return
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
// write code here
        if (head == null || n < 1) {
            return null;
        } else if (head.next == null && n == 1) {
            return null;
        }
        // 虚拟头结点
        ListNode fakeHead = new ListNode(0);
        fakeHead.next = head;
        head = fakeHead;
        ListNode fast = head, slow = fast;
        // 找到顺数第k个节点
        for (int i = 1; i <= n; i++) {
            fast = fast.next;
            if (i != n && fast == null) {
                return head;
            }
        }
        // 这时候两个指针同时向后移动，当fast到大最后，则slow就是倒数第k + 1个节点
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        // k指向k的下一个节点，既删除k个节点
        slow.next = slow.next.next;
        head = head.next;
        fakeHead.next = null;
        return head;
    }
}
