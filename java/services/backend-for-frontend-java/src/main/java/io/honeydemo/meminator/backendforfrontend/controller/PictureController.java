package io.honeydemo.meminator.backendforfrontend.controller;

import java.net.MalformedURLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.instrumentation.annotations.WithSpan;

@RestController
public class PictureController {

    private final WebClient phraseClient;
    private final WebClient imageClient;
    private final WebClient memeClient;
    private static final Logger logger = LogManager.getLogger("backendForFrontend");

    @Autowired
    public PictureController(WebClient.Builder webClientBuilder) {
        this.imageClient = webClientBuilder.baseUrl("http://image-picker:10116").build();
        this.memeClient = webClientBuilder.baseUrl("http://meminator:10117").build();
        this.phraseClient = webClientBuilder.baseUrl("http://phrase-picker:10118").build();
    }

    @PostMapping("/createPicture")
    public Mono<Object> createPicture() throws MalformedURLException {

        // RG - get user name & id, or set default if null
        String user_id = "12345";
        String user_name = System.getProperty("user.name");
        
        if (user_name == null) {
            user_name = "Default User";
        }

        // RG - get current span and add custom attributes from vars above
        Span span = Span.current();
        span.setAttribute("user.name", user_name);
        span.setAttribute("user.id", user_id);

        var phraseResult = phraseClient.get().uri("/phrase").retrieve().toEntity(PhraseResult.class);
        var imageResult = imageClient.get().uri("/imageUrl").retrieve().toEntity(ImageResult.class);

        var bothResults = Mono.zip(phraseResult, imageResult);

        // Set content type header
        MediaType mediaType = MediaType.IMAGE_PNG;
        logger.info("media type is " + mediaType);

        var meme = bothResults.flatMap(v -> {
            String phrase = v.getT1().getBody().getPhrase();
            String imageUrl = v.getT2().getBody().getImageUrl();
            logger.info("app.phrase=" + phrase + ", app.imageUrl=" + imageUrl);

            return memeClient.post().uri("/applyPhraseToPicture").bodyValue(new MemeRequest(phrase, imageUrl))
                    .retrieve().toEntity(byte[].class);
        });

        // Return the image file as a ResponseEntity
        return meme.map(v -> {
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(v.getBody());
        });
    }

    static class PhraseResult {
        private String phrase;

        public PhraseResult() {
        }

        // RG - Span around getPhrase method + set phrase selected attribute
        @WithSpan("getPhrase")
        public String getPhrase() {
            Span span = Span.current();
            span.setAttribute("phrase.selected", phrase);
            return phrase;
        }

        public void setPhrase(String phrase) {
            this.phrase = phrase;
        }
    }

    static class ImageResult {
        private String imageUrl;

        public ImageResult() {
        }

        // RG - Span around getImageUrl method + set image selected attribute
        @WithSpan("getImage")
        public String getImageUrl() {
            Span span = Span.current();
            span.setAttribute("image.selected", imageUrl);
            return imageUrl;
        }

        public void setImageUrl(String phrase) {
            this.imageUrl = phrase;
        }

    }

    static class MemeRequest {
        private String phrase;
        private String imageUrl;

        public MemeRequest() {
        }

        public MemeRequest(String phrase, String imageUrl) {
            this.phrase = phrase;
            this.imageUrl = imageUrl;
        }

        public String getPhrase() {
            return phrase;
        }

        public void setPhrase(String phrase) {
            this.phrase = phrase;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
