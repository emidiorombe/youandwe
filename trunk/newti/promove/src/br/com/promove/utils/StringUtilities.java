package br.com.promove.utils;

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

	public static String getValueFromErrorMessage(String msgErro, String fieldErro) {
		if (msgErro != null) {
			String erros[] = msgErro.split(";");
			for (String erro : erros) {
				if(erro.startsWith(fieldErro)) {
					//String campos[] = erro.split(" ");
					//if(campos.length >= 2)
					//	return campos[1];
					return erro.replaceAll(fieldErro + " ", " ").replaceAll(" não existe!", " ").replaceAll(" não existe", " ").trim();
				}
			}
		}
		return null;
	}

	public static String removeErrorMessage(String msgErro, String fieldErro) {
		String msg = msgErro;
		
		if (msgErro != null) {
			String erros[] = msgErro.split(";");
			for (String erro : erros) {
				if(erro.startsWith(fieldErro)) {
					msg.replaceAll(erro + ";", "");
				}
			}
		}
		return msg;
	}

	public static String getChassiFromErrorMessage(String msgErro) {
		return getValueFromErrorMessage(msgErro, "Veiculo");
	}

	public static String getModeloFromErrorMessage(String msgErro) {
		return getValueFromErrorMessage(msgErro, "Modelo");
	}

	public static String getCorFromErrorMessage(String msgErro) {
		return getValueFromErrorMessage(msgErro, "Cor");
	}

	public static String getTranspFromErrorMessage(String msgErro) {
		return getValueFromErrorMessage(msgErro, "Transportadora");
	}
}
