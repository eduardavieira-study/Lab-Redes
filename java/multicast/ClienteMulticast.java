import java.net.*;
import java.io.IOException;

public class ClienteMulticast {
    // TODO: substitua pelo seu OFFSET pessoal (ver seção 3.3) — use o MESMO valor
    // do servidor
    static final int OFFSET = 74;

    public static void main(String[] args) throws IOException {
        System.setProperty("java.net.preferIPv4Stack", "true");

        String grupoMulticast = "230.0.0.1";
        int porta = 4446 + OFFSET;

        try (MulticastSocket socket = new MulticastSocket(porta)) {
            InetAddress grupo = InetAddress.getByName(grupoMulticast);
            InetSocketAddress endpointGrupo = new InetSocketAddress(grupo, porta);

            NetworkInterface interfaceRede = NetworkInterface.getByInetAddress(InetAddress.getByName("127.0.0.1"));

            socket.joinGroup(endpointGrupo, interfaceRede);
            System.out.println(
                    "[Multicast] Inscrito no grupo " + grupoMulticast + ":" + porta + ". Aguardando avisos...");

            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket pacote = new DatagramPacket(buffer, buffer.length);
                socket.receive(pacote);
                String mensagem = new String(pacote.getData(), 0, pacote.getLength());
                System.out.println("[Multicast] Recebido: " + mensagem);
            }
        }
    }
}
