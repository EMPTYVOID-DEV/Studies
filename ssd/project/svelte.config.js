import nodeAdapter from '@sveltejs/adapter-node';
import { vitePreprocess } from '@sveltejs/vite-plugin-svelte';

/** @type {import('@sveltejs/kit').Config} */
const config = {
	preprocess: vitePreprocess(),
	kit: {
		env: {
			dir: './'
		},
		adapter: nodeAdapter({
			out: './express/build'
		})
	}
};

export default config;
