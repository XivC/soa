export class Page {

    constructor(app) {
        this.app = app
    }

    onCreate() {
        document.body.innerHTML = ""
    }

    onDestroy() {}
}