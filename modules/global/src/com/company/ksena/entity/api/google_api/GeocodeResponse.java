package com.company.ksena.entity.api.google_api;

import java.io.Serializable;
import java.util.List;

public class GeocodeResponse implements Serializable {
	private PlusCode plusCode;
	private List<ResultsItem> results;
	private String status;

	public void setPlusCode(PlusCode plusCode){
		this.plusCode = plusCode;
	}

	public PlusCode getPlusCode(){
		return plusCode;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"GeocodingResponse{" + 
			"plus_code = '" + plusCode + '\'' + 
			",results = '" + results + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}