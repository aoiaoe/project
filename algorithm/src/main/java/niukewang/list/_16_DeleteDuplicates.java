package niukewang.list;

/**
 * 删除有序列表中重复的元素, 只保留出现一次的元素
 * 1 -> 1 -> 1 -> 2 -> 2 -> 3 ->
 * 2 -> 3 ->
 * 因为1和2重复出现，所有重复的都删除
 */
public class _16_DeleteDuplicates {

    public static void main(String[] args) {
        ListNode head = ListNode.create(new int[]{1,1});
        head.disp();
        head = deleteDuplicates(head);
        head.disp();
    }
    public static ListNode deleteDuplicates (ListNode head) {
        // write code here
        if(head == null || head.next == null){
            return head;
        }
        ListNode newHead = new ListNode(head.val - 1);
        newHead.next = head;
        ListNode sentinel = newHead;
        ListNode sentinel2;
        int val;
        boolean flag = false;
        while (sentinel != null && sentinel.next != null){
            val = sentinel.next.val;
            sentinel2 = sentinel.next;
            flag = false;
            // 从当前节点向后遍历，直到找到不同的节点
            while (sentinel2 != null && sentinel2.next != null){
                if(sentinel2.next.val != val){
                    break;
                }
                flag = true;
                sentinel2 = sentinel2.next;
            }
            if()
            sentinel.next = sentinel2;
            sentinel = sentinel2;
        }
        return newHead.next;
    }
}
