package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonPropertyOrder({"名前","フリガナ","在職状況","グループ入社日","グループ退職日","雇用体系","メールアドレス","電話番号","事業本部","事業部","法人","拠点統括","拠点","部署","役職","職務","所属区分","所属開始日","所属終了日","出向先"})
@Data
public class csvExport {
	@JsonProperty("名前")
	private String name;
	@JsonProperty("フリガナ")
	private String name_kana;
	@JsonProperty("在職状況")
	private String status;
	@JsonProperty("グループ入社日")
	//@JsonFormat(pattern = "yyyy/MM/dd")
	private String entering_date;
	@JsonProperty("グループ退職日")
	//@JsonFormat(pattern = "yyyy/MM/dd")
	private String leaving_date;
	@JsonProperty("雇用体系")
	private String employment_type;
	@JsonProperty("メールアドレス")
	private String mail_address;
	@JsonProperty("電話番号")
	private String telephone_number;
	@JsonProperty("事業本部")
	private String business_org;
	@JsonProperty("事業部")
	private String division;
	@JsonProperty("法人")
	private String company;
	@JsonProperty("拠点統括")
	private String general_branch;
	@JsonProperty("拠点")
	private String branch;
	@JsonProperty("部署")
	private String department;
	@JsonProperty("役職")
	private String official_position;
	@JsonProperty("職務")
	private String emp_job;
	@JsonProperty("所属区分")
	private String org_kbn;
	@JsonProperty("所属開始日")
	//@JsonFormat(pattern = "yyyy/MM/dd")
	private String start_date;
	@JsonProperty("所属終了日")
	//@JsonFormat(pattern = "yyyy/MM/dd")
	private String end_date;
	@JsonProperty("出向先")
	private String second_company;
	
	
	public csvExport() {}

	public csvExport(String name, String name_kana, String status, String entering_date, String leaving_date,
			String employment_type, String mail_address, String telephone_number, String business_org, String division,
			String company, String general_branch, String branch, String department, String official_position,
			String emp_job, String org_kbn, String start_date, String end_date, String second_company) {
		
		this.name = name;
		this.name_kana = name_kana;
		this.status = status;
		this.entering_date = entering_date;
		this.leaving_date = leaving_date;
		this.employment_type = employment_type;
		this.mail_address = mail_address;
		this.telephone_number = telephone_number;
		this.business_org = business_org;
		this.division = division;
		this.company = company;
		this.general_branch = general_branch;
		this.branch = branch;
		this.department = department;
		this.official_position = official_position;
		this.emp_job = emp_job;
		this.org_kbn = org_kbn;
		this.start_date = start_date;
		this.end_date = end_date;
		this.second_company = second_company;
	}
	
}
