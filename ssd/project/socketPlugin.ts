import { Server } from 'socket.io';
import { ViteDevServer } from 'vite';

type message = {
	username: string;
	content: string;
	attachment: string;
};

function ioServer(server) {
	const io = new Server(server);
	io.on('connection', (socket) => {
		socket.on('join', (username: string) => {
			io.emit('join', username);
			// @ts-ignore
			socket.username = username;
		});
		socket.on('message', (message: message) => {
			io.emit('message', message);
		});
		socket.on('disconnect', () => {
			//@ts-ignore
			io.emit('leave', socket.username);
		});
	});
}

export const webSocketServer = {
	name: 'webSocketServer',
	configureServer(server: ViteDevServer) {
		ioServer(server.httpServer);
	}
};
