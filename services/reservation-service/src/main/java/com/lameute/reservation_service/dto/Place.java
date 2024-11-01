package com.lameute.reservation_service.dto;

import lombok.Data;

@Data
public class Place {
    private long id;

    private String name;

    private String town;

    private String longitude;

    private String latitude;

    private String details;
}
