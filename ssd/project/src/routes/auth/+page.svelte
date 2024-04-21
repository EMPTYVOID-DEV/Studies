<script lang="ts">
	import { enhance } from '$app/forms';
	import { goto } from '$app/navigation';

	export let form: { message: string };
	let action: 'sign in' | 'sign up' = 'sign up';
</script>

<div class="w-screen h-screen bg-backgroundColor flex flex-col items-center justify-center gap-4">
	<form
		class="flex flex-col gap-3 w-[50%] md:w-5/6 items-center justify-center"
		method="post"
		action="?/{action}"
		use:enhance
	>
		{#if action == 'sign up'}
			<input
				class="w-full p-2 rounded-bdr border-2 border-foregroundColor font-bodyFont focus:border-primaryColor text-foregroundColor"
				type="text"
				name="username"
				placeholder="Username"
			/>
		{/if}
		<input
			class="w-full p-2 text-foregroundColor font-bodyFont rounded-bdr border-2 border-foregroundColor focus:border-primaryColor"
			type="email"
			name="email"
			placeholder="Email"
		/>
		<input
			class="w-full p-2 rounded-bdr border-2 font-bodyFont text-foregroundColor border-foregroundColor focus:border-primaryColor"
			type="password"
			name="password"
			placeholder="Password"
		/>
		<button
			class="w-5/6 p-2 bg-primaryColor font-bodyFont text-backgroundColor rounded-bdr"
			type="submit">{action === 'sign up' ? 'Sign Up' : 'Sign In'}</button
		>
		{#if form}
			<span class="text-dangerColor">{form.message}</span>
		{/if}
	</form>

	<div class="flex flex-col gap-1">
		{#if action == 'sign up'}
			<span class="text-foregroundColor"
				>Already have an account <button
					class="text-primaryColor"
					on:click={() => (action = 'sign in')}>Sign In</button
				></span
			>
		{:else}
			<span class="text-foregroundColor"
				>Don't have an account <button
					class="text-primaryColor"
					on:click={() => (action = 'sign up')}>Sign Up</button
				></span
			>
			<span class="text-foregroundColor"
				>Forget your password <button
					class="text-primaryColor"
					on:click={() => goto('/auth/password-reset')}
				>
					Reset it</button
				></span
			>
		{/if}
	</div>
</div>
