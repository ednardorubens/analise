package br.com.dbc.dados.analise.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Vendedor {

	private final String cpf;
	private final String name;
	private final String salary;
	
	public Vendedor(final List<String> campos) {
		this(campos.get(0), campos.get(1), campos.get(2));
	}
	
}