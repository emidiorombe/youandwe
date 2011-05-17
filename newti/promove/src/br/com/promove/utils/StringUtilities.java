package br.com.promove.utils;

import java.util.Iterator;
import java.util.List;

import br.com.promove.entity.Veiculo;

/**
 * Classe para realizar operações utilitárias com String
 * @author rafael
 *
 */
public class StringUtilities {
	private StringUtilities(){}
	
	/**
	 * Transforma um booleano em texto humanamente compreensível true = Sim, false = Não
	 * @param booleano a ser transformado
	 * @return representação textual do booleano
	 */
	public static String booleanToReadableString(boolean b) {
		return b ? "Sim" : "Não";
	}
	
	/**
	 * Capitaliza(primeira sílaba em maiúscula e demais em minúscula) a string
	 * @param string a ser capitalizada
	 * @return String capitalizada
	 */
	public static String capitalize(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String listVeiculoToChassiInClause(List<Veiculo> veiculos) {
		StringBuilder retorno = new StringBuilder("");
		for(int i = 0; i < veiculos.size(); i++) {
			retorno.append(veiculos.get(i).getChassi());
			
			if(i < veiculos.size()-1) {
				retorno.append(",");
			}
		}
		return retorno.toString();
	}

	public static String getChassiFromErrorMessage(String msgErro) {
		String erros[] = msgErro.split(";");
		for (String erro : erros) {
			if(erro.startsWith("Veiculo")) {
				String campos[] = erro.split(" ");
				if(campos.length >= 2)
					return campos[1];
			}
		}
		return null;
	}

	public static String getCorFromErrorMessage(String message) {
		String erros[] = message.split(";");
		for (String erro : erros) {
			if(erro.startsWith("Cor")) {
				String campos[] = erro.split(" ");
				if(campos.length >= 2)
					return campos[1];
			}
		}
		return null;
	}
	
	public static String getModeloFromErrorMessage(String message) {
		String erros[] = message.split(";");
		for (String erro : erros) {
			if(erro.startsWith("Modelo")) {
				String campos[] = erro.split(" ");
				if(campos.length >= 2)
					return campos[1];
			}
		}
		return null;
	}
	
	
}
