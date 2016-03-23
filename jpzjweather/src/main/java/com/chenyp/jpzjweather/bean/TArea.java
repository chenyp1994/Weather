package com.chenyp.jpzjweather.bean;


public class TArea {

	private long id;
	private long areaid;
	private String namecn;
	private String nameen;
	private String districtcn;
	private String districten;
	private String provincecn;
	private String provinceen;
	private String nationen;
	private String nationcn;
	
	private long opklockversion;

	public long getOpklockversion() {
		return opklockversion;
	}

	public void setOpklockversion(long opklockversion) {
		this.opklockversion = opklockversion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAreaid() {
		return areaid;
	}

	public void setAreaid(long areaid) {
		this.areaid = areaid;
	}

	public String getNamecn() {
		return namecn;
	}

	public void setNamecn(String namecn) {
		this.namecn = namecn;
	}

	public String getNameen() {
		return nameen;
	}

	public void setNameen(String nameen) {
		this.nameen = nameen;
	}

	public String getDistrictcn() {
		return districtcn;
	}

	public void setDistrictcn(String districtcn) {
		this.districtcn = districtcn;
	}

	public String getDistricten() {
		return districten;
	}

	public void setDistricten(String districten) {
		this.districten = districten;
	}

	public String getProvincecn() {
		return provincecn;
	}

	public void setProvincecn(String provincecn) {
		this.provincecn = provincecn;
	}

	public String getProvinceen() {
		return provinceen;
	}

	public void setProvinceen(String provinceen) {
		this.provinceen = provinceen;
	}

	public String getNationen() {
		return nationen;
	}

	public void setNationen(String nationen) {
		this.nationen = nationen;
	}

	public String getNationcn() {
		return nationcn;
	}

	public void setNationcn(String nationcn) {
		this.nationcn = nationcn;
	}
}
