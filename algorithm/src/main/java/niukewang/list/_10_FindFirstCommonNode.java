package niukewang.list;

/**
 * 寻找两个链表的第一个公共节点
 *  1 -> 2 -> 3
 *              -> 6 -> 7
 *      4 -> 5
 * 第一个公共节点为: 6
 */
public class _10_FindFirstCommonNode {


    /**
     * 双指针：
     *  思路:
     *      两个有公共串的链表,使用两个指针从头开始遍历, 到了尾结点如果不相遇，则交换两个链表的指针，再次从头遍历
     *      有两种情况:
     *
     *      1、无公共串 假设链表1长10， 链表2长7
     *          当第一次遍历到尾结点后，交换指针，再次遍历,因为都移动了(10+7), 同时到达尾结点
     *          都为null, 退出
     *      2、有公共串 假设链表1    --- 7 --->
     *                                      --- 4 --->
     *                    链表2    --- 3 --->
     *          第一次遍历到达尾结点，交换指针, 再次遍历.
     *          1指针移动了{7 + 4 + 3} 2指针移动了(3 + 4 + 7)
     *          然后同时指向公共节点的第一个节点，判断相等，循环退出
     *
     * @param pHead1
     * @param pHead2
     * @return
     */
    public static ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if(pHead1 == null || pHead2 == null){
            return null;
        }
        ListNode first = pHead1;
        ListNode second = pHead2;
        while (first != second) {
            first = first == null ? second : first.next;
            second = second == null ? first : second.next;
        }
        return first;
    }
}
