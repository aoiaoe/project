package niukewang.list;

/**
 * 删除有序列表中相同的元素，使每个元素只出现一次
 *  1 -> 1 -> 2 -> 2 -> 3 ->
 *  1 -> 2 -> 3 ->
 */
public class _15_DeleteDuplicates {

    public static void main(String[] args) {
        ListNode head = ListNode.create(new int[]{1, 1, 1, 2, 2, 3});
        head.disp();
        head = deleteDuplicatesByFindNSame(head);
        head.disp();
    }

    /**
     * 遍历, 获取当前节点，然后使用当前节点的值，向后遍历直到找到不同的节点, 将当前节点后继指向找到的节点
     * @param head
     * @return
     */
    public static ListNode deleteDuplicatesByFindNSame(ListNode head) {
        // write code here
        if(head == null || head.next == null){
            return head;
        }
        ListNode sentinel = head;
        ListNode sentinel2;
        int val;
        while (sentinel != null){
            val = sentinel.val;
            sentinel2 = sentinel.next;
            // 从当前节点向后遍历，直到找到不同的节点
            while (sentinel2 != null){
                if(sentinel2.val != val){
                    break;
                }
                sentinel2 = sentinel2.next;
            }
            sentinel.next = sentinel2;
            sentinel = sentinel.next;
        }
        return head;
    }
    /**
     * 遍历, 如果相同，则将当前后继节点指向 当前后继的后继，继续遍历
     * @param head
     * @return
     */
    public static ListNode deleteDuplicates (ListNode head) {
        // write code here
        if(head == null || head.next == null){
            return head;
        }
        ListNode sentinel = head;
        int val = sentinel.val;
        while (sentinel != null && sentinel.next != null){
            val = sentinel.val;
            if(val == sentinel.next.val){
                sentinel.next = sentinel.next.next;
                continue;
            }
            sentinel = sentinel.next;
        }
        return head;
    }
}
