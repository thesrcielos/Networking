package org.eci.url;

import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class Exercise2 {

    @PostMapping("/enviar-url")
    public Response recibirUrl(@RequestBody UrlRequest request) throws MalformedURLException {

        return new Response(UrlReader.readUrl(request.getUrl()));
    }

    // DTO interno
    static class UrlRequest {
        private String url;
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    static class Response {
        private String mensaje;
        public Response(String mensaje) { this.mensaje = mensaje; }
        public String getMensaje() { return mensaje; }
    }

}
