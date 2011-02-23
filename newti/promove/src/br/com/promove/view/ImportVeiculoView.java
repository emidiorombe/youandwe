package br.com.promove.view;

import br.com.promove.view.form.ImporVeiculoNacionalForm;
import br.com.promove.view.form.ImportVeiculoImportadoForm;

import com.vaadin.ui.VerticalLayout;

public class ImportVeiculoView extends VerticalLayout{
	private ImporVeiculoNacionalForm nacional_form;
	private ImportVeiculoImportadoForm importado_form;
	
	public ImportVeiculoView() {
		setSpacing(true);
		addComponent(new ImporVeiculoNacionalForm().getLayout());
		addComponent(new ImportVeiculoImportadoForm().getLayout());
	}
}
