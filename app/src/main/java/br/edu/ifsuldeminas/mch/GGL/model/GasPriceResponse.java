package br.edu.ifsuldeminas.mch.GGL.model;

import java.util.List;

public class GasPriceResponse {
    private String filter;
    private boolean success;
    private List<GasolinePrice> results;

    // Getters e Setters
    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<GasolinePrice> getResults() {
        return results;
    }

    public void setResults(List<GasolinePrice> results) {
        this.results = results;
    }
}