package com.example.transfer.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RegistForm {
	
	// IDは通常、フォームの入力には使用せず、表示や更新時に使うことが多い。(バリデーション不要、そのままでok)
	private Integer id;
	
	
	// sqlのsnake_caseからjava規約のcamelCaseに書式の変更(entityで実行済み)
	// 入力の必須項目は@NotBlank(文字型)・@NotNull(数値)をつける

	
	// 出発セクションの入力
	@NotBlank(message = "出発路線は必ず入力してください")
	private String startLine;
	
	@NotBlank(message = "出発駅は必ず入力してください")
	private String startStation;
	
	@NotNull(message = "出発車両番号は必ず入力してください")
	private Integer startCar;
	
	@NotNull(message = "出発ドア番号は必ず入力してください")
	private Integer startDoor;
	
	
	// 経由セクションの入力(任意でOK)
	private String viaLine;
	private String viaStation;
	private Integer viaCar;
	private Integer viaDoor;
	
	
	// 到着セクションの入力
	@NotBlank(message = "到着路線は必ず入力してください")
	private String endLine;
	
	@NotBlank(message = "到着駅は必ず入力してください")
	private String endStation;
	
	@NotNull(message = "到着車両番号は必ず入力してください")
	private Integer endCar;
	
	@NotNull(message = "到着ドア番号は必ず入力してください")
	private Integer endDoor;
	
	
	// 目的地の入力は任意(null判定は不要)
	private String destination;
	

}
