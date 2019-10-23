package net.atos.bpm.model;

/**
 * Created by mac that represent Code/Name pair
 */
public class ValuePairBean {

	private String code;

	private String name;

	public ValuePairBean(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
