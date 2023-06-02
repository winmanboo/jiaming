package com.deepcode.jiaming.uaa.deserializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * 用于解决 JackSon 反序列化问题
 * <br/>
 * If you believe this class is safe to deserialize, please provide an explicit mapping using Jackson annotations or by providing a Mixin.
 * If the serialization is only done by a trusted source, you can also enable default typing.
 * <link>https://stackoverflow.com/questions/70919216/jwtauthenticationtoken-is-not-in-the-allowlist-jackson-issue</link>
 *
 * @author winmanboo
 * @date 2023/6/1 22:30
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonDeserialize(using = SecurityUserDeserializer.class)
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecurityUserMixin {
}
