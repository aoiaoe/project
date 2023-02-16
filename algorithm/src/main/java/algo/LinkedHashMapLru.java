package algo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 使用LinkedHashMap实现LRU
 * @author jzm
 * @date 2023/2/15 : 11:10
 */
public class LinkedHashMapLru<K,V> extends LinkedHashMap<K, V> {

    /**
     * 配置在map中缓存多少个数据
     */
    private int cacheSize;

    public LinkedHashMapLru(int cacheSize) {
        // 第三个参数为true,则代表按访问顺讯排序，放调用get方法之后，会将获取的元素放到最后，代表最近使用过
        // 缓存满了之后，只需要移除最前面的元素，即可达到lru效果
        super(16, (float) 0.5, true);
        this.cacheSize = cacheSize;
    }
    // 重写是否移除最先放入的数据, 当元素个数大于配置的缓存个数时，就移除最早的元素
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > cacheSize;
    }

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(LinkedHashMapLru.class);
        LinkedHashMapLru<String, Integer> cache = new LinkedHashMapLru<>(10);
        for (int i = 0; i < 12; i++) {
            cache.put("k" + i, i);
        }
        System.out.println("aa");
        log.info("all cache :'{}'",cache);
        cache.get("k3");
        log.info("get k3 :'{}'", cache);
        cache.get("k4");
        log.info("get k4 :'{}'", cache);
        cache.get("k4");
        log.info("get k4 :'{}'", cache);
        cache.put("k" + 13, 13);
        log.info("After running the LRU algorithm cache :'{}'", cache);
    }
}
