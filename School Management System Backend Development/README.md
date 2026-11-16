# Multi-School Management System — Backend

Java 21 / Spring Boot 3.4 / Maven / PostgreSQL backend for the QR-based school arrival and dismissal project.

## Included
- One-time initial school + school administrator setup
- JWT authentication and school membership context
- School-scoped student, guardian and class APIs
- QR credential issuance (opaque random token; SHA-256 hash stored) and revocation
- Arrival/departure validation and attendance history
- Mock SMS gateway (default) and configurable Arkesel adapter
- Notification history and attendance summary
- Flyway database migration and Swagger UI

## Requirements
- JDK 21
- PostgreSQL 14+
- IntelliJ IDEA or Maven 3.9+

## 1. Create database
```sql
CREATE DATABASE school_management_db;
```

## 2. Configure environment
Set `DATABASE_USERNAME` and `DATABASE_PASSWORD`. Set `JWT_SECRET` to a random secret of at least 32 bytes. Defaults are for local development only. Do not commit real secrets.

Example (PowerShell):
```powershell
$env:DATABASE_USERNAME="postgres"
$env:DATABASE_PASSWORD="your_local_password"
$env:JWT_SECRET="replace-with-a-long-random-secret-at-least-32-bytes"
```

## 3. Run
```bash
mvn spring-boot:run
```

Swagger: http://localhost:8080/swagger-ui/index.html
Health: http://localhost:8080/api/v1/health

## 4. First-time setup (only works while schools table is empty)
`POST /api/v1/setup/initial-school`
```json
{"schoolName":"Demo School","schoolCode":"DEMO01","adminName":"School Admin","email":"admin@example.com","password":"ChangeMe123!"}
```

## 5. Login
`POST /api/v1/auth/login`
```json
{"email":"admin@example.com","password":"ChangeMe123!","schoolId":1}
```
Use the returned access token as `Authorization: Bearer <token>`.

## Main endpoints
| Method | Path | Purpose |
|---|---|---|
| GET | `/api/v1/health` | Health check |
| POST | `/api/v1/setup/initial-school` | One-time initial setup |
| POST | `/api/v1/auth/login` | Login |
| GET | `/api/v1/schools/me` | Current school |
| GET/POST | `/api/v1/classes` | List/create classes |
| GET/POST | `/api/v1/students` | List/create students |
| GET/PUT | `/api/v1/students/{id}` | Read/update student |
| GET/POST | `/api/v1/guardians` | List/create guardians |
| POST | `/api/v1/guardians/link` | Link guardian to student |
| POST | `/api/v1/qr/students/{studentId}` | Issue QR credential and PNG base64 |
| DELETE | `/api/v1/qr/{credentialId}` | Revoke QR credential |
| POST | `/api/v1/attendance/scan` | Record arrival/departure |
| GET | `/api/v1/attendance` | Attendance history |
| GET | `/api/v1/notifications` | Notification history |
| GET | `/api/v1/reports/attendance-summary` | Daily summary |

## SMS configuration
By default `SMS_PROVIDER=mock`; messages are printed to the application console and marked sent for development. To enable the Arkesel adapter, set `SMS_PROVIDER=arkesel`, `ARKESEL_API_KEY`, and `ARKESEL_SENDER_ID`. Verify the current Arkesel API endpoint, request format, sender-ID approval and account credits against your Arkesel dashboard before live use. Never expose API keys in source code.

## Important production hardening still required
This is a functional MVP foundation, not a production-certified school system. Before real student data is used, add refresh-token/session revocation, password reset and email verification, rate limiting, stronger audit coverage, pagination, guardian consent/verification, notification outbox with scheduled retries and provider delivery callbacks, PostgreSQL row-level security, backups, monitoring, CORS allow-list, HTTPS, and comprehensive automated tenant-isolation/security tests. Review Ghana's Data Protection Act, 2012 (Act 843), and obtain school/institution approval before processing children's data.

## Notes
- QR codes contain random opaque tokens, not student names or phone numbers. The raw token is returned once at issuance; store/print it securely.
- Attendance history currently uses the latest event to validate arrival/departure state. For real deployments, define daily attendance boundaries, corrections, overnight edge cases and concurrency rules.
- Never trust a school ID from the browser; tenant context comes from the signed JWT membership.
