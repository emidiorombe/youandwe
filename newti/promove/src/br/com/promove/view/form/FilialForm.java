package br.com.promove.view.form;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import br.com.promove.entity.Filial;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.FilialView;

public class FilialForm extends BaseForm{

	private Button save;
	private Button novo;
	private Button remove;
	private FilialView view;
	private VerticalLayout form_layout = new VerticalLayout();
	private CadastroService cadastroService;
	
	public FilialForm() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
		buildForm();
	}
	
	public void setView(FilialView view) {
		this.view = view;
	}

	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		save = new Button("Salvar", new FilialFormListener());
		remove = new Button("Excluir", new FilialFormListener());
		novo = new Button("Novo", new FilialFormListener());
		
		createFormBody(new BeanItem<Filial>(new Filial()));
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

	public Component getFormLayout() {
		return form_layout;
	}

	public void createFormBody(BeanItem<Filial> item) {
		setItemDataSource(item);
		setFormFieldFactory(new FilialFieldFactory(item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"codigo", "nome", "sigla", "codigoExterno"});
		
		
	}
	
	private void addNewFilial() {
		createFormBody(new BeanItem<Filial>(new Filial()));
		
	}
	
	class FilialFieldFactory extends DefaultFieldFactory{
		private boolean newLocal;
		public FilialFieldFactory(boolean b) {
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
			}else if(propertyId.equals("codigoExterno")) {
				f.addValidator(new IntegerValidator("Código externo deve ser numérico"));
			}
			return f;
		}
		
	}
	
	class FilialFormListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<Filial> item = (BeanItem<Filial>) getItemDataSource();
						cadastroService.salvarFilial(item.getBean());
						view.getTable().getContainer().addItem(item.getBean());
						addNewFilial();
						showSuccessMessage(view, "Filial salva!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível salvar Filial");
				}
				
			}else if(event.getButton() == novo){
				addNewFilial();
			}else if(event.getButton() == remove){
				try {
					BeanItem<Filial> item = (BeanItem<Filial>) getItemDataSource();
					if(item.getBean().getId() != null) {
						cadastroService.excluirFilial(item.getBean());
						view.getTable().getContainer().removeItem(item.getBean());
						showSuccessMessage(view, "Filial removida");
					}
					addNewFilial();
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível remover Filial");
				}
			}
		}

	}

}
