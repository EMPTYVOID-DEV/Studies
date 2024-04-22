import express from "express";
import path from "path";
import * as url from "url";
import { hashAndWriteToFile } from "./utils/hash.js";

const __dirname = url.fileURLToPath(new URL(".", import.meta.url));
const assetsPath = path.join(__dirname, "../assets/");

const app = express();

app.use(express.json());

app.post("/hash", (req, res) => {
  const filename = req.body.filename;
  const filePathSrc = path.join(assetsPath, filename);
  const filePathDest = path.join(
    assetsPath,
    "hash" + filename.split(".")[0] + ".txt"
  );
  hashAndWriteToFile(filePathSrc, filePathDest);
  res.status(200).send();
});

app.listen(6000, () => {
  console.log("listing at port 6000 ");
});
