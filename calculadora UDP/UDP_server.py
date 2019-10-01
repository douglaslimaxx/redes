from socket import *

serverPort = 12000
serverSocket = socket(AF_INET,SOCK_DGRAM)
serverSocket.bind(('localhost',serverPort))

print ("The server is ready to receive")

while 1:
    sentence, addr = serverSocket.recvfrom(1024)
     
    capitalizedSentence = sentence.decode('utf-8').upper()

    op, a, b = capitalizedSentence.split()

    resultado = 0

    a = int(a)
    b = int(b)

    if (op == 'ADD'):
        resultado = a + b
    elif (op == 'SUB'):
        resultado = a - b
    elif (op == 'MULT'):
        resultado = a * b
    elif (op == 'DIV'):
        if (b == 0):
            resultado = None
        else:
            resultado = a / b
    elif (op == 'EXP'):
        resultado = a ** b


    serverSocket.sendto(str(resultado).encode('utf-8'), addr)