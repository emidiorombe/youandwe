package br.com.promove.view.form;

import br.com.promove.entity.Clima;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.ClimaAvariaView;

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

public class ClimaForm extends BaseForm{
	private ClimaAvariaView view;
	private VerticalLayout form_layout = new VerticalLayout();
	private Button save;
	private Button novo;
	private Button remove;
	
	private AvariaService avariaService;
	
	public ClimaForm() {
		avariaService = ServiceFactory.getService(AvariaService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		save = new Button("Salvar", new ClimaFormListener());
		remove = new Button("Excluir", new ClimaFormListener());
		novo = new Button("Novo", new ClimaFormListener());
		
		createFormBody(new BeanItem<Clima>(new Clima()));
		form_layout.addComponent(this);
		form_layout.addComponent(createFooter());
		form_layout.setSpacing(true);
		form_layout.setMargin(false, true, false, true);
		
	}

	public void createFormBody(BeanItem<Clima> item) {
		setItemDataSource(item);
		setFormFieldFactory(new ClimaFieldFactory(item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"codigo", "descricao"});
		
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

	public void setView(ClimaAvariaView view) {
		this.view = view;
	}

	public Component getFormLayout() {
		return form_layout;
	}
	
	public void addNewClima() {
		createFormBody(new BeanItem<Clima>(new Clima()));
	}
	
	class ClimaFieldFactory extends DefaultFieldFactory{
		private boolean newLocal;
		public ClimaFieldFactory(boolean b) {
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
	
	class ClimaFormListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<Clima> item = (BeanItem<Clima>) getItemDataSource();
						avariaService.salvarClima(item.getBean());
						view.getTable().getContainer().addItem(item.getBean());
						addNewClima();
						showSuccessMessage(view, "Clima salvo!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível salvar Clima");
				}
				
			}else if(event.getButton() == novo){
				addNewClima();
			}else if(event.getButton() == remove){
				try {
					BeanItem<Clima> item = (BeanItem<Clima>) getItemDataSource();
					if(item.getBean().getId() != null) {
						avariaService.excluirClima(item.getBean());
						view.getTable().getContainer().removeItem(item.getBean());
						showSuccessMessage(view, "Clima removido");
					}
					addNewClima();
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível remover Clima");
				}
			}
		}

	}

}
