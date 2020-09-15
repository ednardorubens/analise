package br.com.dbc.dados.analise.routes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoteadorTest {

	@Test
	@SuppressWarnings("resource")
	@DisplayName("Deve ler o arquivo de entrada, processar os dados e gerar o arquivo de saída.")
	void testConfigure() throws Exception {
		final CamelContext camelContext = new DefaultCamelContext();
	    camelContext.addRoutes(new Roteador() {
	    	@Override
	    	public void configure() {
	    		super.configure("src/test/resources");
	    	}
	    });
	    camelContext.start();
	    Thread.sleep(2500);
	    
	    final Path pathOut = Path.of("src/test/resources/data/out/vendas.done.dat");
		Assertions.assertTrue(Files.exists(pathOut));
		
		restoreFile("vendas1.dat", "vendas2.dat");
		camelContext.stop();
	}

	private void restoreFile(final String ... fileNames) throws IOException {
		for (String fileName : fileNames) {
			final Path pathIn = Path.of("src/test/resources/data/in/" + fileName);
			final Path pathProcess = Path.of("src/test/resources/data/in/.camel/" + fileName);
			Files.move(pathProcess, pathIn);
		}
	}

}
