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

import br.com.promove.entity.Carreta;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.CarretaView;

public class CarretaForm extends BaseForm{
	private Button save;
	private Button novo;
	private Button remove;
	private CarretaView view;
	private VerticalLayout form_layout = new VerticalLayout();
	private CadastroService cadastroService;
	
	public CarretaForm() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		save = new Button("Salvar", new CarretaFormListener());
		remove = new Button("Excluir", new CarretaFormListener());
		novo = new Button("Novo", new CarretaFormListener());
		
		createFormBody(new BeanItem<Carreta>(new Carreta()));
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
	
	public void createFormBody(BeanItem<Carreta> item) {
		setItemDataSource(item);
		setFormFieldFactory(new CarretaFieldFactory(item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"codigo", "placa", "ativo"});
	}
	
	private void addNewCarreta() {
		createFormBody(new BeanItem<Carreta>(new Carreta()));
		
	}
	
	class CarretaFieldFactory extends DefaultFieldFactory{
		private boolean newLocal;
		public CarretaFieldFactory(boolean b) {
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
	
	class CarretaFormListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<Carreta> item = (BeanItem<Carreta>) getItemDataSource();
						cadastroService.salvarCarreta(item.getBean());
						view.getTable().getContainer().addItem(item.getBean());
						addNewCarreta();
						showSuccessMessage(view, "Carreta salva!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível salvar Carreta");
				}
				
			}else if(event.getButton() == novo){
				addNewCarreta();
			}else if(event.getButton() == remove){
				try {
					BeanItem<Carreta> item = (BeanItem<Carreta>) getItemDataSource();
					if(item.getBean().getId() != null) {
						cadastroService.excluirCarreta(item.getBean());
						view.getTable().getContainer().removeItem(item.getBean());
						showSuccessMessage(view, "Carreta removida");
					}
					addNewCarreta();
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível remover Carreta");
				}
			}
		}

	}

	public void setView(CarretaView view) {
		this.view = view;
	}

	public Component getFormLayout() {
		return form_layout;
	}

}
