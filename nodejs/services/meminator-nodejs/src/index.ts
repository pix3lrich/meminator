import express, { Request, Response } from 'express';
import { download } from "./download";
import { applyTextWithImagemagick } from "./applyTextWithImagemagick";

const app = express();
const PORT = 10117;

// Middleware to parse JSON bodies
app.use(express.json());

app.get("/health", (req: Request, res: Response) => {
    res.send("OK");
});

app.post('/applyPhraseToPicture', async (req, res) => {
    try {
        const input = req.body;
        let { phrase: inputPhrase, imageUrl } = input;
        const phrase = inputPhrase.toLocaleUpperCase();

        // download the image, defaulting to a local image
        const inputImagePath = await download(imageUrl);

        const outputImagePath = await applyTextWithImagemagick(phrase, inputImagePath);
        res.sendFile(outputImagePath);
    }
    catch (error) {
        console.error('Error creating picture:', error);
        res.status(500).send('Internal Server Error');
    }
})

// Start the server
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
