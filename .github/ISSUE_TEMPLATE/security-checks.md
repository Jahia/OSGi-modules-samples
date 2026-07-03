---
name: Security checks
about: Create a security checklist
title: ''
type: Task
projects: ["Jahia/22"]
labels: ['security-checks', 'Area:Security']
---

> [!TIP]
> **Focus:** Risks that might be introduced by this feature's code, data flows, and business logic.
> **Expected time:** 30–60 minutes
> **When:** At the best time decided by the team and in all cases before the release.
>
> **TL;DR:** Fill in the feature summary, go through each of the 6 STRIDE sections, check off items (or mark N/A), document outcomes, and create issues for anything High or Critical. If your feature has no new inputs, APIs, data access, or permission changes, document why and close.
> 
> **AI agent:** We suggest delegating the first review to a [Security architect agent](https://github.com/msitarzewski/agency-agents/blob/main/security/security-architect.md) with a prompt similar to this:
> > In the context of our security due diligence at Jahia (we're a software editor), we are performing a security review when tackling an Epic, this review is organized via a dedicated issue (link), I would like you to guide me through filling this document, one step at a time and provide the answer as a comment to this issue. Main is the current release, this epic was implemented between the xxx tag and the current state of main.
---
### How to Use This Checklist
1. Fill in the **Feature Summary** section below first.
2. For each checkbox in the **Feature Scope** section: assess whether it applies to your feature.
- If it applies → check the box and document your finding/mitigation in the "Outcome" section.
- If it doesn't apply → check the box and write "N/A — [brief reason]".
- If you're unsure → leave unchecked and flag for discussion with the team.
3. Create issues for any finding rated **High** or above.
4. This issue can be closed when all checkboxes are addressed, outcomes are documented, and issues are linked.
> [!NOTE]
> **Fast path:** If this feature introduces **no new inputs, no new data access, no new APIs, and no permission changes** (e.g., purely cosmetic/CSS), document why below and close this issue.
---
### ⚠️ Platform Baseline Gate
> Does your feature modify any of the following? Authentication, session management, logging infrastructure, rate limiting, or platform access controls.
> **If yes → escalate to architecture before proceeding.**
These are established once and inherited by all stories. Assume they are in place unless your story explicitly modifies them:
- Authentication infrastructure (credential attack protections, session/token lifecycle, MFA for admin).
- Platform rate limiting, quotas, and DoS protections.
- Log infrastructure (integrity, log access controls, retention, alerting).
- Time synchronization and audit infrastructure.
- Regular privileged role review and access governance processes.
---
### Feature Summary (fill before reviewing)
- **What does this feature do (one sentence)?**
- **What data does it read/write?**
- **What external systems does it interact with?**
- **Who can trigger it?** (anonymous / authenticated / admin)
---
### Feature Scope (STRIDE)
Review the 6 risk areas below for threats your feature introduces or changes.
#### 1. Spoofing Identity (Authentication)
- [ ] Does this feature check user identity or delegated permissions before sensitive actions?
- [ ] Are authorization checks enforced at object/record level? *(e.g., can user A access user B's content by changing an ID in the URL — IDOR/BOLA)*
- [ ] Does this feature create service-to-service calls or machine identities? If yes, are they authenticated and authorized?
- [ ] Is tenant/domain isolation maintained if applicable?
> [!IMPORTANT]
> **Outcome / Identified actions / Issues:**
> - ...
#### 2. Tampering with Data (Integrity)
- [ ] What untrusted inputs does this feature accept (params, files, payloads, config)? Are they validated and sanitized?
- [ ] What injection risks exist? *(e.g., does a search field get interpolated into a JCR/SQL query? Is user input rendered unescaped in JSP/React?)*
- [ ] If this feature calls other services, are responses validated?
- [ ] If files are handled, are type/content verified and stored safely (not just extension)?
> [!IMPORTANT]
> **Outcome / Identified actions / Issues:**
> - ...
#### 3. Repudiation (Auditing)
- [ ] What security-relevant actions should be logged (auth failures, data changes, admin actions)?
- [ ] Are logs correlated/traceable (who, what, when, from where)?
- [ ] Can audit data be replayed or spoofed?
> [!IMPORTANT]
> **Outcome / Identified actions / Issues:**
> - ...
#### 4. Information Disclosure (Confidentiality)
- [ ] What sensitive data does this feature handle (PII, tokens, keys, config)?
- [ ] Is it minimized, classified, and access-restricted?
- [ ] Is it protected in transit and at rest per policy?
- [ ] Can it leak via errors, debug output, headers, caches, or logs?
> [!IMPORTANT]
> **Outcome / Identified actions / Issues:**
> - ...
#### 5. Denial of Service (Availability)
- [ ] Does this feature call remote services? Are there timeouts and backoff?
- [ ] Are there expensive operations (queries, file processing, regex, loops)? Can they be bounded or rate-limited?
- [ ] Can this feature's endpoints or async workers be starved or overloaded? Design graceful degradation.
> [!IMPORTANT]
> **Outcome / Identified actions / Issues:**
> - ...
#### 6. Elevation of Privilege (Authorization)
- [ ] Does this feature grant or check permissions? Are they least-privilege for the feature's scope?
- [ ] Can business logic be bypassed through parameter, state, or workflow manipulation?
- [ ] Are authorization checks consistent across UI, API, background jobs, and internal service calls?
> [!IMPORTANT]
> **Outcome / Identified actions / Issues:**
> - ...
---
### Severity Guidance for Findings
| Severity | Description | Action |
|----------|-------------|--------|
| **Critical** | Auth bypass, RCE, data leak at scale | Block release, fix immediately |
| **High** | IDOR, stored XSS, privilege escalation | Create Issues, fix before release |
| **Medium** | Missing rate-limit, verbose errors | Create Issues, plan for future fix |
| **Low** | Minor info disclosure in headers | Backlog |
---
### Completion Checklist
- [ ] All STRIDE sections reviewed (checked or marked N/A)
- [ ] Issues created and linked for any High/Critical findings
- [ ] Discussed with team if anything is High or Critical
