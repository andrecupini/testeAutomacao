package br.com.finchsolucoes.testeAutomacao.repository;

import br.com.finchsolucoes.testeAutomacao.entity.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long> {
}
