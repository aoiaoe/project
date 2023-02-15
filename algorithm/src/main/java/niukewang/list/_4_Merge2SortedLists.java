package niukewang.list;

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
        if(list1 != null){
            temp.next = list1;
        }
        if(list2 != null){
            temp.next = list2;
        }
        return head.next;
    }

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
        ListNode.disp(Merge(head1, null));
    }
}
