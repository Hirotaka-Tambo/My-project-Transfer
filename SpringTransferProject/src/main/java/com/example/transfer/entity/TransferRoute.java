package com.example.transfer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "transfer_route")
public class TransferRoute {
	
	@Id //メインキー(DBのリーダー格==Primary Key)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// javaの規格に則して、snale_caseからcamelCaseに変数名を統一
	//　学習のために、@Columnアノテーションを明示的に記述。
	
	// startセクション
	@Column(name = "start_line")
	private String startLine;
	@Column(name = "start_station")
	private String startStation;
	@Column(name = "start_car")
	private Integer startCar;
	@Column(name = "start_door")
	private Integer startDoor;
	
	
	// viaセクション
	@Column(name = "via_line")
	private String viaLine;
	@Column(name = "via_station")
	private String viaStation;
	@Column(name = "via_car")
	private Integer viaCar;
	@Column(name = "via_door")
	private Integer viaDoor;
	
	
	
	// endセクション
	@Column(name = "end_line")
	private String endLine;
	@Column(name = "end_station")
	private String endStation;
	@Column(name = "end_car")
	private Integer endCar;
	@Column(name = "end_door")
	private Integer endDoor;
	
	private String destination;
}
