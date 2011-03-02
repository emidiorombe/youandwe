package br.com.promove.view.form;


import java.util.Locale;

import br.com.promove.entity.Avaria;
import br.com.promove.entity.Clima;
import br.com.promove.entity.ExtensaoAvaria;
import br.com.promove.entity.LocalAvaria;
import br.com.promove.entity.OrigemAvaria;
import br.com.promove.entity.TipoAvaria;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class AvariaForm extends BaseForm {
	private Button save;
	private Button excluir;
	private Button novo;
	private VerticalLayout f_layout = new VerticalLayout();
	private AvariaService avariaService;
	private CadastroService cadastroService;
	
	public AvariaForm() {
		avariaService = ServiceFactory.getService(AvariaService.class);
		cadastroService = ServiceFactory.getService(CadastroService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setCaption("Adicionar Nova Avaria");
		setImmediate(true);
		setSizeFull();
		f_layout.setSpacing(true);
		
		save = new Button("Salvar", new FormButtonListener(this));
		excluir = new Button("Excluir", new FormButtonListener(this));
		novo = new Button("Novo", new FormButtonListener(this));
		createFormBody(new BeanItem<Avaria>(new Avaria()));
		f_layout.addComponent(this);
		f_layout.addComponent(createFooter());
		f_layout.setSpacing(true);
		
	}
	
	public Component getFormLayout() {
		return f_layout;
	}
	
	public void createFormBody(BeanItem<Avaria> avaria){
		setItemDataSource(avaria);
		setFormFieldFactory(new AvariaFieldFactory(this, avaria.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"veiculo", "tipo", "local", "origem", "extensao", "clima", "dataLancamento","hora", "foto", "observacao"});
		
	}
	
	private Component createFooter(){
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.addComponent(excluir);
		footer.addComponent(novo);
		footer.setVisible(true);
		
		return footer;
	}
	
	public void addNewAvaria() {
		createFormBody(new BeanItem<Avaria>(new Avaria()));
	}
	

class AvariaFieldFactory extends DefaultFieldFactory{
	
	private AvariaForm avariaForm;
	private boolean isNew;

	public AvariaFieldFactory(AvariaForm avariaForm, boolean isNew) {
		this.avariaForm = avariaForm;
		this.isNew = isNew;
	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field f = super.createField(item, propertyId, uiContext);
		
		try {
			if(propertyId.equals("tipo")) {
				ComboBox c = new ComboBox("Tipo Avaria");
				c.addContainerProperty("label", String.class, null);
				
				for(TipoAvaria tp: avariaService.buscarTodosTipoAvaria()){
					Item i = c.addItem(tp);
					i.getItemProperty("label").setValue(tp.getDescricao());
				}
				
				c.setRequired(true);
				c.setRequiredError("Tipo Avaria obrigatório");
				c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
				c.setImmediate(true);
				c.setNullSelectionAllowed(false);
				c.setPropertyDataSource(item.getItemProperty(propertyId));
				c.setItemCaptionPropertyId("label");
				
				if (c.getValue() ==  null && c.size() > 0)
                    c.setValue(c.getItemIds().iterator().next());
				
				
				return c;
			}else if(propertyId.equals("local")) {
				ComboBox c = new ComboBox("Local Avaria");
				c.addContainerProperty("label", String.class, null);
				
				for(LocalAvaria l: avariaService.buscarTodosLocaisAvaria()){
					Item i = c.addItem(l);
					i.getItemProperty("label").setValue(l.getDescricao());
				}
				
				c.setRequired(true);
				c.setRequiredError("Local obrigatório");
				c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
				c.setImmediate(true);
				c.setNullSelectionAllowed(false);
				c.setPropertyDataSource(item.getItemProperty(propertyId));
				c.setItemCaptionPropertyId("label");
				
				if (c.getValue() ==  null && c.size() > 0)
                    c.setValue(c.getItemIds().iterator().next());
				
				
				return c;
			}else if(propertyId.equals("origem")) {
				ComboBox c = new ComboBox("Origem Avaria");
				c.addContainerProperty("label", String.class, null);
				
				for(OrigemAvaria or: avariaService.buscarTodasOrigensAvaria()){
					Item i = c.addItem(or);
					i.getItemProperty("label").setValue(or.getDescricao());
				}
				
				c.setRequired(true);
				c.setRequiredError("Origem obrigatória");
				c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
				c.setImmediate(true);
				c.setNullSelectionAllowed(false);
				c.setPropertyDataSource(item.getItemProperty(propertyId));
				c.setItemCaptionPropertyId("label");
				
				if (c.getValue() ==  null && c.size() > 0)
                    c.setValue(c.getItemIds().iterator().next());
				
				
				return c;
			}else if(propertyId.equals("extensao")) {
				ComboBox c = new ComboBox("Extensão Avaria");
				c.addContainerProperty("label", String.class, null);
				
				for(ExtensaoAvaria ext: avariaService.buscarTodasExtensoesAvaria()){
					Item i = c.addItem(ext);
					i.getItemProperty("label").setValue(ext.getDescricao());
				}
				
				c.setRequired(true);
				c.setRequiredError("Extensão obrigatória");
				c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
				c.setImmediate(true);
				c.setNullSelectionAllowed(false);
				c.setPropertyDataSource(item.getItemProperty(propertyId));
				c.setItemCaptionPropertyId("label");
				
				if (c.getValue() ==  null && c.size() > 0)
                    c.setValue(c.getItemIds().iterator().next());
				
				
				return c;
			}else if(propertyId.equals("clima")) {
				ComboBox c = new ComboBox("Condições Climáticas");
				c.addContainerProperty("label", String.class, null);
				
				for(Clima cl: avariaService.buscarTodosClimas()){
					Item i = c.addItem(cl);
					i.getItemProperty("label").setValue(cl.getDescricao());
				}
				
				c.setRequired(true);
				c.setRequiredError("Condição Climática obrigatório");
				c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
				c.setImmediate(true);
				c.setNullSelectionAllowed(false);
				c.setPropertyDataSource(item.getItemProperty(propertyId));
				c.setItemCaptionPropertyId("label");
				
				if (c.getValue() ==  null && c.size() > 0)
                    c.setValue(c.getItemIds().iterator().next());
				
				
				return c;
			}else if(propertyId.equals("observacao")) {
				TextField tf = new TextField("Observação", "");
				tf.setNullRepresentation("");
				tf.setRows(5);
				tf.setColumns(20);
				tf.setImmediate(true);
				return tf;
			}else if(propertyId.equals("foto")){
				TextField tf = new TextField("Foto");
				tf.setNullRepresentation("");
				tf.setImmediate(true);
				return tf;
			}else if(propertyId.equals("veiculo")){
				TextField tf = new TextField("Chassi");
				tf.setNullRepresentation("");
				tf.setImmediate(true);
				return tf;
			}else if(propertyId.equals("dataLancamento")) {
				PopupDateField data = new PopupDateField("Data");
				data.setResolution(DateField.RESOLUTION_DAY);
				data.setLocale(new Locale("pt", "BR"));
				return data;
			}else if(propertyId.equals("hora")) {
				TextField tf = new TextField("Hora");
				tf.setNullRepresentation("");
				tf.setImmediate(true);
				return tf;
			}
		}catch(PromoveException pe) {
			pe.printStackTrace();
			showErrorMessage(avariaForm, "Não foi possível montar o formulário de Avaria");
		}
		return null;
	}
}

class FormButtonListener implements ClickListener{

	private AvariaForm form;

	public FormButtonListener(AvariaForm form) {
		this.form = form;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == save){
			try{
				validate();
				if(isValid()){
					commit();
					BeanItem<Avaria> item = (BeanItem<Avaria>) getItemDataSource();
					avariaService.salvarAvaria(item.getBean());
					addNewAvaria();
					showSuccessMessage(form, "Avaria salva!");
				}
			}catch(InvalidValueException ive){
				setValidationVisible(true);
			}catch(IllegalArgumentException iae){
				showErrorMessage(form,iae.getMessage());
			}catch(PromoveException de){
				showErrorMessage(form,"Não foi possível salvar Avaria");
			}
			
		}else if(event.getButton() == novo){
			addNewAvaria();
		}else if(event.getButton() == excluir){
			try {
				BeanItem<Avaria> item = (BeanItem<Avaria>) getItemDataSource();
				if(item.getBean().getId() != null) {
					avariaService.excluirAvaria(item.getBean());
					showSuccessMessage(form, "Avaria removida");
				}
				addNewAvaria();
			}catch(PromoveException de){
				showErrorMessage(form, "Não foi possível remover Avaria");
			}
		}
	}
		
	}
}
