package geekbang.algo.arraylist.exercise;

import geekbang.algo.arraylist.ListNode;

import java.util.Random;

/**
 * 删除链表倒数第N个节点
 */
public class _4_LinkDeleteLastN {

    public static void main(String[] args) {
        ListNode head = ListNode.create(10);

        ListNode.display(head);
        int n = new Random().nextInt(10);
        System.out.println("delete last :" + n);
        head = deleteLastN(head, n);

        ListNode.display(head);
    }

    /**
     *
     * @param head  链表
     * @param n     需要删除的节点的倒数index
     * @return
     */
    private static ListNode deleteLastN(ListNode head, int n) {
        if(head == null)
            return head;
        if(n < 1){
            throw new IllegalArgumentException("删除节点的索引不能小于1");
        }
        // todo 需要兼容一个或者两个节点的链表

        // 快慢指针计算链表长度
        int nodeCount = -1;
        ListNode slow = head;
        ListNode fast = head;
        int count = 0;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            count++;
        }
        nodeCount = count * 2;
        // 如果不为空则为奇数,需要+1
        if(fast != null){
             nodeCount += 1;
        }
        if(n > nodeCount){
            throw new IllegalArgumentException("删除节点的索引不能大于链表长度");
        }
        // 计算需要删除的节点的正数index相对于中间节点的位置
        // tmp为需要跳跃的次数 = 需要删除节点正数index - 1
        // 跳到该节点前一个节点
        int tmp = -1;
        if (n > count){
            tmp = nodeCount - n + 1;
        } else {
            // 如果相对于在前半段，则将slow指向head
            tmp = nodeCount - n;
            slow = head;
        }
        count = 0;
        while(count < tmp - 1){
            slow = slow.next;
            count++;
        }
        // 删除节点
        fast = slow.next;
        if(fast != null) {
            slow.next = fast.next;
            fast.next = null;
        }

        return head;
    }
}
