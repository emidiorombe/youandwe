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
		StringBuilder retorno = new StringBuilder("(");
		for(int i = 0; i < veiculos.size(); i++) {
			retorno.append(veiculos.get(i).getChassi());
			
			if(i < veiculos.size()-1) {
				retorno.append(",");
			}
		}
		retorno.append(")");
		return retorno.toString();
	}
	
	
}
