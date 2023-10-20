package niukewang.list;

/**
 * 合并两个排序的列表
 */
public class _4_Merge2SortedLists {

    public static ListNode Merge(ListNode list1,ListNode list2) {
        if(list1 == null){
            return list2;
        }
        if (list2 == null){
            return list1;
        }
        ListNode head = new ListNode(0);
        ListNode temp = head;
        while (list1 != null && list2 != null){
            if(list1.val < list2.val){
                temp.next = list1;
                list1 = list1.next;
            } else {
                temp.next = list2;
                list2 = list2.next;
            }
            temp = temp.next;
        }
        temp.next = list1 != null ? list1 : list2;
        return head.next;
    }

    public ListNode mergeTwoLists(ListNode a, ListNode b) {
        if (a == null || b == null) {
            return a != null ? a : b;
        }
        ListNode head = new ListNode(0);
        ListNode tail = head, aPtr = a, bPtr = b;
        while (aPtr != null && bPtr != null) {
            if (aPtr.val < bPtr.val) {
                tail.next = aPtr;
                aPtr = aPtr.next;
            } else {
                tail.next = bPtr;
                bPtr = bPtr.next;
            }
            tail = tail.next;
        }
        tail.next = (aPtr != null ? aPtr : bPtr);
        return head.next;
    }
//    作者：力扣官方题解
//    链接：https://leetcode.cn/problems/merge-k-sorted-lists/solutions/219756/he-bing-kge-pai-xu-lian-biao-by-leetcode-solutio-2/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

    public static void main(String[] args) {
        ListNode head1 = new ListNode(0);
        ListNode dummy1 = head1;
        for (int i = 2; i < 10; i=i+2) {
            ListNode node = new ListNode(i);
            dummy1.next = node;
            dummy1 = node;
        }

        ListNode head2 = new ListNode(1);
        ListNode dummy2 = head2;
        for (int i = 3; i < 10; i=i+2) {
            ListNode node = new ListNode(i);
            dummy2.next = node;
            dummy2 = node;
        }

        ListNode.disp(head1);
        System.out.println();
        ListNode.disp(head2);
        System.out.println();
        ListNode.disp(Merge(head1, head2));
    }
}
