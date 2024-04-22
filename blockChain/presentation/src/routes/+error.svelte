<script>
  import { goto } from "$app/navigation";
  import { page } from "$app/stores";

  /**
   * @type {Map<number, { name: string; message: string }>}
   */
  const errorMap = new Map([
    [
      400,
      {
        name: "BAD REQUEST",
        message: "It seems there is something wrong on your side",
      },
    ],
    [
      404,
      {
        name: "PAGE NOT FOUND",
        message:
          "The page you looking for either does'not exist or has it name change.",
      },
    ],
    [
      500,
      {
        name: "SERVICE UNAVAILABLE",
        message:
          "The service you demanding has encountered some issues recently try access it later.",
      },
    ],
    [
      403,
      {
        name: "FORBIDDEN",
        message:
          "You should'not be here this area is unaccessable at the moment.",
      },
    ],
  ]);
</script>

<div
  class="error flex flex-col items-center justify-center gap-8 w-full h-screen bg-gradient-to-b from-backgroundColor to-intermediateColor overflow-hidden"
>
  <h1
    class="bg-clip-text text-transparent bg-gradient-to-r from-dangerColor to-dangerColor/50"
  >
    Oops!
  </h1>
  <div class="flex flex-col items-center gap-4 w-4/5">
    {#if errorMap.has($page.status)}
      <h3 class=" text-foregroundColor text-center">
        {$page.status} - {errorMap.get($page.status).name}
      </h3>
      <p class="text-foregroundColor text-center">
        {errorMap.get($page.status).message}
      </p>
    {:else}
      <h3 class=" text-foregroundColor text-center">
        {$page.status} - unknown
      </h3>
      <p class="text-foregroundColor text-center">unknown error has occured</p>
    {/if}
  </div>
  <button
    on:click={() => goto("/")}
    class="bg-dangerColor text-backgroundColor py-2 px-4 rounded-md font-semibold"
  >
    GO TO HOMEPAGE
  </button>
</div>
