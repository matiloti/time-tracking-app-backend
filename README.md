# ⏱️ Time Tracking App Backend

## 🧩 What

Welcome to my app! 👋

This is the backend for my **time tracking application**, which allows you to track the time you invest in your projects.

Projects can be split into a set of **milestones**, and each milestone can be further divided into multiple **tasks**.  
This segmentation lets you register **fine-grained time records**, depending on the exact part of your project you are currently working on.

Later on, you’ll be able to take advantage of all the collected data to 📊:
- Visualize where you invest your time
- See which types of tasks consume more effort
- Track progress across projects and milestones
- Understand which days you work more or less
- And more!

In the future, the app will also include **team support** 👥, enabling teams to make better development estimations and planning decisions.

---

## 🎯 Why

This is my **personal project**. I’ve always wanted to build my own app, but I never set aside the time to do it.  
Now I’ve committed to it, so here I am! 🚀

I’m publishing **weekly journal videos on YouTube** documenting the progress of the project.  

You can watch them on my YouTube channel here:  
📺 https://www.youtube.com/channel/UC2CJ7iUwbCFI3JJAv4sSWEQ

---

## 🔌 Current Endpoints

| Method | URI                  | Description         |
|-------|----------------------|---------------------|
| `POST` | `/project`          | Create a project    |
| `GET`  | `/projects/findAll` | Fetch all projects  |

---

## 🛠️ Tech Stack

- 🧠 Kotlin
- 🌱 Spring Boot
- 🐘 PostgreSQL
- 🐳 Docker
- 📦 Testcontainers
- 🧪 JUnit 5
- 🎭 Mockito (Kotlin)
- 🤖 GitHub Actions (automated tests)

---

## ▶️ Running It

### 💻 Without Docker
```bash
./gradlew clean build
./gradlew bootRun
```

### 🐳 With Docker
```bash
./gradlew clean build
./gradlew bootJar
docker-compose up --build
```

## 🧪 Testing It
```bash
./gradlew test
```

## 🌐 CURL Examples

### Creating a Project
```bash
curl --location 'http://localhost:8080/project' \
--header 'Content-Type: application/json' \
--data '{
    "title": "Test1",
    "description": "Test test Test test Test test Test test",
    "categoryId": 1
}'
```

### Finding all projects
```bash
curl --location 'http://localhost:8080/projects/findAll'
```