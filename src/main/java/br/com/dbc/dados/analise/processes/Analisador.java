package br.com.dbc.dados.analise.processes;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.dbc.dados.analise.model.Cliente;
import br.com.dbc.dados.analise.model.Tipo;
import br.com.dbc.dados.analise.model.Venda;
import br.com.dbc.dados.analise.model.Vendedor;

/**
 * Classe funcional de processamento dos dados
 * @author Ednardo Rubens
 *
 */
@SuppressWarnings("rawtypes")
public class Analisador implements Function<Map, Object> {

	public static String buscarQtdVendedores(final List<Vendedor> vendedores) {
		return getQuantidade(vendedores);
	}

	public static String buscarQtdClientes(final List<Cliente> clientes) {
		return getQuantidade(clientes);
	}
	
	public static String buscarVendaMaisCara(final List<Venda> vendas) {
		if (vendas != null) {
			final Map<String, Double> valores = vendas.stream()
				.collect(Collectors.toMap(
					Venda::getId,
					Venda::getValor,
					(old, neil) -> neil
				));
			
			final Optional<Entry<String, Double>> maxEntry = valores.entrySet().stream()
				.max(Comparator.comparing(Map.Entry::getValue));
			
			return maxEntry.map(Entry::getKey).orElse("0");
		}
		return "0";
	}
	
	public static String buscarNomePiorVendedor(final List<Venda> vendas) {
		if (vendas != null) {
			final Map<String, Double> valores = vendas.stream()
				.collect(Collectors.groupingBy(
					Venda::getSalesmanName,
					Collectors.summingDouble(Venda::getValor)
				));
			
			final Optional<Entry<String, Double>> maxEntry = valores.entrySet().stream()
				.min(Comparator.comparing(Map.Entry::getValue));
			
			return maxEntry.map(Entry::getKey).orElse("");
		}
		return "";
	}
	
	private static String getQuantidade(final List<?> lista) {
		if (lista != null && !lista.isEmpty()) {
			return String.valueOf(lista.size());
		}
		return "0";
	}

	@Override
	@SuppressWarnings("unchecked")
	public String apply(final Map dadosProcessados) {
		final List<Venda> vendas = (List<Venda>) dadosProcessados.get(Tipo.VENDA);
		final List<Cliente> clientes = (List<Cliente>) dadosProcessados.get(Tipo.CLIENTE);
		final List<Vendedor> vendedores = (List<Vendedor>) dadosProcessados.get(Tipo.VENDEDOR);
		
		return Stream.of( //Buscar informações
    		"Quantidade de clientes no arquivo de entrada: " + buscarQtdClientes(clientes),
    		"Quantidade de vendedor no arquivo de entrada: " + buscarQtdVendedores(vendedores),
    		"ID da venda mais cara: " + buscarVendaMaisCara(vendas),
    		"O pior vendedor: " + buscarNomePiorVendedor(vendas)
		).collect(Collectors.joining("\n"));
	}

}
