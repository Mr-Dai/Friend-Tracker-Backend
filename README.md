# WeTrack-Backend

[![Build Status](https://travis-ci.org/WeTrack/WeTrack-Backend.svg?branch=master)](https://travis-ci.org/WeTrack/WeTrack-Backend)

Java backend project for WeTrack, our HKUST CSIT 5510 course project.

## Test Run

Execute command:

```
gradlew appRun
```

The server should start to listen to requests on [http://localhost:8080/](http://localhost:8080/).

The project will open a MongoDB connection to `localhost:27017` anonymously and use its `wetrack` database. Make sure your MongoDB instance is correctly configured and running when you start the project.

## Future Task

- [x] Deploy version `0.1`.
- [ ] Enable STOMP protocol for the WebSocket service.
- [ ] Write test cases for the WebSocket service.
- [ ] Migrate from Jersey to SpringMVC.
- [ ] Implement cache-aware single-entity `GET` for all entities (new `createdAt` and `updatedAt` field).
- [ ] Implement file and image uploading.
- [ ] Implement friend invitation.
- [ ] Implement chat invitation.
- [ ] Implement permission framework (location-sharing, friend-adding...).
- [ ] Deploy version `0.2`.
- [ ] Refactor the service classes to add Spring Aspect for advanced logging and monitoring.
- [ ] Implement P2P Encryption.
- [ ] Deploy version `0.3` in `HTTPS` and `TLS`.
- [ ] Implement 3rd-party account sign up and sign in. (QQ, WeChat, Google)
