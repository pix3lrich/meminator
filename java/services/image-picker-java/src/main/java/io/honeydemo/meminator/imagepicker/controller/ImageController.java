package io.honeydemo.meminator.imagepicker.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    private static final List<String> ImageList = Arrays.asList(
            "Angrybird.JPG",
            "Arco&Tub.png",
            "IMG_9343.jpg",
            "a real heatmap.png",
            "angry-lemon-ufo.JPG",
            "austintiara4.png",
            "baby-geese.jpg",
            "bbq.jpg",
            "beach.JPG",
            "bunny-mask.jpg",
            "busted-light.jpg",
            "cat-glowing-eyes.JPG",
            "cat-on-leash.JPG",
            "cat-with-bowtie.heic",
            "cat.jpg",
            "clementine.png",
            "cow-peeking.jpg",
            "different-animals-01.png",
            "dratini.png",
            "everything-is-an-experiment.png",
            "experiment.png",
            "fine-food.jpg",
            "flower.jpg",
            "frenwho.png",
            "genshin-spa.jpg",
            "grass-and-desert-guy.png",
            "honeycomb-dogfood-logo.png",
            "horse-maybe.png",
            "is-this-emeri.png",
            "jean-and-statue.png",
            "jessitron.png",
            "keys-drying.jpg",
            "leftridge.png",
            "lime-on-soap-dispenser.jpg",
            "loki-closeup.jpg",
            "lynia.png",
            "ninguang-at-work.png",
            "paul-r-allen.png",
            "pile-of-cars.png",
            "please.png",
            "roswell-nose.jpg",
            "roswell.JPG",
            "salt-packets-in-jar.jpg",
            "scarred-character.png",
            "square-leaf-with-nuts.jpg",
            "stu.jpeg",
            "sweating-it.png",
            "tanuki.png",
            "tennessee-sunset.JPG",
            "this-is-fine-trash.jpg",
            "three-pillars-2.png",
            "trash-flat.jpg",
            "walrus-painting.jpg",
            "windigo.png",
            "yellow-lines.JPG");

    private static final String bucketName = System.getenv("BUCKET_NAME");
    private static final String imageUrlPrefix = "https://" + bucketName + ".s3.amazonaws.com/";

    @GetMapping("/imageUrl")
    public ImageResult imageUrl() {
        // choose a random image from the list
        String chosenImage = ImageList.get((int) (Math.random() * ImageList.size()));
        // INSTRUMENTATION: add a useful attribute
        String imageUrl = imageUrlPrefix + chosenImage;
        return new ImageResult(imageUrl);
    }

    public static class ImageResult {
        private String imageUrl;

        public ImageResult(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getImageUrl() {
            return this.imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
