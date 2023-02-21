package niukewang.list;

/**
 * @author jzm
 * @date 2023/2/21 : 15:40
 */
public class RemoveDuplicate {

    public static void main(String[] args) {
        ListNode head = ListNode.create(new int[]{1});
        ListNode.disp(head);
        head = removeDuplicate(head);
        ListNode.disp(head);
    }


    public static ListNode removeDuplicate(ListNode head){
        if(head == null || head.next == null){
            return head;
        }
        // 新增头结点，解决头结点被移除的问题
        ListNode newHead = new ListNode(head.val - 1);
        newHead.next = head;
        ListNode sentinel = newHead;
        int val;
        // 如果值相同，删除掉相同节点，并不改变sentinel指向新头结点
        // 如果值不相同(两个比较的节点一定不为空)，则将sentinel指向第二个节点
        // 此处sentinel.next不会空指针
        while (sentinel.next != null && sentinel.next.next != null){
            // 判断相邻两个节点值是否相等
            if(sentinel.next.val == sentinel.next.next.val){
                val = sentinel.next.val;
                // 将所有值相等的节点都跳过,包括自己
                while (sentinel.next != null && val == sentinel.next.val){
                    sentinel.next = sentinel.next.next;
                }
            } else {
                // 不相等，则移动哨兵节点
                sentinel = sentinel.next;
            }
        }
        return newHead.next;
    }
}
