package br.com.promove.view.form;

import java.util.Locale;

import br.com.promove.entity.Transportadora;
import br.com.promove.entity.Ctrc;
import br.com.promove.entity.VeiculoCtrc;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CtrcService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class VeiculoCtrcForm extends BaseForm{
	private VerticalLayout form_layout = new VerticalLayout();
	private Button save;
	private Button novo;
	private Button remove;
	
	private CtrcService ctrcService;
	
	public VeiculoCtrcForm() {
		ctrcService = ServiceFactory.getService(CtrcService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		save = new Button("Salvar", new VeiculoCtrcFormListener(this));
		remove = new Button("Excluir", new VeiculoCtrcFormListener(this));
		novo = new Button("Novo", new VeiculoCtrcFormListener(this));
		
		createFormBody(new BeanItem<VeiculoCtrc>(new VeiculoCtrc()));
		form_layout.addComponent(this);
		form_layout.addComponent(createFooter());
		//form_layout.setSpacing(true);
		form_layout.setMargin(false, true, false, true);
		
	}

	public void createFormBody(BeanItem<VeiculoCtrc> item) {
		setItemDataSource(item);
		setFormFieldFactory(new CtrcFieldFactory(this, item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"ctrc", "veiculo", "numeroNF", "serieNF", "dataNF", "valorMercadoria"});
	}
	
	private Component createFooter(){
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.addComponent(remove);
		footer.addComponent(novo);
		footer.setVisible(true);
		
		return footer;
	}

	public Component getFormLayout() {
		return form_layout;
	}
	
	public void addNewVeiculoCtrc() {
		createFormBody(new BeanItem<VeiculoCtrc>(new VeiculoCtrc()));
	}
	
	class CtrcFieldFactory extends DefaultFieldFactory{
		private boolean newLocal;
		private VeiculoCtrcForm form;
		public CtrcFieldFactory(VeiculoCtrcForm form, boolean b) {
			this.form = form;
			newLocal = b;
		}

		@Override
		public Field createField(Item item, Object propertyId, Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);
			
			if(f instanceof TextField){
				((TextField)f).setNullRepresentation("");
				f.setRequired(true);
				f.setRequiredError("Preenchimento do campo '" + StringUtilities.capitalize(propertyId.toString()) + "' é obrigatório.");
			}
			
			if(propertyId.equals("ctrc")) {
				f.setReadOnly(true);
				f.setWidth("300px");
			} else if(propertyId.equals("veiculo")) {
				if(!newLocal)
					f.setReadOnly(true);
				f.setWidth("200px");
			} else if(propertyId.equals("dataNF")) {
				PopupDateField data = new PopupDateField("Data");
				data.setResolution(DateField.RESOLUTION_DAY);
				data.setLocale(new Locale("pt", "BR"));
				return data;
			} else if(propertyId.equals("numeroNF")) {
				f.addValidator(new IntegerValidator(propertyId.toString() + " deve ser numérico"));
			} else {
				f.setRequired(false);
				if(propertyId.equals("valorMercadoria")) {					
					f.addValidator(new DoubleValidator(propertyId.toString() + " deve ser numérico"));
				}
			}
			return f;
		}
		
	}
	
	class VeiculoCtrcFormListener implements ClickListener{
		private VeiculoCtrcForm form;
		public VeiculoCtrcFormListener(VeiculoCtrcForm form) {
			 this.form = form;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<VeiculoCtrc> item = (BeanItem<VeiculoCtrc>) getItemDataSource();
						ctrcService.salvarVeiculoCtrc(item.getBean());
						addNewVeiculoCtrc();
						showSuccessMessage(form, "Veículo do CTRC salvo!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(form,"Não foi possível salvar Veículo do CTRC");
				}catch(IllegalArgumentException iae){
					showErrorMessage(form, iae.getMessage());
				}
				
			}else if(event.getButton() == novo){
				addNewVeiculoCtrc();
			}else if(event.getButton() == remove){
				try {
					BeanItem<VeiculoCtrc> item = (BeanItem<VeiculoCtrc>) getItemDataSource();
					if(item.getBean().getId() != null) {
						ctrcService.excluirVeiculoCtrc(item.getBean());
						showSuccessMessage(form, "Veículo do CTRC removido");
					}
					addNewVeiculoCtrc();
				}catch(PromoveException de){
					showErrorMessage(form, "Não foi possível remover Veículo do CTRC");
				}
			}
		}

	}
}
