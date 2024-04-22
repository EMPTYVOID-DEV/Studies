import crypto from "crypto";
import fs from "fs";

export function hashAndWriteToFile(
  inputFilePath,
  outputFilePath,
  algorithm = "sha256",
) {
  return new Promise((resolve, reject) => {
    const hash = crypto.createHash(algorithm);
    const input = fs.createReadStream(inputFilePath);
    const output = fs.createWriteStream(outputFilePath);

    input.on("error", (err) => {
      reject(err);
    });

    output.on("error", (err) => {
      reject(err);
    });

    input.pipe(hash).on("error", (err) => {
      reject(err);
    });

    hash.once("readable", () => {
      hash.pipe(output);
    });

    output.on("close", () => {
      resolve();
    });
  });
}
