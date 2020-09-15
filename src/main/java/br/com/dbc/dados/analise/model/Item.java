package br.com.dbc.dados.analise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Item {

	private final String id;
	private final String quantity;
	private final String price;
	
	public Item(final String item) {
		this(item.split("-"));
	}

	private Item(final String[] item) {
		this(item[0], item[1], item[2]);
	}
	
	public Double getValor() {
		return Integer.valueOf(quantity) * Double.valueOf(price);
	}
	
}
