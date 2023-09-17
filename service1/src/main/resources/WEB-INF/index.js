import {PageCollectionDragons} from "./view/pages/page_collection_dragons.js";

var currentPage = null

function showPage(page) {
    if (currentPage != null) {
        currentPage.onDestroy()
    }
    currentPage = page
    currentPage.onCreate()
}

showPage(new PageCollectionDragons())