# tarot-api

A self-hosted Ktor API serving the 78-card Rider-Waite-Smith tarot deck: keywords, long-form meanings, and journaling-style reflection prompts for both upright and reversed orientations.

## Endpoints

| Method | Path                    | Description                                   |
|--------|-------------------------|------------------------------------------------|
| `GET`  | `/health`               | Health check for the deployment platform       |
| `GET`  | `/api/v1/cards`         | All 78 cards. Optional `?suit=` filter         |
| `GET`  | `/api/v1/cards/{id}`    | Single card by id                              |

`suit` is one of `major_arcana`, `wands`, `cups`, `swords`, `pentacles`.

### Example: list

```
GET /api/v1/cards?suit=wands
```

```json
{
  "count": 14,
  "cards": [
    {
      "id": "ace_of_wands",
      "name": "Ace of Wands",
      "suit": "wands",
      "rank": "ace",
      "value": 1,
      "upright": {
        "keywords": ["inspiration", "new opportunities", "growth", "potential"],
        "meaning": "A single spark of inspiration arrives...",
        "reflection": "Which creative pursuit have I been hesitating to begin?"
      },
      "reversed": {
        "keywords": ["delays", "lack of motivation", "missed opportunity", "false starts"],
        "meaning": "That initial spark can fizzle before it really catches...",
        "reflection": "What is blocking me from acting on an exciting idea?"
      }
    }
  ]
}
```

### Example: single card

```
GET /api/v1/cards/the_fool
```

Returns the card object shown above (without the `count`/`cards` wrapper).

### Example: not found

```
GET /api/v1/cards/not-a-card
```

```json
{ "error": "Card not found: not-a-card" }
```

Returns HTTP `404`.

## Run locally

```bash
./gradlew run
```

Then, in another terminal:

```bash
curl http://localhost:8080/health
curl http://localhost:8080/api/v1/cards
```

## Test

```bash
./gradlew test
```

## Deploy (Fly.io)

```bash
fly launch   # first time only, skip postgres
fly deploy
```

`fly.toml` points Fly's health checks at `/health`. The `Dockerfile` builds a fat jar via the Ktor Gradle plugin's `buildFatJar` task and runs it on a slim JRE base image.
