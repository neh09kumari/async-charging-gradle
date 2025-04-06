****Async Charging Challenge ****

This project is a simulation of an asynchronous service communication flow for an electric vehicle charging station.

**It supports:**

Ktor server with `/start-session` API  
Real HTTP callback to a client (e.g., Python server)  
this version uses Gradle

---

 How to Run the Project

**1. Prerequisites**

- JDK 11
- Gradle 
- Python 3 (for optional callback server)
- IntelliJ IDEA (recommended)

---

**** 2. Run the Kotlin Application****

In IntelliJ:

- Open the project
- Run `App.kt` (in `com.example`)
- You should see:
  ```
  Responding at http://0.0.0.0:8080
  ```

the server is started on port **8080**.

---
** How to Test the `/start-session` API**

**** 1. Use curl:****

```bash
curl --location 'http://localhost:8080/start-session' \
--header 'Content-Type: application/json' \
--data '{
  "station_id": "123e4567-e89b-12d3-a456-426614174000",
  "driver_token": "validDriverToken1234567890",
  "callback_url": "http://localhost:9000/fake-callback"
}'
```

**Expected response:**

```json
{
  "status": "accepted",
  "message": "Request is being processed asynchronously. The result will be sent to the provided callback URL."
}
```

---

**Simulate the Callback Server(optional)**

 1. Navigate to `fake-callback/` folder

```bash
cd fake-callback
```

2. Run the Python server : 
```bash
python3 fake_callback.py
```

This server listens on `http://localhost:9000` and prints any callback it receives.

**You should see below output:**
---
Received POST to /:

127.0.0.1 - - [06/Apr/2025 09:55:29] "POST / HTTP/1.1" 200 -

---

** How it Works**

1. `/start-session` receives your session request
2. It enqueues it in an async channel
3. A background worker processes it after 1 second
4. The worker makes a real HTTP POST request to the provided callback URL

---

