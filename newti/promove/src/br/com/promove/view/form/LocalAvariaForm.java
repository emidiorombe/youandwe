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

import br.com.promove.entity.LocalAvaria;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.LocalAvariaView;

public class LocalAvariaForm extends BaseForm{

	private Button save;
	private Button novo;
	private Button remove;
	
	private LocalAvariaView view;
	private VerticalLayout f_layout = new VerticalLayout();
	private AvariaService avariaService;
	
	public LocalAvariaForm() {
		avariaService = ServiceFactory.getService(AvariaService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		save = new Button("Salvar", new LocalAvariaFormListener());
		remove = new Button("Excluir", new LocalAvariaFormListener());
		novo = new Button("Novo", new LocalAvariaFormListener());
		
		createFormBody(new BeanItem<LocalAvaria>(new LocalAvaria()));
		f_layout.addComponent(this);
		f_layout.addComponent(createFooter());
		f_layout.setSpacing(true);
		f_layout.setMargin(false, true, false, true);
		
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

	public void setView(LocalAvariaView view) {
		this.view = view;
		
	}

	public Component getFormLayout() {
		return f_layout;
	}

	public void createFormBody(BeanItem<LocalAvaria> item) {
		setItemDataSource(item);
		setFormFieldFactory(new LocalAvariaFieldFactory(item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"codigo", "descricao", "acessorio"});
		
		
	}
	
	private void addNewLocalAvaria() {
		createFormBody(new BeanItem<LocalAvaria>(new LocalAvaria()));
		
	}
	
	class LocalAvariaFieldFactory extends DefaultFieldFactory{
		private boolean newLocal;
		public LocalAvariaFieldFactory(boolean b) {
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
	
	class LocalAvariaFormListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<LocalAvaria> item = (BeanItem<LocalAvaria>) getItemDataSource();
						avariaService.salvarLocalAvaria(item.getBean());
						view.getTable().getContainer().addItem(item.getBean());
						addNewLocalAvaria();
						showSuccessMessage(view, "Peça salva!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível salvar Peça");
				}
				
			}else if(event.getButton() == novo){
				addNewLocalAvaria();
			}else if(event.getButton() == remove){
				try {
					BeanItem<LocalAvaria> item = (BeanItem<LocalAvaria>) getItemDataSource();
					if(item.getBean().getId() != null) {
						avariaService.excluirLocalAvaria(item.getBean());
						view.getTable().getContainer().removeItem(item.getBean());
						showSuccessMessage(view, "Peça removida");
					}
					addNewLocalAvaria();
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível remover Peça");
				}
			}
		}

	}

}
