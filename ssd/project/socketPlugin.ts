import { Server } from 'socket.io';
import { ViteDevServer } from 'vite';

type message = {
	username: string;
	content: string;
	room: string;
};

type newUser = {
	username: string;
	room: string;
};

function ioServer(server) {
	const io = new Server(server);
	io.on('connection', (socket) => {
		socket.on('join', (newUser: newUser) => {
			socket.join(newUser.room);
		});
		socket.on('message', (message: message) => {
			io.in(message.room).emit('message', message);
		});
	});
}

export const webSocketServer = {
	name: 'webSocketServer',
	configureServer(server: ViteDevServer) {
		ioServer(server.httpServer);
	}
};
