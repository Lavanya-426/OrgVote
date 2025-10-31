OrgVoteApp - Simple Election Management System (Interview-ready)
-----------------------------------------------------------------
Run:
1. mvn clean package
2. mvn spring-boot:run
   or: java -jar target/OrgVoteApp-0.0.1-SNAPSHOT.jar
3. Open http://localhost:8080/login.html

Features:
- Admin & User login (from uploaded CSV/XLSX)
- Admin: upload users (CSV/XLSX), upload candidates (CSV/XLSX), create/start/end election, view results
- User: login and vote once
- Minimal Tailwind-based UI (via CDN)
- H2 in-memory DB (use /h2-console for inspection)

CSV formats:
- Users CSV: username,password,role
- Candidates CSV: candidate name per line

Notes:
- This is a demo: passwords stored plain-text (for simplicity).
- H2 is in-memory; restart clears data.

