package br.com.promove.view.form;

import br.com.promove.application.PromoveApplication;
import br.com.promove.entity.Avaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class AvariaForm extends BaseForm {
	private Button save;
	private VerticalLayout f_layout = new VerticalLayout();
	private AvariaService avariaService;
	
	public AvariaForm() {
		avariaService = ServiceFactory.getService(AvariaService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setCaption("Adicionar Nova Avaria");
		setImmediate(true);
		setSizeFull();
		f_layout.setSpacing(true);
		
		save = new Button("Salvar", new FormButtonListener());
		createFormBody(new BeanItem<Avaria>(new Avaria()));
		f_layout.addComponent(this);
		f_layout.addComponent(createFooter());
		f_layout.setSpacing(true);
		
	}
	
	public Component getFormLayout() {
		return f_layout;
	}
	
	private void createFormBody(Item avaria){
		setItemDataSource(avaria);
		setFormFieldFactory(new AvariaFieldFactory());
		setVisibleItemProperties(new Object[]{"tipo"});
		
		
	}
	
	private Component createFooter(){
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.setVisible(true);
		
		return footer;
	}
	
	private void addFormComponents(GenericFormField...fields){
		for (GenericFormField field : fields) {
			addItemProperty(field.getName(), field);
		}
	}


class AvariaFieldFactory extends DefaultFieldFactory{
	
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field f = super.createField(item, propertyId, uiContext);
		try {
			if(propertyId.equals("tipo")) {
				ComboBox c = new ComboBox("Tipo Avaria");
				c.setRequired(true);
				c.setRequiredError("Tipo de Avaria obrigatório");
				c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
				c.setImmediate(true);
				c.setNullSelectionAllowed(false);
				
				for(TipoAvaria tp: avariaService.buscarTodosTipoAvaria()){
					c.addItem(tp);
				}
				return c;
			}else {
				return f;
			}
		}catch(PromoveException pe) {
				
		}
		return null;
	}
}

class FormButtonListener implements ClickListener{

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == save){
			try{
				commit();
				if(isValid()){
					Property property = getItemProperty("tipo");
					System.out.println(":::::Form valido " + property.getValue());
				}
			}catch(InvalidValueException ive){
				setValidationVisible(true);
			}
			
			
		}
		
	}
	
}
}
