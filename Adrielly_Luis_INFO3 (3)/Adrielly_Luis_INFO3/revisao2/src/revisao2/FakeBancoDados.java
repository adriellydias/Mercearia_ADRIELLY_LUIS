package revisao2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FakeBancoDados {

    private static Vector<Produto> produtos;

    //leitura do arquivo
    private static void cargaArquivo() {
        //ajuste na criação do vetor vindo de inicialização e mudanças
        if (produtos == null) {
            produtos = new Vector<>();
        } else {
            produtos.clear();
        }

        // não se passa a extensão apenas ao manipular diretórios
        File arquivoCsv = new File("C:\\Users\\infin\\OneDrive\\Área de Trabalho\\progII\\Códigos\\produtos.csv");
        try {

            //trava de arquivo para impossibilidade de alteração externa ou apontamento de software.
            FileReader marcaLeitura = new FileReader(arquivoCsv);

            //otimiza a leitura ao acoplar vários bytes em um único envio ao contrário do unitário FileReader
            BufferedReader bufLeitura = new BufferedReader(marcaLeitura);

            // ---------------- leitura das linhas -----------------
            //primeira (cabeçalho) - descartável ->
            bufLeitura.readLine();

            //primeira linha útil (tendo os dados necessários) ->
            String linha = bufLeitura.readLine();

            //toda última linha de arquivo é representada como null pela ausência de informação
            while (linha != null) {
                //outras linhas ->
                //corte das cedulas em um array
                String infos[] = linha.split(";");

                //refinamento dos dados vindos do array
                int cod = Integer.parseInt(infos[0]);
                String nome = infos[1];
                double preco = Double.parseDouble(infos[2]);
                int quant = Integer.parseInt(infos[3]);

                //produtos adcionados no vetor
                produtos.add(new Produto(cod, nome, preco, quant));

                //att da variável linha lendo a de baixo para continuidade do cilco até o término
                linha = bufLeitura.readLine();
            }
            //Liberando do arquivo para outros processos
            bufLeitura.close();
            //tratamento de possíveis erros
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo não existe");
        } catch (IOException e) {
            System.out.println("Arquivo corrompido");
        }
    }

    public static Produto consultaProdutoCod(int cod) {
        //Se o arquivo ainda não foi carregado, basta carregá-lo
        if (produtos == null) {
            cargaArquivo();
        }

        //busca pelo produto usando o código informado
        for (Produto prodI : produtos) {      //for (int i = 0; i<produtos.size; i++)
            if (prodI.getCodigo() == cod) //if(produtos.get(i).getCodigo() == cod)
            {
                return prodI;
            }
        }
        //não existe produto com o código informado
        return null;
    }

    public static void atualizaArquivo(){
        File arquivo = new File("C:\\Users\\infin\\OneDrive\\Área de Trabalho\\progII\\Códigos\\produtos.csv");
        
        try {
            FileWriter escritor = new FileWriter(arquivo);
            
            BufferedWriter bufEscrita = new BufferedWriter(escritor);
            
            for(int i = 0; i < produtos.size();i++){
                //pela concatenação com uma string java entende a chamada auto do toString.
                bufEscrita.write(produtos.get(i)+"\n");
            }
            
            bufEscrita.flush();
            bufEscrita.close();
            
        } catch (IOException ex) {
            System.out.println("Dispositivo com falha");
        }
    }
}
