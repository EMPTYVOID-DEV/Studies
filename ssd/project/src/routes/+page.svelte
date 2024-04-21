<script>
	import { goto } from '$app/navigation';
</script>

<div class="w-screen h-screen flex flex-col justify-center items-center gap-3">
	<h1 class="text-primaryColor">SSD Project</h1>
	<button
		class="w-1/4 p-2 bg-primaryColor font-bodyFont text-backgroundColor rounded-bdr font-bold"
		on:click={() => goto('/chat')}>CHAT</button
	>
</div>
