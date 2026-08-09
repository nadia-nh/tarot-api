# Implementation checklist

- [x] Add this checklist
- [x] Scaffold Ktor project (build files, gradle wrapper, `GET /health`)
- [x] Add data models (`TarotCard`, `Orientation`, `CardListResponse`, `ErrorResponse`) + serialization test
- [x] Add `cards.json` (normalized from source data) + `CardRepository` + repository tests
- [x] Add API routes (`GET /api/v1/cards`, `GET /api/v1/cards/{id}`) + plugin wiring + route tests
- [x] Add deployment scaffolding (`Dockerfile`, `fly.toml`) — `docker build .` verified working
- [x] Write `README.md`
