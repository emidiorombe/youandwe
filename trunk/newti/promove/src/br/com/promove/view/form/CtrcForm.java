package br.com.promove.view.form;

import java.util.Locale;

import br.com.promove.application.PromoveApplication;
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

public class CtrcForm extends BaseForm{
	private VerticalLayout form_layout = new VerticalLayout();
	private Button save;
	private Button novo;
	private Button remove;
	private Button veiculo;
	private PromoveApplication app;
	
	private CtrcService ctrcService;
	
	public CtrcForm(PromoveApplication app) {
		ctrcService = ServiceFactory.getService(CtrcService.class);
		this.app = app;
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		save = new Button("Salvar", new CtrcFormListener(this));
		remove = new Button("Excluir", new CtrcFormListener(this));
		novo = new Button("Novo", new CtrcFormListener(this));
		veiculo = new Button("Incluir Veículo", new CtrcFormListener(this));
		
		createFormBody(new BeanItem<Ctrc>(new Ctrc()));
		form_layout.addComponent(this);
		form_layout.addComponent(createFooter());
		//form_layout.setSpacing(true);
		form_layout.setMargin(false, true, false, true);
		
	}

	public void createFormBody(BeanItem<Ctrc> item) {
		setItemDataSource(item);
		setFormFieldFactory(new CtrcFieldFactory(this, item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"filial", "numero", "tipo", "serie", "transp", "dataEmissao", "placaFrota", "placaCarreta", "nomeMotorista", "ufOrigem", "municipioOrigem", "ufDestino", "municipioDestino", "valorMercadoria", "cancelado"});
	}
	
	private Component createFooter(){
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.addComponent(remove);
		footer.addComponent(novo);
		//footer.addComponent(veiculo);
		footer.setVisible(true);
		
		return footer;
	}

	public Component getFormLayout() {
		return form_layout;
	}
	
	public void addNewCtrc() {
		createFormBody(new BeanItem<Ctrc>(new Ctrc()));
	}
	
	class CtrcFieldFactory extends DefaultFieldFactory{
		private boolean newLocal;
		private CtrcForm form;
		public CtrcFieldFactory(CtrcForm form, boolean b) {
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
			
			if(propertyId.equals("filial") || propertyId.equals("numero") || propertyId.equals("tipo")) {
				if(!newLocal)
					f.setReadOnly(true);
				f.addValidator(new IntegerValidator(propertyId.toString() + " deve ser numérico"));
				f.setWidth("100px");
			} else if(propertyId.equals("serie")) {
				if(!newLocal)
					f.setReadOnly(true);
				f.setWidth("100px");
			} else if(propertyId.equals("transp")) {
				try {
					ComboBox c = new ComboBox("Transportadora");
					c.addContainerProperty("label", String.class, null);
				
					for(Transportadora t: ctrcService.buscarTodasTransportadoras()) {
						Item i = c.addItem(t);
						i.getItemProperty("label").setValue(t.getDescricao());
					}
					
					c.setRequired(true);
					c.setRequiredError("Transportadora obrigatória");
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					if(!newLocal)
						c.setReadOnly(true);
					
					return c;
				}catch (PromoveException e) {
					showErrorMessage(form, "Não foi possível buscar as Transportadoras");
				}
			} else if(propertyId.equals("dataEmissao")) {
				PopupDateField data = new PopupDateField("Data");
				data.setResolution(DateField.RESOLUTION_DAY);
				data.setLocale(new Locale("pt", "BR"));
				data.setRequired(true);
				return data;
			} else {
				f.setRequired(false);
				if(propertyId.equals("ufOrigem") || propertyId.equals("ufDestino")) {					
					f.setCaption(f.getCaption().replaceAll("Uf", "UF"));
					f.setWidth("100px");
				} else if(propertyId.equals("nomeMotorista") || propertyId.equals("municipioOrigem") || propertyId.equals("municipioDestino")) {					
					f.setWidth("300px");
				} else if(propertyId.equals("valorMercadoria")) {					
					f.addValidator(new DoubleValidator(propertyId.toString() + " deve ser numérico"));
				/*
				} else if(propertyId.equals("taxaRct") || propertyId.equals("taxaRcf") || propertyId.equals("taxaRr") || propertyId.equals("taxaFluvial")) {
					f.addValidator(new DoubleValidator(propertyId.toString() + " deve ser numérico"));
					f.setCaption(f.getCaption().replaceAll("Rct", "RCT").replaceAll("Rr", "RR").replaceAll("Rcf", "RCF"));
					f.setWidth("100px");
				*/
				}
			}
			return f;
		}
		
	}
	
	class CtrcFormListener implements ClickListener {
		private CtrcForm form;
		public CtrcFormListener(CtrcForm form) {
			 this.form = form;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			BeanItem<Ctrc> item = (BeanItem<Ctrc>) getItemDataSource();
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						ctrcService.salvarCtrc(item.getBean());
						addNewCtrc();
						showSuccessMessage(form, "CTRC salvo!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(form,"Não foi possível salvar CTRC");
				}catch(IllegalArgumentException iae){
					showErrorMessage(form, iae.getMessage());
				}
				
			}else if(event.getButton() == novo){
				addNewCtrc();
			}else if(event.getButton() == remove){
				try {
					if(item.getBean().getId() != null) {
						ctrcService.excluirCtrc(item.getBean());
						showSuccessMessage(form, "CTRC removido");
					}
					addNewCtrc();
				}catch(PromoveException de){
					showErrorMessage(form, "Não foi possível remover CTRC");
				}
			}else if(event.getButton() == veiculo){
				VeiculoCtrcForm form = new VeiculoCtrcForm();
				VeiculoCtrc veic = new VeiculoCtrc();
				veic.setCtrc(item.getBean());
				app.setMainView(form.getFormLayout());
				form.createFormBody(new BeanItem<VeiculoCtrc>(veic));
			}
		}
	}
}
