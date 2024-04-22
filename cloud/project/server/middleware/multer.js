import multer from "multer";
import path from "path";
import * as url from "url";

const __dirname = url.fileURLToPath(new URL(".", import.meta.url));
const filesPath = path.join(__dirname, "../../assets");

const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, filesPath);
  },
  filename: function (req, file, cb) {
    cb(null, file.originalname);
  },
});
export const upload = multer({ storage });
