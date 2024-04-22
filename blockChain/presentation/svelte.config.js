import { vitePreprocess } from "@sveltejs/vite-plugin-svelte";
import adapter from "@sveltejs/adapter-auto";

// { name: "What is supply chain", href: "supplychain" },
// { name: "What is blockchain", href: "blockchain" },
// {
// 	name: "What are supply chain management problems",
// 	href: "supplychain-problems",
// },
// {
// 	name: "The usage of blockchain in supply chain management",
// 	href: "usage-blockchain-supplychain",
// },
// {
// 	name: "Challenges",
// 	href: "challenges",
// },
// {
// 	name: "References",
// 	href: "references",
// },

/** @type {import('@sveltejs/kit').Config} */
const config = {
  preprocess: vitePreprocess(),
  kit: {
    prerender: {
      entries: [
        "/sections/supplychain",
        "/sections/blockchain",
        "/sections/supplychain-problems",
        "/sections/usage-blockchain-supplychain",
        "/sections/challenges",
        "/sections/references",
      ],
    },
    env: {
      dir: "./",
    },
    adapter: adapter(),
  },
};

export default config;
