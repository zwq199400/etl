package com.etl.entity.pojo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;

/**
 * @author zhouwq
 * @date 2020/10/28 10:52
 */

@Data
@ToString
public class BaseEntity{

    private JSONObject param;

    private String index;

    private Integer eventType;
}
