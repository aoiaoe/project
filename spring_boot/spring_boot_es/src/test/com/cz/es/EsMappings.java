package com.cz.es;

/**
 * @author jzm
 * @date 2024/6/13 : 19:16
 */
public class EsMappings {

    public static final String AGG_TEST_MAPPINGS = "{" +
            "    \"properties\": {" +
            "      \"name\": {" +
            "        \"type\": \"text\"" +
            "      }," +
            "      \"sex\": {" +
            "        \"type\": \"keyword\"" +
            "      }," +
            "      \"buyCount\": {" +
            "        \"type\": \"long\"" +
            "      }," +
            "      \"createMonth\": {" +
            "        \"type\": \"keyword\"" +
            "      }" +
            "    }" +
            "  }";
}
