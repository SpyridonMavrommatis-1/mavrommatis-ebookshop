# 🎯 `aop/` — Logging Aspect for Service Layer

This package contains a Spring AOP aspect that provides **cross-cutting logging logic** for all public service methods.  
It enhances observability, debuggability, and performance tracking across the application without polluting business logic.

---

## 🔍 Class: `LoggingAspect`

### 📁 Location:
```java
com.mavrommatis.ebookshop.ebookshop.aop.LoggingAspect
```

### 🧠 Purpose:
- Intercepts all public methods in the `service` package and subpackages
- Logs:
    - Method entry and argument values
    - Execution time in milliseconds
    - Exceptions (if thrown)

### 🧩 Technologies Used

- Spring AOP  
  Uses `@Aspect`, `@Around`, and `@Pointcut` to apply cross-cutting logic (e.g., logging) 
- around public service methods.

- SLF4J Logging (via Logback)  
  Unified logging API (`Logger`, `LoggerFactory`) abstracted by SLF4J and implemented via Logback, 
- the default backend in Spring Boot.

- Runtime Method Reflection via AOP  
  Retrieves method-level metadata at runtime using `joinPoint.getSignature()` and `MethodSignature`, 
- enabling inspection of method name, class, and arguments without modifying business logic.

---

## ✨ Features

| Feature                   | Description |
|---------------------------|-------------|
| `@Around` Advice          | Full control around method execution (before, after, error) |
| Pointcut expression       | Targets: `execution(public * com.mavrommatis.ebookshop.ebookshop.service..*(..))` |
| Duration tracking         | Uses `System.currentTimeMillis()` |
| Argument introspection    | Logs method arguments with `Arrays.toString(...)` |
| Error logging             | Captures and logs exception type and message |

---

## 💡 Example Output

```text
➡️ Entering method: BookServiceImpl.getBookById with arguments: [123]
✅ Method BookServiceImpl.getBookById executed in 18 ms
```

Or in case of failure:

```text
❌ Exception in method CustomerServiceImpl.updateCustomer after 42 ms: Customer not found
```

---

## 🛠️ Notes

- The aspect is marked with `@Component` and picked up by Spring automatically.
- Designed specifically for the **service layer** to monitor business logic performance and trace errors.
- Non-intrusive: no changes required in actual service classes.

---

## 📌 Future Ideas

- Add conditional logging (e.g. based on environment or annotation presence)
- Extend to controller layer with separate aspect
- Integrate with tracing tools (e.g., Zipkin, Sleuth)

---

> 🧭 *“AOP is the art of doing things everywhere, without doing them anywhere.”*  
> This logging aspect keeps your service layer clean, while keeping your logs rich.

