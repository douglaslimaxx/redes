from socket import *

serverName = "localhost"
serverPort = 12000

clientSocket = socket(AF_INET, SOCK_DGRAM)

sentence = input('Input lowercase sentence:')

clientSocket.sendto(sentence.encode('utf-8'), (serverName,serverPort))

modifiedSentence = clientSocket.recvfrom(1024)

print("From Server:", modifiedSentence[0].decode('utf-8'))
