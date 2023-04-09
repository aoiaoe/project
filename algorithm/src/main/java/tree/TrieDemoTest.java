package tree;

/**
 * @author jzm
 * @date 2023/4/6 : 17:06
 */
public class TrieDemoTest {

    public static void main(String[] args) {
        Trie root = new Trie('/');
        root.insert("hello");
        root.insert("hi");
        root.insert("hex");
        root.insert("he");
        root.insert("her");
        root.insert("huge");
        root.insert("happy");
        System.out.println("done");

        System.out.println("是否包含 hello：" + root.find("hello"));
        System.out.println("是否包含 hi：" + root.find("hi"));
        System.out.println("是否包含 hug：" + root.find("hug"));
        System.out.println("是否包含 hehe：" + root.find("hehe"));
        System.out.println("是否包含 happy：" + root.find("happy"));
    }
}
