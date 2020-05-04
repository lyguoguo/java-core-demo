package com.gly.elastic.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MetricBean {
    String vhlSta;
    String speed;
    String traMode;
    String batCur;
    String ins;
    String vin;
}
