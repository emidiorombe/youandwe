package br.com.promove.view.form;

import java.util.Iterator;

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

import br.com.promove.entity.Carreta;
import br.com.promove.entity.Frota;
import br.com.promove.entity.Motorista;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.CadastroService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.MotoristaView;

public class MotoristaForm extends BaseForm{
	private Button save;
	private Button novo;
	private Button remove;
	private MotoristaView view;
	private VerticalLayout form_layout = new VerticalLayout();
	private CadastroService cadastroService;
	
	public MotoristaForm() {
		cadastroService = ServiceFactory.getService(CadastroService.class);
		buildForm();
	}
	
	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		save = new Button("Salvar", new MotoristaFormListener());
		remove = new Button("Excluir", new MotoristaFormListener());
		novo = new Button("Novo", new MotoristaFormListener());
		
		createFormBody(new BeanItem<Motorista>(new Motorista()));
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
	
	public void createFormBody(BeanItem<Motorista> item) {
		setItemDataSource(item);
		setFormFieldFactory(new MotoristaFieldFactory(item.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"codigo", "nome", "cnh", "rg", "cpf", "frota", "carreta", "ativo"});
	}
	
	private void addNewMotorista() {
		createFormBody(new BeanItem<Motorista>(new Motorista()));
		
	}
	
	class MotoristaFieldFactory extends DefaultFieldFactory{
		private boolean newLocal;
		public MotoristaFieldFactory(boolean b) {
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
			} else if(propertyId.equals("nome")) {
				f.setWidth("300px");
			}else if(propertyId.equals("frota")) {
				try {
					ComboBox c = new ComboBox("Frota");
					c.addContainerProperty("label", String.class, null);
					
					for(Frota fr: cadastroService.buscarTodasFrotas()) {
						Item i = c.addItem(fr);
						i.getItemProperty("label").setValue(fr.getPlaca());
					}
					
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(true);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					if (c.size() > 0) {
						Frota f2 = ((BeanItem<Motorista>) getItemDataSource()).getBean().getFrota();
						if(f2 != null) {
							Iterator<Frota> it = c.getItemIds().iterator(); 
							while(it.hasNext()) {
								Frota f1 = it.next();
								if(f2.getId().equals(f1.getId())) {
									c.setValue(f1);
								}
							}
						}
					}
					
					return c;
				}catch (PromoveException e) {
					showErrorMessage(view,"Não foi possível buscar Frotas");
				}
			}else if(propertyId.equals("carreta")) {
				try {
					ComboBox c = new ComboBox("Carreta");
					c.addContainerProperty("label", String.class, null);
					
					for(Carreta ca: cadastroService.buscarTodasCarretas()) {
						Item i = c.addItem(ca);
						i.getItemProperty("label").setValue(ca.getPlaca());
					}
					
					c.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
					c.setImmediate(true);
					c.setNullSelectionAllowed(true);
					c.setPropertyDataSource(item.getItemProperty(propertyId));
					c.setItemCaptionPropertyId("label");
					
					if (c.size() > 0) {
						Carreta f2 = ((BeanItem<Motorista>) getItemDataSource()).getBean().getCarreta();
						if(f2 != null) {
							Iterator<Carreta> it = c.getItemIds().iterator(); 
							while(it.hasNext()) {
								Carreta f1 = it.next();
								if(f2.getId().equals(f1.getId())) {
									c.setValue(f1);
								}
							}
						}
					}
					
					return c;
				}catch (PromoveException e) {
					showErrorMessage(view,"Não foi possível buscar Frotas");
				}
			}
			return f;
		}
		
	}
	
	class MotoristaFormListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton() == save){
				try{
					validate();
					if(isValid()){
						commit();
						BeanItem<Motorista> item = (BeanItem<Motorista>) getItemDataSource();
						cadastroService.salvarMotorista(item.getBean());
						view.getTable().getContainer().addItem(item.getBean());
						addNewMotorista();
						showSuccessMessage(view, "Motorista salvo!");
					}
				}catch(InvalidValueException ive){
					setValidationVisible(true);
				}catch(PromoveException de){
					showErrorMessage(view,"Não foi possível salvar Motorista");
				}
				
			}else if(event.getButton() == novo){
				addNewMotorista();
			}else if(event.getButton() == remove){
				try {
					BeanItem<Motorista> item = (BeanItem<Motorista>) getItemDataSource();
					if(item.getBean().getId() != null) {
						cadastroService.excluirMotorista(item.getBean());
						view.getTable().getContainer().removeItem(item.getBean());
						showSuccessMessage(view, "Motorista removido");
					}
					addNewMotorista();
				}catch(PromoveException de){
					showErrorMessage(view, "Não foi possível remover Motorista");
				}
			}
		}

	}

	public void setView(MotoristaView view) {
		this.view = view;
	}

	public Component getFormLayout() {
		return form_layout;
	}

}
