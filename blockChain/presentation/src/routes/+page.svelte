<script lang="ts">
  import { links } from "$lib/const";
</script>

<div
  class="w-svw h-svh grid grid-cols-2 gap-6 items-center px-4 bg-backgroundColor"
>
  <div class="w-full flex flex-col gap-4">
    <h1 class="w-full text-primaryColor">
      Blockchain: Streamlining Supply Chain Operations
    </h1>
    <span class="text-foregroundColor text-opacity-60 font-bold">
      Revolutionizing supply chains with blockchain technology. Enabling
      seamless traceability, data integrity, and automated processes. Fostering
      trust and transparency from source to consumer.
    </span>
  </div>
  <div class="w-5/6 flex flex-col gap-2">
    <h2 class="text-foregroundColor">Topics</h2>
    {#each links as link}
      <a
        class="capitalize text-foregroundColor rounded-bdr bg-primaryColor bg-opacity-50 px-4 py-2 hover:bg-opacity-80"
        href={`/sections/${link.href}`}>{link.name}</a
      >
    {/each}
  </div>
</div>
