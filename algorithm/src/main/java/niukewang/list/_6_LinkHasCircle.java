package niukewang.list;

import java.util.HashSet;

/**
 * 检测链表中是否存在环
 * 思路1: 快慢指针
 * 思路2: 哈希表
 */
public class _6_LinkHasCircle {

    public static void main(String[] args) {
        ListNode head = ListNode.create(10);
//        System.out.println(hasCycle(head));
//        System.out.println(hasCycle(null));
//        System.out.println(hasCycle(ListNode.create(1)));
//        System.out.println(hasCycle(ListNode.create(2)));
//        System.out.println(hasCycle(ListNode.create(3)));
        System.out.println(hasCycle(ListNode.createCycle(10)));
//
        System.out.println(hasCycleByHash(head));
        System.out.println(hasCycleByHash(null));
        System.out.println(hasCycleByHash(ListNode.create(1)));
        System.out.println(hasCycleByHash(ListNode.create(2)));
        System.out.println(hasCycleByHash(ListNode.create(3)));
        System.out.println(hasCycleByHash(ListNode.createCycle(10)));
    }

    /**
     * 哈希表
     * 思路: 将所有节点放入set,如果后续节点存在于哈希表中，则说明有环
     * @param head
     * @return
     */
    public static boolean hasCycleByHash(ListNode head){
        if(head == null || head.next == null){
            return false;
        }
        HashSet<ListNode> set = new HashSet<>();
        ListNode sentinel = head;
        while (sentinel.next != null){
           if(set.contains(sentinel)){
               return true;
           }
           set.add(sentinel);
           sentinel = sentinel.next;
        }
        return false;
    }

    /**
     * 快慢指针检测
     * 思路: 利用多个指针，不同步长, 如果任意两个相遇，则说明链表中存在环
     * @param head
     * @return
     */
    public static boolean hasCycle(ListNode head) {
        if(head == null || head.next == null){
            return false;
        }
        ListNode fast,slow;
        fast = slow = head;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
            if(slow == fast){
                return true;
            }

        }

        return false;
    }
}
