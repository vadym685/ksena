package com.company.ksena.entity.api.google_api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResultsItem implements Serializable {

	@SerializedName("formatted_address")
	private String formattedAddress;
	private List<String> types;
	private Geometry geometry;
	private List<AddressComponentsItem> addressComponents;
	private PlusCode plusCode;
	private String placeId;

	public void setFormattedAddress(String formattedAddress){
		this.formattedAddress = formattedAddress;
	}

	public String getFormattedAddress(){
		return formattedAddress;
	}

	public void setTypes(List<String> types){
		this.types = types;
	}

	public List<String> getTypes(){
		return types;
	}

	public void setGeometry(Geometry geometry){
		this.geometry = geometry;
	}

	public Geometry getGeometry(){
		return geometry;
	}

	public void setAddressComponents(List<AddressComponentsItem> addressComponents){
		this.addressComponents = addressComponents;
	}

	public List<AddressComponentsItem> getAddressComponents(){
		return addressComponents;
	}

	public void setPlusCode(PlusCode plusCode){
		this.plusCode = plusCode;
	}

	public PlusCode getPlusCode(){
		return plusCode;
	}

	public void setPlaceId(String placeId){
		this.placeId = placeId;
	}

	public String getPlaceId(){
		return placeId;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"formatted_address = '" + formattedAddress + '\'' + 
			",types = '" + types + '\'' + 
			",geometry = '" + geometry + '\'' + 
			",address_components = '" + addressComponents + '\'' + 
			",plus_code = '" + plusCode + '\'' + 
			",place_id = '" + placeId + '\'' + 
			"}";
		}
}