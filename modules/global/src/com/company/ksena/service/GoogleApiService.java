package com.company.ksena.service;

import com.company.ksena.entity.api.google_api.GeocodeResponse;

public interface GoogleApiService {
    String NAME = "erp_GoogleApiService";

    void init();

    GeocodeResponse getGeocodeByCoordinates(String apiKey, double lat, double lon);

    GeocodeResponse getGeocodeByAddress(String apiKey, String address);
}