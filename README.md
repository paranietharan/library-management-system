# Library Management System — Backend

A Spring Boot REST API for a school library management application, developed as a second-year university group project. The system brings together member accounts, book lending and reservations, fines, complaints, and an article-sharing section for the library community.

This repository contains the Java backend. The companion [React frontend](https://github.com/paranietharan/library-management-system-web-application) provides member and librarian interfaces.

## Project ownership and contribution

[Paranietharan](https://github.com/paranietharan) is the **team leader**, was responsible for the **article section**, and now maintains the full codebase across both repositories.

The article module is the primary area of individual contribution highlighted here. Overall codebase maintenance reflects the team-lead role; the wider library system was developed collaboratively by the project team.

## Application overview

```text
React frontend (localhost:3000)
             |
             | HTTP / JSON and multipart uploads
             v
Spring Boot API (localhost:8080)
             |
             +---- Spring Data JPA ---- MySQL
             |
             +---- JavaMail ---------- SMTP service
```

The backend includes modules for:

- Member accounts, login, profiles, and email verification.
- Books, lending, reservations, and lending history.
- Fines and complaints.
- Articles, comments, ratings, and article search.
- Email notifications used by account and library workflows.

## Article module

The article section connects the React publishing and reading screens to persistent data in MySQL. Its implementation includes:

- Publishing articles with a title, body, author reference, and uploaded image.
- Listing articles, retrieving article details, and finding articles by author.
- Editing and deleting articles.
- Searching by title or body text.
- Adding, listing, and deleting comments.
- Adding or updating ratings, retrieving an article's aggregate rating, and retrieving a user's rating.
- Deleting related comments and ratings when an article is removed.
- Email notifications for article edits and deletions.

### Source guide

All paths below are relative to `src/main/java/com/alphacodes/librarymanagementsystem/`.

| Layer | Entry points |
| --- | --- |
| HTTP endpoints | [ArticleController](src/main/java/com/alphacodes/librarymanagementsystem/controller/ArticleController.java), [ArticleCommentController](src/main/java/com/alphacodes/librarymanagementsystem/controller/ArticleCommentController.java), [ArticleRatingController](src/main/java/com/alphacodes/librarymanagementsystem/controller/ArticleRatingController.java) |
| Business logic | [ArticleServiceImpl](src/main/java/com/alphacodes/librarymanagementsystem/service/impl/ArticleServiceImpl.java), [ArticleCommentServiceImpl](src/main/java/com/alphacodes/librarymanagementsystem/service/impl/ArticleCommentServiceImpl.java), [ArticleRatingServiceImpl](src/main/java/com/alphacodes/librarymanagementsystem/service/impl/ArticleRatingServiceImpl.java) |
| Persistence | [ArticleRepository](src/main/java/com/alphacodes/librarymanagementsystem/repository/ArticleRepository.java), [ArticleCommentRepository](src/main/java/com/alphacodes/librarymanagementsystem/repository/ArticleCommentRepository.java), [ArticleRatingRepository](src/main/java/com/alphacodes/librarymanagementsystem/repository/ArticleRatingRepository.java) |
| Data model | [Article](src/main/java/com/alphacodes/librarymanagementsystem/Model/Article.java), [ArticleComment](src/main/java/com/alphacodes/librarymanagementsystem/Model/ArticleComment.java), [ArticleRating](src/main/java/com/alphacodes/librarymanagementsystem/Model/ArticleRating.java) |

### Main article endpoints

Paths are relative to `http://localhost:8080`.

| Method | Path | Purpose |
| --- | --- | --- |
| GET | `/article/allArticles` | List article views |
| GET | `/article/view/{articleId}` | Retrieve an article view |
| GET | `/article/getByUserID/{userId}` | List an author's articles |
| POST | `/article/addArticle` | Publish using multipart form data |
| PUT | `/article/editArticle/{articleId}` | Edit using multipart form data |
| DELETE | `/article/{userId}/delete/{articleId}` | Delete an article |
| GET | `/article/search/heading/{heading}` | Search titles |
| GET | `/article/search/body/{body}` | Search article bodies |
| GET / POST | `/article/{articleId}/comment` | List or add comments |
| DELETE | `/article/comment/{articleCommentId}` | Delete a comment |
| GET / POST | `/article/{articleID}/rating` | Read aggregate rating or add/update a rating |
| GET | `/article/{articleID}/rating/{userId}` | Read a user's rating |

The multipart publish endpoint expects `title`, `body`, `authorId`, and `articleImg`. The edit endpoint accepts the same fields, with the image parameter optional. Use an existing user's ID for the author reference. JSON request shapes for comments and ratings are defined in the [DTO directory](src/main/java/com/alphacodes/librarymanagementsystem/DTO).

## Technology

- Java 17 and Spring Boot 3.2.2, as declared in [pom.xml](pom.xml).
- Spring Web and Spring Data JPA.
- MySQL and Hibernate.
- JWT libraries and BCrypt password hashing.
- Spring Boot Mail, Lombok, and OpenCSV.
- Maven Wrapper included in this repository.

## Run locally

### 1. Prerequisites and checkout

Install JDK 17 and MySQL, then clone the repository:

```bash
git clone https://github.com/paranietharan/library-management-system.git
cd library-management-system
```

### 2. Create the database

Run this in your local MySQL client:

```sql
CREATE DATABASE IF NOT EXISTS librarymanagementsystem;
```

The existing configuration uses Hibernate `ddl-auto=update` to update tables in that database.

### 3. Configure database and email access

Spring Boot reads defaults from [application.properties](src/main/resources/application.properties). Override local connection details with environment variables rather than committing credentials:

```bash
export SPRING_DATASOURCE_URL='jdbc:mysql://localhost:3306/librarymanagementsystem'
export SPRING_DATASOURCE_USERNAME='your_local_mysql_user'
export SPRING_DATASOURCE_PASSWORD='your_local_mysql_password'
```

For email verification and workflows that send notifications, configure a working SMTP account:

```bash
export SPRING_MAIL_HOST='your_smtp_host'
export SPRING_MAIL_PORT='587'
export SPRING_MAIL_USERNAME='your_smtp_username'
export SPRING_MAIL_PASSWORD='your_smtp_password'
```

The checked-in mail settings enable SMTP authentication and STARTTLS. Adapt those settings to your mail provider. Article edits and deletions send email, so include SMTP configuration when testing the complete article workflow.

### 4. Start the API

On macOS/Linux:

```bash
sh mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The default API address is `http://localhost:8080`. Start the [frontend](https://github.com/paranietharan/library-management-system-web-application#run-locally) separately on port 3000.

Browser access is configured in [WebConfig.java](src/main/java/com/alphacodes/librarymanagementsystem/configs/WebConfig.java). If cross-origin requests fail, check that the allowed origin matches the frontend's actual origin, including whether a trailing slash is present.

## Build and test commands

With MySQL and the required application configuration available:

```bash
sh mvnw test
sh mvnw package
```

The current test source contains a Spring application-context test. Dedicated article workflow tests are a future improvement; these commands do not imply complete feature coverage.

## Project structure

```text
src/main/java/com/alphacodes/librarymanagementsystem/
├── controller/       HTTP endpoints
├── service/          Service contracts and implementations
├── repository/       Spring Data repositories
├── Model/            Persistent entities
├── DTO/              Request and response objects
├── configs/          Application configuration
├── EmailService/     Email support
└── util/             Shared utilities
src/main/resources/   Runtime configuration
src/test/             Test source
```

## Suggested article demonstration

1. Start MySQL, the API, and the React frontend; configure SMTP for email-dependent actions.
2. Sign in with a member account and open the article section.
3. Publish an article with an image, then find it in the list and through search.
4. Open the article, add a comment, and submit a rating.
5. Edit and delete the article, checking the corresponding data and notification behavior.

This sequence highlights the article contribution across UI, API, service logic, and persistence. See the [frontend README](https://github.com/paranietharan/library-management-system-web-application) for the corresponding screens and routes.
