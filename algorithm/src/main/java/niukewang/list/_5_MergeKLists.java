package niukewang.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 合并k个已排序的链表
 * 合并 k个升序的链表并将结果作为一个升序的链表返回其头节点。
 * <p>
 * 数据范围：节点总数0≤n≤5000，每个节点的val满足∣val∣<=1000
 * 要求：时间复杂度O(nlogn)
 */
public class _5_MergeKLists {

    public static void main(String[] args) {
        ListNode head1 = ListNode.create(5);
        ListNode head2 = ListNode.create(6, 5);
        ListNode head3 = ListNode.create(11, 5);
        ListNode head4 = ListNode.create(16, 5);
        ListNode head5 = ListNode.create(20, 5);
        ArrayList<ListNode> heads = new ArrayList<>();
        heads.add(head1);
        heads.add(head2);
        heads.add(head3);
        heads.add(head4);
        heads.add(head5);
        ListNode head = mergeKLists(heads);
        ListNode.disp(head);

    }

    public static ListNode mergeKLists(ArrayList<ListNode> lists) {
        return mergeKLists(lists, 0, lists.size() - 1);
    }

    public static ListNode mergeKLists(ArrayList<ListNode> lists, int start, int end){
        if(start > end){
            return null;
        } else if(start == end){
            return lists.get(start);
        }
        int mid = ( start + end) /2;
        return merge2List(mergeKLists(lists, start, mid), mergeKLists(lists, mid + 1, end));
    }

    public static ListNode merge2List(ListNode left, ListNode right){
        return _4_Merge2SortedLists.Merge(left, right);
    }
}
