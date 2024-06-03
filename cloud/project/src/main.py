import pymupdf
import zipfile
from io import BytesIO
from typing import List
from fastapi import FastAPI, UploadFile, Response


app = FastAPI()


def fileCheck(files: List[UploadFile]):
    for file in files:
        if not file.content_type == "application/pdf":
            return True
    return False


@app.get("/")
def main():
    return Response("", status_code=302, headers={"location": "/docs"})


@app.post("/create_embedded_pdf")
async def create_embedded_pdf(files: List[UploadFile]):
    if fileCheck(files):
        return Response("All files must be in pdf format", 403)
    dummyPdf = pymupdf.open()
    dummyPdf._newPage(pno=-1, width=595, height=842)
    for file in files:
        file_data = await file.read()
        file_buffer = BytesIO(file_data)
        dummyPdf.embfile_add(
            buffer_=file_buffer, filename=file.filename, name=file.filename
        )
    output = BytesIO()
    dummyPdf.save(output)
    output.seek(0)

    return Response(
        content=output.read(),
        status_code=200,
        media_type="application/pdf",
        headers={"Content-Disposition": "attachment; filename='output.pdf'"},
    )


@app.post("/extract_embedded_pdf")
async def extract_embedded_pdf(file: UploadFile):
    if fileCheck([file]):
        return Response("File must be in pdf format", 403)
    file_data = await file.read()
    file_buffer = BytesIO(file_data)
    file_doc = pymupdf.open(filetype="pdf", stream=file_buffer)
    zip_buffer = BytesIO()
    with zipfile.ZipFile(zip_buffer, "w") as pdfZip:
        for attachment in file_doc.embfile_names():
            attachmentBytes = file_doc.embfile_get(attachment)
            pdfZip.writestr(attachment, attachmentBytes)
    zip_buffer.seek(0)
    return Response(
        content=zip_buffer.read(),
        status_code=200,
        media_type="application/zip",
        headers={"Content-Disposition": "attachment; filename='pdf.zip'"},
    )
