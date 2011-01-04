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

import br.com.promove.entity.ExtensaoAvaria;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.ExtensaoAvariaView;

public class ExtensaoAvariaForm extends BaseForm{
	private ExtensaoAvariaView view;
	private VerticalLayout f_layout = new VerticalLayout();
	private AvariaService avariaService;
	
	private Button save;
	private Button novo;
	private Button remove;
	
	public ExtensaoAvariaForm() {
		avariaService = ServiceFactory.getService(AvariaService.class);
		buildForm();
	}

	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();

		save = new Button("Salvar", new ExtensaoAvariaFormListener());
		remove = new Button("Excluir", new ExtensaoAvariaFormListener());
		novo = new Button("Novo", new ExtensaoAvariaFormListener());

		createFormBody(new BeanItem<ExtensaoAvaria>(new ExtensaoAvaria()));
		f_layout.addComponent(this);
		f_layout.addComponent(createFooter());
		f_layout.setSpacing(true);
		
	}
	
	public void createFormBody(BeanItem<ExtensaoAvaria> ext) {
		setItemDataSource(ext);
		setFormFieldFactory(new ExtensaoAvariaFieldFactory(ext.getBean().getId() == null));
		setVisibleItemProperties(new Object[] { "codigo", "descricao"});

	}
	
	private Component createFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.addComponent(remove);
		footer.addComponent(novo);
		footer.setVisible(true);

		return footer;
	}
	
	public void addNewExtensaoAvaria() {
		createFormBody(new BeanItem<ExtensaoAvaria>(new ExtensaoAvaria()));
	}

	public void setView(ExtensaoAvariaView view) {
		this.view = view;
		
	}

	public Component getFormLayout() {
		return f_layout;
	}
	
	class ExtensaoAvariaFieldFactory extends DefaultFieldFactory {
		private boolean newExtensao;

		public ExtensaoAvariaFieldFactory(boolean b) {
			newExtensao = b;
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
				if (!newExtensao)
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
	
	class ExtensaoAvariaFormListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			if (event.getButton() == save) {
				try {
					validate();
					if (isValid()) {
						commit();
						BeanItem<ExtensaoAvaria> item = (BeanItem<ExtensaoAvaria>) getItemDataSource();
						avariaService.salvarExtensaoAvaria(item.getBean());
						view.getTable().getContainer().addItem(item.getBean());
						addNewExtensaoAvaria();
						showSuccessMessage(view, "Extensao Avaria salvo");
					}
				} catch (InvalidValueException ive) {
					setValidationVisible(true);
				} catch (PromoveException de) {
					showErrorMessage(view,
							"Não foi possível salvar Extensao Avaria");
				}

			} else if (event.getButton() == novo) {
				addNewExtensaoAvaria();
			} else if (event.getButton() == remove) {
				try {
					BeanItem<ExtensaoAvaria> item = (BeanItem<ExtensaoAvaria>) getItemDataSource();
					if (item.getBean().getId() != null) {
						avariaService.excluirExtensaoAvaria(item.getBean());
						view.getTable().getContainer()
								.removeItem(item.getBean());
						showSuccessMessage(view, "Extensao Avaria removido");
					}
					addNewExtensaoAvaria();
				} catch (PromoveException de) {
					showErrorMessage(view,
							"Não foi possível remover Extensão de Avaria");
				}
			}
		}
	}

}
