package niukewang.list;

/**
 * 链表中的节点每k个一组翻转
 * 描述
 * 将给出的链表中的节点每 k个一组翻转，返回翻转后的链表
 * 如果链表中的节点数不是 k 的倍数，将最后剩下的节点保持原样
 * 你不能更改节点中的值，只能更改节点本身。
 *
 * 数据范围：0≤n≤2000，1≤k≤2000，链表中每个元素都满足0≤val≤1000
 * 要求空间复杂度O(1)，时间复杂度O(n)
 * 例如：
 * 给定的链表是1→2→3→4→5
 * 对于k=2, 你应该返回2→1→4→3→5
 * 对于k=3, 你应该返回3→2→1→4→5
 */
public class _3_ReverseKGroup {

    public static void main(String[] args) {
        ListNode head = ListNode.create(1);
        head = reverseKGroup(head, 2);
        ListNode.disp(head);
    }
    /**
     * 另一种思路1： 每次找到前面的K个节点，然后细化成反转链表问题
     * @param head ListNode类
     * @param k int整型
     * @return ListNode类
     */
    public static ListNode reverseKGroup (ListNode head, int k) {
        // write code here
        if(head == null || k < 2){
            return head;
        }
        ListNode sentinel = head;
        // 反转后的总链表
        ListNode xEnd = null;
        // 反转后的每一组的子链表头结点
        ListNode y = null;
        // 反转后的每一组的子链表尾结点
        ListNode yEnd = null;
        ListNode temp = null;
        int count = 0;
        // 是否是第一组
        boolean first = true;
        while (sentinel != null){
            // 检查长度, 不够则跳出循环
            if(count == 0 && !checkLength(sentinel, k)){
                break;
            }
            // 每一组开始,记录翻转后链表的结束节点
            if(count == 0){
                y = yEnd = sentinel;
                sentinel = sentinel.next;
                y.next = null;
                count++;
                continue;
            }
            // 翻转节点
            temp = sentinel;
            sentinel = sentinel.next;
            temp.next = y;
            y = temp;

            count++;
            if(count == k){
                count = 0;
                // 如果是,第一轮循环, 则更新头结点
                if(first){
                    head = y;
                    first = false;
                } else{
                    // 每一组的最后一次循环, 减翻转后的节点拼接到总链表后面
                    xEnd.next = y;
                }
                // 记录反转后总链表的结束节点
                xEnd = yEnd;
            }
        }
        // 将剩下的不足的几点，拼接到总链表的后面
        if (sentinel != null && xEnd != null){
            xEnd.next = sentinel;
        }
        return head;
    }

    // 校验链表长度
    public static boolean checkLength(ListNode head, int k){
        int index = 0;
        ListNode sentinel = head;
        while (sentinel != null){
            index++;
            if(index == k){
                return true;
            }
            sentinel = sentinel.next;
        }
        return false;
    }
}
