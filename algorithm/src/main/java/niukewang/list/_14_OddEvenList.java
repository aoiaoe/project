package niukewang.list;

public class _14_OddEvenList {

    public static void main(String[] args) {
        ListNode head = ListNode.create(new int[]{2,1,3,5,6,4,7});
        head.disp();
        head = oddEvenList2Pointer(head);
        head.disp();
    }

    /**
     * 双指针
     *  思路: 一开始, 奇数指针指向头, 偶数指针指向第二个节点
     *      每遍历一次，均让后继指针指向 下一个的下一个, 然后再移动指针到指针的下一个
     *      知道为空退出
     *      在将偶数节点拼接到奇数节点后
     * @param head
     * @return
     */
    public static ListNode oddEvenList2Pointer(ListNode head) {
        if(head == null || head.next == null || head.next.next == null){
            return head;
        }
        // write code here
        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;
        while (even != null && even.next != null){
            odd.next = odd.next.next;
            odd = odd.next;
            even.next = even.next.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    /**
     * 挨个遍历
     * @param head
     * @return
     */
    public static ListNode oddEvenList (ListNode head) {
        if(head == null || head.next == null || head.next.next == null){
            return head;
        }
        // write code here
        ListNode oddHead = new ListNode(-1), oddSentinel = oddHead;
        ListNode evenHead = new ListNode(-1), evenSentinel = evenHead;
        int index = 0;
        while (head != null){
            if(index % 2 == 0) {
                oddSentinel.next = head;
                oddSentinel = oddSentinel.next;
                head = head.next;
                oddSentinel.next = null;
            } else {
                evenSentinel.next = head;
                evenSentinel = evenSentinel.next;
                head = head.next;
                evenSentinel.next = null;
            }
            index++;
        }
        if(evenHead.next != null){
            oddSentinel.next = evenHead.next;
            evenHead.next = null;
        }
        return oddHead.next;
    }
}
