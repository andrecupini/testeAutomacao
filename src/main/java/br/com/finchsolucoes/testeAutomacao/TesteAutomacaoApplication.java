package br.com.finchsolucoes.testeAutomacao;

import br.com.finchsolucoes.testeAutomacao.component.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class TesteAutomacaoApplication {

    @Autowired
    private Bot bot;

    public static void main(String[] args) {
        SpringApplication.run(TesteAutomacaoApplication.class, args);
    }

    @PostConstruct
    public void run() {
        bot.run();
    }

}

