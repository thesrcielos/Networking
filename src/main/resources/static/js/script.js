function enviarUrl() {
    const url = document.getElementById("urlInput").value;
    fetch("/api/enviar-url", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ url: url })
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById("respuesta").innerText = data.mensaje;
    })
    .catch(error => {
        console.error("Error:", error);
    });
}
