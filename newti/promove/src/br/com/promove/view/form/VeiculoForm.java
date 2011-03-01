package br.com.promove.view.form;

import br.com.promove.entity.Cor;
import br.com.promove.entity.Modelo;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
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
		
	}

	public void createFormBody(BeanItem<Veiculo> item) {
		setItemDataSource(item);
		setFormFieldFactory(new VeiculoFieldFactory(this, item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"chassi", "modelo", "cor"});
		
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
				f.setRequired(true);
				f.setRequiredError("Preenchimento do campo '" + StringUtilities.capitalize(propertyId.toString()) + "' é obrigatório.");
			}
			
			if(propertyId.equals("codigo")) {
				if(!newLocal)
					f.setReadOnly(true);
			}else if(propertyId.equals("modelo")) {
				try {
					ComboBox c = new ComboBox("Modelo");
					c.addContainerProperty("label", String.class, null);
				
					for(Modelo m: cadastroService.buscarTodosModelos()) {
						Item i = c.addItem(m);
						i.getItemProperty("label").setValue(m.getDescricao());
					}
					
					c.setRequired(true);
					c.setRequiredError("Tipo obrigatório");
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					if (c.getValue() ==  null && c.size() > 0)
	                    c.setValue(c.getItemIds().iterator().next());
					
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
					c.setRequiredError("Tipo obrigatório");
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					if (c.getValue() ==  null && c.size() > 0)
	                    c.setValue(c.getItemIds().iterator().next());
					
					return c;
				}catch (PromoveException e) {
					showErrorMessage(form, "Não foi possível buscar as Cores");
				}
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
