package com.itechart.model.dto;

public class PositionInCompanyDTO {
    private Long id;
    private String position;
    public PositionInCompanyDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
