package br.com.promove.view.form;

import br.com.promove.entity.Transportadora;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CtrcService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.TransportadoraView;

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

public class TransportadoraForm extends BaseForm{
	private TransportadoraView view;
	private VerticalLayout form_layout = new VerticalLayout();
	private Button save;
	private Button novo;
	private Button remove;
	
	private CtrcService ctrcService;
	
	public TransportadoraForm() {
		ctrcService = ServiceFactory.getService(CtrcService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		save = new Button("Salvar", new TransportadoraFormListener());
		remove = new Button("Excluir", new TransportadoraFormListener());
		novo = new Button("Novo", new TransportadoraFormListener());
		
		createFormBody(new BeanItem<Transportadora>(new Transportadora()));
		form_layout.addComponent(this);
		form_layout.addComponent(createFooter());
		form_layout.setSpacing(true);
		form_layout.setMargin(false, true, false, true);
		
	}

	public void createFormBody(BeanItem<Transportadora> item) {
		setItemDataSource(item);
		setFormFieldFactory(new TransportadoraFieldFactory(item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"codigo", "cnpj", "descricao"});
		
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

	public void setView(TransportadoraView view) {
		this.view = view;
	}

	public Component getFormLayout() {
		return form_layout;
	}
	
	public void addNewTransportadora() {
		createFormBody(new BeanItem<Transportadora>(new Transportadora()));
	}
	
	class TransportadoraFieldFactory extends DefaultFieldFactory{
		private boolean newLocal;
		public TransportadoraFieldFactory(boolean b) {
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
			} else if(propertyId.equals("descricao")) {
				f.setWidth("300px");
			}
			return f;
		}
		
	}
	
	class TransportadoraFormListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<Transportadora> item = (BeanItem<Transportadora>) getItemDataSource();
						ctrcService.salvarTransportadora(item.getBean());
						view.getTable().getContainer().addItem(item.getBean());
						addNewTransportadora();
						showSuccessMessage(view, "Transportadora salva!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,de.getMessage() + ". Não foi possível salvar Transportadora");
				}
				
			}else if(event.getButton() == novo){
				addNewTransportadora();
			}else if(event.getButton() == remove){
				try {
					BeanItem<Transportadora> item = (BeanItem<Transportadora>) getItemDataSource();
					if(item.getBean().getId() != null) {
						ctrcService.excluirTransportadora(item.getBean());
						view.getTable().getContainer().removeItem(item.getBean());
						showSuccessMessage(view, "Transportadora removida");
					}
					addNewTransportadora();
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível remover Transportadora");
				}
			}
		}

	}

}
