package br.com.promove.view.form;

import br.com.promove.entity.TipoAvaria;
import br.com.promove.exception.PromoveException;
import br.com.promove.service.AvariaService;
import br.com.promove.service.ServiceFactory;
import br.com.promove.utils.StringUtilities;
import br.com.promove.view.TipoAvariaView;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class TipoAvariaForm extends BaseForm {
	private TipoAvariaView view;

	private Button save;
	private Button novo;
	private Button remove;

	private VerticalLayout f_layout = new VerticalLayout();
	private AvariaService avariaService;

	public TipoAvariaForm() {
		avariaService = ServiceFactory.getService(AvariaService.class);
		buildForm();
	}

	private void buildForm() {
		setWriteThrough(false);
		setImmediate(true);
		setSizeFull();

		save = new Button("Salvar", new TipoAvariaFormListener());
		remove = new Button("Excluir", new TipoAvariaFormListener());
		novo = new Button("Novo", new TipoAvariaFormListener());

		createFormBody(new BeanItem<TipoAvaria>(new TipoAvaria()));
		f_layout.addComponent(this);
		f_layout.addComponent(createFooter());
		f_layout.setSpacing(true);
		f_layout.setMargin(false, true, false, true);

	}

	public void createFormBody(BeanItem<TipoAvaria> tpa) {
		setItemDataSource(tpa);
		setFormFieldFactory(new TipoAvariaFieldFactory(
				tpa.getBean().getId() == null));
		setVisibleItemProperties(new Object[] { "codigo", "descricao",
				"perdaTotal", "falta" });

	}

	public void addNewTipoAvaria() {
		createFormBody(new BeanItem<TipoAvaria>(new TipoAvaria()));
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

	public Component getFormLayout() {
		return f_layout;
	}

	public void setView(TipoAvariaView view) {
		this.view = view;
	}

	class TipoAvariaFieldFactory extends DefaultFieldFactory {
		private boolean newTipo;

		public TipoAvariaFieldFactory(boolean b) {
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

	class TipoAvariaFormListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			if (event.getButton() == save) {
				try {
					validate();
					if (isValid()) {
						commit();
						BeanItem<TipoAvaria> item = (BeanItem<TipoAvaria>) getItemDataSource();
						avariaService.salvarTipoAvaria(item.getBean());
						view.getTable().getContainer().addItem(item.getBean());
						addNewTipoAvaria();
						showSuccessMessage(view, "Tipo Avaria salvo");
					}
				} catch (InvalidValueException ive) {
					setValidationVisible(true);
				} catch (PromoveException de) {
					showErrorMessage(view,
							"Não foi possível salvar Tipo Avaria");
				}

			} else if (event.getButton() == novo) {
				addNewTipoAvaria();
			} else if (event.getButton() == remove) {
				try {
					BeanItem<TipoAvaria> item = (BeanItem<TipoAvaria>) getItemDataSource();
					if (item.getBean().getId() != null) {
						avariaService.excluirTipoAvaria(item.getBean());
						view.getTable().getContainer()
								.removeItem(item.getBean());
						showSuccessMessage(view, "Tipo Avaria removido");
					}
					addNewTipoAvaria();
				} catch (PromoveException de) {
					showErrorMessage(view,
							"Não foi possível remover Tipo de Avaria");
				}
			}
		}
	}
}
