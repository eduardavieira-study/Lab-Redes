import socket
import struct
import time

OFFSET = 74

GRUPO_MULTICAST = "230.0.0.1"
PORTA = 4446 + OFFSET

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, 2)
sock.setsockopt(
    socket.IPPROTO_IP, socket.IP_MULTICAST_IF, socket.inet_aton("127.0.0.1")
)
sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_LOOP, 1)

print(f"[Multicast] Enviando avisos para o grupo {GRUPO_MULTICAST}:{PORTA}")

for contador in range(1, 6):
    mensagem = f"Aviso #{contador}: a aula começa em {5 - contador} minuto(s)!"
    sock.sendto(mensagem.encode("utf-8"), (GRUPO_MULTICAST, PORTA))

    print(f"[Multicast] Enviado: {mensagem}")
    time.sleep(2)

sock.close()
