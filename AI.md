# AI Tool Usage Log (iP)

## Tools used
- **ChatGPT (OpenAI)** — used for debugging help, explanations, design/OOP review, refactoring suggestions, and drafting documentation (JavaDoc/Markdown).

## How I used AI (general)
- Used AI to explain errors/concepts, propose refactors, and draft text/comments.
- I reviewed and adapted suggestions to my codebase and verified by compiling/running tests/Gradle where applicable.

---

## Week 2 (Levels 0–6)
**AI usage**
- Helped diagnose **syntax / compiler errors** and interpret error messages.
- Asked AI to review my **class diagram / OOP structure** (whether responsibilities are reasonable, whether design is “OOP enough”, and suggestions to make classes cleaner).
- Asked AI to explain parts of the iP instructions I didn’t understand (e.g., text UI behaviour/requirements).
- Asked AI to review some code sections for **cleanliness** (duplication, method length, naming).

**Observations**
- Good for quickly spotting obvious issues and suggesting cleaner structure.
- Needed manual judgment to keep the design consistent with my own code.

---

## Week 3 (PR to upstream + branching workflow + Level-7 Save + Level-8 Dates/Times + Gradle/JUnit/Jar + JavaDoc/CodingStandard/Find)
**AI usage**
- After doing the Git tutorial, asked AI to produce a **compiled checklist of Git commands** for the required workflow (branch → merge commit (no-ff) → tag → push).
- **Storage (Level-7 Save)**: I had not learned file I/O/storage before, so I used AI to explain how to structure basic save/load logic and common pitfalls (paths, missing file, etc.), then implemented and tested it.
- **Dates/Times (Level-8)**: used AI to explain handling/parsing/formatting approaches (then adapted to what the level required).
- **JavaDoc increment**: I wrote one JavaDoc in my style and asked AI to draft similar JavaDoc for other classes/methods; I reviewed and edited to match actual behavior.
- **Coding standard / OOP cleanup**: asked AI to point out methods that were too long / doing too many things, then refactored accordingly.

**Observations**
- AI was most helpful for repetitive drafting (JavaDoc) and quick refactor ideas.
- I still verified everything by reading the code and running it.

---

## Week 4 (GFMD PR description + peer PR reviews + A-CheckStyle + Level-10 GUI + A-Varargs)
**AI usage**
- **GUI (Level-10)**: used AI to explain JavaFX structure and how to implement the GUI step-by-step.
    - Also used AI to understand **FXML** and controller wiring (I cross-checked with course JavaFX tutorial/build.gradle guidance to avoid incompatible setup).
- **Varargs (A-Varargs)**: asked AI for a clear example and when it’s appropriate to use.

**Observations**
- AI helped me understand JavaFX/FXML concepts faster, but I followed the course-provided Gradle/JavaFX instructions to avoid “Frankenstein” configs.

---

## Week 5 (new JAR + full commit messages + PR-based increments A-Assertions/A-CodeQuality/A-Streams + A-CI + extension)
**AI usage**
- Used AI to suggest **code quality improvements** (readability, smaller methods, clearer naming) before/while doing CodeQuality-related work.
- Used AI to help draft some text for **full commit message bodies** (then edited to match what I actually changed).
- Used AI to explain CI-related errors/messages when setting up CI.

**Observations**
- Helpful for code review style suggestions; still needed manual decision-making.

---

## Week 6 (A-AiAssisted + optional enhancements + finalisation + product website)

**AI usage**
- Continued using ChatGPT to improve productivity across development and finalisation tasks.
- Used ChatGPT to:
  - Debug CI/Checkstyle issues (e.g., interpreting error logs and fixing formatting violations).
  - Implement and refine the **A-Personality** feature (greetings + pickup line interaction) and improve code structure/readability.
  - Clarify JavaFX implementation details (e.g., setting `Stage` title so “Valencia” shows in the window title bar).
  - Improve UI presentation for the product screenshot (e.g., reducing avatar size in chat bubbles to fit more content).
  - Draft and polish documentation (User Guide in `docs/README.md`, PR/Markdown formatting, and this `AI.md` content).
- This `AI.md` serves as my record of AI-assisted work, as required.

**Observations**
- Helpful for rewriting English paragraphs so I don’t need to keep re-checking grammar.
- Helped with Markdown formatting to make docs cleaner and more consistent.
- Useful for quickly pinpointing fixes from error messages (especially CI failures), but still required manual verification and adjustments to match my codebase.

---

## Overall AI Overview for iP
**What worked well**
- Faster debugging (especially when interpreting errors).
- Quick refactoring ideas and alternative designs to consider.
- Faster drafting of documentation (JavaDoc/Markdown).

**What didn’t work well**
- Sometimes suggests changes that don’t match my actual code structure (e.g., different class/method names), so I still needed to adapt and verify manually.

**Estimated time saved (rough)**
- ~2–4 hours per week.

