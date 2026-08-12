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