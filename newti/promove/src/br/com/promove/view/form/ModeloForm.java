package br.com.promove.view.form;

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

import br.com.promove.entity.Fabricante;
import br.com.promove.entity.Modelo;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.ModeloView;

public class ModeloForm extends BaseForm{
	private Button save;
	private Button novo;
	private Button remove;
	private ModeloView view;
	private VerticalLayout form_layout = new VerticalLayout();
	private CadastroService cadastroService;
	
	public ModeloForm() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		save = new Button("Salvar", new ModeloFormListener());
		remove = new Button("Excluir", new ModeloFormListener());
		novo = new Button("Novo", new ModeloFormListener());
		
		createFormBody(new BeanItem<Modelo>(new Modelo()));
		form_layout.addComponent(this);
		form_layout.addComponent(createFooter());
		form_layout.setSpacing(true);
		
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
	
	public void createFormBody(BeanItem<Modelo> item) {
		setItemDataSource(item);
		setFormFieldFactory(new ModeloFieldFactory(item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"codigo", "descricao", "fabricante"});
	}
	
	private void addNewModelo() {
		createFormBody(new BeanItem<Modelo>(new Modelo()));
		
	}
	
	class ModeloFieldFactory extends DefaultFieldFactory{
		private boolean newLocal;
		public ModeloFieldFactory(boolean b) {
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
				
			}else if(propertyId.equals("fabricante")) {
				try {
					ComboBox c = new ComboBox("Fabricante");
					c.addContainerProperty("label", String.class, null);
					
					for(Fabricante fab : cadastroService.buscarTodosFabricantes()) {
						Item i = c.addItem(fab);
						i.getItemProperty("label").setValue(fab.getNome());
					}
					
					c.setRequired(true);
					c.setRequiredError("Fabricante obrigatório");
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(false);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					if (c.size() > 0)
	                    c.setValue(c.getItemIds().iterator().next());
					
					return c;
				}catch (PromoveException e) {
					showErrorMessage(view,"Não foi possível buscar Fabricantes");
				}
				
			}
			return f;
		}
		
	}
	
	class ModeloFormListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<Modelo> item = (BeanItem<Modelo>) getItemDataSource();
						cadastroService.salvarModelo(item.getBean());
						view.getTable().getContainer().addItem(item.getBean());
						addNewModelo();
						showSuccessMessage(view, "Modelo salvo!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível salvar Modelo");
				}
				
			}else if(event.getButton() == novo){
				addNewModelo();
			}else if(event.getButton() == remove){
				try {
					BeanItem<Modelo> item = (BeanItem<Modelo>) getItemDataSource();
					if(item.getBean().getId() != null) {
						cadastroService.excluirModelo(item.getBean());
						view.getTable().getContainer().removeItem(item.getBean());
						showSuccessMessage(view, "Modelo removido");
					}
					addNewModelo();
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível remover Modelo");
				}
			}
		}

	}

	public void setView(ModeloView view) {
		this.view = view;
	}

	public Component getFormLayout() {
		return form_layout;
	}

}
