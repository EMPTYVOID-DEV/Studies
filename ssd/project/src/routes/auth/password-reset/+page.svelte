<script lang="ts">
	import { enhance } from '$app/forms';
	import type { SubmitFunction } from '@sveltejs/kit';

	export let form: { message: string };
	let email = '';
	let state: 'send' | 'verify' | 'reset' = 'send';
	const verifyEnhance: SubmitFunction = async () => {
		return ({ result, update }) => {
			if (result.type == 'success') state = 'reset';
			update();
		};
	};

	const sendEnhance: SubmitFunction = async () => {
		return ({ result, update }) => {
			if (result.type == 'success') state = 'verify';
			update();
		};
	};
	const resetEnhance: SubmitFunction = async ({ formData }) => {
		formData.append('email', email);
	};
</script>

<div class="w-screen h-screen flex justify-center items-center">
	<div class="w-[60%] md:w-5/6 flex flex-col justify-center items-center gap-3">
		{#if state == 'send'}
			<span class="text-foregroundColor">
				Please provide the email address associated with the account for which you would like to
				reset the password.</span
			>
			<form action="?/send" method="post" use:enhance={sendEnhance} class="contents">
				<input
					type="email"
					name="email"
					bind:value={email}
					class="w-full p-2 rounded-bdr border-2 font-bodyFont text-foregroundColor border-foregroundColor focus:border-primaryColor"
				/>
				<button class="w-5/6 p-2 bg-primaryColor font-bodyFont text-backgroundColor rounded-bdr"
					>Send me an email</button
				>
			</form>
		{:else if state == 'verify'}
			<span class="text-foregroundColor">
				An email has been sent to your address. If you have not yet received it, we recommend
				checking your spam folder. Once located, please proceed by copying the verification code and
				pasting it below.
			</span>
			<form action="?/verify" method="post" use:enhance={verifyEnhance} class="contents">
				<input
					type="text"
					name="otp"
					class="w-full p-2 rounded-bdr border-2 font-bodyFont text-foregroundColor border-foregroundColor focus:border-primaryColor"
				/>
				<button class="w-5/6 p-2 bg-primaryColor font-bodyFont text-backgroundColor rounded-bdr"
					>Verify</button
				>
			</form>
		{:else}
			<span class="text-foregroundColor">Type your new password </span>
			<form action="?/reset" method="post" use:enhance={resetEnhance} class="contents">
				<input
					type="password"
					name="password"
					class="w-full p-2 rounded-bdr border-2 font-bodyFont text-foregroundColor border-foregroundColor focus:border-primaryColor"
				/>
				<button class="w-5/6 p-2 bg-primaryColor font-bodyFont text-backgroundColor rounded-bdr"
					>confirm</button
				>
			</form>
		{/if}
		{#if form}
			<span class="text-dangerColor">{form.message}</span>
		{/if}
	</div>
</div>
