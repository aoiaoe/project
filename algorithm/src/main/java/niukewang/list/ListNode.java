package niukewang.list;

class ListNode {
    int val;
    ListNode next = null;
    ListNode(int val) {
        this.val = val;
    }

    public static void disp(ListNode head){
        if(head == null){
            return;
        }
        ListNode dummy = head;
        while (dummy != null){
            System.out.print(dummy.val + " -> ");
            dummy = dummy.next;
        }
        System.out.println();
    }

    public static ListNode create(int n){
        ListNode head = new ListNode(1);
        ListNode dummy = head;
        for (int i = 2; i <= n; i++) {
            ListNode node = new ListNode(i);
            dummy.next = node;
            dummy = node;
        }
        return head;
    }
}