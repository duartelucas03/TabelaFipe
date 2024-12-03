package br.com.alura.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(@JsonAlias("Valor") String valor,
                      @JsonAlias("Modelo") String modelo,
                      @JsonAlias("Marca") String marca,
                      @JsonAlias("AnoModelo") Integer ano,
                      @JsonAlias("Combustivel") String tipoCombustivel) {
}
