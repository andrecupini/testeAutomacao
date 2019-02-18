package br.com.finchsolucoes.testeAutomacao.component;

import br.com.finchsolucoes.testeAutomacao.entity.Jogo;
import br.com.finchsolucoes.testeAutomacao.repository.JogoRepository;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSection;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class Bot {

    @Autowired
    private JogoRepository jr;

    private List<Jogo> getJogos(String data) {

        WebClient webClient = new WebClient();

        List<Jogo> jogos = new ArrayList<>();

        try {
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            HtmlPage page = webClient.getPage("https://globoesporte.globo.com/placar-ge/" + data + "/jogos.ghtml");
            List<HtmlSection> sections = page.getByXPath("//*[@id=\"futuro\"]/section");

            if (!sections.isEmpty()) {

                for (HtmlSection section : sections) {

                    Jogo jogo = new Jogo();

                    List<HtmlSpan> campeonatos = section.getByXPath("./article/header/h1/span");
                    List<HtmlSpan> clubes = section.getByXPath("./article/div/div/span");
                    if (!campeonatos.isEmpty()) {
                        HtmlSpan campenonatoObj = campeonatos.get(0);
                        String campenonato = campenonatoObj.getTextContent();
                        String horario = campenonatoObj.getNextElementSibling().getTextContent();

                        jogo.setCampeonato(campenonato);
                        Date dt = new Date();
                        if (!data.equals("hoje")) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            dt = sdf.parse(data);
                        }
                        jogo.setData(dt);
                        jogo.setHorario(horario);

                        System.out.println(campenonato + horario);
                    }
                    if (!clubes.isEmpty()) {
                        String clubeMandante = clubes.get(0).getTextContent();
                        String clubeVisitante = clubes.get(1).getTextContent();

                        jogo.setClube_mandante(clubeMandante);
                        jogo.setClube_visitante(clubeVisitante);

                        System.out.println(clubeMandante + " x " + clubeVisitante);
                        System.out.println("--------------------------------------------------------");
                    }
                    if (jogo.isValid()) {
                        jogos.add(jogo);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jogos;
    }

    private List<Jogo> getJogos() {
        return getJogos("hoje");
    }

    public void run() {
        Date data = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.DATE, 1);
        String dia1 = new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
        cal.add(Calendar.DATE, 1);
        String dia2 = new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
        cal.add(Calendar.DATE, 1);
        String dia3 = new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());


        Bot bot = new Bot();

        List<Jogo> jogosHoje = bot.getJogos();
        for (Jogo jogoHoje : jogosHoje) {
            jr.save(jogoHoje);
        }

        List<Jogo> jogosDia1 = bot.getJogos(dia1);
        for (Jogo jogo : jogosDia1) {
            jr.save(jogo);
        }

        List<Jogo> jogosDia2 = bot.getJogos(dia2);
        for (Jogo jogo : jogosDia2) {
            jr.save(jogo);
        }

        List<Jogo> jogosDia3 = bot.getJogos(dia3);
        for (Jogo jogo : jogosDia3) {
            jr.save(jogo);
        }



    }
}
