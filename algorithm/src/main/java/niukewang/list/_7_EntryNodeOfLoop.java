package niukewang.list;

import java.util.HashSet;

/**
 * 链表中环的入口结点
 *  * 思路1: 快慢指针+(从头遍历和环中遍历相遇)
 *  * 思路2: 哈希表
 */
public class _7_EntryNodeOfLoop {

    public static void main(String[] args) {
        ListNode head = ListNode.createCycle(10);
        System.out.println(EntryNodeOfLoop(head));
        System.out.println(EntryNodeOfLoopByHash(head));
    }

    /**
     * 快慢指针
     * 思路:
     *      1、先利用快慢指针判断链表中是否存在环
     *      2、快慢指针相遇后，用一个指针从头节点->相遇节点遍历, 另一个指针从相遇节点在环中遍历, 两个指针再次相遇的地方就是环入口
     *  第二步原理:
     *      头结点 --x--> 节点入口 --y-->环中相遇节点
     *                      ↑              |
     *                      --------z-------
     *          设快指针步长是慢指针的2倍, 两个指针在环外走的步数为 x,
     *                                 节点入口到环中相遇节点步数为 y,
     *                                 相遇节点继续遍历到入口节点的步数为 z
     *                                 快指针在环中遍历了 n 圈, 总步数: x + n(y + z) + y;
     *                                 慢指针在环中遍历了 m 圈, 总步数: x + m(y + z) + y;
     *                                 因为相遇了，所以步数相同，有公式 2 * {x + m(y + z) + y} = x + n(y + z) + y
     *                                                            -> 2x + 2m(y + z) + 2y = x + n(y + z) + y
     *                                                            -> x + y = (2m -n)(y + z)(因为环的大小是y+z，说明从链表头经过环入口到达相遇地方经过的距离等于整数倍环的大小)
     *                                                            因为m和n是常数， 化简得 x + y = y + z, 既 x = z
     *                                                            因为x 和 z 都是到入口节点的步数,
     *                                                            所以一个节点从头遍历,一个节点从相遇节点遍历
     *                                                            再次相遇一定是环的入口节点
     *
     * @param pHead
     * @return
     */
    public static ListNode EntryNodeOfLoop(ListNode pHead) {
        if(pHead == null || pHead.next == null){
            return null;
        }
        ListNode fast,slow;
        fast = slow = pHead;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
            // 说明有环
            if(slow == fast){
                slow = pHead;
                while (slow != fast){
                    slow=slow.next;
                    fast=fast.next;
                }
                return slow;
            }

        }

        return null;
    }

    /**
     * 存在于hash表中的节点即是链表的入口节点
     * @param pHead
     * @return
     */
    public static ListNode EntryNodeOfLoopByHash(ListNode pHead) {
        if(pHead == null || pHead.next == null){
            return null;
        }
        HashSet<ListNode> set = new HashSet<>();
        ListNode sentinel = pHead;
        while (sentinel.next != null){
            if(set.contains(sentinel)){
                return sentinel;
            }
            set.add(sentinel);
            sentinel = sentinel.next;
        }
        return null;
    }
}
