package com.autowise.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderGoldDto {
    public Long goldSourceId;
    public Long craftId;
    public Double weightPerUnit;
}