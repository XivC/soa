import {Page} from "./page.js";

export class PageCollection extends Page {
    page = 0
    isFetching = false
    hasReachedEnd = false

    timerId = 0

    constructor(app) {
        super(app);
    }


    onCreate() {
        super.onCreate()

        document.body.innerHTML = '<button id="create-button">Create</button>\n' +
            '<div class="card-container" id="cardContainer"></div>\n' +
            '<div class="loading" id="loadingIndicator">\n' +
            '    <div class="spinner"></div>\n' +
            '</div>';
        this.cardContainer = document.getElementById("cardContainer");
        this.loadingIndicator = document.getElementById("loadingIndicator")
        document.getElementById("create-button").onclick = () => this.navigateToItem(null)
        let that = this
        function isElementInViewport(el) {
            const rect = el.getBoundingClientRect();
            return (
                rect.top >= 0 &&
                rect.left >= 0 &&
                rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
                rect.right <= (window.innerWidth || document.documentElement.clientWidth)
            );
        }
        this.timerId = setInterval(() => {
            if (isElementInViewport(that.loadingIndicator) && !that.isFetching && !that.hasReachedEnd) {
                that.fetchMoreElements()
            }
        }, 100)
    }

    onDestroy() {
        super.onDestroy()
        clearInterval(this.timerId)
    }

    reload() {
        this.page = 0
        this.isFetching = false
        this.hasReachedEnd = false
        this.cardContainer.innerHTML = ""
        this.loadingIndicator.style.display = 'block'
    }

    fetchMoreElements = () => {
        this.isFetching = true
        let pageWhenCalled = this.page
        let that = this
        this.fetchElements(pageWhenCalled, (items) => {
            if (that.page !== pageWhenCalled) return;
            items.forEach((item) => {
                const card = document.createElement("div");
                card.classList.add("card");
                card.innerHTML = this.cardInnerHtml(item)
                card.onclick = () => this.navigateToItem(item)
                that.cardContainer.appendChild(card);
            });
            if (items.length === 0) {
                this.loadingIndicator.style.display = 'none'
                this.hasReachedEnd = true
            }
            this.page += 1
            this.isFetching = false
        })
    }

    fetchElements(page, callback) {}

    cardInnerHtml(item) {}

    navigateToItem(item) {}
}
