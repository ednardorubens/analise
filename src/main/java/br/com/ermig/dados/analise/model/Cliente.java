package br.com.ermig.dados.analise.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Cliente {

	private final String cnpj;
	private final String name;
	private final String businessArea;
	
	public Cliente(final List<String> campos) {
		this(campos.get(0), campos.get(1), campos.get(2));
	}
	
}