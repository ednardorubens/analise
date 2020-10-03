package br.com.ermig.dados.analise.model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

@Getter
public class Venda {

	private final String id;
	private final List<Item> itens;
	private final String salesmanName;
	
	public Venda(final List<String> campos) {
		this(campos.get(0), campos.get(1), campos.get(2));
	}
	
	public Venda(final String id, final String itens, final String salesmanName) {
		this(id, extract(itens), salesmanName);
	}

	public Venda(final String id, final List<Item> itens, final String salesmanName) {
		this.id = id;
		this.itens = itens;
		this.salesmanName = salesmanName;
	}
	
	public Double getValor() {
		if (itens != null && !itens.isEmpty()) {
			return itens.stream().mapToDouble(Item::getValor).sum();
		}
		return 0d;
	}
	
	private static List<Item> extract(final String itens) {
		if (StringUtils.isNotBlank(itens)) {
			return Stream.of(itens.replace("[", "").replace("]", "").split(","))
				.map(Item::new)
				.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

}