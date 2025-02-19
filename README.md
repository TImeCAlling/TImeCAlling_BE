# 타임콜링(TImeCAlling)


## Intro

<img width="1411" alt="스크린샷 2025-02-19 오후 5 31 56" src="https://github.com/user-attachments/assets/9af2d088-c634-41f2-b906-ffb9ba2929fb" />
<img width="1442" alt="스크린샷 2025-02-19 오후 5 37 36" src="https://github.com/user-attachments/assets/726d8c30-6d58-4524-86ec-e53ec9b3878e" />
<img width="1442" alt="스크린샷 2025-02-19 오후 5 37 49" src="https://github.com/user-attachments/assets/9328290e-2a5b-4434-9f7a-506c5cee8640" />
<img width="1442" alt="스크린샷 2025-02-19 오후 5 38 09" src="https://github.com/user-attachments/assets/48ba942e-8352-447c-9300-4ce205a11669" />
<img width="1442" alt="스크린샷 2025-02-19 오후 5 38 19" src="https://github.com/user-attachments/assets/632f593a-f8f3-4968-83e1-cff0ff45091d" />
<img width="1441" alt="스크린샷 2025-02-19 오후 5 38 32" src="https://github.com/user-attachments/assets/33846929-86b7-4b65-98a3-c1646b7fed57" />
<img width="1443" alt="스크린샷 2025-02-19 오후 5 38 43" src="https://github.com/user-attachments/assets/fe20cdaf-626e-4d80-b524-a93501b4525c" />

## 아키텍처

![KakaoTalk_Photo_2025-02-19-17-48-53](https://github.com/user-attachments/assets/2bbca004-7225-433a-836e-5958961325a0)

## 패키지 구조

```
src
├── main
│   ├── java
│   │   └── TImeCAlling
│   │       └── spring
│   │           ├── apiPayload
│   │           │   ├── code
│   │           │   │   └── status
│   │           │   └── exception
│   │           │       └── handler
│   │           ├── auth
│   │           │   ├── Handler
│   │           │   └── filter
│   │           ├── config
│   │           ├── converter
│   │           │   ├── alarmList
│   │           │   ├── schedule
│   │           │   └── user
│   │           ├── domain
│   │           │   ├── base
│   │           │   └── enums
│   │           ├── repository
│   │           │   ├── alarmList
│   │           │   ├── schedule
│   │           │   └── user
│   │           ├── service
│   │           │   ├── alarmList
│   │           │   ├── pushMessageNotification
│   │           │   ├── s3
│   │           │   ├── schedule
│   │           │   │   └── checklist
│   │           │   └── user
│   │           ├── validation
│   │           │   ├── annotation
│   │           │   └── validator
│   │           └── web
│   │               ├── controller
│   │               │   ├── alarmList
│   │               │   ├── pushMessageNotification
│   │               │   ├── schedule
│   │               │   └── user
│   │               └── dto
│   │                   ├── alarmList
│   │                   ├── checklist
│   │                   ├── fcm
│   │                   ├── pushMessageNotification
│   │                   ├── schedule
│   │                   └── user
│   └── resources
│       ├── firebase
│       ├── static
│       └── templates
```
