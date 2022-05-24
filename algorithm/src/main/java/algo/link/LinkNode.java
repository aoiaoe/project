package algo.link;

import java.util.Random;

/**
 * @author jzm
 * @date 2022/5/17 : 10:18
 */
public class LinkNode {

    public int val;
    public LinkNode next;

    public LinkNode(int val, LinkNode next){
        this.val = val;
    }

    public LinkNode(int val){
        this.val = val;
    }

    public static void display(LinkNode head){
        if(head == null){
            return;
        }
        LinkNode p = head;
        do {
            System.out.print(p.val + " ");
            p = p.next;
        } while (p != null);
        System.out.println();
    }

    public static LinkNode createNode(int num){
        Random random = new Random();
        int counter = random.nextInt(10);
        LinkNode head = new LinkNode(counter), p = head;
        for (int i = 1; i < num; i++) {
            counter += random.nextInt(10);
            p.next = new LinkNode(counter);
            p = p.next;
        }
        return head;
    }
}
