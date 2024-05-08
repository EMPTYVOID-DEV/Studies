<script lang="ts">
	import { page } from '$app/stores';
	import SendIcon from '$lib/client/ui/icons/sendIcon.svelte';
	import { io } from '$lib/client/utils/clientSocket';
	import { generateId } from 'lucia';
	import { onMount } from 'svelte';

	type message = {
		username: string;
		content: string;
		room: string;
	};

	const room = $page.params.room;
	const randUsername = generateRandomUsername();
	let currentMessage: string = '';
	let messages: message[] = [];

	function generateRandomUsername() {
		const seed = $page.data.username;
		const random = generateId(4);
		return `${seed}_${random}`;
	}

	function sendMessage() {
		io.emit('message', { content: currentMessage, username: randUsername, room });
		currentMessage = '';
	}
	onMount(() => {
		io.on('message', (message: message) => (messages = [...messages, message]));
		io.emit('join', { username: randUsername, room });
		window.addEventListener('keydown', (e) => {
			if (e.key == 'Enter') sendMessage();
		});
	});
</script>

<div class="w-screen h-screen grid px-[1%] py-[1%]">
	<section class="w-full h-full flex flex-col gap-4 md:px-2 md:pb-2">
		<div
			class="w-full h-full flex flex-col gap-2 p-3 overflow-auto bg-foregroundColor/15 rounded-bdr border-2 border-foregroundColor"
		>
			{#each messages as message}
				<div class="flex flex-col gap-[2px] {message.username != randUsername ? 'self-end' : ''}">
					<span class="text-primaryColor">{message.username}</span>
					<span class="text-foregroundColor bg-primaryColor/25 `rounded-bdr px-2 py-1 w-fit"
						>{message.content}</span
					>
				</div>
			{/each}
		</div>
		<div class="w-full flex gap-2 items-end">
			<input
				bind:value={currentMessage}
				type="text"
				placeholder="New message"
				class="w-full outline-none p-3 border-2 bg-primaryColor bg-opacity-30 placeholder-mutedColor text-foregroundColor border-primaryColor rounded-bdr"
			/>
			<button
				class="cursor-pointer p-2 border-4 border-primaryColor rounded-bdr"
				on:click={sendMessage}
			>
				<SendIcon />
			</button>
		</div>
	</section>
</div>
