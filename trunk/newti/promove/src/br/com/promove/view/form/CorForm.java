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

import br.com.promove.entity.Cor;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.CorView;

public class CorForm extends BaseForm{

	private CorView view;
	private VerticalLayout form_layout = new VerticalLayout();
	private Button save;
	private Button novo;
	private Button remove;
	
	private CadastroService cadastroService;
	
	public CorForm() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		save = new Button("Salvar", new CorFormListener());
		remove = new Button("Excluir", new CorFormListener());
		novo = new Button("Novo", new CorFormListener());
		
		createFormBody(new BeanItem<Cor>(new Cor()));
		form_layout.addComponent(this);
		form_layout.addComponent(createFooter());
		form_layout.setSpacing(true);
		form_layout.setMargin(false, true, false, true);
		
	}

	public void createFormBody(BeanItem<Cor> item) {
		setItemDataSource(item);
		setFormFieldFactory(new CorFieldFactory(item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"codigo", "descricao", "codigoExterno"});
		
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

	public void setView(CorView view) {
		this.view = view;
	}

	public Component getFormLayout() {
		return form_layout;
	}
	
	public void addNewCor() {
		createFormBody(new BeanItem<Cor>(new Cor()));
	}
	
	class CorFieldFactory extends DefaultFieldFactory{
		private boolean newCor;
		public CorFieldFactory(boolean b) {
			newCor = b;
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
				if(!newCor)
					f.setReadOnly(true);
			} else if(propertyId.equals("descricao")) {
				f.setWidth("300px");
			}else if(propertyId.equals("codigoExterno")) {
				f.setRequired(false);
			}
			return f;
		}
		
	}
	
	class CorFormListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<Cor> item = (BeanItem<Cor>) getItemDataSource();
						cadastroService.salvarCor(item.getBean());
						view.getTable().getContainer().addItem(item.getBean());
						addNewCor();
						showSuccessMessage(view, "Cor salva!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível salvar Cor");
				}
			}else if(event.getButton() == novo){
				addNewCor();
			}else if(event.getButton() == remove){
				try {
					BeanItem<Cor> item = (BeanItem<Cor>) getItemDataSource();
					if(item.getBean().getId() != null) {
						cadastroService.excluirCor(item.getBean());
						view.getTable().getContainer().removeItem(item.getBean());
						showSuccessMessage(view, "Cor removida");
					}
					addNewCor();
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível remover Cor");
				}
			}
		}

	}

}
