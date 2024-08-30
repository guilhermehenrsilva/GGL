package br.edu.ifsuldeminas.mch.GGL.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GasPriceAPI {

   @GET("otherCountriesGasoline")
   Call<GasPriceResponse> gasPrices(@Header("authorization") String apiKey);
}
