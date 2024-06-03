import subprocess


def start():
    cmd = ["uvicorn", "main:app", "--reload", "--port", "8000", "--app-dir", "src"]
    subprocess.run(cmd)


start()
