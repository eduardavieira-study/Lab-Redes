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

---

## 6.7 Perguntas — Parte C (Multicast)

Durante os testes de Multicast, foi necessário realizar algumas alterações nos códigos para que a comunicação funcionasse tanto na implementação em Java quanto em Python. Inicialmente, foram utilizadas as implementações fornecidas, porém o macOS apresentou problemas no envio de pacotes multicast, mesmo após diferentes tentativas de configuração e testes de rede. Após os ajustes necessários nos sockets, foi possível realizar a comunicação e validar o funcionamento do Multicast.

### 1. Qual é a diferença fundamental entre enviar a mesma mensagem para 3 clientes usando **unicast repetido 3 vezes** e enviar **uma única vez** via multicast? Pense em termos de tráfego de rede.

No **unicast**, o servidor precisa enviar a mesma mensagem individualmente para cada cliente. Dessa forma, para três clientes, a mesma mensagem será transmitida três vezes, gerando um maior tráfego de dados na rede.

No **multicast**, o servidor envia a mensagem uma única vez para o endereço do grupo multicast. Os clientes que estiverem inscritos nesse grupo recebem a mensagem. Dessa forma, o multicast pode reduzir o tráfego da rede quando vários clientes precisam receber a mesma informação.

### 2. O que é o **TTL** (time-to-live) configurado no socket multicast e por que ele é importante para controlar o alcance dos pacotes na rede?

O **TTL (Time To Live)** define o alcance que um pacote multicast pode ter na rede, limitando a quantidade de saltos que ele pode realizar entre dispositivos de rede.

No código utilizado, o TTL foi configurado com o valor `2`. Isso limita o alcance dos pacotes, evitando que os avisos sejam propagados por uma quantidade muito grande de redes. Dessa forma, o TTL ajuda a controlar o alcance da comunicação multicast e evita que os pacotes sejam enviados para redes que não deveriam recebê-los.

### 3. Se um dos clientes ficar temporariamente offline e voltar depois, ele recebe os avisos que perdeu? Por quê? Relacione com a arquitetura de comunicação em grupo.

Não. Se um cliente estiver offline no momento em que uma mensagem for enviada, ele não receberá essa mensagem quando voltar.

Isso acontece porque o Multicast utilizado realiza a comunicação por meio de datagramas UDP, enviando as mensagens para o grupo naquele momento. Os clientes precisam estar inscritos no grupo e disponíveis para receber os pacotes quando eles forem enviados. Como não existe, nessa implementação, um mecanismo para armazenar as mensagens enviadas, os avisos perdidos não são recuperados posteriormente.

Portanto, quando o cliente voltar, ele poderá receber as próximas mensagens enviadas para o grupo, mas não receberá as mensagens que foram enviadas enquanto estava offline.

---

