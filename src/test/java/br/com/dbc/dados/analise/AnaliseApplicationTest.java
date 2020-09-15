package br.com.dbc.dados.analise;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class AnaliseApplicationTest {

	@Test
	void test() {
		AnaliseApplication.main(new String[] {
			"--spring.profiles.active=test",
			"--logging.level.root=OFF"
	    });
	}

}