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

import br.com.promove.entity.Frota;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.FrotaView;

public class FrotaForm extends BaseForm{
	private Button save;
	private Button novo;
	private Button remove;
	private FrotaView view;
	private VerticalLayout form_layout = new VerticalLayout();
	private CadastroService cadastroService;
	
	public FrotaForm() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		save = new Button("Salvar", new FrotaFormListener());
		remove = new Button("Excluir", new FrotaFormListener());
		novo = new Button("Novo", new FrotaFormListener());
		
		createFormBody(new BeanItem<Frota>(new Frota()));
		form_layout.addComponent(this);
		form_layout.addComponent(createFooter());
		form_layout.setSpacing(true);
		form_layout.setMargin(false, true, false, true);
		
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
	
	public void createFormBody(BeanItem<Frota> item) {
		setItemDataSource(item);
		setFormFieldFactory(new FrotaFieldFactory(item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"codigo", "placa", "ativo"});
	}
	
	private void addNewFrota() {
		createFormBody(new BeanItem<Frota>(new Frota()));
		
	}
	
	class FrotaFieldFactory extends DefaultFieldFactory{
		private boolean newLocal;
		public FrotaFieldFactory(boolean b) {
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
	
	class FrotaFormListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<Frota> item = (BeanItem<Frota>) getItemDataSource();
						cadastroService.salvarFrota(item.getBean());
						view.getTable().getContainer().addItem(item.getBean());
						addNewFrota();
						showSuccessMessage(view, "Frota salva!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível salvar Frota");
				}
				
			}else if(event.getButton() == novo){
				addNewFrota();
			}else if(event.getButton() == remove){
				try {
					BeanItem<Frota> item = (BeanItem<Frota>) getItemDataSource();
					if(item.getBean().getId() != null) {
						cadastroService.excluirFrota(item.getBean());
						view.getTable().getContainer().removeItem(item.getBean());
						showSuccessMessage(view, "Frota removida");
					}
					addNewFrota();
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível remover Frota");
				}
			}
		}

	}

	public void setView(FrotaView view) {
		this.view = view;
	}

	public Component getFormLayout() {
		return form_layout;
	}

}
