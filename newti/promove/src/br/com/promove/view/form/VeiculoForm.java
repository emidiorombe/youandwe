package br.com.promove.view.form;

import java.util.Locale;

import br.com.promove.entity.Cor;
import br.com.promove.entity.Modelo;
import br.com.promove.entity.TipoVeiculo;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.DoubleValidator;
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



public class VeiculoForm extends BaseForm{
	private VerticalLayout form_layout = new VerticalLayout();
	private Button save;
	private Button novo;
	private Button remove;
	
	private CadastroService cadastroService;
	
	public VeiculoForm() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		save = new Button("Salvar", new VeiculoFormListener(this));
		remove = new Button("Excluir", new VeiculoFormListener(this));
		novo = new Button("Novo", new VeiculoFormListener(this));
		
		createFormBody(new BeanItem<Veiculo>(new Veiculo()));
		form_layout.addComponent(this);
		form_layout.addComponent(createFooter());
		form_layout.setSpacing(true);
		form_layout.setMargin(false, true, false, true);
	}

	public void createFormBody(BeanItem<Veiculo> item) {
		setItemDataSource(item);
		setFormFieldFactory(new VeiculoFieldFactory(this, item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"chassi", "modelo", "cor", "dataLancamento", "tipo", "navio", "valorMercadoria"});
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
	
	public void addNewVeiculo() {
		createFormBody(new BeanItem<Veiculo>(new Veiculo()));
	}
	
	class VeiculoFieldFactory extends DefaultFieldFactory{
		private boolean newLocal;
		private VeiculoForm form;
		public VeiculoFieldFactory(VeiculoForm form, boolean b) {
			this.form = form;
			newLocal = b;
		}

		@Override
		public Field createField(Item item, Object propertyId, Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);
			
			if(f instanceof TextField){
				((TextField)f).setNullRepresentation("");
				if(!propertyId.equals("navio")) {
					f.setRequired(true);
					f.setRequiredError("Preenchimento do campo '" + StringUtilities.capitalize(propertyId.toString()) + "' é obrigatório.");
				}
			}
			
			if(propertyId.equals("chassi")) {
				if(!newLocal)
					f.setReadOnly(true);
				f.setWidth("200px");
			}else if(propertyId.equals("modelo")) {
				try {
					ComboBox c = new ComboBox("Modelo");
					c.addContainerProperty("label", String.class, null);
				
					for(Modelo m: cadastroService.buscarTodosModelos()) {
						Item i = c.addItem(m);
						i.getItemProperty("label").setValue(m.getDescricao());
					}
					
					c.setRequired(true);
					c.setRequiredError("Modelo obrigatório");
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					//if (c.getValue() == null && c.size() > 0)
                    	//c.setValue(c.getItemIds().iterator().next());
					
					return c;
				}catch (PromoveException e) {
					showErrorMessage(form, "Não foi possível buscar os Modelos");
				}
			}else if(propertyId.equals("cor")) {
				try {
					ComboBox c = new ComboBox("Cor");
					c.addContainerProperty("label", String.class, null);
				
					for(Cor cor: cadastroService.buscarTodasCores()) {
						Item i = c.addItem(cor);
						i.getItemProperty("label").setValue(cor.getDescricao());
					}
					
					c.setRequired(true);
					c.setRequiredError("Cor obrigatória");
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					return c;
				}catch (PromoveException e) {
					showErrorMessage(form, "Não foi possível buscar as Cores");
				}
			}else if(propertyId.equals("dataLancamento")) {
				PopupDateField data = new PopupDateField("Data");
				data.setResolution(DateField.RESOLUTION_DAY);
				data.setLocale(new Locale("pt", "BR"));
				data.setRequired(true);
				return data;
			}else if(propertyId.equals("tipo")) {
				ComboBox c = new ComboBox("Tipo de Veículo");
				c.addContainerProperty("label", String.class, null);
				
				try {
					for(TipoVeiculo tv: cadastroService.buscarTodosTiposVeiculos()){
						Item i = c.addItem(tv);
						i.getItemProperty("label").setValue(tv.getNome());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				c.setRequired(true);
				c.setRequiredError("Tipo obrigatório");
				c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
				c.setImmediate(true);
				c.setNullSelectionAllowed(false);
				c.setPropertyDataSource(item.getItemProperty(propertyId));
				c.setItemCaptionPropertyId("label");
				
				return c;
			} else if(propertyId.equals("valorMercadoria")) {					
				f.setRequired(false);
				f.addValidator(new DoubleValidator(propertyId.toString() + " deve ser numérico"));
			}
			return f;
		}
		
	}
	
	class VeiculoFormListener implements ClickListener{
		private VeiculoForm form;
		public VeiculoFormListener(VeiculoForm form) {
			 this.form = form;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<Veiculo> item = (BeanItem<Veiculo>) getItemDataSource();
						cadastroService.salvarVeiculo(item.getBean());
						addNewVeiculo();
						showSuccessMessage(form, "Veiculo salvo!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(form,"Não foi possível salvar Veiculo");
				}catch(IllegalArgumentException iae){
					showErrorMessage(form, iae.getMessage());
				}
				
			}else if(event.getButton() == novo){
				addNewVeiculo();
			}else if(event.getButton() == remove){
				try {
					BeanItem<Veiculo> item = (BeanItem<Veiculo>) getItemDataSource();
					if(item.getBean().getId() != null) {
						cadastroService.excluirVeiculo(item.getBean());
						showSuccessMessage(form, "Veiculo removido");
					}
					addNewVeiculo();
				}catch(PromoveException de){
					showErrorMessage(form, "Não foi possível remover Veiculo");
				}
			}
		}

	}
}
