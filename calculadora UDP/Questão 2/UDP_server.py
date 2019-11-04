import socket
from threading import Thread
import time

UDP_IP = 'localhost'
UDP_PORT = 12000

ADDR = (UDP_IP, UDP_PORT)

def calculate(op, a, b):
    if (op == 'ADD'):
        result = a + b
    elif (op == 'SUB'):
        result = a - b
    elif (op == 'MULT'):
        result = a * b
    elif (op == 'DIV'):
        if (b == 0):
            result = None
        else:
            result = a / b
    elif (op == 'EXP'):
        result = a ** b
    return result

def handle_connection(sentence, client):
    #time.sleep(2)

    serverSocket.sendto(str('ACK').encode('utf-8'), client)
    
    #time.sleep(2)

    capitalizedSentence = sentence.decode('utf-8').upper()
    op, a, b = capitalizedSentence.split()

    result = 0
    a = int(a)
    b = int(b)

    result = str(calculate(op, a, b)).encode('utf-8')
    print('\nResponding result: {}'.format(result.decode('utf-8')))

    serverSocket.sendto(result, client)

serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
serverSocket.bind(ADDR)

print("The Server is ready to receive")

while True:
    sentence, client = serverSocket.recvfrom(1024)
    print('\nRecieved request from IP {}, port {}'.format(client[0], client[1]))

    connection_handler = Thread(target=handle_connection, args=(sentence, client, ))
    connection_handler.start()