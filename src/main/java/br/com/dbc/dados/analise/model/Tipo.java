package br.com.dbc.dados.analise.model;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.stream.Stream;

public enum Tipo {
	
	VENDEDOR("001", Vendedor.class),
	CLIENTE("002", Cliente.class),
	VENDA("003", Venda.class);
	
	public final String key;
	public final Class<?> clazz;
	
	public static final String SEPARADOR = "ç";
	
	private Tipo(final String key, final Class<?> clazz) {
		this.key = key;
		this.clazz = clazz;
	}

	public static Tipo getByKey(final String key) {
		return Stream.of(values())
			.filter(t -> t.key.equals(key))
			.findFirst()
			.orElseThrow();
	}
	
	public static Tipo getByClass(final Class<?> clazz) {
		return Stream.of(values())
				.filter(t -> t.clazz.equals(clazz))
				.findFirst()
				.orElseThrow();
	}
	
	public Object getObject(final String[] campos) throws Exception {
		@SuppressWarnings("unchecked")
		final Constructor<List<String>> constructor = (Constructor<List<String>>) this.clazz.getConstructor(List.class);
		return constructor.newInstance(List.of(campos));
	}
	
	public static final Object getObject(final String linha) {
		try {
			final int index = linha.indexOf(SEPARADOR);
			final Tipo tipo = Tipo.getByKey(linha.substring(0, index));
			final String sub = linha.substring(index + 1); //Retirar tipo
			final String[] campos = sub.split(SEPARADOR);
			return tipo.getObject(campos);
		} catch (Exception e) {
			return null;
		}
	}
}
