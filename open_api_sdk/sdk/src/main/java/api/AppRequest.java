package api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppRequest<T> {

    private String url;
    private String publicKey;
    private String appId;
    private T data;

}
