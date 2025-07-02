package com.example.transfer.form;

import lombok.Data;

@Data
public class RegistForm {
	private Integer id;
	
	private String starrt_line;
	private String start_station;
	private Integer start_car;
	private Integer start_door;
	
	private String via_line;
	private String via_station;
	private Integer via_car;
	private Integer via_door;
	
	private String end_line;
	private String end_station;
	private Integer end_car;
	private Integer end_door;
	
	private String destination;
	

}
