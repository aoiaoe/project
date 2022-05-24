package algo.link;

/**
 * 合并两个有序链表
 * @author jzm
 * @date 2022/5/17 : 10:17
 */
public class _01_Merge2SortedList {

    public static void main(String[] args) {
        LinkNode l1 = LinkNode.createNode(5);
        LinkNode.display(l1);
        LinkNode l2 = LinkNode.createNode(5);
        LinkNode.display(l2);

        LinkNode l3 = merge2SortedList(l1, l2);
        LinkNode.display(l3);
    }

    public static LinkNode merge2SortedList(LinkNode l1, LinkNode l2){
        // 虚拟头结点，避免处理空指针，降低代码复杂性
        LinkNode dummy = new LinkNode(-1), p = dummy;
        LinkNode p1 = l1, p2 = l2;

        // 同时不为空，才做比较
        while (p1 != null && p2 != null){
            if(p1.val < p2.val){
                p.next = p1;
                p1 = p1.next;
            } else {
                p.next = p2;
                p2 = p2.next;
            }
            p = p.next;
        }

        // 如果有剩余的，肯定是更大的，直接拼接到链表后面即可
        if(p1 != null){
            p.next = p1;
        }
        if(p2 != null){
            p.next = p2;
        }

        return dummy.next;
    }
}
