package com.example.transfer.form;

import lombok.Data;

@Data
public class RegistForm {
	private Integer id;
	
	
	// sqlのsnake_caseからjava規約のcamelCaseに書式の変更(entityで実行済み)
	
	
	private String startLine;
	private String startStation;
	private Integer startCar;
	private Integer startDoor;
	
	private String viaLine;
	private String viaStation;
	private Integer viaCar;
	private Integer viaDoor;
	
	private String endLine;
	private String endStation;
	private Integer endCar;
	private Integer endDoor;
	
	private String destination;
	

}
