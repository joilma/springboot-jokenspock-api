package br.com.jokenspock.enums;

public enum MensagemValidacao {
	
	SEM_JOGADAS_CONSULTA("Não existem jogadas a exibir."),
	SEM_JOGADAS_REMOVER("Não há jogadas a remover"),
	ERRO_CONSULTA("Não foi possível obter as jogadas"),
	SEM_JOGADA_ENVIADA("Nenhuma jogada foi enviada."),
	SEM_VENCEDOR("Não consegui determinar quem ganhou :("),
	SUCESSO("Jogador %s Vitória"),
	ERRO_CADASTRO("Erro ao cadastrar jogadas."),
	SUCESSO_CADASTRO("Jogadas cadastradas com sucesso."),
	SEM_JOGADAS_REMOVIDAS("Não foram encontradas as jogadas para remover"),;
	
	private String mensagem;
	
	MensagemValidacao (String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
