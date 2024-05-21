from fastapi import FastAPI, Request, Response, UploadFile, File
from PyPDF2 import PdfMerger, PdfReader
from io import BytesIO


app = FastAPI()


@app.get("/")
def sayhi():
    return "Cloud micro project"


@app.post("/create_embedded_pdf")
async def create_embedded_pdf(request: Request):
    fd = await request.form()
    pdfs = fd.getlist("pdfs")
    merger = PdfMerger()
    for pdf in pdfs:
        pdf_data = await pdf.read()
        bytes = BytesIO(pdf_data)
        merger.append(bytes)
    merged_pdf = BytesIO()
    merger.write(merged_pdf)
    merged_pdf.seek(0)
    headers = {"Content-Disposition": "inline; filename=merged.pdf"}
    return Response(
        merged_pdf.getvalue(), 200, headers=headers, media_type="application/pdf"
    )


@app.post("/extract_embedded_pdf")
async def extract_embedded_pdf(pdf: UploadFile = File(...)):
    pdf_data = await pdf.read()
    bytes_io = BytesIO(pdf_data)
    pdf_reader = PdfReader(bytes_io)

    attachments = {}
    for page_num in range(len(pdf_reader.pages)):
        page = pdf_reader.pages[page_num]

        if "/EmbeddedFiles" in page:
            embedded_files = page["/EmbeddedFiles"].getObject()

            for embedded_file in embedded_files:
                file_name = embedded_file.getObject()["/F"].decode()
                file_stream = (
                    embedded_file.getObject()["/EF"].getObject()["/F"].getData()
                )

                attachments[file_name] = file_stream

    return {"attachments": attachments}
