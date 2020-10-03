package br.com.ermig.dados.analise.processes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.ermig.dados.analise.model.Tipo;

/**
 * Classe de transformação e separação dos dados por tipos
 * @author Ednardo Rubens
 *
 */
public class Extrator implements Function<String, Object> {
	
	/**
	 * Ler string e separa os tipos em lista de atributos
	 * @param dados
	 * @return Ex.: Map[VENDEDOR -> List(Vendedor{nome, cpf}), CLIENTE -> List(Cliente{nome, cnpj})]
	 */
	public Map<Tipo, List<Object>> apply(final String dados) {
		if (dados != null) {
			return Stream.of(dados.split("\\n"))
				.filter(Objects::nonNull)
				.map(Tipo::getObject)
				.filter(Objects::nonNull)
				.collect(Collectors.groupingBy(obj -> Tipo.getByClass(obj.getClass())));
		}
		return new HashMap<>();
	}
	
}