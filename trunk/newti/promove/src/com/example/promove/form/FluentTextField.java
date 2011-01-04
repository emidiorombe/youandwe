package com.example.promove.form;

import com.vaadin.data.Validator;
import com.vaadin.ui.TextField;

public class FluentTextField extends TextField implements GenericFormField{
	
	private String name;
	
	public FluentTextField add_validator(Validator validator){
		addValidator(validator);
		return this; 
	}

	public String getName() {
		return name;
	}

	public FluentTextField setName(String name) {
		this.name = name;
		return this;
	}
	
	
}
