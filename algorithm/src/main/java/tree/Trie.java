package tree;

import lombok.Getter;
import lombok.Setter;
import sun.swing.StringUIClientPropertyKey;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典树、前缀树
 * @author jzm
 * @date 2023/4/6 : 16:49
 */
@Getter
@Setter
public class Trie {

    private char ch;
    private boolean end;
    private Map<Character, Trie> trie;

    public Trie(char ch){
        this.ch = ch;
        this.trie = new HashMap<>(16);
    }

    public Trie(char ch, boolean end){
        this.ch = ch;
        this.end = end;
        this.trie = new HashMap<>(16);
    }

    public void insert(String target){
        if(target == null || target.length() < 1){
            throw new RuntimeException("Target string cannot be empty!");
        }
        this.insert(this, target);
    }

    public void insert(Trie trie, String target){
        char[] chars = target.toCharArray();
        Trie aim = trie;
        for (int i = 0; i < chars.length; i++) {
            aim = this.insert(aim, chars[i], i == chars.length - 1);
        }
    }

    public Trie insert(Trie trie, char ch, boolean end){
        Trie aim = trie.getTrie().get(ch);
        if(aim == null){
            aim = new Trie(ch, end);
            trie.getTrie().put(ch, aim);
        }
        if(end){
            aim.setEnd(true);
        }
        return aim;
    }

    public boolean find(String target){
        if(target == null || target.length() < 1){
            return false;
        }
        char[] chars = target.toCharArray();
        Trie aim = this;
        for (int i = 0; i < chars.length; i++) {
            aim = aim.getTrie().get(chars[i]);
            if(aim == null){
                return false;
            }
        }
        return aim.isEnd();
    }
}
