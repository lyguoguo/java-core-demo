package com.gly.elastic.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleLoginInfo {
    private String aveFuelConsumption;
    private String averageElectricConsumption;
    private String icu_avg;
    private String loc;
    private String locationPoint;
    private String modelCode;
    private String odometer;
    private String sysTimestamp;
    private String vin;
}
