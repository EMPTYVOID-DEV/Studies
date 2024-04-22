const fileInput = document.getElementById("fileInput");
const uploadBtn = document.getElementById("uploadBtn");

uploadBtn.addEventListener("click", async () => {
  const file = fileInput.files[0];
  if (!file) {
    alert("Please select a file to upload.");
    return;
  }

  const formData = new FormData();
  formData.append("file", file);
  try {
    const response = await fetch("http://localhost:3000/upload", {
      method: "POST",
      body: formData,
    });

    if (response.ok) {
      const data = await response.json();
      const a = document.createElement("a");
      a.href = data.hashLoc;
      a.textContent = "download_your_hashed_file";
      document.body.append(a);
    } else {
      alert("File upload failed.");
    }
  } catch (error) {
    console.error("Error:", error);
    alert("An error occurred during file upload.");
  }
});
