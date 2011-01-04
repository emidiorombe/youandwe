package br.com.promove.view.form;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import br.com.promove.entity.OrigemAvaria;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.OrigemAvariaView;

public class OrigemAvariaForm extends BaseForm{

	private OrigemAvariaView view;
	
	private Button save;
	private Button novo;
	private Button remove;
	
	private VerticalLayout f_layout = new VerticalLayout();
	private AvariaService avariaService;
	
	public OrigemAvariaForm() {
		avariaService = ServiceFactory.getService(AvariaService.class);
		buildForm();
	}

	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();
		
		save = new Button("Salvar", new OrigemAvariaFormListener());
		remove = new Button("Excluir", new OrigemAvariaFormListener());
		novo = new Button("Novo", new OrigemAvariaFormListener());
		
		createFormBody(new BeanItem<OrigemAvaria>(new OrigemAvaria()));
		f_layout.addComponent(this);
		f_layout.addComponent(createFooter());
		f_layout.setSpacing(true);
		
	}
	
	public void createFormBody(BeanItem<OrigemAvaria> oav){
		setItemDataSource(oav);
		setFormFieldFactory(new OrigemAvariaFieldFactory(oav.getBean().getId() == null));
		setVisibleItemProperties(new Object[]{"codigo", "descricao", "responsabilidade", "sigla"});
		
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
	
	public void addNewOrigemAvaria() {
		createFormBody(new BeanItem<OrigemAvaria>(new OrigemAvaria()));
	}

	public void setView(OrigemAvariaView view) {
		this.view = view;
	}

	public Component getFormLayout() {
		return f_layout;
	}
	
	class OrigemAvariaFormListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			if (event.getButton() == save) {
				try {
					validate();
					if (isValid()) {
						commit();
						BeanItem<OrigemAvaria> item = (BeanItem<OrigemAvaria>) getItemDataSource();
						avariaService.salvarOrigemAvaria(item.getBean());
						view.getTable().getContainer().addItem(item.getBean());
						addNewOrigemAvaria();
						showSuccessMessage(view, "Origem Avaria salvo");
					}
				} catch (InvalidValueException ive) {
					setValidationVisible(true);
				} catch (PromoveException de) {
					showErrorMessage(view,
							"Não foi possível salvar Origem Avaria");
				}

			} else if (event.getButton() == novo) {
				addNewOrigemAvaria();
			} else if (event.getButton() == remove) {
				try {
					BeanItem<OrigemAvaria> item = (BeanItem<OrigemAvaria>) getItemDataSource();
					if (item.getBean().getId() != null) {
						avariaService.excluirOrigemAvaria(item.getBean());
						view.getTable().getContainer()
								.removeItem(item.getBean());
						showSuccessMessage(view, "Origem Avaria removido");
					}
					addNewOrigemAvaria();
				} catch (PromoveException de) {
					showErrorMessage(view,
							"Não foi possível remover Origem de Avaria");
				}
			}
		}
	}
	
	class OrigemAvariaFieldFactory extends DefaultFieldFactory {
		private boolean newTipo;

		public OrigemAvariaFieldFactory(boolean b) {
			newTipo = b;
		}

		@Override
		public Field createField(Item item, Object propertyId,
				Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);

			if (f instanceof TextField) {
				((TextField) f).setNullRepresentation("");
				f.setRequired(true);
				f.setRequiredError("Preenchimento do campo '"
						+ StringUtilities.capitalize(propertyId.toString())
						+ "' é obrigatório.");
			}

			if (propertyId.equals("codigo")) {
				if (!newTipo)
					f.setReadOnly(true);
				f.addValidator(new IntegerValidator(
						"Código deve ser um número inteiro"));
			} else if (propertyId.equals("descricao")) {
				f.addValidator(new StringLengthValidator(
						"Descrição deve ter no mínimo 3 e no máximo 50 caracteres",
						3, 50, false));
			}

			return f;
		}

	}

}
