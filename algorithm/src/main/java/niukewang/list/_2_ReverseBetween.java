package niukewang.list;

/**
 * 链表内指定区间反转
 */
public class _2_ReverseBetween {

    public static void main(String[] args) {
        ListNode head = ListNode.create(2);
        ListNode.disp(head);

        head = reverseBetween(head, 1, 2);

        ListNode.disp(head);

    }
    /**
     *
     * @param head ListNode类
     * @param m int整型
     * @param n int整型
     * @return ListNode类
     */
    public static ListNode reverseBetween(ListNode head, int m, int n) {
        if(m <= 0 || n <= 0 || m >= n){
            return head;
        }
        // write code here
        // 思路 区间反转: x=head(未反转前区间) y(反转区间) z(未反转后区间)
        ListNode z = head;
        ListNode y = null;
        ListNode yEnd = null;
        ListNode temp = null;
        ListNode xEnd = null;
        int index = 1;
        // 如果节点不为空，并且还在结束区间内
        while (z != null && index <= n){
            if(index < m){
                // 如果小于起始区间, 每次循环记录x最后一个节点是什么, 用于最后连接反转后的区间 x.next = y
                xEnd = z;
                z = z.next;
                index++;
                continue;
            }
            if(index == m){
                y = z;
                // 如果等于起始区间， 则记录y的最后一个节点是什么，用于最后连接剩下的未反转的区间 y.next = z
                yEnd = y;
                z = z.next;
                // 断掉y区间的最后一个节点的后继,否则会成环
                yEnd.next = null;
                index++;
                continue;
            }
            // 每次循环将区间内的节点反转
            temp = z;
            z = z.next;
            temp.next = null;
            temp.next = y;
            y = temp;

            index++;
        }
        // 如果x的最后一个节点不为空(区间起始不为1),则拼接y
        if(xEnd != null){
            xEnd.next = y;
        }
        // 凭借剩下的未反转的列表
        if(z != null){
            yEnd.next = z;
        }
        // 如果起始节点是1,则头结点应该是反转后节点的节点y
        if(m == 1){
            head = y;
        }
        return head;
    }
}
