import clientSocket from 'socket.io-client';

const endPoint = 'http://192.168.1.7:5173';

const socket = clientSocket(endPoint);

export const io = socket;
