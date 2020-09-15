package br.com.dbc.dados.analise.processes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.dbc.dados.analise.model.Item;
import br.com.dbc.dados.analise.model.Tipo;
import br.com.dbc.dados.analise.model.Venda;

class ExtratorTest {
	
	public static final int SIZE = 1000000;
	
	private static String gerarMassaDados() {
		return IntStream.range(0, SIZE).mapToObj(i ->
			String.valueOf("001ç%sçVendedor_%sç5000\n002ç%sçLoja_%sç5000\n003ç%sç[1-%s-10,2-3%s-1.50,3-4%s-0.10]çVendedor_%s".replaceAll("%s", String.valueOf(i)))
		)
		.collect(Collectors.joining("\n"));
	}
	
	public static Map<Tipo, List<Object>> getDadosConvertidos() {
		return new Extrator().apply(gerarMassaDados());
	}

	@Test
	@DisplayName("Deve retornar uma lista de objetos quando o arquivo estiver preenchido corretamente.")
	void testTransform() {
		final Map<Tipo, List<Object>> transform = getDadosConvertidos();
		final Venda venda = (Venda) transform.get(Tipo.VENDA).get(0);
		Assertions.assertNotNull(venda);
		
		final List<Item> itens = venda.getItens();
		Assertions.assertNotNull(itens);
		Assertions.assertFalse(itens.isEmpty());
		
		final Item item0 = itens.get(0);
		Assertions.assertNotNull(item0);
	}
	
	@Test
	@DisplayName("Deve retornar um objeto diferente de nulo quando o arquivo estiver preenchido corretamente.")
	void testConverterFile() {
		final Map<Tipo, List<Object>> dadosConvertidos = getDadosConvertidos();
		Assertions.assertNotNull(dadosConvertidos);
		Assertions.assertNotNull(dadosConvertidos.get(Tipo.VENDEDOR));
		Assertions.assertNotNull(dadosConvertidos.get(Tipo.CLIENTE));
		Assertions.assertNotNull(dadosConvertidos.get(Tipo.VENDA));
	}
	
	@Test
	@DisplayName("Deve retornar um objeto vazio quando o arquivo vazio ou não tiver os dados esperados.")
	void testConverterEmptyFile() {
		final Extrator extrator = new Extrator();
		Assertions.assertTrue(extrator.apply(null).isEmpty());
		Assertions.assertTrue(extrator.apply("").isEmpty());
		Assertions.assertTrue(extrator.apply("empty").isEmpty());
	}

}
