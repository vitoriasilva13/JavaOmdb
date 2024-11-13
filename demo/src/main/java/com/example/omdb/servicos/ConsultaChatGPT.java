package com.example.omdb.servicos;

import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

/**
 * Classe que realiza a consulta ao serviço de tradução do OpenAI
 * */
public class ConsultaChatGPT {

    /**
     * Método que realiza a consulta ao serviço de tradução do OpenAI
     * @param texto texto a ser traduzido
     * @return texto traduzido ou o texto original caso ocorra algum erro
     * */
    public static String obterTraducao(String texto) {

        if (System.getenv("OPENAI_API_KEY") == null) {
            return texto;
        }

        OpenAiService service = new OpenAiService(System.getenv("OPENAI_API_KEY"));
        try {
            CompletionRequest requisicao = CompletionRequest.builder()
                    .model("gpt-3.5-turbo-instruct")
                    .prompt("traduza para o português o texto: " + texto)
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            var resposta = service.createCompletion(requisicao);
            return resposta.getChoices().get(0).getText();
        } catch (OpenAiHttpException e) {
            return texto;
        }
    }
}
