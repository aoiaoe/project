package niukewang.list;

/**
 * 链表中倒数最后k个结点
 */
public class _8_FindKthToTail {

    public static void main(String[] args) {
        ListNode head = ListNode.create(11);
        head.disp();
        FindKthToTailByMoveKStep(head, 10).disp();
    }

    /**
     * 快慢指针
     * 思路：
     * @param pHead
     * @param k
     * @return
     */
    public static ListNode FindKthToTailByMoveKStep(ListNode pHead, int k) {
        // write code here
        if(pHead == null || k < 1){
            return null;
        }
        ListNode fast = pHead, slow = fast;
        // 找到顺数第k个节点
        for (int i = 1; i < k; i++) {
            fast = fast.next;
            if(fast == null){
                return null;
            }
        }
        // 这时候两个指针同时向后移动，当fast到大最后，则slow就是倒数第k个节点
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
    /**
     * 快慢指针 + 再次遍历
     * 思路:
     * 先找到中点，再从头或者从中点遍历
     * @param pHead
     * @param k
     * @return
     */
    public static ListNode FindKthToTail (ListNode pHead, int k) {
        // write code here
        if(pHead == null || k < 1){
            return null;
        } else if(pHead.next == null && k == 1){
            return pHead;
        }

        // 先用快慢指针，快指针到终点获得总链表长度，慢指针到中间，获取中点长度
        int mid = 0,length = 0;
        ListNode fast = pHead, slow = fast;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            mid++;
        }
        // 现在slow处于中点, mid则为中点的步数
        length = mid * 2 + (fast != null ? 1 : 0);
        // 如果长度不足k个
        if(length < k){
            return null;
        }
        if(k < mid){
            for (int i = mid; i < length - k; i++){
                slow = slow.next;
            }
        } else {
            slow = pHead;
            for (int i = 0; i < length - k; i ++){
                slow = slow.next;
            }
        }
        return slow;
    }

}
