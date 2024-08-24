package com.wzh.entity;

public class Integralevent {
    private Integer id;
    private Integer protagonistId;
    private String eventInfo;
    private Integer integralVariation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProtagonistId() {
        return protagonistId;
    }

    public void setProtagonistId(Integer protagonistId) {
        this.protagonistId = protagonistId;
    }

    public String getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }

    public Integer getIntegralVariation() {
        return integralVariation;
    }

    public void setIntegralVariation(Integer integralVariation) {
        this.integralVariation = integralVariation;
    }
}
