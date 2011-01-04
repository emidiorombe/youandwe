package br.com.promove.view.form;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import br.com.promove.entity.Fabricante;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.FabricanteView;

public class FabricanteForm extends BaseForm{
	private Button save;
	private Button novo;
	private Button remove;
	private FabricanteView view;
	private VerticalLayout form_layout = new VerticalLayout();
	private CadastroService cadastroService;
	
	public FabricanteForm() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
		buildForm();
	}
	
	public void setView(FabricanteView view) {
		this.view = view;
	}

	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		save = new Button("Salvar", new FabricanteFormListener());
		remove = new Button("Excluir", new FabricanteFormListener());
		novo = new Button("Novo", new FabricanteFormListener());
		
		createFormBody(new BeanItem<Fabricante>(new Fabricante()));
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

	public Component getFormLayout() {
		return form_layout;
	}

	public void createFormBody(BeanItem<Fabricante> item) {
		setItemDataSource(item);
		setFormFieldFactory(new FabricanteFieldFactory(item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"codigo", "nome"});
		
		
	}
	
	private void addNewFabricante() {
		createFormBody(new BeanItem<Fabricante>(new Fabricante()));
		
	}
	
	class FabricanteFieldFactory extends DefaultFieldFactory{
		private boolean newLocal;
		public FabricanteFieldFactory(boolean b) {
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
			}
			return f;
		}
		
	}
	
	class FabricanteFormListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<Fabricante> item = (BeanItem<Fabricante>) getItemDataSource();
						cadastroService.salvarFabricante(item.getBean());
						view.getTable().getContainer().addItem(item.getBean());
						addNewFabricante();
						showSuccessMessage(view, "Fabricante salvo!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível salvar Fabricante");
				}
				
			}else if(event.getButton() == novo){
				addNewFabricante();
			}else if(event.getButton() == remove){
				try {
					BeanItem<Fabricante> item = (BeanItem<Fabricante>) getItemDataSource();
					if(item.getBean().getId() != null) {
						cadastroService.excluirFabricante(item.getBean());
						view.getTable().getContainer().removeItem(item.getBean());
						showSuccessMessage(view, "Fabricante removido");
					}
					addNewFabricante();
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível remover Fabricante");
				}
			}
		}

	}

}
