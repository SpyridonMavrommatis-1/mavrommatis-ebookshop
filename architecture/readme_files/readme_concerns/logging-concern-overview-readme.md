### 📥 HTTP Request Logging

This Spring Boot application includes a configuration that logs details of every incoming 
HTTP request using `CommonsRequestLoggingFilter`.

#### ✅ What It Logs
- HTTP method and URL
- Query parameters (e.g., `?id=123`)
- Request headers
- Request body (up to 10KB)

#### 🧠 Why It's Useful
- Helps debug malformed requests and payloads
- Enables auditing of client behavior
- Improves observability in production environments

#### 🔧 Configuration Summary
- `includeQueryString = true`
- `includePayload = true`
- `includeHeaders = true`
- `maxPayloadLength = 10000`
- `afterMessagePrefix = "REQUEST DATA : "`

#### 📝 Example Log Output
REQUEST DATA : uri=/api/books?id=42; method=GET; headers=...; payload={"title":"The Hobbit"}
