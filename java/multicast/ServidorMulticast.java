import java.net.*;
import java.io.IOException;

public class ServidorMulticast {
    // TODO: substitua pelo seu OFFSET pessoal (ver seção 3.3)
    static final int OFFSET = 74;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("java.net.preferIPv4Stack", "true");

        String grupoMulticast = "230.0.0.1";
        int porta = 4446 + OFFSET;

        InetAddress grupo = InetAddress.getByName(grupoMulticast);
        try (MulticastSocket socket = new MulticastSocket()) {
            socket.setTimeToLive(2);
            socket.setNetworkInterface(NetworkInterface.getByInetAddress(InetAddress.getByName("127.0.0.1")));
            int contador = 1;
            System.out.println("[Multicast] Enviando avisos para o grupo " + grupoMulticast + ":" + porta);
            while (contador <= 5) {
                String mensagem = "Aviso #" + contador + ": a aula começa em " + (5 - contador) + " minuto(s)!";
                byte[] dados = mensagem.getBytes();
                DatagramPacket pacote = new DatagramPacket(dados, dados.length, grupo, porta);
                socket.send(pacote);
                System.out.println("[Multicast] Enviado: " + mensagem);
                contador++;
                Thread.sleep(2000);
            }
        }
    }
}