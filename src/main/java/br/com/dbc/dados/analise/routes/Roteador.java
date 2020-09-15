package br.com.dbc.dados.analise.routes;

import java.util.Map;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.dbc.dados.analise.processes.Analisador;
import br.com.dbc.dados.analise.processes.Extrator;

@Component
public class Roteador extends RouteBuilder {
	
	@Value("${HOMEPATH}")
	private String path;
	
	public void configure() {
		configure(path);
    }

	protected void configure(final String drivePath) {
		
		final String origem = "file://" + drivePath + "/data/in?antInclude=**/*.dat";
		final String destino = "file://" + drivePath + "/data/out?fileName=vendas.done.dat";
		
		from(origem)
		.log(LoggingLevel.INFO, "Lendo o arquivo ${file:name}.")
		.aggregate(constant(true), AggregationStrategies.string("\n")).completionTimeout(500L)
        .transform().body(String.class, new Extrator())
        .transform().body(Map.class, new Analisador())
        .log(LoggingLevel.INFO, "Gerando o arquivo vendas.done.dat.")
        .to(destino)
        .end();
	}
    
}
