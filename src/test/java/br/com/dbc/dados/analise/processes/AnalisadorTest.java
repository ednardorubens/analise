package br.com.dbc.dados.analise.processes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.dbc.dados.analise.model.Cliente;
import br.com.dbc.dados.analise.model.Tipo;
import br.com.dbc.dados.analise.model.Venda;
import br.com.dbc.dados.analise.model.Vendedor;

@SuppressWarnings("unchecked")
class AnalisadorTest {
	
	public static final String expected1 = String.valueOf(ExtratorTest.SIZE);
	public static final String expected2 = String.valueOf(ExtratorTest.SIZE - 1);
	public static final String expected3 = "Vendedor_0";

	@Test
	@DisplayName("Deve retornar os valores ('" + ExtratorTest.SIZE + "', '" + ExtratorTest.SIZE + "', '" + (ExtratorTest.SIZE - 1) + "', 'Vendedor_0') quando receber a massa de dados gerada.")
	void testMassaDadosObj() {
		final Map<Tipo, List<Object>> massaDados = ExtratorTest.getDadosConvertidos();
		
		final List<Vendedor> vendedores = converter(massaDados.get(Tipo.VENDEDOR), Vendedor.class);
		final List<Cliente> clientes = converter(massaDados.get(Tipo.CLIENTE), Cliente.class);
		final List<Venda> vendas = converter(massaDados.get(Tipo.VENDA), Venda.class);
		
		Assertions.assertEquals(expected1, Analisador.buscarQtdVendedores(vendedores));
		Assertions.assertEquals(expected1, Analisador.buscarQtdClientes(clientes));
		Assertions.assertEquals(expected2, Analisador.buscarVendaMaisCara(vendas));
		Assertions.assertEquals(expected3, Analisador.buscarNomePiorVendedor(vendas));
	}
		
	@Test
	@DisplayName("Deve retornar valores default('0', '0', '0', '') quando o arquivo for vazio.")
	void testVazio() {
		avaliarDadosVazios(new HashMap<>());
	}

	@Test
	@DisplayName("Deve retornar valores default('0', '0', '0', '') quando os dados do arquivo forem vazios.")
	void testSemDados() {
		final Map<Tipo, List<Object>> massaSemDados = new HashMap<>();
		massaSemDados.put(Tipo.VENDEDOR, new ArrayList<>());
		massaSemDados.put(Tipo.CLIENTE, new ArrayList<>());
		massaSemDados.put(Tipo.VENDA, new ArrayList<>());
		
		avaliarDadosVazios(massaSemDados);
	}
	
	@Test
	@DisplayName("Deve retornar valores default('0', '0', '0', '') quando os dados do arquivo forem nulos.")
	void testListasNulas() {
		final Map<Tipo, List<Object>> massaSemDados = new HashMap<>();
		massaSemDados.put(Tipo.VENDEDOR, null);
		massaSemDados.put(Tipo.CLIENTE, null);
		massaSemDados.put(Tipo.VENDA, null);
		
		avaliarDadosVazios(massaSemDados);
	}
	
	@Test
	@DisplayName("Deve retornar valores default('0', '') quando os dados de vandas do arquivo forem nulos.")
	void testVendasNulas() {
		Assertions.assertEquals("0", Analisador.buscarVendaMaisCara(null));
		Assertions.assertEquals("", Analisador.buscarNomePiorVendedor(null));
	}

	private void avaliarDadosVazios(final Map<Tipo, List<Object>> massaDados) {
		final List<Vendedor> vendedores = converter(massaDados.get(Tipo.VENDEDOR), Vendedor.class);
		final List<Cliente> clientes = converter(massaDados.get(Tipo.CLIENTE), Cliente.class);
		final List<Venda> vendas = converter(massaDados.get(Tipo.VENDA), Venda.class);
		
		Assertions.assertEquals("0", Analisador.buscarQtdVendedores(vendedores));
		Assertions.assertEquals("0", Analisador.buscarQtdClientes(clientes));
		Assertions.assertEquals("0", Analisador.buscarVendaMaisCara(vendas));
		Assertions.assertEquals("", Analisador.buscarNomePiorVendedor(vendas));
	}
	
	private <T> List<T> converter(final List<Object> objetos, final Class<T> clazz) {
		final List<T> lista = new ArrayList<>();
		if (objetos != null) {
			lista.addAll((List<T>) objetos);
		}
		return lista;
	}

}
