package edu.seu.mtyx.enums;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.alibaba.fastjson.serializer.EnumSerializer;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@JSONType(serializer = EnumSerializer.class, deserializer = EnumDeserializer.class, serializeEnumAsJavaBean = true)
@Getter
public enum UserType {
    USER(0,"会员"),
    LEADER(1,"团长" );

    @EnumValue
    private Integer code ;
    private String comment ;

    UserType(Integer code, String comment ){
        this.code=code;
        this.comment=comment;
    }
}