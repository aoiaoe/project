package leetcode;

public class _25_ReverseKGroup {

    public static void main(String[] args) {
        ListNode listNode = ListNode.create(10);
        listNode.disp();
        ListNode listNode1 = reverseKGroup(listNode, 2);
        listNode1.disp();
    }

    static ListNode subTail = null;
    public static ListNode reverseKGroup(ListNode head, int k) {

        ListNode dummy = new ListNode();
        int count = 0;
        ListNode sentinel = head;
        // 记录当前子链表的最后一个节点,用于断链,形成自联表
        // 其实也可以修改链表翻转算法,添加元素K个,大于K个不处理即可
        ListNode tmp = null;
        // 记录当前已翻转链表的尾结点
        // 和子链表不使用同一个节点是因为,存在链表末尾不足K个元素的情况
        ListNode tail = dummy;
        // 每K个元素组成一个链表翻转，然后加入到新链表后
        while (head != null && count < k){
            if(tail == null && tail != dummy ){
                tail = head;
            }
            tmp = head;
            head = head.next;
            count++;
            // 元素到达K个,翻转加入链表末尾
            if(count == k){
                //
                tmp.next = null;
                tail.next = reverseGroup(sentinel);
                sentinel = head;
                tail = subTail;
                count = 0;
            }

        }
        // 剩余不足K个元素的链表直接加入链表末尾
        if(subTail != null) {
            subTail.next = sentinel;
        }
        return dummy.next;
    }
    // 翻转链表
    public static ListNode reverseGroup(ListNode head){
        ListNode dummy = null;
        ListNode sentinel = null;
        subTail = null;
        while (head != null){
            if(subTail == null){
                subTail = head;
            }
            sentinel = head;
            head = head.next;
            sentinel.next = dummy;
            dummy = sentinel;
        }
        return dummy;
    }
}
