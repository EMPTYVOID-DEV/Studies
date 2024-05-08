<script lang="ts">
	import { enhance } from '$app/forms';
	import type { SubmitFunction } from '@sveltejs/kit';

	export let form: { message: string };

	let state: 'send' | 'verify' = 'send';
	const sendEnhance: SubmitFunction = async () => {
		return ({ result, update }) => {
			if (result.type == 'success') state = 'verify';
			update();
		};
	};
</script>

<div class="w-screen h-screen flex justify-center items-center">
	<div class="w-[60%] md:w-5/6 flex flex-col justify-center items-center gap-3">
		{#if state == 'send'}
			<span class="text-foregroundColor">
				Just one more step: let's verify your email. Click "Send me an email" below to receive the
				verification email in your inbox.</span
			>
			<form action="?/send" method="post" use:enhance={sendEnhance} class="contents">
				<button class="w-5/6 p-2 bg-primaryColor font-bodyFont text-backgroundColor rounded-bdr"
					>Send me an email</button
				>
			</form>
		{:else}
			<span class="text-foregroundColor">
				An email has been sent to your address. If you have not yet received it, we recommend
				checking your spam folder. Once located, please proceed by copying the verification code and
				pasting it below.
			</span>
			<form action="?/verify" method="post" use:enhance class="contents">
				<input
					type="text"
					name="otp"
					class="w-full p-2 rounded-bdr border-2 font-bodyFont text-foregroundColor border-foregroundColor focus:border-primaryColor"
				/>
				<button class="w-5/6 p-2 bg-primaryColor font-bodyFont text-backgroundColor rounded-bdr"
					>Verify</button
				>
			</form>
		{/if}
		{#if form}
			<span class="text-dangerColor">{form.message}</span>
		{/if}
	</div>
</div>
