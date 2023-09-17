export class App {

    stack = []

    showPage(page) {
        if (this.currentPage != null) {
            this.currentPage.onDestroy()
        }
        this.currentPage = page
        this.currentPage.onCreate()
    }

    pushPage(page) {
        this.stack.push(page)
        this.showPage(page)
    }

    popPage() {
        console.log(this.stack)
        if (this.stack.length > 1) {
            this.stack.pop()
            this.showPage(this.stack[this.stack.length - 1])
        }
    }
}
