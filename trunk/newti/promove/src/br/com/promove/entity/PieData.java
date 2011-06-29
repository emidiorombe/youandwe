package br.com.promove.entity;

public class PieData {
	private String label;
	private String value;
	
	public PieData() {}
	
	public PieData(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "PieData [label=" + label + ", value=" + value + "]";
	}
	
	
	
}
