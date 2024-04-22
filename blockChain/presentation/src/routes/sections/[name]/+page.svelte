<script lang="ts">
  import { goto } from "$app/navigation";
  import { page } from "$app/stores";
  import { links } from "$lib/const";
  import Reveal from "$lib/ui/reveal.svelte";
  $: sectionIndex = links.findIndex((el) => el.href == $page.params.name);
  $: previous = links[sectionIndex - 1];
  $: next = links[sectionIndex + 1];
</script>

<div class="w-svw h-svh flex flex-col gap-4">
  <nav class="w-full h-16 flex items-center justify-between px-4">
    <h3 class="capitalize text-primaryColor justify-self-center">
      {links[sectionIndex].name}
    </h3>
    <div class="flex gap-2">
      {#if previous}
        <button
          class="bg-foregroundColor text-backgroundColor rounded-bdr px-3 py-2"
          on:click={() => goto(`/sections/${previous.href}`)}>Previous</button
        >
      {/if}
      {#if next}
        <button
          class="bg-primaryColor text-backgroundColor rounded-bdr px-3 py-2"
          on:click={() => goto(`/sections/${next.href}`)}>Next</button
        >
      {/if}
    </div>
  </nav>
  {#key sectionIndex}
    <div id="content" class="w-full flex-grow px-4">
      <Reveal mdFile={links[sectionIndex].name} />
    </div>
  {/key}
</div>
