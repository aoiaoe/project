import api.AppRequest;
import client.HelloClient;
import com.alibaba.fastjson.JSON;
import dto.HelloDto;
import response.CommonResponse;
import utils.RSAUtil;
import vo.HelloVo;

public class App {

    /**
     * 公钥
     */
    private static final String PUBLIC_KEY_STRING = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuK2X+4lNbSemjb+/ApdH3Zl6sc9PwwAPVybFsIZdTlHzuVk3eoISy9CRQcc7X/IIdX7sEMPr4C0rjs6KJgz+qgHoYKNhSNzvSM+tL7o5MDirG/67yRUpQOOyDUhwJNncxOga+mZHZozPIZj95r70vuQdbhF8lUmNNia/oKLHztyQMQS+RHQuZnUBCHmUgoYsMoek8CCTQ5vv1PLtDgBAM9zADFqr8QL17nerwoFffDXD+Jj/9v3tMlqWGWdhwu7fMXSjb5+lOggKcwYpwQxcUuIS/+QzOzlTEyaxq9D9qkXe1+ilW6WFSbtiFdPcfZqbUhJEZLtIP8ARw/fP4yr3WwIDAQAB";

    
    public static void main(String[] args) {
        HelloDto dto = new HelloDto();
        dto.setName("sep");
        dto.setChaos(123123);
        AppRequest<HelloDto> request = AppRequest.<HelloDto>builder()
                .appId("000001")
                .url("http://localhost:18080/api/v1/hello")
                .publicKey(RSAUtil.PUBLIC_KEY_STRING)
                .data(dto)
                .build();
        HelloVo pageModelBaseResponse = HelloClient.queryUserList(request);
        System.out.println(JSON.toJSONString(pageModelBaseResponse));
    }

}
