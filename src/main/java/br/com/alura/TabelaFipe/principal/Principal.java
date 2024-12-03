package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.DadosVeiculos;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.model.Veiculo;
import br.com.alura.TabelaFipe.service.ConsumoApi;
import br.com.alura.TabelaFipe.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/",
            MARCA= "/marcas/", MODELOS= "/modelos/", ANO="/anos/";
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();


    public void exibeMenu(){
        System.out.println("*** ESCOLHA O TIPO DE VEÍCULO QUE DESEJA BUSCAR: ***\ncarros\nmotos\ncaminhoes");
        var tipo = leitura.nextLine();
        var json = ConsumoApi.obterDados(ENDERECO + tipo.toLowerCase() + MARCA);
        var marcas = conversor.obterLista(json, DadosVeiculos.class);
        marcas.stream().sorted(Comparator.comparing(DadosVeiculos::nome))
                .forEach(System.out::println);

        System.out.println("DIGITE O CÓDIGO DA MARCA DESEJADA: ");
        var codigoMarca = leitura.nextLine();
        json = ConsumoApi.obterDados(ENDERECO + tipo.toLowerCase() + MARCA + codigoMarca + MODELOS);
        var modeloLista = conversor.obterDados(json, Modelos.class);
        System.out.println("Modelos da marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(DadosVeiculos::nome))
                .forEach(System.out::println);

        System.out.println("BUSQUE PELO NOME OU TRECHO: ");
        var busca = leitura.nextLine();
        List<DadosVeiculos> modeloBuscado = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(busca.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("Modelos filtrados: ");
        modeloBuscado.forEach(System.out::println);

        System.out.println("Digite o código do modelo: ");
        var codigoModelo = leitura.nextLine();
        json = ConsumoApi.obterDados(ENDERECO + tipo.toLowerCase() + MARCA
                + codigoMarca + MODELOS + codigoModelo + ANO);
        List<DadosVeiculos> anos = conversor.obterLista(json, DadosVeiculos.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = ENDERECO + tipo.toLowerCase() + MARCA
                    + codigoMarca + MODELOS + codigoModelo + ANO + anos.get(i).codigo();
            json = ConsumoApi.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("Veículos filtrados por ano: ");
        veiculos.forEach(System.out::println);
    }
}
