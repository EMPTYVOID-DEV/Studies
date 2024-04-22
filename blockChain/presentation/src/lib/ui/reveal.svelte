<script>
  import Reveal from "reveal.js";
  import Markdown from "reveal.js/plugin/markdown/markdown";
  import highlight from "reveal.js/plugin/highlight/highlight";
  import "reveal.js/dist/reveal.css";
  import "reveal.js/plugin/highlight/monokai.css";
  import "reveal.js/dist/theme/black-contrast.css";
  import { onMount } from "svelte";
  export let mdFile;

  onMount(() => {
    const width = document.getElementById("content").clientWidth;
    const height = document.getElementById("content").clientHeight;
    const deck = new Reveal({ slideNumber: true, width, height });

    deck.initialize({
      plugins: [Markdown, highlight],
    });
  });
</script>

<div class="reveal">
  <div class="slides">
    <section
      data-charset="iso-8859-15"
      data-markdown="/sections/{mdFile}.md"
      data-separator="^\n\n\n"
      data-separator-vertical="^\n\n"
    />
  </div>
</div>

<style>
  .reveal :global(p) {
    font-size: clamp(1.2rem, calc(1.2rem + ((1vw - 0.48rem) * 0.1736)), 1.5rem);
    font-family: "Lora" serif;
  }
  .reveal :global(h1) {
    font-size: clamp(2.8rem, calc(2.8rem + ((1vw - 0.48rem) * 1.3889)), 3.8rem);
    font-weight: bold;
    font-family: "Lora" serif;
  }
  .reveal :global(h2) {
    font-size: clamp(2.5rem, calc(2rem + ((1vw - 0.48rem) * 1.3889)), 3rem);
    font-weight: bold;
    font-family: "Lora" serif;
  }
  .reveal :global(h3) {
    font-size: clamp(2rem, calc(1.5rem + ((1vw - 0.48rem) * 0.5208)), 2.5rem);
    font-weight: bold;
    font-family: "Lora" serif;
  }
  .reveal :global(h4) {
    font-size: clamp(
      1.5rem,
      calc(1.125rem + ((1vw - 0.48rem) * 0.3472)),
      1.875rem
    );
    font-weight: bold;
    font-family: "Lora" serif;
  }
</style>
