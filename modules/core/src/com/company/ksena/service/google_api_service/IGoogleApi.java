package com.company.ksena.service.google_api_service;

import com.company.ksena.entity.api.google_api.GeocodeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGoogleApi {

    @GET("geocode/json")
    public Call<GeocodeResponse> getGeocodeByLatLon(
            @Query("key") String apiKey,
            @Query("latlng") String latlng
    );

    @GET("geocode/json")
    public Call<GeocodeResponse> getGeocodeByAddress(
            @Query("key") String apiKey,
            @Query("address") String address
    );
}
