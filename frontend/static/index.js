import {PageCollectionDragons} from "./view/pages/dragons/page_collection_dragons.js";
import {App} from './view/app.js'


class SOAApp extends App {

    constructor() {
        super();
        this.pushPage(new PageCollectionDragons(this))
    }
}

new SOAApp()
