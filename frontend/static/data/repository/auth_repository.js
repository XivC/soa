class AuthRepository {

    setUserId(userId) {
        this.userId = userId
    }

    getUserId() {
        return this.userId
    }
}

export let authRepository = new AuthRepository()
