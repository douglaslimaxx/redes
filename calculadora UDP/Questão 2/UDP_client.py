import socket
from threading import Thread
from queue import Queue

SERVER_NAME = "localhost"
SERVER_PORT = 12000

ADDR = (SERVER_NAME, SERVER_PORT)

def wait_response(confirmations):
    response = clientSocket.recvfrom(1024)
    confirmations.put(response)

clientSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

operation = input('Input operation:')

confirmations = Queue()

while True:
    print('Sending - {} - to Server'.format(operation))

    clientSocket.sendto(operation.encode('utf-8'), ADDR)

    connection_handler = Thread(target=wait_response, args=(confirmations, ))
    connection_handler.start()
    connection_handler.join(timeout=1)

    if (confirmations.qsize() > 0 and confirmations.get()[0].decode('utf-8') == 'ACK'):
        print('ACK recieved from Server\n')
        response = clientSocket.recvfrom(1024)

        if (response[0].decode('utf-8') != 'ACK'):
                print('Received result response from server')
                print("\nResult: {}\n".format(response[0].decode('utf-8')))
                break