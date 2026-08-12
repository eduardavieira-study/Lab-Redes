# Respostas
## 4.6 Perguntas — Parte A (TCP)

### 1. O que acontece se você iniciar o cliente antes do servidor? Por que isso ocorre, considerando o funcionamento do TCP?

Quando o cliente é iniciado antes do servidor, ocorre um erro na tentativa de conexão, pois ainda não existe um servidor aguardando conexões na porta configurada. Isso acontece porque o TCP precisa estabelecer uma conexão entre o cliente e o servidor antes que os dados possam ser enviados.

Durante os testes, percebi que, ao iniciar o cliente antes do servidor, a comunicação não funciona. Quando o servidor é iniciado primeiro, ele fica aguardando uma conexão e, depois que o cliente é iniciado, a comunicação acontece normalmente.

### 2. O TCP garante que as mensagens cheguem na ordem em que foram enviadas. Qual mecanismo do protocolo é responsável por isso?

O TCP utiliza números de sequência para identificar os dados enviados. Com isso, ele consegue organizar os segmentos recebidos e garantir que os dados sejam entregues à aplicação na mesma ordem em que foram enviados.

Nos testes realizados, as mensagens chegaram ao servidor na mesma ordem em que foram enviadas pelo cliente, confirmando esse comportamento do TCP.

### 3. Na sua implementação, o que aconteceria se dois clientes tentassem se conectar ao mesmo tempo? O código atual suporta isso? Justifique observando o código do servidor.

A implementação atual não foi desenvolvida para atender vários clientes simultaneamente. O servidor realiza uma chamada de `accept()` para receber uma conexão e, depois disso, fica tratando as mensagens desse cliente.

Para permitir vários clientes ao mesmo tempo, seria necessário modificar a implementação para aceitar novas conexões continuamente e utilizar, por exemplo, threads para tratar cada cliente separadamente.

Durante os testes, também foi possível perceber que a comunicação entre Java e Python funciona normalmente. Por exemplo, um cliente Java conseguiu se conectar ao servidor Python, e também é possível fazer o contrário. Isso acontece porque ambos utilizam o protocolo TCP, independentemente da linguagem utilizada na implementação.

---

## 5.6 Perguntas — Parte B (UDP)

### 1. No passo 2 da tarefa, o que aconteceu quando você enviou uma mensagem com o servidor desligado? Compare com o que aconteceria em TCP e explique a diferença observada, relacionando com o conceito de "sem conexão".

Quando o servidor UDP estava desligado, o cliente conseguiu enviar a mensagem mesmo sem existir uma conexão estabelecida com o servidor. Porém, como não havia nenhum servidor para receber e responder ao datagrama, o cliente ficou aguardando uma resposta.

A diferença para o TCP está justamente no estabelecimento da conexão. No TCP, o cliente tenta estabelecer uma conexão antes de enviar os dados e, se o servidor estiver desligado, a tentativa de conexão falha. Já no UDP não existe essa etapa de conexão, então o cliente simplesmente envia o datagrama para o endereço e a porta informados, sem ter a garantia de que ele será recebido.

### 2. Cite dois exemplos de aplicações reais que usam UDP e explique, para cada uma, por que a confiabilidade do TCP não é essencial (ou até atrapalharia).

Um exemplo são os **jogos online**, que precisam receber informações rapidamente sobre a posição dos jogadores e outras ações. Nesse caso, a velocidade é mais importante do que garantir a entrega de cada pacote, pois uma informação atrasada pode deixar de ser útil.

Outro exemplo é o **streaming de áudio e vídeo em tempo real**. Pequenas perdas de dados podem ser menos prejudiciais do que atrasos causados pela retransmissão de pacotes. Por isso, o UDP pode ser utilizado quando é mais importante manter a transmissão acontecendo em tempo real.

### 3. No código, o servidor UDP não mantém nenhum registro de "quem está conectado". Isso seria possível de implementar? O que mudaria na arquitetura da aplicação?

Sim. Seria possível implementar um controle dos clientes no servidor, armazenando informações como endereço IP e porta de cada cliente que enviar uma mensagem.

Nesse caso, o servidor precisaria manter uma estrutura para armazenar esses clientes e atualizar essa lista conforme novas mensagens fossem recebidas. Porém, isso não transformaria o UDP em um protocolo orientado à conexão. O controle seria feito pela própria aplicação, já que o UDP continua não estabelecendo uma conexão entre cliente e servidor.