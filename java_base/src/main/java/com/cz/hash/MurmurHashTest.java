package com.cz.hash;

import cn.hutool.core.codec.Base62;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * murmurhash 使用场景: 短链服务
 * murmurhash先将长链hash成十进制数字
 * 还太长，则可以使用base62编码成更短的字符串
 * why base62? 因为相比于base64，只包含数字和字母大小写，去除了+/等符号，更符合url
 */
public class MurmurHashTest {

    private static final char[] CHARS = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private static final int SIZE = CHARS.length;

    @Test
    public void testMurmurHash() {
        String url = "https://www.baidu.com";
        HashFunction hashFunction = Hashing.murmur3_32();
        Long hashed = hashFunction.hashUnencodedChars(url).padToLong();
        System.out.println(hashed);
        System.out.println(convertDecToBase62(hashed));
    }


    private static String convertDecToBase62(long num) {
        if (num == 0) return "0"; // 特殊情况处理：当数字为0时，返回"0"
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            int i = (int) (num % SIZE); // 计算余数
            sb.append(CHARS[i]); // 将余数映射到 Base62 字符
            num /= SIZE; // 更新数字
        }
        return sb.reverse().toString(); // 逆序返回 Base62 编码结果
    }
}
