package niukewang.list;

public class _1_ReverseList {

    public static ListNode ReverseList(ListNode head) {
        //新链表
        ListNode newHead = null;
        while (head != null) {
            //先保存访问的节点的下一个节点，保存起来
            //留着下一步访问的
            ListNode temp = head.next;
            //每次访问的原链表节点都会成为新链表的头结点，
            //其实就是把新链表挂到访问的原链表节点的
            //后面就行了
            head.next = newHead;
            //更新新链表
            newHead = head;
            //重新赋值，继续访问
            head = temp;
        }
        //返回新链表
        return newHead;
    }
//    public static ListNode ReverseList(ListNode head) {
//        if (head == null || head.next == null) {
//            return head;
//        }
//        ListNode sentinel = head;
//        sentinel = sentinel.next;
//        // 断头结点
//        head.next = null;
//        ListNode dummy;
//        while (sentinel != null) {
//            dummy = sentinel;
//            sentinel = sentinel.next;
//            dummy.next = head;
//            head = dummy;
//        }
//        return head;
//    }

    public static void main(String[] args) {
        ListNode.disp(ReverseList(null));

        ListNode head = new ListNode(0);
        ListNode dummy = head;
        for (int i = 1; i < 5; i++) {
            ListNode node = new ListNode(i);
            dummy.next = node;
            dummy = node;
        }
        ListNode.disp(head);
        System.out.println();
        ListNode.disp(ReverseList(head));
    }
}
