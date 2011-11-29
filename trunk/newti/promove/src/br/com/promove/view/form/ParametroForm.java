package br.com.promove.view.form;

import br.com.promove.entity.Parametro;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.ParametroView;

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

public class ParametroForm extends BaseForm{
	private ParametroView view;
	private VerticalLayout form_layout = new VerticalLayout();
	private Button save;
	private Button novo;
	private Button remove;
	
	private CadastroService cadastroService;
	
	public ParametroForm() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		save = new Button("Salvar", new ParametroFormListener());
		remove = new Button("Excluir", new ParametroFormListener());
		novo = new Button("Novo", new ParametroFormListener());
		
		createFormBody(new BeanItem<Parametro>(new Parametro()));
		form_layout.addComponent(this);
		form_layout.addComponent(createFooter());
		form_layout.setSpacing(true);
		form_layout.setMargin(false, true, false, true);
		
	}

	public void createFormBody(BeanItem<Parametro> item) {
		setItemDataSource(item);
		setFormFieldFactory(new ParametroFieldFactory(item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"chave", "valor"});
		
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

	public void setView(ParametroView view) {
		this.view = view;
	}

	public Component getFormLayout() {
		return form_layout;
	}
	
	public void addNewParametro() {
		createFormBody(new BeanItem<Parametro>(new Parametro()));
	}
	
	class ParametroFieldFactory extends DefaultFieldFactory{
		private boolean newLocal;
		public ParametroFieldFactory(boolean b) {
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
			
			if(propertyId.equals("chave")) {
				if(!newLocal)
					f.setReadOnly(true);
			} else if(propertyId.equals("valor")) {
				f.setWidth("250px");
			}
			return f;
		}
		
	}
	
	class ParametroFormListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<Parametro> item = (BeanItem<Parametro>) getItemDataSource();
						cadastroService.salvarParametro(item.getBean());
						view.getTable().getContainer().addItem(item.getBean());
						addNewParametro();
						showSuccessMessage(view, "Parâmetro salvo!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível salvar Parâmetro");
				}
				
			}else if(event.getButton() == novo){
				addNewParametro();
			}else if(event.getButton() == remove){
				try {
					BeanItem<Parametro> item = (BeanItem<Parametro>) getItemDataSource();
					if(item.getBean().getId() != null) {
						cadastroService.excluirParametro(item.getBean());
						view.getTable().getContainer().removeItem(item.getBean());
						showSuccessMessage(view, "Parâmetro removido");
					}
					addNewParametro();
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível remover Parâmetro");
				}
			}
		}

	}

}
