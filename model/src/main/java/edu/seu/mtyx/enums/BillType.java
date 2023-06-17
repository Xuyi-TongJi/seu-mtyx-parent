package edu.seu.mtyx.enums;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.alibaba.fastjson.serializer.EnumSerializer;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@JSONType(serializer = EnumSerializer.class, deserializer = EnumDeserializer.class, serializeEnumAsJavaBean = true)
@Getter
public enum BillType {
    ORDER(0,"订单佣金"),
    WITHDRAW(1,"提现" ),
    REFUND(1,"订单退款" );

    @EnumValue
    private Integer code ;
    private String comment ;

    BillType(Integer code, String comment ){
        this.code=code;
        this.comment=comment;
    }
}