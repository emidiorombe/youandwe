package com.example.promove.form;

import com.example.promove.application.PromoveApplication;
import com.example.promove.entity.Avaria;
import com.vaadin.data.Property;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Form;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AvariaForm extends Form {
	private Button save = new Button("Salvar", new FormButtonListener());
	private Button cancel = new Button("Cancelar", new FormButtonListener());
	private Button edit = new Button("Editar", new FormButtonListener());
	private PromoveApplication app;
	private VerticalLayout f_laytou = new VerticalLayout();
	
	public AvariaForm() {
		buildForm();
	}
	
	
	
	private void buildForm() {
		setWriteThrough(false);
		setCaption("Adicionar Nova Avaria");
		setImmediate(true);
		setSizeFull();
		
		createFormBody();
		f_laytou.addComponent(this);
		f_laytou.addComponent(createFooter());
		
		
		
	}
	
	public Component getFormLayout() {
		return f_laytou;
	}
	
	private void createFormBody(){
		Layout flayout = getLayout();
		
//		g_layout.addComponent(new Label("Campo 1"));
//		g_layout.addComponent(new FluentTextField().add_Validator(new IntegerValidator("Deve ser inteiro")));
		TextField tf = new TextField("Tipo");
		tf.addValidator(new IntegerValidator("Erro int"));
		FluentTextField tipo = new FluentTextField().setName("tipoAvaria").add_validator(new IntegerValidator("Deve ser inteiro"));
		
		addFormComponents(tipo);
		
	}
	
	private Component createFooter(){
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.addComponent(cancel);
		footer.addComponent(edit);
		footer.setVisible(true);
		
		return footer;
	}
	
	private void addFormComponents(GenericFormField...fields){
		for (GenericFormField field : fields) {
			addItemProperty(field.getName(), field);
		}
	}




class FormButtonListener implements ClickListener{

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == save){
			try{
				commit();
				if(isValid()){
					Property property = getItemProperty("tipoAvaria");
					System.out.println(":::::Form valido " + property.getType());
				}
			}catch(InvalidValueException ive){
				System.out.println(":::::Falhou o form");
			}
			
			
		}
		
	}
	
}
}
