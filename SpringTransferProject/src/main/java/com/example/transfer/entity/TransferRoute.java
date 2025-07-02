package com.example.transfer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transfer_route")
public class TransferRoute {
	
	@Id //メインキー(DBのリーダー格==Primary Key)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String start_line;
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
	
	private String destinationString;
}
