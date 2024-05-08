<script lang="ts">
	import { goto } from '$app/navigation';
	export let roomName: string = '';
</script>

<div class="w-screen h-screen flex items-center justify-center">
	<div class="w-[50%] flex flex-col gap-2 items-center">
		<h1 class="text-primaryColor">Welcome</h1>
		<span class="text-foregroundColor">To start chatting enter a room name</span>
		<input
			bind:value={roomName}
			placeholder="Room name"
			class="w-full p-2 rounded-bdr border-2 border-foregroundColor font-bodyFont focus:border-primaryColor text-foregroundColor"
		/>
		<button
			on:click={() => goto(`/chat/${roomName}`)}
			class="w-5/6 p-2 bg-primaryColor font-bodyFont text-backgroundColor rounded-bdr"
			type="submit">Join room</button
		>
	</div>
</div>
