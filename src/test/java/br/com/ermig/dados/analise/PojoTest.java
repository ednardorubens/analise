package br.com.ermig.dados.analise;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.ermig.dados.analise.model.Cliente;
import br.com.ermig.dados.analise.model.Item;
import br.com.ermig.dados.analise.model.Venda;
import br.com.ermig.dados.analise.model.Vendedor;

class PojoTest {

	@Test
	void testCriarCliente() {
		final String cnpj = "123554";
		final String name = "João";
		final String businessArea = "Comercial";
		final Cliente cliente = new Cliente(List.of(cnpj, name, businessArea));
		Assertions.assertEquals(cnpj, cliente.getCnpj());
		Assertions.assertEquals(name, cliente.getName());
		Assertions.assertEquals(businessArea, cliente.getBusinessArea());
	}
	
	@Test
	void testCriarVendedor() {
		final String cpf = "123554";
		final String name = "João";
		final String salary = "56,23";
		final Vendedor vendedor = new Vendedor(List.of(cpf, name, salary));
		Assertions.assertEquals(cpf, vendedor.getCpf());
		Assertions.assertEquals(name, vendedor.getName());
		Assertions.assertEquals(salary, vendedor.getSalary());
	}
	
	@Test
	void testCriarVenda() {
		final String id = "01";
		final String itens = "";
		final String salesmanName = "Alberto";
		final Venda venda = new Venda(List.of(id, itens, salesmanName));
		Assertions.assertEquals(id, venda.getId());
		Assertions.assertTrue(venda.getItens().isEmpty());
		Assertions.assertEquals(salesmanName, venda.getSalesmanName());
		Assertions.assertEquals(0d, venda.getValor());
	}
	
	@Test
	void testCriarItemVenda() {
		final String id = "1";
		final String quantity = "3";
		final String price = "9";
		final Item item = new Item(id, quantity, price);
		Assertions.assertEquals(id, item.getId());
		Assertions.assertEquals(quantity, item.getQuantity());
		Assertions.assertEquals(price, item.getPrice());
		Assertions.assertEquals(27D, item.getValor());
	}

}
