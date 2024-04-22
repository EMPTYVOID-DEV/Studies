import express from "express";
import fetch from "node-fetch";
import { upload } from "./middleware/multer.js";
import path from "path";
import * as url from "url";

const __dirname = url.fileURLToPath(new URL(".", import.meta.url));
const assetsPath = path.join(__dirname, "../assets/");
const app = express();

const isDocker = process.argv[2];

const serviceOrigin = isDocker ? "service" : "localhost";

app.use(express.static(assetsPath));

app.post("/upload", upload.single("file"), async (req, res) => {
  const filename = req.file.filename;
  const payload = {
    filename,
  };
  await fetch(`http://${serviceOrigin}:6000/hash`, {
    method: "POST",
    body: JSON.stringify(payload),
    headers: { "Content-Type": "application/json" },
  });
  res.status(200).send({ hashLoc: "hash" + filename.split(".")[0] + ".txt" });
});

app.use("*", (req, res) => {
  res.sendFile(path.join(assetsPath, "index.html"));
});

app.listen(3000, () => {
  console.log("listing at port 3000 ");
});
